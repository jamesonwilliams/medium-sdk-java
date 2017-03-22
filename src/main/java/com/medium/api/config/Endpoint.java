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
package com.medium.api.config;

/**
 * Endpoint describes the locations of Medium HTTPS endpoints.
 */
public class Endpoint {

    /**
     * The base of all URLs that exercise the public API.
     */
    public static final String API_BASE = "https://api.medium.com/v1";

    /**
     * The base of the URL for browser authentication.
     */
    public static final String AUTHORIZE_BASE = "https://medium.com/m/oauth/authorize";
}

