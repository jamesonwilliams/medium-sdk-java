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

package com.medium.api.dependencies.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import com.medium.api.auth.AccessToken;
import com.medium.api.config.ConfigFile;
import com.medium.api.model.Contributor;
import com.medium.api.model.Error;
import com.medium.api.model.Image;
import com.medium.api.model.Post;
import com.medium.api.model.Publication;
import com.medium.api.model.Submission;
import com.medium.api.model.User;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;

import java.io.IOException;
import java.util.Arrays;

/**
 * These tests tests the functionality of the Jackson wrapper itself; to
 * answer the question: "But can it run Crysis?" see the model POJOs
 * themselves. Actually, that will only help you see if the POJOs can be
 * serialized. Probably can't run Crysis.
 */
public class JacksonModelConverterTest {

    /**
     * Semantic placeholder for a variable that isn't used because we're
     * mocking behaviour.
     */
    private static final String NO_OP = "";

    /**
     * Mocked JSON returned by the mapper.
     */
    private static final String EXPECTED_JSON = "{\"key\":\"value\"}";

    @Mock
    private ObjectMapper mapper;

    @Mock
    private JsonNode jsonNode;

    private JsonModelConverter objectUnderTest;

    @Before
    public void setup() {
        initMocks(this);
        setupMapperMocks();

        objectUnderTest = new JacksonModelConverter(mapper);
    }

    private void setupMapperMocks() {
        try {
            when(mapper.writeValueAsString(any()))
                .thenReturn(EXPECTED_JSON);
        } catch (JsonProcessingException exception) {
            fail("writeValueAsString() mock threw JsonProcessingException :"
                + exception.getMessage());
        }

        try {
            when(mapper.readTree(anyString()))
                .thenReturn(jsonNode);
        } catch (IOException exception) {
            fail("readTree() mock threw IOException: "
                + exception.getMessage());
        }
    }

    @Test
    public void testAsAccessToken_HappyPath() {
        AccessToken token = objectUnderTest.asAccessToken(NO_OP);
    }

    @Test
    public void testAsJson_AccessToken_HappyPath() {
        objectUnderTest.asJson(mock(AccessToken.class));
    }

    @Test
    public void testAsUser_HappyPath() {
        objectUnderTest.asUser(NO_OP);
    }

    @Test
    public void testAsJson_User_HappyPath() {
        objectUnderTest.asJson(mock(User.class));
    }

    @Test
    public void testAsError_HappyPath() {
        objectUnderTest.asError(NO_OP);
    }

    @Test
    public void testAsJson_Error_HappyPath() {
        objectUnderTest.asJson(mock(Error.class));
    }

    @Test
    public void testAsPost_HappyPath() {
        objectUnderTest.asPost(NO_OP);
    }

    @Test
    public void testAsJson_Post_HappyPath() {
        objectUnderTest.asJson(mock(Post.class));
    }

    @Test
    public void testAsPublicationList_HappyPath() {
        objectUnderTest.asPublicationList(NO_OP);
    }

    @Test
    public void testAsJson_PublicationList_HappyPath() {
        objectUnderTest.asJson(Arrays.asList(mock(Publication.class)));
    }

    @Test
    public void testAsSubmission_HappyPath() {
        objectUnderTest.asSubmission(NO_OP);
    }

    @Test
    public void testAsJson_Submission_HappyPath() {
        objectUnderTest.asJson(mock(Submission.class));
    }

    @Test
    public void testAsImage_HappyPath() {
        objectUnderTest.asJson(NO_OP);
    }

    @Test
    public void testAsJson_Image_HappyPath() {
        objectUnderTest.asJson(mock(Image.class));
    }

    @Test
    public void testAsContributorList_HappyPath() {
        objectUnderTest.asContributorList(NO_OP);
    }

    @Test
    public void testAsJson_ContributorList_HappyPath() {
        objectUnderTest.asJson(Arrays.asList(mock(Contributor.class)));
    }

    @Test
    public void testAsConfigFile_HappyPath() {
        objectUnderTest.asConfigFile(NO_OP);
    }

    @Test
    public void testAsJson_ConfigFile_HappyPath() {
        objectUnderTest.asJson(mock(ConfigFile.class));
    }
}

