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

/**
 * Tests that a Submission can be built, serialized, deserialized.
 */
public class SubmissionTest {

    private static final String ARRANGED_TITLE =
        "Hard things in software development";

    private static final ContentFormat ARRANGED_CONTENT_FORMAT =
        ContentFormat.HTML;

    private static final String ARRANGED_CONTENT =
        "<p>Cache invalidation</p><p>Naming things</p>";

    private static final PublishStatus ARRANGED_PUBLISH_STATUS =
        PublishStatus.DRAFT;

    private static final Collection<String> ARRANGED_TAGS =
        Arrays.asList("development", "design");

    private static final Submission ARRANGED_SUBMISSION =
        new Submission.Builder()
            .withTitle(ARRANGED_TITLE)
            .withContentFormat(ARRANGED_CONTENT_FORMAT)
            .withContent(ARRANGED_CONTENT)
            .withPublishStatus(ARRANGED_PUBLISH_STATUS)
            .withTags(ARRANGED_TAGS)
            .build();

    private static final String ARRANGED_JSON =
        TestUtils.getResourceContents("submission.json");

    private final JsonModelConverter converter;

    public SubmissionTest() {
        converter = new JacksonModelConverter();
    }

    @Test
    public void testBuilderNoArgs() {
        assertNotNull(new Submission.Builder().build());
    }

    @Test
    public void testBuilderFullArgs() {
    }

    @Test
    public void testAsJson_Submission_HappyPath() throws JSONException {

        final Submission actualSubmission =
            converter.asSingle(Submission.class, ARRANGED_JSON);

        assertEquals(ARRANGED_TITLE, actualSubmission.getTitle());
        assertEquals(ARRANGED_TAGS, actualSubmission.getTags());
        assertEquals(ARRANGED_PUBLISH_STATUS, actualSubmission.getPublishStatus());
        assertEquals(ARRANGED_CONTENT, actualSubmission.getContent());
        assertEquals(ARRANGED_CONTENT_FORMAT, actualSubmission.getContentFormat());
    }

    @Test
    public void testAsSubmission_HappyPath() throws JSONException {
        JSONAssert.assertEquals(
            ARRANGED_JSON, converter.asJson(ARRANGED_SUBMISSION), false
        );
    }
}

