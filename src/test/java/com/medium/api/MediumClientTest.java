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

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.medium.api.auth.AccessToken;
import com.medium.api.auth.AccessTokenRequest;
import com.medium.api.auth.AuthorizationCodeRequestBuilder;
import com.medium.api.auth.Credentials;
import com.medium.api.auth.RefreshTokenRequest;
import com.medium.api.auth.Scope;

import com.medium.api.dependencies.http.HttpClient;
import com.medium.api.dependencies.json.JsonModelConverter;

import com.medium.api.model.Contributor;
import com.medium.api.model.Image;
import com.medium.api.model.Post;
import com.medium.api.model.Publication;
import com.medium.api.model.Submission;
import com.medium.api.model.User;

import com.medium.api.util.StringUtils;
import com.medium.api.test.TestUtils;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * These are broader component-level integration tests on the Medium
 * client, not just unit tests.
 *
 * HTTP endpoint is mocked and substituded with cached test resources to
 * mock it.
 */
public class MediumClientTest {

    private static final String TEST_ENDPOINT = "http://api.foo.bar/v42";
    private static final String TEST_TOKEN = "KoolToke44";
    private static final String TEST_STATE = "state";
    private static final String TEST_REDIRECT_URL = "http://127.0.0.1:9000/callback";
    private static final String TEST_CLIENT_ID = "KoolClient22";
    private static final String TEST_CLIENT_SECRET = "SecretSauces";

    private static final Credentials TEST_CREDENTIALS =
        new Credentials(TEST_CLIENT_ID, TEST_CLIENT_SECRET);

    private static final Collection<Scope> TEST_SCOPES = Arrays.asList(
        Scope.BASIC_PROFILE,
        Scope.PUBLISH_POST,
        Scope.LIST_PUBLICATIONS
    );

    @Mock
    private HttpClient http;

    private Medium medium;

    @Before
    public void setup() {
        initMocks(this);

        medium = new MediumClient.Builder()
            .withCredentials(TEST_CREDENTIALS)
            .withAccessToken(TEST_TOKEN)
            .withEndpoint(TEST_ENDPOINT)
            .withHttpClient(http)
            .build();
    }

    @Test
    public void testGetAuthorizationUrl() {
        assertEquals(
            getExpectedAuthorizationUrl(),
            medium.getAuthorizationUrl(
                TEST_STATE, TEST_REDIRECT_URL, TEST_SCOPES
            )
        );
    }

    private static String getExpectedAuthorizationUrl() {

        final StringBuilder builder = new StringBuilder();

        builder.append("https://medium.com/m/oauth/authorize");
        builder.append("?client_id=KoolClient22");
        builder.append("&scope=basicProfile,publishPost,listPublications");
        builder.append("&state=state");
        builder.append("&response_type=code");
        builder.append("&redirect_uri=http://127.0.0.1:9000/callback");

        return builder.toString();
    }

    @Test
    public void testExchangeAuthorizationCode() {

        // Arrange
        final String exchangeUrl = TEST_ENDPOINT + "/tokens";
        final String mockJson = TestUtils.getResourceContents("access-token.json");
        when(http.post(eq(exchangeUrl), anyString())).thenReturn(mockJson);

        // Act
        AccessToken token = medium.exchangeAuthorizationCode("", "");

        // Assert
        assertEquals(
            "e27bb85c111d0f4fb27597db25fd4d359ff066ba10d6779ec2aa01bfa60a0a958",
            token.getAccessToken()
        );
    }

    @Test
    public void testExchangeRefreshToken() {

        // Arrange
        final String exchangeUrl = TEST_ENDPOINT + "/tokens";
        final String mockJson = TestUtils.getResourceContents("access-token.json");
        when(http.post(eq(exchangeUrl), anyString())).thenReturn(mockJson);

        // Act
        AccessToken token = medium.exchangeRefreshToken("");

        // Assert
        assertEquals(
            "e27bb85c111d0f4fb27597db25fd4d359ff066ba10d6779ec2aa01bfa60a0a958",
            token.getAccessToken()
        );
    }

    @Test
    public void testGetUser() {

        // Arrange
        final String getUserUrl = TEST_ENDPOINT + "/me";
        final String mockJson = TestUtils.getResourceContents("user.json");
        when(http.get(eq(getUserUrl))).thenReturn(mockJson);

        // Act
        User user = medium.getUser();

        // Assert
        assertEquals("majelbstoat", user.getUsername());
        assertEquals("https://medium.com/@majelbstoat", user.getUrl());
    }

    @Test
    public void testListPublications() {

        // Arrange
        final String publicationUrl =
            TEST_ENDPOINT + "/users/KoolUser22/publications";
        final String mockJson =
            TestUtils.getResourceContents("publication-list.json");
        when(http.get(eq(publicationUrl))).thenReturn(mockJson);
        
        // Act
        List<Publication> publications = medium.listPublications("KoolUser22");
        
        // Assert
        assertEquals("Developers", publications.get(1).getName());
    }

    @Test
    public void testListContributors() {

        // Arrange
        final String contributorsUrl =
            TEST_ENDPOINT + "/publications/KoolPub66/contributors";
        final String mockJson =
            TestUtils.getResourceContents("contributor-list.json");
        when(http.get(eq(contributorsUrl))).thenReturn(mockJson);
        
        // Act
        List<Contributor> contributors = medium.listContributors("KoolPub66");

        // Assert
        assertEquals("b45573563f5a", contributors.get(3).getPublicationId());
    }

    @Test
    public void testCreatePost() {

        // Arrange
        final String postUrl = TEST_ENDPOINT + "/users/KoolUser98/posts";
        final String mockJson =
            TestUtils.getResourceContents("post.json");
        when(http.post(eq(postUrl), anyString())).thenReturn(mockJson);

        // Act
        Post post = medium.createPost(
            new Submission.Builder().build(), "KoolUser98"
        );

        // Assert
        assertEquals(1442286338435L, post.getPublishedAt().getTime());
    }

    @Test
    public void testCreatePostForPublication() {

        // Arrange
        final String postUrl = TEST_ENDPOINT + "/publications/KoolPub/posts";
        final String mockJson =
            TestUtils.getResourceContents("post.json");
        when(http.post(eq(postUrl), anyString())).thenReturn(mockJson);

        // Act
        Post post = medium.createPostForPublication(
            new Submission.Builder().build(), "KoolPub"
        );

        // Assert
        assertEquals(
            "http://jamietalbot.com/posts/liverpool-fc",
            post.getCanonicalUrl()
        );
    }
}


