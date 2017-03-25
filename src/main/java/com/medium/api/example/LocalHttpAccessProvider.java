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

package com.medium.api.example;

import com.medium.api.Medium;

import com.medium.api.auth.AccessProvider;
import com.medium.api.auth.AccessToken;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * LocalHttpAccessProvider listens on the port specified in the
 * redirectUri for an authorization code from Medium.
 *
 * Upon obtaining the authorization code, the LocalHttpAccessProvider
 * will invoke the Medium API to exchange it for a bearer token.
 *
 * Inspriation for this (although it is now largely rewriteen) was taken
 * from Square's Connect API Examples project:
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
     * The HTTP server listening for auth codes.
     */
    private HttpServer server;

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
            server = HttpServer.create(new InetSocketAddress(portNumber), 0);
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
     * Notifies the observer that we have obtained a valid access token
     * (or not.)
     *
     * @param token the token that is available; if none, provide null
     */
    private void notifyObserver(final AccessToken token) {
        if (null != token && null != token.getAccessToken()) {
            observer.onAccessGranted(token);
        } else {
            observer.onAccessError();
        }
    }

    /**
     * Extracts the authorization code parameter value from a map of
     * query parameters.
     *
     * @param uri the uri from which to extract an auth code
     *
     * @return the auth code if found; null, otherwise.
     */
    private String extractAuthorizationCode(final URI uri) {
        final Map<String, String> parameters =
            getQueryParameters(uri.getQuery());

        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            if (parameter.getKey().equals("code")) {
                return parameter.getValue();
            }
        }

        return null;
    }

    /**
     * Gets the parameters inside a query string, as a map.
     *
     * @param query the query string portion of an URL
     *
     * @return the parameters in the query as a name value map
     */
    private static Map<String, String> getQueryParameters(final String query) {
        final String[] parameters = query.split("&");
        final Map<String, String> map = new HashMap<String, String>();

        for (String parameter : parameters) {
            map.put(parameter.split("=")[0], parameter.split("=")[1]);
        }

        return map;
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
         * @param exchange the http exchange to handle
         *
         * @throws IOException
         *         If we can't interact with the exchange
         */
        public void handle(HttpExchange exchange) throws IOException {

            System.out.println("Request received: "
                + exchange.getRequestURI().toString());

            if (!exchange.getRequestMethod().equals("GET")) {
                exchange.sendResponseHeaders(405, 0);
                exchange.getResponseBody().close();
            }

            // Extract the returned authorization code from the URL
            final String authorizationCode =
                extractAuthorizationCode(exchange.getRequestURI());

            if (authorizationCode == null) {
                // The request to the Redirect URL did not include an
                // authorization code.  Something went wrong.
                notifyObserver(null);
                exchange.sendResponseHeaders(200, 0); // ... 200?
                exchange.getResponseBody().close();
                server.stop(0);
                return;
            }

            notifyObserver(medium.exchangeAuthorizationCode(
                authorizationCode, redirectUri.toString()
            ));

            exchange.sendResponseHeaders(200, 0);
            exchange.getResponseBody().close();

            server.stop(0);
        }
    }
}

