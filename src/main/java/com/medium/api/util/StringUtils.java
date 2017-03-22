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
package com.medium.api.util;

import java.util.Collection;

/**
 * Provides utility methods for working with String.
 */
public final class StringUtils {

    /**
     * Joins the collection of items as a string containing their
     * representations joined by a separator in between them.
     *
     * NOTE: this is available in Java 8, and in numerous libraries
     * which we aren't using to reduce number of dependencies.
     *
     * @param <T> the type of the items to be joined as a string
     * @param separator the string to use to separate the inputs
     * @param items the strings that should be joined
     *
     * @return the joined version of the input strings
     */
    public static <T> String join(final String separator,
            final Collection<T> items) {

        StringBuilder builder = new StringBuilder();

        for (T item : items) {
            if (builder.length() > 0) {
                builder.append(separator);
            }

            builder.append(item.toString());
        }

        return builder.toString();
    }
}

