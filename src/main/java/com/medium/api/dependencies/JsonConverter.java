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

package com.medium.api.dependencies;

import java.io.IOException;

/**
 * A generic interface describing a JSON Converter.
 *
 * This is in place to help isolate other components from dependencies
 * on specific implementations of JSON libaries.
 *
 * NOTE: this interface happens to resemble Gson somewhat; this is
 * merely a hat-tip to their API design. In fact, we implement this
 * interface with Jackson.
 */
public interface JsonConverter {

    /**
     * Deserializes the provided JSON into an object of the given type.
     *
     * @param json the JSON to be deserialized
     * @param classOfT the class of type T
     * @param <T> the type of the desired object
     *
     * @return an object of type T from the provided JSON string
     *
     * @throws IOException
     *         if the JSON cannot be converted into an object due to any
     *         reason, including the case where the json is null or
     *         malformed
     */
    public <T> T fromJson(final String json, Class<T> classOfT)
            throws IOException;

    /**
     * Serializes the provided object into a JSON string.
     *
     * @param object the object to be serialized
     *
     * @return the JSON representation of the object
     *
     * @throws IOException
     *         if the provided object cannot be serialized due to any
     *         reason
     */
    public String toJson(final Object object)
            throws IOException;
}
