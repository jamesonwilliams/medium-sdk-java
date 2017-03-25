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

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

/**
 * OkHttpClient is a dependency wrapper around the OkHttp library.
 *
 * OkHttp is used to implement the logic of our local HTTP client
 * interface.
 *
 * "OkHttp may change, but your app logic shouldn't have to." (TM)
 */
public class OkayHttpClient implements HttpClient {

    // Miscellaneous HTTP Header Constants
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String ACCEPT = "Accept";
    private static final String ACCEPT_CHARSET = "Accept-Charset";
    private static final String UTF_8 = "utf-8";
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer";

    /**
     * The OkHttpClient instance to use for making HTTP calls.
     */
    private final OkHttpClient okHttpClient;

    /**
     * The valid access token for Medium.
     */
    private String bearerToken;

    /**
     * Constructs a new OkayHttpClient.
     */
    public OkayHttpClient() {
        this(null);
    }

    /**
     * Constructs a new OkHttpClient using a default access token for
     * all requests.
     *
     * @param bearerToken the bearer access token which should be added
     *                    to the header of all outgoing requests
     */
    public OkayHttpClient(final String bearerToken) {
        this(null, bearerToken);
    }

    /**
     * Constructs a new OkHttpClient using a specific OkHttpClient
     * instance and a default access token for all outgoing HTTP
     * request headers.
     *
     * @param okHttpClient the OkHttpClient instance to use
     * @param bearerToken the access token to put in all outgoing
     *                    requests
     */
    public OkayHttpClient(
            final OkHttpClient okHttpClient, final String bearerToken) {

        if (null != okHttpClient) {
            this.okHttpClient = okHttpClient;
        } else {
            this.okHttpClient = new OkHttpClient();
        }

        setBearerToken(bearerToken);
    }

    @Override
    public String post(final String url, final String json)
            throws HttpException {
        try {
            return makeRequest(
                ofUrl(url).post(RequestBody.create(
                    MediaType.parse(APPLICATION_JSON), json
                )).build()
            );
        } catch (final IOException ioException) {
            throw new HttpException(-1, ioException.getMessage());
        }
    }

    @Override
    public String get(final String url) throws HttpException {
        try {
            return makeRequest(ofUrl(url).build());
        } catch (final IOException ioException) {
            throw new HttpException(-1, ioException.getMessage());
        }
    }

    @Override
    public void setBearerToken(final String bearerToken) {
        this.bearerToken = bearerToken;
    }

    /**
     * Bootstraps a request builder with common header information, and
     * with a target url.
     *
     * For use as: makeRequest(ofUrl(...));
     *
     * @param url the url of which we are making a request
     *
     * @return a request builder with some default values set.
     * @throws IOException
     *         On unsuccessful HTTP transport (unrelated to error codes)
     */
    private Request.Builder ofUrl(final String url) throws IOException {
        final Request.Builder builder = new Request.Builder()
            .url(url)
            .header(CONTENT_TYPE, APPLICATION_JSON)
            .addHeader(ACCEPT, APPLICATION_JSON)
            .addHeader(ACCEPT_CHARSET, UTF_8);

        if (null != bearerToken) {
            builder.addHeader(
                AUTHORIZATION,
                String.format("%s %s", BEARER, bearerToken)
            );
        }

        return builder;
    }

    /**
     * Executes an HTTP request.
     *
     * @param request the request to execute
     *
     * @return the content of the response body as a string
     * @throws IOException
     *         On unsuccessful HTTP transport (unrelated to error codes)
     */
    private String makeRequest(final Request request) throws IOException {

        final Response response = okHttpClient.newCall(request).execute();
    
        if (!response.isSuccessful()) {
            throw new HttpException(
                response.code(), response.body().string()
            );
        }

        return response.body().string();
    }
}

