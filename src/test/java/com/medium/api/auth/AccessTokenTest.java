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

    private static final String ARRANGED_TOKEN_TYPE = "Bearer";

    private static final String ARRANGED_ACCESS_TOKEN =
        "e27bb85c111d0f4fb27597db25fd4d359ff066ba10d6779ec2aa01bfa60a0a958";

    private static final String ARRANGED_REFRESH_TOKEN =
        "244921d8529c565dfd9097f3bf7ecd66231cdb51807bdf2c875ed23109e4c0893";

    private static final List<Scope> ARRANGED_SCOPE =
        Arrays.asList(Scope.BASIC_PROFILE, Scope.PUBLISH_POST);

    private static final long ARRANGED_EXPIRES_AT = 1490323450314L;

    final String ARRANGED_JSON =
        TestUtils.getResourceContents("access-token.json");

    /**
     * The thing we are testing, identified by its interface.
     */
    private JsonModelConverter objectUnderTest;

    /**
     * Setups the the object under test, the JacksonModelConverter.
     */
    @Before
    public void setup() {
        objectUnderTest = new JacksonModelConverter();
    }

    @Test
    public void testAsAccessToken_HappyPath() {

        // ARRANGED_JSON is already arranged

        // Act
        final AccessToken actualToken =
            objectUnderTest.asAccessToken(ARRANGED_JSON);

        // Assert
        assertEquals(ARRANGED_TOKEN_TYPE, actualToken.getTokenType());
        assertEquals(ARRANGED_ACCESS_TOKEN, actualToken.getAccessToken());
        assertEquals(ARRANGED_REFRESH_TOKEN, actualToken.getRefreshToken());
        assertEquals(ARRANGED_SCOPE, actualToken.getScope());
        assertEquals(ARRANGED_EXPIRES_AT, actualToken.getExpiresAt());
    }

    @Test
    public void testAsJson_AccessToken_HappyPath() {
        // Arrange
        final AccessToken arrangedToken = new AccessToken(
            ARRANGED_TOKEN_TYPE,
            ARRANGED_ACCESS_TOKEN,
            ARRANGED_REFRESH_TOKEN,
            ARRANGED_SCOPE,
            ARRANGED_EXPIRES_AT
        );

        // Act
        final String actualJson = objectUnderTest.asJson(arrangedToken);

        // Assert
        JSONAssert.assertEquals(ARRANGED_JSON, actualJson, false);
    }
}

