package com.medium.api.auth;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import com.medium.api.config.Endpoint;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Arrays;
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
     * The API credentials to use when generating authorization
     * requests.
     */
    private final Credentials credentials;

    /**
     * The URI which the OAuth2 server calls when an authorization code
     * is generated.
     */
    private final String redirectUri;

    /**
     * Constructs a new AuthorizationHandler.
     *
     * @param credentials the credentials to use while authenticating.
     * @param redirectUri the URI two which the OAuth2 server will
     *                    callback when an authorization code is
     *                    generated
     */
    public AuthorizationHandler(
            final Credentials credentials, final String redirectUri) {

        this.credentials = credentials;
        this.redirectUri = redirectUri;
    }

    /**
     * Gets the authorization URL.
     *
     * @return the authorization URL.
     */
    public String getAuthorizationUrl() {
        return new AuthorizationCodeRequestBuilder()
            .withEndpoint(Endpoint.AUTHORIZE_BASE)
            .withClientId(credentials.getClientId())
            .withScopes(Arrays.asList(Scope.BASIC_PROFILE, Scope.PUBLISH_POST))
            .withState("whatever")
            .withRedirectUri(redirectUri)
            .asUri();
    }

    /**
     * Run the server which will listen for the callback post from the
     * OAuth endpoint.
     */
    public void run() {

        // Set headers common to every Connect API reques
        Unirest.setDefaultHeader("Authorization", "");
        Unirest.setDefaultHeader("Content-Type", "application/json");
        Unirest.setDefaultHeader("Accept", "application/json");

        try {
            int portNumber = 9000;
            HttpServer server = HttpServer.create(new InetSocketAddress(portNumber), 0);
            server.createContext("/Callback", new CallbackHandler());
            server.createContext("/", new AuthorizeHandler());
            server.setExecutor(null);
            server.start();
            System.out.println("Listening on port " + portNumber);
        } catch (IOException e) {
            System.out.println("Server startup failed. Exiting.");
            System.exit(1);
        }
    }

    /**
     * AuthroizeHandler needs to be documented.
     */
    class AuthorizeHandler implements HttpHandler {

        /**
         * Handles an HTTP exchange.
         *
         * @param t the exhange to handle
         *
         * @throws IOException
         *        If we can't interact with the exchange
         */
        public void handle(HttpExchange t) throws IOException {

            // Reject non-GET requests
            if (!t.getRequestMethod().equals("GET")) {
                t.sendResponseHeaders(405, 0);
                t.getResponseBody().close();
            }

            final String authorizationUrl = getAuthorizationUrl();

            final byte[] out = (String.format("<a href=\"%s\">Click here</a> ", authorizationUrl)
                    + "to authorize the application.").getBytes("UTF-8");
            t.sendResponseHeaders(200, out.length);
            t.getResponseBody().write(out);
            t.getResponseBody().close();
        }
    }

    /**
     * Serves requests from Square to your application's redirect URL
     * Note that you need to set your application's Redirect URL to
     * http://localhost:9000/Callback from your application dashboard
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

            // Provide the code in a request to the Obtain Token endpoin
            JSONObject oauthRequestBody;

            try {
                oauthRequestBody = new JSONObject();
                oauthRequestBody.put("client_id", credentials.getClientId());
                oauthRequestBody.put("client_secret", credentials.getClientSecret());
                oauthRequestBody.put("code", authorizationCode);
                oauthRequestBody.put("grant_type", "authorization_code");
                oauthRequestBody.put("redirect_uri", redirectUri);
            } catch (JSONException jsonE) {
                throw new RuntimeException(jsonE);
            }

            JsonNode bodyNode = new JsonNode(oauthRequestBody.toString());

            HttpResponse<JsonNode> response;
            try {
                String tokenEndpoint = Endpoint.API_BASE + "/tokens";
                response = Unirest.post(tokenEndpoint).body(bodyNode).asJson();
            } catch (UnirestException e) {
                System.out.println("Code exchange failed!");
                t.sendResponseHeaders(405, 0);
                t.getResponseBody().close();
                return;
            }

            if (response != null) {
                JSONObject responseBody = response.getBody().getObject();
                if (responseBody.has("access_token")) {

                    // Here, instead of printing the access token, your
                    // application server should store i securely and
                    // use it in subsequent requests to the Connect API
                    // on behalf of the merchant.
                    try {
                        System.out.println("Access token: "
                            + responseBody.getString("access_token"));
                        System.out.println("Authorization succeeded!");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    t.sendResponseHeaders(200, 0);
                    t.getResponseBody().close();
                }

            } else {
                // The response from the Obtain Token endpoint did not
                // include an access token.  Something went wrong.
                System.out.println("Code exchange failed!");
                t.sendResponseHeaders(200, 0);
                t.getResponseBody().close();
            }

            t.sendResponseHeaders(200, 0);
            t.getResponseBody().close();
        }
    }
}
