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
import com.medium.api.auth.RefreshTokenRequest;
import com.medium.api.auth.Scope;

import com.medium.api.dependencies.http.HttpClient;
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
     * The URL to the Medium API.
     */
    private final String endpoint;

    /**
     * The HTTP client that will be used to talk to the endpoint.
     */
    private final HttpClient httpClient;

    /**
     * Serializes and deserializes objects from the data model.
     */
    private final JsonModelConverter converter;

    /**
     * Construct a new instance of MediumClient via Credentials.
     *
     * @param credentials the credentails to use
     */
    public MediumClient(final Credentials credentials) {
        this.endpoint = Endpoint.API_BASE;
        this.httpClient = new UnirestHttpClient();
        this.converter = new JacksonModelConverter();
        this.credentials = credentials;
    }

    /**
     * Constructs a new instance of MediumClient with an access token.
     *
     * @param accessToken the access token to use
     */
    public MediumClient(final String accessToken) {
        this((Credentials)null);
        this.httpClient.setBearerToken(accessToken);
    }

    /**
     * Constructs a new Medium Client with the options that were set in
     * the builder.
     *
     * @param builder the builder from which parameter values will
     *                obtained
     */
    private MediumClient(final Builder builder) {
        this.credentials = builder.credentials;
        this.endpoint = builder.endpoint;
        this.httpClient = builder.httpClient;
        this.converter = builder.converter;
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

        return converter.asSingle(AccessToken.class, httpClient.post(
            endpoint + "/tokens",
            converter.asJson(new AccessTokenRequest.Builder()
                .withClientId(credentials.getClientId())
                .withClientSecret(credentials.getClientSecret())
                .withCode(code)
                .withRedirectUri(redirectUri)
                .build()
            )
        ));
    }

    @Override
    public AccessToken exchangeRefreshToken(final String refreshToken) {

        return converter.asSingle(AccessToken.class, httpClient.post(
            endpoint + "/tokens",
            converter.asJson(new RefreshTokenRequest.Builder()
                .withClientId(credentials.getClientId())
                .withClientSecret(credentials.getClientSecret())
                .withRefreshToken(refreshToken)
                .build()
            )
        ));
    }

    @Override
    public User getUser() {
        return converter.asSingle(User.class, httpClient.get(
            endpoint + "/me"
        ));
    }

    @Override
    public List<Publication> listPublications(final String userId) {
        return converter.asListOf(Publication.class, httpClient.get(
            String.format("%s/users/%s/publications", endpoint, userId)
        ));
    }

    @Override
    public List<Contributor> listContributors(final String publicationId) {
        return converter.asListOf(Contributor.class, httpClient.get(
            String.format("%s/publications/%s/contributors",
                endpoint, publicationId
            )
        ));
    }

    @Override
    public Post createPost(
            final Submission submission, final String userId) {

        return converter.asSingle(Post.class, httpClient.post(
            String.format("%s/users/%s/posts", endpoint, userId),
            converter.asJson(submission)
        ));
    }

    @Override
    public Post createPostForPublication(
            final Submission submission, final String publicationId) {

        return converter.asSingle(Post.class, httpClient.post(
            String.format("%s/publications/%s/posts",
                endpoint, publicationId
            ),
            converter.asJson(submission)
        ));
    }

    @Override
    public Image uploadImage() {
        throw new RuntimeException("Not implement yet.");
    }

    /**
     * Builder fascilitates the creation of a MediumClient.
     */
    public static class Builder {

        private Credentials credentials;
        private String accessToken;
        private String endpoint;
        private HttpClient httpClient;
        private JsonModelConverter converter;

        /**
         * Constructs a new instance of Builder with default values
         * where possible.
         */
        public Builder() {
            this.converter = new JacksonModelConverter();
            this.endpoint = Endpoint.API_BASE;
            this.httpClient = new UnirestHttpClient(accessToken);
        }

        /**
         * Sets the credentials to use.
         *
         * @param credentials the credentials to use
         *
         * @return the updated instance of the builder
         */
        public Builder withCredentials(final Credentials credentials) {
            this.credentials = credentials;
            return this;
        }

        /**
         * Sets the JSON to Model Converter to use for serialization and
         * deserialization of objects.
         *
         * @param converter the JSON to Model converter
         *
         * @return the updated instance of the builder
         */
        public Builder withConverter(final JsonModelConverter converter) {
            this.converter = converter;
            return this;
        }

        /**
         * Sets the API endpoint to use, eg. https://api.medium.com/v1
         *
         * @param endpoint the base url of the API endpoint to use
         *
         * @return the updated instance of the builder
         */
        public Builder withEndpoint(final String endpoint) {
            this.endpoint = endpoint;
            return this;
        }

        /**
         * Sets the access token to use.
         *
         * @param accessToken the access token to use
         *
         * @return the updated instance of the builder
         */
        public Builder withAccessToken(final String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        /**
         * Sets the HTTP client implementation to use.
         *
         * @param httpClient the HTTP client implementation to use
         *
         * @return the updated instance of the builder
         */
        public Builder withHttpClient(final HttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        /**
         * Contructs a new instance of a MediumClient using the options
         * that were provided to this Builder instance.
         *
         * @return a new instance of MediumClient.
         */
        public MediumClient build() {
            if (null != this.accessToken) {
                this.httpClient.setBearerToken(this.accessToken);
            }

            return new MediumClient(this);
        }
    }
}

