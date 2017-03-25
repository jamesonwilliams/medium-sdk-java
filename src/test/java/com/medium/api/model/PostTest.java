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

import org.junit.Test;
import org.junit.Before;

import org.skyscreamer.jsonassert.JSONAssert;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

/**
 * Tests the Post class.
 */
public class PostTest {

    private static final String TEST_ID = "e6f36a";
    private static final String TEST_TITLE = "Liverpool FC";
    private static final String TEST_AUTHOR_ID = "5303d74c64f66366f00cb9b2a94f3251bf5";
    private static final String TEST_URL = "https://medium.com/@majelbstoat/liverpool-fc-e6f36a";
    private static final String TEST_CANONICAL_URL = "http://jamietalbot.com/posts/liverpool-fc";
    private static final String TEST_LICENSE_URL = "https://medium.com/policy/9db0094a1e0f";
    private static final Date TEST_PUBLISHED_AT = new Date(1442286338435L);
    private static final PublishStatus TEST_PUBLISH_STATUS = PublishStatus.PUBLIC;
    private static final License TEST_LICENSE = License.ALL_RIGHTS_RESERVED;
    private static final Collection<String> TEST_TAGS =
        Arrays.asList("football", "sport", "Liverpool");

    private static final String TEST_JSON =
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
        final Post result = new Post.Builder()
            .withId(TEST_ID)
            .withTitle(TEST_TITLE)
            .withAuthorId(TEST_AUTHOR_ID)
            .withTags(TEST_TAGS)
            .withUrl(TEST_URL)
            .withCanonicalUrl(TEST_CANONICAL_URL)
            .withPublishStatus(TEST_PUBLISH_STATUS)
            .withLicense(TEST_LICENSE)
            .withLicenseUrl(TEST_LICENSE_URL)
            .build();

        assertEquals(TEST_ID, result.getId());
        assertEquals(TEST_TITLE, result.getTitle());
        assertEquals(TEST_AUTHOR_ID, result.getAuthorId());
        assertEquals(TEST_TAGS, result.getTags());
        assertEquals(TEST_URL, result.getUrl());
        assertEquals(TEST_CANONICAL_URL, result.getCanonicalUrl());
        assertEquals(TEST_PUBLISH_STATUS, result.getPublishStatus());
        assertEquals(TEST_LICENSE, result.getLicense());
        assertEquals(TEST_LICENSE_URL, result.getLicenseUrl());
    }

    /**
     * Test deserialization.
     */
    @Test
    public void testAsJson_Post_HappyPath() {
        final Post post = converter.asSingle(Post.class, TEST_JSON);

        assertEquals(TEST_ID, post.getId());
        assertEquals(TEST_TITLE, post.getTitle());
        assertEquals(TEST_AUTHOR_ID, post.getAuthorId());
        assertEquals(TEST_TAGS, post.getTags());
        assertEquals(TEST_URL, post.getUrl());
        assertEquals(TEST_CANONICAL_URL, post.getCanonicalUrl());
        assertEquals(TEST_PUBLISH_STATUS, post.getPublishStatus());
        assertEquals(TEST_PUBLISHED_AT, post.getPublishedAt());
        assertEquals(TEST_LICENSE, post.getLicense());
        assertEquals(TEST_LICENSE_URL, post.getLicenseUrl());
    }

    @Test
    public void testAsPost_HappyPath() {
        final Post post = new Post(
            TEST_ID,
            TEST_TITLE,
            TEST_AUTHOR_ID,
            TEST_TAGS,
            TEST_URL,
            TEST_CANONICAL_URL,
            TEST_PUBLISH_STATUS,
            TEST_PUBLISHED_AT,
            TEST_LICENSE,
            TEST_LICENSE_URL
        );

        final String json =
            TestUtils.wrapInEnvelope(converter.asJson(post));

        JSONAssert.assertEquals(TEST_JSON, json, false);
    }
}

