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

package com.medium.api.test;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Utilities for working with tests.
 */
public final class TestUtils {

    /**
     * Gets the contents of the test resource as a string.
     *
     * Note: this is probably pretty "Maven"-y.
     *
     * @param resourceId the id of the resource (e.g. a file name)
     *
     * @return the contents of the resource
     */
    public static String getResourceContents(final String resourceId) {
        final ClassLoader classloader =
            Thread.currentThread().getContextClassLoader();

        final InputStream inputStream =
            classloader.getResourceAsStream(resourceId);

        final Scanner scanner =
            new Scanner(inputStream).useDelimiter("\\A");

        final String contents =
            scanner.hasNext() ? scanner.next() : "";

        return contents;
    }
}
