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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

/**
 * An AccessToken may be returned as one of the possible outcomes after
 * an {@link AccessTokenRequest} is placed. The access token in the
 * resul may be used to exercise the enpoint's main
 * (not-security-dominated) APIs.
 */
public class AccessToken {

    /**
     * The JSON key for the token type.
     */
    private static final String TOKEN_TYPE_KEY = "token_type";

    /**
     * The JSON key for the access token field.
     */
    private static final String ACCESS_TOKEN_KEY = "access_token";

    /**
     * The JSON key for the refresh token field.
     */
    private static final String REFRESH_TOKEN_KEY = "refresh_token";

    /**
     * The JSON key for the scope filed.
     */
    private static final String SCOPE_KEY = "scope";

    /**
     * The JSON key name for the expires at field.
     */
    private static final String EXPIRES_AT_KEY = "expires_at";

    /**
     * The token type. Should contain the literal string "Bearer".
     */
    private final String tokenType;

    /**
     * A token that is valid for 60 days and may be used to perform
     * authenticated requests on behalf of the user.
     */
    private final String accessToken;

    /**
     * A token that does not expire which may be used to acquire a new
     * access_token.
     */
    private final String refreshToken;

    /**
     * The scopes of access granted to the application.
     */
    private final Collection<Scope> scope;

    /**
     * The timestamp in unix time when the access token will expire.
     */
    private final long expiresAt;

    /**
     * Constructs a new instance of AccessToken.
     *
     * @param tokenType the type of token granted
     * @param accessToken the access token that was granted
     * @param refreshToken a token to use for refreshing the access
     * @param scope the scopes of access that were granted
     * @param expiresAt the time when the token will expire
     */
    @JsonCreator
    public AccessToken(
            @JsonProperty(TOKEN_TYPE_KEY)   final String tokenType,
            @JsonProperty(ACCESS_TOKEN_KEY) final String accessToken,
            @JsonProperty(REFRESH_TOKEN_KEY)final String refreshToken,
            @JsonProperty(SCOPE_KEY)        final Collection<Scope> scope,
            @JsonProperty(EXPIRES_AT_KEY)   final long expiresAt) {

        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.scope = scope;
        this.expiresAt = expiresAt;
    }

    /**
     * Gets the token type.
     *
     * @return the token type
     */
    @JsonProperty(TOKEN_TYPE_KEY)
    public String getTokenType() {
        return tokenType;
    }

    /**
     * Gets the access token.
     *
     * @return the access token
     */
    @JsonProperty(ACCESS_TOKEN_KEY)
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Gets the refresh token.
     *
     * @return the refresh token
     */
    @JsonProperty(REFRESH_TOKEN_KEY)
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Gets the scope.
     *
     * @return the access scope
     */
    @JsonProperty(SCOPE_KEY)
    public Collection<Scope> getScope() {
        return scope;
    }

    /**
     * Gets the expiration time of the token.
     *
     * @return the expiration time of the token in UNIX time
     */
    @JsonProperty(EXPIRES_AT_KEY)
    public long getExpiresAt() {
        return expiresAt;
    }
}

