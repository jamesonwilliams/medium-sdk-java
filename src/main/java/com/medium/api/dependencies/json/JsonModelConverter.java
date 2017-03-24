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

import java.util.List;

/**
 * A Json Model Converter must be capable of serializing and
 * deserializing all of the types in the model.
 *
 * NOTE: you want so badly for your Java-language Json Serialization
 * library to "just work" and handle complex types and immutable
 * objects. But it just doesn't. So that's why we are getting explicit
 * about our model's type names in this interface.
 */
public interface JsonModelConverter {

    /**
     * Serializes the specified object to JSON.
     *
     * @param object the object to serialize.
     *
     * @return the JSON serialization of Object, as as String.
     */
    String asJson(final Object object);

    /**
     * Deserializes JSON as a single item of a given type.
     *
     * @param <T> the type of returned item
     * @param asType the class of the item returned
     * @param json the JSON to deserialize
     *
     * @return an object of the requested class, deserialized from the
     *         provided json string
     */
    <T> T asSingle(final Class<T> asType, final String json);

    /**
     * Deserializes JSON as a list of objects of a given type.
     *
     * @param <T> the type of the items in the result list
     * @param asType the class of the items in the list
     * @param json the JSON to deserialize
     *
     * @return a list of objects of the requested class, deserialized
     *         from the provided JSON string.
     */
    <T> List<T> asListOf(final Class<T> asType, final String json);
}

