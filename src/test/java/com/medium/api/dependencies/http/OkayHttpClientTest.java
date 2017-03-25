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

package com.medium.api.dependencies.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import com.medium.api.test.TestUtils;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Test the OkayHttpClient implementation.
 *
 * Since the okhttp3 library is pretty much entirely comprised of final
 * classes, Mockito is of practically almost no use at all. So, instead
 * we have to use the vendor tool, this MockWebServer which will server
 * mock HTTP responses.
 */
public class OkayHttpClientTest {

    private static final String BEARER = "Bearer";
    private static final String TEST_TOKEN = "VERY_SECRET_STUFF";
    private static final String NEW_TOKEN ="NuToken(22)";
    private static final String AUTHORIZATION = "Authorization";

    // Just some random JSON
    private static final String TEST_JSON =
            TestUtils.getResourceContents("contributor-list.json");

    private static final String TEST_URL = "/v1/chat/";

    private MockWebServer server = new MockWebServer();
    private OkayHttpClient objectUnderTest;

    @Before
    public void setup() throws IOException {
        objectUnderTest = new OkayHttpClient(TEST_TOKEN);
        server.start();
    }

    @After
    public void teardown() throws IOException {
        server.shutdown();
    }

    /**
     * Test that get() can return content from the body of a valid and
     * successful response.
     */
    @Test
    public void testGet_HappyPath() throws IOException, InterruptedException {
        server.enqueue(new MockResponse().setBody(TEST_JSON));

        final String url = server.url(TEST_URL).toString();
        assertEquals(TEST_JSON, objectUnderTest.get(url));

        validateRequest(server.takeRequest());
    }

    /**
     * Test that a 500 error on a get call materializes as our
     * HttpException type.
     *
     * @throws IOException
     *         Must not happen; this is thrown by okhttp3 itself
     */
    @Test(expected=HttpException.class)
    public void testGet_500() throws IOException {
        server.enqueue(new MockResponse().setResponseCode(503));
        objectUnderTest.get(server.url(TEST_URL).toString());
    }

    /**
     * Test that post() can return conten from the body of a valid and
     * successful response.
     *
     * @throws IOException
     *         Must not happen; this comes from okhttp3 itself
     * @throws InterruptedException
     *         On issues with this test infrastructure itself
     */
    @Test
    public void testPost_HappyPath() throws IOException, InterruptedException {
        server.enqueue(new MockResponse().setBody(TEST_JSON));

        final String url = server.url(TEST_URL).toString();
        assertEquals(TEST_JSON, objectUnderTest.post(url, TEST_JSON));

        validateRequest(server.takeRequest());
    }

    /**
     * Test that a simulated poorly formed post() request will generate
     * an HttpExcpeiton.
     *
     * @throws IOException
     *         Must not happen, this is from okhttp3 itself
     */
    @Test(expected=HttpException.class)
    public void testPost_400() throws IOException {
        server.enqueue(new MockResponse().setResponseCode(400));
        objectUnderTest.get(server.url(TEST_URL).toString());
    }

    /**
     * Tests that a call to setBearerToken() has the result of updating
     * the token that is used in the request headers.
     *
     * @throws IOException
     *         Must not happen, this is from okhttp3 itself
     * @throws InterruptedException
     *         On issues with this test infrastructure itself
     */
    @Test
    public void testSetBearerToken_HappyPath()
            throws InterruptedException, IOException {

        // Arrange
        server.enqueue(new MockResponse().setBody(TEST_JSON));
        server.enqueue(new MockResponse().setBody(TEST_JSON));
        final String url = server.url(TEST_URL).toString();

        // First call uses old bearer token
        assertEquals(TEST_JSON, objectUnderTest.post(url, ""));
        final RecordedRequest beforeSetBearer = server.takeRequest();
        validateRequest(beforeSetBearer);

        // Act
        objectUnderTest.setBearerToken(NEW_TOKEN);

        // Assert -- this call uses new bearer token
        assertEquals(TEST_JSON, objectUnderTest.post(url, ""));
        final RecordedRequest afterSetBearer = server.takeRequest();
        assertNotEquals(
            beforeSetBearer.getHeader(AUTHORIZATION),
            afterSetBearer.getHeader(AUTHORIZATION)
        );
        assertEquals(
            afterSetBearer.getHeader(AUTHORIZATION),
            String.format("%s %s", BEARER, NEW_TOKEN)
        );
    }

    /**
     * This is fair game, I guess, but just make sure the entire header
     * is gone not just the token value.
     *
     * @throws IOException
     *         Must not do this
     * @throws InterruptedException
     *         On issues with our test infrastructure
     */
    @Test
    public void testSetBearerToken_Null()
            throws IOException, InterruptedException {

        server.enqueue(new MockResponse().setBody(TEST_JSON));

        final String url = server.url(TEST_URL).toString();

        objectUnderTest.setBearerToken(null);

        assertEquals(TEST_JSON, objectUnderTest.post(url, ""));
        final RecordedRequest afterSetBearer = server.takeRequest();
        assertNull(afterSetBearer.getHeader(AUTHORIZATION));
    }

    /**
     * Validates that a request was to our test URL, and that it
     * contained the expected authorization header.
     */
    private void validateRequest(final RecordedRequest request) {
        assertEquals(TEST_URL, request.getPath());
        assertEquals(
            request.getHeader(AUTHORIZATION),
            String.format("%s %s", BEARER, TEST_TOKEN)
        );
    }
}

