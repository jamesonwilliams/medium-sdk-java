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
 * A RefreshTokenRequest is made to the tokens endpoint after having
 * already have received an access token, to "refresh" it (and so get a
 * new one.)
 */
public class RefreshTokenRequest extends TokenRequest {

    /**
     * The grant type must be "refresh_token" when requesting a refresh
     * token.
     */
    public static final String DEFAULT_GRANT_TYPE = "refresh_token";

    /**
     * The long-lived refresh token that will be used to obtain another
     * access token.
     */
    private final String refreshToken;

    /**
     * Constructs a new RefreshTokenRequest.
     *
     * @param builder the builder from which the request attributes will
     *                be obtained
     */
    private RefreshTokenRequest(final Builder builder) {
        super(builder);
        this.refreshToken = builder.refreshToken;
    }

    /**
     * Gets the refresh token that is sent to the OAuth2 endpoint to
     * request a new access token.
     *
     * @return the refresh token
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Builder fasciliates the contrustion of a RefreshTokenRequest.
     */
    public static class Builder extends TokenRequest.Builder<Builder> {

        private String refreshToken;

        public Builder() {
            super(DEFAULT_GRANT_TYPE);
        }

        /**
         * Sets the refresh token.
         *
         * @param refreshToken the refresh token to use.
         *
         * @return an instance of the updated builder
         */
        public Builder withRefreshToken(final String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        /**
         * Builds a new RefreshTokenRequest.
         *
         * @return a new instance of RefreshTokenRequest.
         */
        public RefreshTokenRequest build() {
            return new RefreshTokenRequest(this);
        }
    }
}
