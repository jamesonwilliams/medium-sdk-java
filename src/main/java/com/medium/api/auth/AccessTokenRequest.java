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
package com.medium.api.model;

/**
 * An AccessTokenRequest is made to the tokens endpoint after having
 * obtained a short-lived authorization code by means of the OAuth
 * server having posted to the callback URI.
 */
public class AccessTokenRequest {

    /**
     * The short term authorization code obtained via browser
     * authorization.
     */
    private final String code;

    /**
     * Client access key.
     */
    private final String clientId;

    /**
     * Client access secret.
     */
    private final String clientSecret;

    /**
     * The grant type.
     */
    private final String grantType;

    /**
     * The URL where we the user was sent after they completed the login
     * dialog.
     */
    private final String redirectUri;

    /**
     * Constructs a new AccessTokenRequest.
     *
     * @param builder the builder from which the request attributes will
     *                be obtained.
     */
    private AccessTokenRequest(final Builder builder) {
        this.code = builder.code;
        this.clientId = builder.clientId;
        this.clientSecret = builder.clientSecret;
        this.grantType = builder.grantType;
        this.redirectUri = builder.redirectUri;
    }

    /**
     * Gets the short-term authorization code obtained at login.
     *
     * @return the authorization code
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets the client aceess key.
     *
     * @return the client id
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Gets the client access secret.
     *
     * @return the client secre
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * Gets the grant type.
     *
     * @return the grant type
     */
    public String getGrantType() {
        return grantType;
    }

    /**
     * Gets the URL where the user was sent after completing the
     * login dialog.
     *
     * @return the redirect uri
     */
    public String getRedirectUri() {
        return redirectUri;
    }

    /**
     * Builder fascilitates the construction of an
     * AccessTokenRequest.
     */
    public static class Builder {

        private String code;
        private String clientId;
        private String clientSecret;
        private String grantType;
        private String redirectUri;

        /**
         * Sets the authorization code.
         *
         * @param code the authorization code to se
         *
         * @return the updated instance of the current builder
         */
        public Builder withCode(final String code) {
            this.code = code;
            return this;
        }

        /**
         * Sets the client access key/id.
         *
         * @param clientId the client id
         *
         * @return the updated instance of the current builder
         */
        public Builder withClientId(final String clientId) {
            this.clientId = clientId;
            return this;
        }

        /**
         * Sets the client secret key/id.
         *
         * @param clientSecret the client secre
         *
         * @return the updated instance of the current builder
         */
        public Builder withClientSecret(final String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        /**
         * Sets the grant type.
         *
         * @param grantType the grant type to se
         *
         * @return the updated instance of the current builder
         */
        public Builder withGrantType(final String grantType) {
            this.grantType = grantType;
            return this;
        }

        /**
         * Sets the redirect URL, where the user was sent after logging
         * in.
         *
         * @param redirectUri the redirect uri
         *
         * @return the updated instance of the current builder
         */
        public Builder withRedirectUri(final String redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }

        /**
         * Builds the current configuration into a new instance of
         * AccessTokenRequest.
         *
         * @return a new instance of AccessTokenRequest, with any
         *         requested attributes configured.
         */
        public AccessTokenRequest build() {
            return new AccessTokenRequest(this);
        }
    }
}

