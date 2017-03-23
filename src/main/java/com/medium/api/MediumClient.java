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

package com.medium.api;

import com.medium.api.auth.AccessToken;
import com.medium.api.auth.AccessTokenRequest;
import com.medium.api.auth.AuthorizationCodeRequestBuilder;
import com.medium.api.auth.Credentials;
import com.medium.api.auth.Scope;

import com.medium.api.dependencies.http.HttpClient;
import com.medium.api.dependencies.http.HttpException;
import com.medium.api.dependencies.http.UnirestHttpClient;
import com.medium.api.dependencies.json.JacksonModelConverter;
import com.medium.api.dependencies.json.JsonModelConverter;

import com.medium.api.model.Contributor;
import com.medium.api.model.Image;
import com.medium.api.model.Post;
import com.medium.api.model.Publication;
import com.medium.api.model.Submission;
import com.medium.api.model.User;

import java.util.Collection;
import java.util.List;

/**
 * Implementation of the {@link Medium} API.
 */
public class MediumClient implements Medium {

    /**
     * The credentials this client will use when talking to a Medium
     * OAuth2 endpoint, before obtaining an access token.
     */
    private final Credentials credentials;

    /**
     * The access token that will be used to sign all requests made to
     * Medium APIs.
     */
    private String accessToken;

    /**
     * The URL to the Medium API.
     */
    private String endpoint;

    /**
     * The HTTP client that will be used to talk to the endpoint.
     */
    private HttpClient httpClient;

    /**
     * Serializes and deserializes objects from the data model.
     */
    private JsonModelConverter converter;

    /**
     * Constructs a new Medium Client with client credentials.
     */
    public MediumClient(final Credentials credentials) {
        this.endpoint = Endpoint.API_BASE;
        this.httpClient = new UnirestHttpClient();
        this.credentials = credentials;
        this.converter = new JacksonModelConverter();
    }

    /**
     * Constructs a new Medium Client from an existing access token.
     *
     * @param accessToken an access token obtained from Medium's OAuth2
     *                    token API
     */
    public MediumClient(final String accessToken) {
        this.endpoint = Endpoint.API_BASE;
        this.httpClient = new UnirestHttpClient(accessToken);
        this.accessToken = accessToken;
        this.credentials = null;
        this.converter = new JacksonModelConverter();
    }

    @Override
    public String getAuthorizationUrl(final String state,
            final String redirectUrl, final Collection<Scope> scopes) {

        return new AuthorizationCodeRequestBuilder()
            .withEndpoint(Endpoint.AUTHORIZE_BASE)
            .withClientId(credentials.getClientId())
            .withScopes(scopes)
            .withState(state)
            .withRedirectUri(redirectUrl)
            .asUri();
    }

    @Override
    public AccessToken exchangeAuthorizationCode(
            final String code, final String redirectUri) {

        try {
            return converter.asAccessToken(httpClient.post(
                Endpoint.API_BASE + "/tokens",
                converter.asJson(new AccessTokenRequest.Builder()
                    .withClientId(credentials.getClientId())
                    .withClientSecret(credentials.getClientSecret())
                    .withCode(code)
                    .withRedirectUri(redirectUri)
                    .build()
                )
            ));
        } catch (final HttpException exception) {
            System.out.println("Exception = " + exception.getMessage());
        }

        return null;
    }

    @Override
    public AccessToken exchangeRefreshToken(final String token) {
        throw new RuntimeException("Not implement yet.");
    }

    @Override
    public User getUser() {
        try {
            return converter.asUser(
                httpClient.get(Endpoint.API_BASE + "/me")
            );
        } catch (final HttpException exception) {
            System.out.println("Exception = " + exception.getMessage());
        }

        return null;
    }

    @Override
    public List<Publication> listPublications(final String userId) {
        return null;
    }

    @Override
    public List<Contributor> listPublicationContributors() {
        return null;
    }

    @Override
    public Post publishPost(Submission submission) {
        try {
            return converter.asPost(httpClient.post(
                String.format("%s/users/%s/posts",
                    Endpoint.API_BASE, submission.getUserId()
                ),
                converter.asJson(submission)
            ));
        } catch (final HttpException exception) {
            System.out.println("Exception = " + exception.getMessage());
        }

        return null;
    }

    @Override
    public Post publishPost(Submission submission, Publication publication) {
        return null;
    }

    @Override
    public Image uploadImage() {
        return null;
    }
}

