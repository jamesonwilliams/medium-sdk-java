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
package com.medium.api;

import com.medium.api.auth.AuthorizationHandler;
import com.medium.api.config.ConfigFile;
import com.medium.api.config.ConfigFileReader;

import java.io.IOException;

/**
 * Demonstrates the SDK.
 */
public class DemoApp {

    /**
     * The location of the local config file.
     */
    private static final String CONFIG_FILE_PATH = "./medium-config.json";

    /**
     * Invokes the test program.
     *
     * @param args command line arguments; not used
     *
     * @throws IOException if we can't navigate the user to the
     *                     authorization url
     */
    public static void main(final String[] args) throws IOException {
        ConfigFile config = new ConfigFileReader(CONFIG_FILE_PATH).read();

        AuthorizationHandler handler =
            new AuthorizationHandler(config.getCredentials(), config.getRedirectUri());

        openUrlInBrowser(handler.getAuthorizationUrl());

        handler.run();
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

