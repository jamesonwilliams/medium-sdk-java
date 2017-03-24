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

import com.medium.api.dependencies.json.JacksonModelConverter;
import com.medium.api.dependencies.json.JsonModelConverter;
import com.medium.api.test.TestUtils;

import org.junit.Before;
import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;

import java.util.Arrays;
import java.util.List;

/**
 * Tests that the Publication object can be serialized and deserialized.
 */
public class PublicationTest {

    private static final List<Publication> ARRANGED_PUBLICATION_LIST = Arrays.asList(
        new Publication.Builder()
            .withId("b969ac62a46b")
            .withName("About Medium")
            .withDescription("What is this thing and how does it work?")
            .withUrl("https://medium.com/about")
            .withImageUrl("https://cdn-images-1.medium.com/fit/c/200/200/0*ae1jbP_od0W6EulE.jpeg")
            .build(),
        new Publication.Builder()
            .withId("b45573563f5a")
            .withName("Developers")
            .withDescription("Medium’s Developer resources")
            .withUrl("https://medium.com/developers")
            .withImageUrl("https://cdn-images-1.medium.com/fit/c/200/200/1*ccokMT4VXmDDO1EoQQHkzg@2x.png")
            .build()
    );

    private static final String ARRANGED_JSON =
        TestUtils.getResourceContents("publication-list.json");

    /**
     * We are testing both the Publication POJO as well as the
     * JsonModelConverter, at the same time.
     */
    private JsonModelConverter converter;

    @Before
    public void setup() {
        converter = new JacksonModelConverter();
    }

    /**
     * Test deserialization of a list of Publications.
     */
    @Test
    public void testAsListOf_HappyPath() {

        // Act
        final List<Publication> actualPublicationList =
            converter.asListOf(Publication.class, ARRANGED_JSON);

        for (int index = 0; index < ARRANGED_PUBLICATION_LIST.size(); index++) {
            assertPublicationListEquals(
                ARRANGED_PUBLICATION_LIST.get(index),
                actualPublicationList.get(index)
            );
        }
    }

    private void assertPublicationListEquals(
            final Publication expected, final Publication actual) {

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getUrl(), actual.getUrl());
        assertEquals(expected.getImageUrl(), actual.getImageUrl());
    }

    /**
     * Test serialization of a list of Publications.
     */
    @Test
    public void testAsJson_Publication_HappyPath() {
        // We have to account for the data envelope that was stripped
        // out -- add it back (it exists in the test document.)
        final String actualJson = TestUtils.wrapInEnvelope(
            converter.asJson(ARRANGED_PUBLICATION_LIST)
        );

        JSONAssert.assertEquals(ARRANGED_JSON, actualJson, false);
    }
}

