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
 * An AuthorizationCodeRequest is actually a user-visible hypertex
 * link. These are the parameters that make up an authorization url.
 */
public class AuthorizationCodeRequest {

    /**
     * The client access id.
     */
    private final String clientId;

    /**
     * The scope of access that is being requested.
     *
     * A collection of {@link Scope}s, as a comma-separated and joined string.
     */
    private final String scope;

    /**
     * Arbitrary implementation cookie for a auth code request.
     */
    private final String state;

    /**
     * The response type.
     */
    private final String responseType;

    /**
     * The redirect URI.
     */
    private final String redirectUri;

    /**
     * Constructs an AuthorizationCodeRequest from the attributes
     * specified in the builder instance.
     *
     * @param builder the builder from which attributes will be plucked.
     */
    private AuthorizationCodeRequest(final Builder builder) {
        this.clientId = builder.clientId;
        this.scope = builder.scope;
        this.state = builder.state;
        this.responseType = builder.responseType;
        this.redirectUri = builder.redirectUri;
    }

    /**
     * Builder fascilitates the construction of a new instance of
     * AuthorizationCodeRequest.
     */
    public static class Builder {
        private String clientId;
        private String scope;
        private String state;
        private String responseType;
        private String redirectUri;

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
         * Sets the scope of access that is being requested.
         *
         * @param scope the scope of access being requested, as a string
         *              containing a comma separated list of the string
         *              values of {@link Scope}s.
         *
         * @return the updated instance of the current builder
         */
        public Builder withScope(final String scope) {
            this.scope = scope;
            return this;
        }

        /**
         * Sets the state, an arbitrary text field left for the
         * implementer to user as a tracking cookie.
         *
         * @param state the session state
         *
         * @return the updated instance of the current builder
         */
        public Builder withState(final String state) {
            this.state = state;
            return this;
        }

        /**
         * Sets the response type. Should be the literal "code".
         *
         * @param responseType the response type
         *
         * @return the updated instance of the current builder
         */
        public Builder withResponseType(final String responseType) {
            this.responseType = responseType;
            return this;
        }

        /**
         * Sets the URL to which the user will be redirected upon
         * logging in.
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
         * Prepares a new instance of AuthorizationCodeReques
         * configured with all of the provided values supplied to the
         * builder.
         *
         * @return a new instance of AuthorizationCodeReques
         */
        public AuthorizationCodeRequest build() {
            return new AuthorizationCodeRequest(this);
        }
    }
}
