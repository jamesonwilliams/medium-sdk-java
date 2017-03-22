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

import java.util.Collection;

/**
 * An AccessTokenGrantedResponse is one of the possible outcomes after an
 * {@link AccessTokenRequest} is placed. The access token in the resul
 * may be used to exercise the enpoint's main (not-security-dominated)
 * APIs.
 */
public class AccessTokenGrantedResponse {

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
     * Constructs a new instance of AccessTokenGrantedResponse.
     *
     * @param tokenType the type of token granted
     * @param accessToken the access token that was granted
     * @param refreshToken a token to use for refreshing the access
     * @param scope the scopes of access that were granted
     * @param expiresAt the time when the token will expire
     */
    public AccessTokenGrantedResponse(
            final String tokenType,
            final String accessToken,
            final String refreshToken,
            final Collection<Scope> scope,
            final long expiresAt) {

        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.scope = scope;
        this.expiresAt = expiresAt;
    }
}

