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

import java.security.ProviderException;

/**
 * CredentialsNotFoundException is thrown by a CredentialsProvider when
 * it is unable to provide credentials.
 */
public class CredentialsNotFoundException extends ProviderException {

    /**
     * Constructs a new instance of CredentialsNotFoundException.
     *
     * @param message a message explaining why credentials could not be
     *                found, if the information is available
     */
    public CredentialsNotFoundException(final String message) {
        super(message);
    }
}
