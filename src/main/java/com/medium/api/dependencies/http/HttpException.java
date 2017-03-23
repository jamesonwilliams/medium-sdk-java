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

package com.medium.api.dependencies.http;

/**
 * An HttpException is throws when the HttpClient fails to execute a
 * request and obtain a response (having achieved a stauts code in the
 * 200 series.)
 */
public class HttpException extends RuntimeException {
    
    /**
     * The HTTP status code, if available.
     */
    private final int code;

    /**
     * Constructs a new HttpException.
     *
     * @param code the HTTP status code, if avaialable
     * @param message the reason for the exception
     */
    public HttpException(final int code, final String message) {
        super(message);
        this.code = code;
    }

    /**
     * Gets the status code.
     *
     * @return the status code
     */
    public int getCode() {
        return code;
    }
}

