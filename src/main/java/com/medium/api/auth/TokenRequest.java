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
 * Represents a client request for a new token.
 */
public abstract class TokenRequest {

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
     * Constructs a new TokenRequest.
     *
     * @param builder the builder from which to obtain field values
     */
    protected TokenRequest(final Builder builder) {
        this.clientId = builder.clientId;
        this.clientSecret = builder.clientSecret;
        this.grantType = builder.grantType;
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
     * Fascilitates the construction of a new TokenRequest.
     *
     * @param <T> The type of builder
     */
    public abstract static class Builder<T extends Builder<T>> {

        private String clientId;
        private String clientSecret;
        private String grantType;

        public Builder(final String grantType) {
            this.grantType = grantType;
        }

        /**
         * Sets the client access key/id.
         *
         * @param clientId the client id
         *
         * @return the updated instance of the current builder
         */
        @SuppressWarnings("unchecked")
        public T withClientId(final String clientId) {
            this.clientId = clientId;
            return (T) this;
        }

        /**
         * Sets the client secret key/id.
         *
         * @param clientSecret the client secre
         *
         * @return the updated instance of the current builder
         */
        @SuppressWarnings("unchecked")
        public T withClientSecret(final String clientSecret) {
            this.clientSecret = clientSecret;
            return (T) this;
        }

        /**
         * Provides a method to build a TokenRequest.
         *
         * @return a new instance of a TokenRequest
         */
        public abstract TokenRequest build();
    }
}

