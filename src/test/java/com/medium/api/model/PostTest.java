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

package com.medium.api.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.medium.api.dependencies.json.JacksonModelConverter;
import com.medium.api.dependencies.json.JsonModelConverter;
import com.medium.api.test.TestUtils;

import org.json.JSONException;

import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

/**
 * Tests the Post class.
 */
public class PostTest {

    private static final String ARRANGED_ID = "e6f36a";
    private static final String ARRANGED_TITLE = "Liverpool FC";

    private static final String ARRANGED_AUTHOR_ID =
        "5303d74c64f66366f00cb9b2a94f3251bf5";

    private static final String ARRANGED_URL =
        "https://medium.com/@majelbstoat/liverpool-fc-e6f36a";

    private static final String ARRANGED_CANONICAL_URL =
        "http://jamietalbot.com/posts/liverpool-fc";

    private static final String ARRANGED_LICENSE_URL =
        "https://medium.com/policy/9db0094a1e0f";

    private static final Date ARRANGED_PUBLISHED_AT =
        new Date(1442286338435L);

    private static final PublishStatus ARRANGED_PUBLISH_STATUS =
        PublishStatus.PUBLIC;

    private static final License ARRANGED_LICENSE =
        License.ALL_RIGHTS_RESERVED;

    private static final Collection<String> ARRANGED_TAGS =
        Arrays.asList("football", "sport", "Liverpool");

    private static final Post ARRANGED_POST = new Post.Builder()
        .withId(ARRANGED_ID)
        .withTitle(ARRANGED_TITLE)
        .withAuthorId(ARRANGED_AUTHOR_ID)
        .withTags(ARRANGED_TAGS)
        .withUrl(ARRANGED_URL)
        .withCanonicalUrl(ARRANGED_CANONICAL_URL)
        .withPublishStatus(ARRANGED_PUBLISH_STATUS)
        .withPublishAt(ARRANGED_PUBLISHED_AT)
        .withLicense(ARRANGED_LICENSE)
        .withLicenseUrl(ARRANGED_LICENSE_URL)
        .build();

    private static final String ARRANGED_JSON =
        TestUtils.getResourceContents("post.json");

    private final JsonModelConverter converter;

    public PostTest() {
        converter = new JacksonModelConverter();
    }

    @Test
    public void testBuilderNoArgs() {
        assertNotNull(new Post.Builder().build());
    }

    @Test
    public void testBuilderFullArgs() {
        // Arrange and act have already happened...

        // Assert!
        assertEquals(ARRANGED_ID, ARRANGED_POST.getId());
        assertEquals(ARRANGED_TITLE, ARRANGED_POST.getTitle());
        assertEquals(ARRANGED_AUTHOR_ID, ARRANGED_POST.getAuthorId());
        assertEquals(ARRANGED_TAGS, ARRANGED_POST.getTags());
        assertEquals(ARRANGED_URL, ARRANGED_POST.getUrl());
        assertEquals(ARRANGED_CANONICAL_URL, ARRANGED_POST.getCanonicalUrl());
        assertEquals(ARRANGED_PUBLISH_STATUS, ARRANGED_POST.getPublishStatus());
        assertEquals(ARRANGED_LICENSE, ARRANGED_POST.getLicense());
        assertEquals(ARRANGED_LICENSE_URL, ARRANGED_POST.getLicenseUrl());
    }

    /**
     * Test deserialization.
     */
    @Test
    public void testAsJson_Post_HappyPath() {
        // Act
        final Post post = converter.asSingle(Post.class, ARRANGED_JSON);

        // Assert
        assertEquals(ARRANGED_ID, post.getId());
        assertEquals(ARRANGED_TITLE, post.getTitle());
        assertEquals(ARRANGED_AUTHOR_ID, post.getAuthorId());
        assertEquals(ARRANGED_TAGS, post.getTags());
        assertEquals(ARRANGED_URL, post.getUrl());
        assertEquals(ARRANGED_CANONICAL_URL, post.getCanonicalUrl());
        assertEquals(ARRANGED_PUBLISH_STATUS, post.getPublishStatus());
        assertEquals(ARRANGED_PUBLISHED_AT, post.getPublishedAt());
        assertEquals(ARRANGED_LICENSE, post.getLicense());
        assertEquals(ARRANGED_LICENSE_URL, post.getLicenseUrl());
    }

    @Test
    public void testAsPost_HappyPath() throws JSONException {
        // Act-n-Assert Combo-Pak 2-4-1
        JSONAssert.assertEquals(
            ARRANGED_JSON,
            TestUtils.wrapInEnvelope(converter.asJson(ARRANGED_POST)),
            false
        );
    }
}

