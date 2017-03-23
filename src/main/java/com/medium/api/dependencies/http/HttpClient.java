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
 * A HTTP Client is any HTTP client that accepts request bodies in and
 * receives responses with body content.
 *
 * This generic interface assumes some serialization to String as input
 * and output as a lowest-common-denominator interface this is unlikely
 * to become encumbered by details of object serialization; those
 * decisions are all in the implementer.
 */
public interface HttpClient {

    /**
     * Executes an HTTP POST to the specified URL with data in the
     * request body.
     *
     * @param url the url to which to POST
     * @param body the content for the request body
     *
     * @return On repsonde code 200, the content from the body of the
     *         response.
     *
     * @throws HttpException
     *         If the response code is not 200.
     */
    String post(String url, String body) throws HttpException;

    /**
     * Executes an HTTP GET to the specified URL.
     *
     * @param url the url to GET
     *
     * @return On response code 200, the content from the body of
     *         the response.
     *
     * @throws HttpException
     *         If the response code is not 200.
     */
    String get(String url) throws HttpException;
}

