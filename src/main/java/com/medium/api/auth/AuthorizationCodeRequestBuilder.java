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

import com.medium.api.util.StringUtils;

import java.util.Collection;

/**
 * AuthorizationCodeRequestBuilder is a utility to generate an
 * authorization code request uri, ie
 * "http://medium.com/m/authorize?param=value...".
 */
public class AuthorizationCodeRequestBuilder {

    /**
     * The responseType field currently only supports this one value.
     */
    public static final String DEFAULT_RESPONSE_TYPE = "code";

    /**
     * The authorization endpoint.
     */
    private String endpoint;

    /**
     * The client access id.
     */
    private String clientId;

    /**
     * The scope of access that is being requested.
     */
    private Collection<Scope> scopes;

    /**
     * Arbitrary implementation cookie for a auth code request.
     */
    private String state;

    /**
     * The response type.
     */
    private String responseType;

    /**
     * The redirect URI.
     */
    private String redirectUri;

    /**
     * Constructs an AuthorizationCodeRequest from the attributes
     * specified in the builder instance.
     */
    public AuthorizationCodeRequestBuilder() {
        this.responseType = DEFAULT_RESPONSE_TYPE;
    }

    /**
     * Sets the endpoint that will be used.
     *
     * @param endpoint the endpoint to use, e.g.
     *                 "https://medium.com/m/authorize"
     *
     * @return the updated instance of the current builder
     */
    public AuthorizationCodeRequestBuilder withEndpoint(final String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    /**
     * Sets the client access key/id.
     *
     * @param clientId the client id
     *
     * @return the updated instance of the current builder
     */
    public AuthorizationCodeRequestBuilder withClientId(final String clientId) {
        this.clientId = clientId;
        return this;
    }

    /**
     * Sets the scopes of access that is being requested.
     *
     * @param scopes the scopes of access being requested
     *
     * @return the updated instance of the current builder
     */
    public AuthorizationCodeRequestBuilder withScopes(final Collection<Scope> scopes) {
        this.scopes = scopes;
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
    public AuthorizationCodeRequestBuilder withState(final String state) {
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
    public AuthorizationCodeRequestBuilder withResponseType(final String responseType) {
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
    public AuthorizationCodeRequestBuilder withRedirectUri(final String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }

    /**
     * Gets the Request as a GET-able uri.
     *
     * TODO: there are cleaner implementations of this possible using
     * libraries.
     *
     * @return returns the Uri for the authorization request.
     */
    public String asUri() {
        return String.format("%s?%s=%s&%s=%s&%s=%s&%s=%s&%s=%s",
            endpoint,
            Key.CLIENT_ID, clientId,
            Key.SCOPE, StringUtils.join(",", scopes),
            Key.STATE, state,
            Key.RESPONSE_TYPE, responseType,
            Key.REDIRECT_URI, redirectUri
        );
    }

    /**
     * Enumerates the valid request parameter names in an
     * AuthorizationCodeRequest query string.
     */
    public static enum Key {
        CLIENT_ID("client_id"),
        SCOPE("scope"),
        STATE("state"),
        RESPONSE_TYPE("response_type"),
        REDIRECT_URI("redirect_uri");

        private final String keyAsString;

        Key(final String keyAsString) {
            this.keyAsString = keyAsString;
        }

        @Override
        public String toString() {
            return keyAsString;
        }
    }
}

