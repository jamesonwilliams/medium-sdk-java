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
 * AccessProvider describes a component which is cable of obtaining
 * OAuth2 access tokens, by listening for authorization codes at the
 * redirect uri endpoint.
 */
public interface AccessProvider {

    /**
     * The AccessProvider provides a method to listen for authorization
     * codes.
     */
    void listenForAuthorizationCodes();

    /**
     * An observer of an authentication provider will be notified when
     * an access token is available from the provider, or if there is an
     * error in obtaining one.
     */
    public interface Observer {
        /**
         * Called when the provider has obtained an access token for the
         * client to use.
         *
         * @param token the access token that was obtained
         */
        void onAccessGranted(final AccessToken token);

        /**
         * Called when there is an error in obtaining access.
         */
        void onAccessError();
    }
};

