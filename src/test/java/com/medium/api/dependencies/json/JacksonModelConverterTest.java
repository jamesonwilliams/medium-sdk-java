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

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.medium.api.auth.RefreshTokenRequest;
import com.medium.api.model.Contributor;
import com.medium.api.model.Publication;
import com.medium.api.model.Submission;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;

import java.io.IOException;
import java.util.Arrays;

/**
 * Tests dependency failure scenarios in the JacksonModelConverter (ie
 * what happens when Jackson throws an exception.)
 *
 * Serialization/Deserialization tests for models are broken out
 * per-model, to avoid having a gigantic converter test (and also since
 * there is little else to test for those.)
 */
public class JacksonModelConverterTest {

    @Mock
    private ObjectMapper jackson;

    private JsonModelConverter converter;

    @Before
    public void setup() {
        initMocks(this);
        converter = new JacksonModelConverter(jackson);
    }

    /**
     * Makes sure we are wrapping the depenceny exceptions into our
     * "dependency wall" type.
     */
    @Test(expected = ConverterException.class)
    public void testWriteValueAsStringThrowsJsonProcessingException()
            throws JsonProcessingException {

        doThrow(mock(JsonProcessingException.class))
            .when(jackson)
            .writeValueAsString(any(RefreshTokenRequest.class));

        converter.asJson(mock(RefreshTokenRequest.class));
    }

    /**
     * Makes sure we are wrapping the depenceny exceptions into our
     * "dependency wall" type.
     */
    @SuppressWarnings("unchecked")
    @Test(expected = ConverterException.class)
    public void testTreeToValueThrowsJsonProcessingException()
            throws JsonProcessingException, IOException {

        // The call stack required readTree() before getting to the
        // where treeToValue() is called, so let's assume that works
        when(jackson.readTree(anyString()))
            .thenReturn(mock(JsonNode.class));

        // Uh oh, but treeToValue() didn't.
        doThrow(mock(JsonProcessingException.class))
            .when(jackson)
            .treeToValue(any(JsonNode.class), any(Class.class));

        converter.asSingle(RefreshTokenRequest.class, "{[]}");
    }

    /**
     * Makes sure we are wrapping the depenceny exceptions into our
     * "dependency wall" type.
     */
    @Test(expected = ConverterException.class)
    public void testReadValueThrowsJsonProcessingException()
            throws IOException {

        // The call to readValue() is nested under a few other calls to
        // Jackson, so let's assume that those are working.
        when(jackson.readTree(anyString()))
            .thenReturn(mock(JsonNode.class));
        when(jackson.treeAsTokens(any(JsonNode.class)))
            .thenReturn(mock(JsonParser.class));

        // Uh oh, but readValue() didn't work.
        doThrow(mock(JsonProcessingException.class))
            .when(jackson)
            .readValue(any(JsonParser.class), any(JavaType.class));

        converter.asListOf(Contributor.class, "{[]}");
    }

    /**
     * Makes sure we are wrapping the depenceny exceptions into our
     * "dependency wall" type.
     */
    @Test(expected = ConverterException.class)
    public void testReadTreeThrowsJsonProcessingException()
            throws IOException {

        doThrow(mock(JsonProcessingException.class))
            .when(jackson)
            .readTree(anyString());

        converter.asListOf(Publication.class, "{[]}");
    }
}

