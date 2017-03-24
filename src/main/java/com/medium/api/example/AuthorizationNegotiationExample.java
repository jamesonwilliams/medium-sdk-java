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
import com.medium.api.MediumClient;

import com.medium.api.auth.AccessProvider;
import com.medium.api.auth.AccessToken;
import com.medium.api.auth.Scope;

import com.medium.api.config.ConfigFile;
import com.medium.api.config.ConfigFileReader;

import com.medium.api.model.Contributor;
import com.medium.api.model.Publication;
import com.medium.api.model.User;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Demonstrates use of the SDK with a full OAuth2 authorization flow.
 *
 * This must be run at the public endpoint where the redirect uri is
 * reachable.
 *
 * This program:
 *
 *   - Opens a browser to prompt the user to allow access
 *
 *   - Accepts an authorization code at the configured redirectUri in
 *     the config file
 *
 *   - Uses the authorization code to request a bearer token
 *
 *   - Created a Medium client using that token
 *
 *   - Invokes the simple GetUser API.
 */
public class AuthorizationNegotiationExample {

    /**
     * The location of the local config file.
     */
    private static final String CONFIG_FILE_PATH = "./medium-config.json";

    /**
     * The scopes that will be requested.
     */
    private static final Collection<Scope> REQUESTED_ACCESS_SCOPES =
        Arrays.asList(
            Scope.BASIC_PROFILE,
            Scope.LIST_PUBLICATIONS,
            Scope.PUBLISH_POST
        );

    /**
     * Invokes the test program.
     *
     * @param args command line arguments; not used
     *
     * @throws IOException if we can't navigate the user to the
     *                     authorization url
     */
    public static void main(final String[] args) throws IOException {

        listenForAccessToken(new AccessProvider.Observer() {

            // Called when Access Provider has obtained a token
            @Override
            public void onAccessGranted(final AccessToken token) {
                System.out.println(
                    "Sweet, we got a token: " + token.getAccessToken()
                );
                useApiWithToken(token);
            }

            // Called if Access Provider can't get a token for whatever reason
            @Override
            public void onAccessError() {
                System.err.println("Oh no. What ever could be wrong?");
            }
        });
    }

    /**
     * The much simpler case of using the API with an access token.
     *
     * Uses the access token to get information about the current user.
     *
     * @param token a Medium.com access token, containing the literal
     *              token as a string via token.getAccessToken().
     */
    private static void useApiWithToken(final AccessToken token) {

        // Use the access token to create a Medium Client
        final Medium medium = new MediumClient(token.getAccessToken());

        // Get information about the user
        final User user = medium.getUser();
        System.out.println("Hello, " + user);

        // Get a list of publications associated with the user
        final List<Publication> publications =
            medium.listPublications(user.getId());

        // For each, print details about it
        for (Publication publication : publications) {
            System.out.println(String.format("%s: %s",
                publication.getName(),
                publication.getDescription()
            ));

            System.out.println("Contributors: ");

            // Get a list of the contributors to a publication
            final List<Contributor> contributors =
                medium.listContributors(publication.getId());

            // Show those details
            for (Contributor contributor : contributors) {
                System.out.println(String.format("%s: %s",
                    contributor.getUserId(), contributor.getRole()
                ));
            }
        }
    }

    /**
     * Starts a local HTTP server to listen to an authorization code,
     * then opens the authorization url in a browser URL so that the
     * user can approve.
     *
     * @param observer the observer of access token events
     * @throws IOException
     *         If the config file cannot be read or if the browser
     *         window cannot be opened
     */
    private static void listenForAccessToken(final AccessProvider.Observer observer)
            throws IOException {

        // Load SDK configuration from a file on disk
        ConfigFile config = new ConfigFileReader(CONFIG_FILE_PATH).read();

        // Create an authentication client based on the API credentials
        Medium authClient = new MediumClient(config.getCredentials());

        // Startup a local HTTP server to listen for GET requests on the
        // callback URI. It will get the Authorization code.
        AccessProvider accessProvider = new LocalHttpAccessProvider(
            authClient,
            config.getRedirectUri(),
            observer
        );

        // Create client state cookie
        final String clientState = UUID.randomUUID().toString();

        // Redirect the user to go to the login page to grant access to
        // our application
        openUrlInBrowser(authClient.getAuthorizationUrl(
            clientState, config.getRedirectUri(), REQUESTED_ACCESS_SCOPES
        ));

        // Tell the access provider to expect an authorization code to
        // show up.
        accessProvider.listenForAuthorizationCodes();
    }

    /**
     * On Mac OS X, opens the supplied link in a browser.
     *
     * @param url the url of the link to open.
     *
     * @throws IOException
     *         If the runtime cannot execute the command to open the URL
     *         in the browser
     */
    private static void openUrlInBrowser(final String url) throws IOException {
        String[] args = new String[] {
            "osascript",
            "-e",
            "open location \"" + url + "\""
        };
        Runtime.getRuntime().exec(args);
    }
}

