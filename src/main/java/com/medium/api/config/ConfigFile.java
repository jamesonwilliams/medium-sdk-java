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
package com.medium.api.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.medium.api.auth.Credentials;

/**
 * Defines the model for a configuration file.
 *
 * The config file contains credentials as well as other
 * user-configurable run-time settings.
 */
public class ConfigFile {

    /**
     * Oauth2 access key and secret.
     */
    private final Credentials credentials;

    /**
     * The URL where we will send the user after they have completed the
     * login dialog.
     */
    private final String redirectUri;

    /**
     * Constructs a new representation of a ConfigFile.
     *
     * @param credentials the credentials represented in the file
     * @param redirectUri the callback uri accessed when a user
     *                    completes a login dialog.
     */
    @JsonCreator
    public ConfigFile(
            @JsonProperty("credentials") final Credentials credentials,
            @JsonProperty("redirectUri") final String redirectUri) {

        this.credentials = credentials;
        this.redirectUri = redirectUri;
    }

    /**
     * Gets the value of the the credentials field.
     *
     * @return the value of the credentials field
     */
    public Credentials getCredentials() {
        return credentials;
    }

    /**
     * Gets the value of the redirect uri field.
     *
     * @return the value of the redirect uri field.
     */
    public String getRedirectUri() {
        return redirectUri;
    }
}

