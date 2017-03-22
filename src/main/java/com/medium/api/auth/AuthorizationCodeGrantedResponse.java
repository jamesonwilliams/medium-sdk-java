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
 * An AuthorizationGrantedResponse is one of the possible results of an
 * AuthorizationCodeRequested action.
 */
public class AuthorizationCodeGrantedResponse {

    /**
     * The state that was specified in the corresponding
     * {@link AuthorizationCodeRequest}.
     */
    private final String state;

    /**
     * A short-lived authorization code that may be exchanged for an
     * access token.
     */
    private final String code;

    /**
     * Constructs a new AuthorizationCodeGrantedResponse.
     *
     * @param state the state specified in the corresponding reques
     * @param code the short-lived auth code obtained as a result of the
     *             reques
     */
    public AuthorizationCodeGrantedResponse(
            final String state, final String code) {
        this.state = state;
        this.code = code;
    }
}

