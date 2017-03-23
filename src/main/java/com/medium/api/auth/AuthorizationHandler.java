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
 * AuthorizationHandler.
 *
 * This is adapted from Square's Connect API Examples project:
 *
 * https://github.com/square/connect-api-examples/blob/master/connect-examples/oauth/java/src/main/java/com/squareup/oauthexample/OAuthHandler.java
 */
public class AuthorizationHandler {

    /**
     * The Medium API, so we can request an access token.
     */
    private final Medium medium;

    /**
     * The URI which the OAuth2 server calls when an authorization code
     * is generated.
     */
    private final URI  redirectUri;

    /**
     * Constructs a new AuthorizationHandler.
     *
     * @param medium the medium client
     * @param redirectUri the URI two which the OAuth2 server will
     *                    callback when an authorization code is
     *                    generated, as a string
     */
    public AuthorizationHandler(final Medium medium, final String redirectUri) {
        this.medium = medium;
        this.redirectUri = getUriFromString(redirectUri);
    }

    /**
     * Run the server which will listen for the callback post from the
     * OAuth endpoint.
     */
    public void run() {
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
         * @param t the http exchange to handle
         *
         * @throws IOException
         *         If we can't interact with the exchange
         */
        public void handle(HttpExchange t) throws IOException {

            System.out.println("Request received");

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
                // The request to the Redirect URL did not include an authorization code.
                // Something went wrong.
                System.out.println("Authorization failed!");
                t.sendResponseHeaders(200, 0);
                t.getResponseBody().close();
                return;
            }

            AccessToken token =
                medium.exchangeAuthorizationCode(authorizationCode, redirectUri.toString());

            if (null != token && null != token.getAccessToken()) {
                System.out.println("Access token: " + token.getAccessToken());
                System.out.println("Authorization succeeded!");
            }

            t.sendResponseHeaders(200, 0);
            t.getResponseBody().close();
        }
    }
}
