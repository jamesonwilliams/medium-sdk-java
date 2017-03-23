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

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * UnirestHttpClient is a dependency wrapper around Unirest.
 *
 * Unirest is used to implementat the logic of our local HTTP Client
 * interface.
 *
 * "Unirest may change, but your app logic shouldn't have to." (TM)
 */
public class UnirestHttpClient implements HttpClient {

    /**
     * The Unirest instance used to do HTTP.
     */
    private final Unirest unirest;

    /**
     * Constructs a new UnirestHttpClient from a Bearer token.
     *
     * @param param bearerToken an authorization credential
     */
    public UnirestHttpClient(final String bearerToken) {
        this();
        unirest.setDefaultHeader("Authorization", "Bearer " + bearerToken);
    }

    /**
     * Constructs a new UnirestHttpClient.
     */
    public UnirestHttpClient() {
        unirest = new Unirest();
        // Set headers common to every Connect API reques
        unirest.setDefaultHeader("Content-Type", "application/json");
        unirest.setDefaultHeader("Accept", "application/json");
    }

    @Override
    public String post(final String url, final String body)
            throws HttpException {
        return executeRequest(new PostRequest(url, body));
    }

    @Override
    public String get(final String url) throws HttpException {
        return executeRequest(new GetRequest(url));
    }

    /**
     * Executes an HTTP Request.
     *
     * @param request the request to execute
     *
     * @return the JSON content of the HTTP response
     * @throws HttpException
     *         If the request was not successful
     */
    private String executeRequest(final Request request)
            throws HttpException {

        HttpResponse<JsonNode> response;
        try {
            response = request.execute();

            if (response.getStatus() >= 200 && response.getStatus() < 300) {
                return response.getBody().toString();
            }

            throw new HttpException(
                response.getStatus(), response.getBody().toString()
            );

        } catch (final UnirestException unirestException) {
            throw new HttpException(
                -1, unirestException.getMessage()
            );
        }
    }

    /**
     * An HTTP request that can be executed.
     */
    private interface Request {
        /**
         * Executes a request.
         *
         * @return the HTTP Response as a JsonNode
         *
         * @throws UnirestException
         *         If Unirest cannot satisfy the request
         */
        public HttpResponse<JsonNode> execute() throws UnirestException;
    }

    /**
     * A GET request is an HTTP request.
     */
    private class GetRequest implements Request {

        /**
         * The URL to GET.
         */
        private final String url;

        /**
         * Constructs a new GetRequest.
         *
         * @param url the url to get
         */
        public GetRequest(final String url) {
            this.url = url;
        }

        /**
         * Executes the GET request.
         *
         * @return the JSON content of the response.
         * @throws UnirestException
         *         If Unirest cannot satisfy the request.
         */
        @Override
        public HttpResponse<JsonNode> execute() throws UnirestException {
            return unirest.get(url).asJson();
        }
    }

    /**
     * A POST request is an HTTP request.
     */
    private class PostRequest implements Request {

        /**
         * The URL to post to.
         */
        private final String url;

        /**
         * The data to POST.
         */
        private final String body;

        /**
         * Constructs new POST request.
         *
         * @param url the URL to POST to
         * @param body the content of the body to be POSTed
         */
        public PostRequest(final String url, final String body) {
            this.url = url;
            this.body = body;
        }

        /**
         * Executes a POST request.
         *
         * @return the JSON content of the response.
         * @throws UnirestException
         *         If Unirest cannot satisfy the request.
         */
        @Override
        public HttpResponse<JsonNode> execute() throws UnirestException {
            return unirest.post(url).body(body).asJson();
        }
    }
}

