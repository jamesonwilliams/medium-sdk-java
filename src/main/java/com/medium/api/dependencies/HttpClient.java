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

package com.medium.api.dependencies;

/**
 * HttpClient is a generic interface to abstract our transport
 * dependency logic.
 *
 * It is actually high level; it operates on generic Java objects are
 * request and response objects. So you could post a Foo and get back a
 * Bar. How that comes to fruition is left to the implementer.
 */
public interface HttpClient {

    /**
     * Posts an item and obtains a response from an endpoint.
     *
     * @param <Q> the request type
     * @param <A> the response type
     * @param url the url to which to post
     * @param item the item being posted
     * @param responseType the class of the response object
     *
     * @return a response object
     */
    <Q, A> A post(String url, Q item, Class<A> responseType);

    /**
     * Gest an item of a certain type.
     *
     * @param <A> the type of response object
     * @param url the url to get
     * @param responseType the class of the reponse object
     *
     * @return a response object
     */
    <A> A get(String url, Class<A> responseType);
}

