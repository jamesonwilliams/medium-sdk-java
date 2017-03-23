/*
 * Copyright 2017 nosemaj.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.medium.api.auth;

import com.medium.api.Medium;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * LocalHttpAccessProvider listens on the port specified in the
 * redirectUri for an authorization code from Medium.
 *
 * Upon obtaining the authorization code, the LocalHttpAccessProvider
 * will invoke the Medium API to exchange it for a bearer token.
 *
 * This is adapted (significantly) from Square's Connect API Examples
 * project:
 *
 * https://github.com/square/connect-api-examples/blob/master/connect-examples/oauth/java/src/main/java/com/squareup/oauthexample/OAuthHandler.java
 */
public class LocalHttpAccessProvider implements AccessProvider {

    /**
     * The Medium API, so we can request an access token.
     */
    private final Medium medium;

    /**
     * The URI which the OAuth2 server calls when an authorization code
     * is generated.
     */
    private final URI redirectUri;

    /**
     * The client of the authorization provider who will receive a
     * callback when an access token becomes avaiable (or on error).
     */
    private Observer observer;

    /**
     * Constructs a new LocalHttpAccessProvider.
     *
     * @param medium the medium client
     * @param redirectUri the URI two which the OAuth2 server will
     *                    callback when an authorization code is
     *                    generated, as a string
     * @param observer the observer who will receive callbacks when an
     *                 access token becomes available (or if there is an
     *                 error in doing so.)
     */
    public LocalHttpAccessProvider(
            final Medium medium,
            final String redirectUri,
            final AccessProvider.Observer observer) {
        this.medium = medium;
        this.redirectUri = getUriFromString(redirectUri);
        this.observer = observer;
    }

    /**
     * Run the server which will listen for the callback post from the
     * OAuth endpoint.
     */
    @Override
    public void listenForAuthorizationCodes() {
        try {
            int portNumber = redirectUri.getPort();
            HttpServer server = HttpServer.create(new InetSocketAddress(portNumber), 0);
            server.createContext(redirectUri.getPath(), new CallbackHandler());
            server.setExecutor(null);
            server.start();
            System.out.println("Listening on port " + portNumber);
        } catch (IOException e) {
            System.out.println("Server startup failed. Exiting.");
            System.exit(1);
        }
    }

    /**
     * Gets a URI object from a string.
     *
     * If the string cannot be parsed, returns null.
     *
     * @param string the string to parse for a URI.
     *
     * @return the URI, or null
     */
    private URI getUriFromString(final String string) {
        try {
            return new URI(string);
        } catch (URISyntaxException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Serves requests from Square to your application's redirect URL
     * Note that you need to set your application's Redirect URL to
     * the one you set in the application dashboard.
     */
    class CallbackHandler implements HttpHandler {

        /**
         * Handles an HTTP exchange.
         *
         * TODO: clean this up more.
         *
         * @param t the http exchange to handle
         *
         * @throws IOException
         *         If we can't interact with the exchange
         */
        public void handle(HttpExchange t) throws IOException {

            System.out.println("Request received: " + t.getRequestURI().toString());

            if (!t.getRequestMethod().equals("GET")) {
                t.sendResponseHeaders(405, 0);
                t.getResponseBody().close();
            }

            // Extract the returned authorization code from the URL
            URI requestUri = t.getRequestURI();
            List<NameValuePair> queryParameters =
                URLEncodedUtils.parse(requestUri, "UTF-8");
            String authorizationCode = null;
            for (NameValuePair param : queryParameters) {
                if (param.getName().equals("code")) {
                    authorizationCode = param.getValue();
                    break;
                }
            }

            if (authorizationCode == null) {
                // The request to the Redirect URL did not include an
                // authorization code.  Something went wrong.
                observer.onAccessError();
                t.sendResponseHeaders(200, 0); // ... 200?
                t.getResponseBody().close();
                return;
            }

            AccessToken token =
                medium.exchangeAuthorizationCode(authorizationCode, redirectUri.toString());

            t.sendResponseHeaders(200, 0);
            t.getResponseBody().close();

            if (null != token && null != token.getAccessToken()) {
                observer.onAccessGranted(token);
            }
        }
    }
}

