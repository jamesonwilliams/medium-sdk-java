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

package com.medium.api.auth;

import static org.junit.Assert.assertEquals;

import com.medium.api.dependencies.json.JacksonModelConverter;
import com.medium.api.dependencies.json.JsonModelConverter;
import com.medium.api.test.TestUtils;

import org.junit.Before;
import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Test that the AccessToken can be serialized and deserialized.
 */
public class AccessTokenTest {

    // Fields match those in the ARRANGED_JSON file.
    private static final AccessToken ARRANGED_ACCESS_TOKEN = new AccessToken(
        "Bearer",
        "e27bb85c111d0f4fb27597db25fd4d359ff066ba10d6779ec2aa01bfa60a0a958",
        "244921d8529c565dfd9097f3bf7ecd66231cdb51807bdf2c875ed23109e4c0893",
        Arrays.asList(Scope.BASIC_PROFILE, Scope.PUBLISH_POST),
        1490323450314L
    );

    final String ARRANGED_JSON =
        TestUtils.getResourceContents("access-token.json");

    /**
     * We are testing both the converter and the AccessToken POJO, at
     * once.
     */
    private JsonModelConverter converter;

    /**
     * Setups the the object under test, the JacksonModelConverter.
     */
    @Before
    public void setup() {
        converter = new JacksonModelConverter();
    }

    /**
     * Test deserialization.
     */
    @Test
    public void testAsSingle_HappyPath() {

        // Act
        final AccessToken actualToken =
            converter.asSingle(AccessToken.class, ARRANGED_JSON);

        // Assert
        assertEquals(
            ARRANGED_ACCESS_TOKEN.getTokenType(),
            actualToken.getTokenType()
        );
        assertEquals(
            ARRANGED_ACCESS_TOKEN.getAccessToken(),
            actualToken.getAccessToken()
        );
        assertEquals(
            ARRANGED_ACCESS_TOKEN.getRefreshToken(),
            actualToken.getRefreshToken()
        );
        assertEquals(
            ARRANGED_ACCESS_TOKEN.getScope(),
            actualToken.getScope()
        );
        assertEquals(
            ARRANGED_ACCESS_TOKEN.getExpiresAt(),
            actualToken.getExpiresAt()
        );
    }

    /**
     * Test serialization.
     */
    @Test
    public void testAsJson_AccessToken_HappyPath() {
        // Act
        final String actualJson =
            converter.asJson(ARRANGED_ACCESS_TOKEN);

        // Assert
        JSONAssert.assertEquals(ARRANGED_JSON, actualJson, false);
    }
}

