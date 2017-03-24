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

/**
 * An AccessTokenRequest is made to the tokens endpoint after having
 * obtained a short-lived authorization code by means of the OAuth
 * server having posted to the callback URI.
 */
public class AccessTokenRequest extends TokenRequest {

    /**
     * Currently grantType is always this value.
     */
    public static final String DEFAULT_GRANT_TYPE = "authorization_code";

    /**
     * The short term authorization code obtained via browser
     * authorization.
     */
    private final String code;

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
        super(builder);
        this.code = builder.code;
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
    public static class Builder extends TokenRequest.Builder<Builder> {

        private String code;
        private String redirectUri;

        /**
         * Constructs a new instance of Builder.
         */
        public Builder() {
            super(DEFAULT_GRANT_TYPE);
        }

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
        @Override
        public AccessTokenRequest build() {
            return new AccessTokenRequest(this);
        }
    }
}

