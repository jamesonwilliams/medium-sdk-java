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

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.List;

/**
 * JacksonModelConverter is a Jackson-library implementation of a
 * JsonModelConverter.
 */
public class JacksonModelConverter implements JsonModelConverter {

    /**
     * JSON representations of our model entities are wrapped in an
     * envelope whose key name is:
     */
    private static final String ENVELOPE_FIELD_NAME = "data";

    /**
     * Instance of Jackson ObjectMapper.
     */
    private final ObjectMapper jackson;

    /**
     * Constructs a new JacksonModelConverter.
     */
    public JacksonModelConverter() {
        this(new ObjectMapper());
    }

    /**
     * Constructs a new JacksonModelConverter.
     *
     * @param mapper the mapper to use
     */
    public JacksonModelConverter(final ObjectMapper mapper) {
        this.jackson = mapper;
    }

    @Override
    public <T> T asSingle(Class<T> asType, final String json) {
        return readValueOrError(json, asType);
    }

    @Override
    public <T> List<T> asListOf(Class<T> itemType, final String json) {
        return readValueOrError(json, listOf(itemType));
    }

    @Override
    public String asJson(final Object object) {
        return writeValueAsStringOrError(object);
    }

    /**
     * Gets the JSON string value of the object, or throws an error.
     *
     * @param object the object to serialize
     *
     * @return the serialize json as a string
     */
    private String writeValueAsStringOrError(final Object object) {
        try {
            return jackson.writeValueAsString(object);
        } catch (final IOException mapperException) {
            throw new ConverterException(mapperException.getMessage());
        }
    }

    /**
     * Deserializes a JSON string into an object of a given class.
     *
     * @param <T> the type of object to deserialize into
     * @param json the json to deserialize
     * @param asClass the the class of the output object.
     *
     * @return the object representation of the JSON string
     */
    private <T> T readValueOrError(final String json, Class<T> asClass) {
        try {
            JsonNode node = maybeOpenEnvelope(json);
            return (T) jackson.treeToValue(node, asClass);
        } catch (final IOException mapperException) {
            throw new ConverterException(mapperException.getMessage());
        }
    }

    /**
     * Deserializes a JSON string into an object of a named type.
     *
     * @param <T> the type of object to deserialize into
     * @param json the json to deserialize
     * @param type the the type of the output object.
     *
     * @return the object representation of the JSON string
     */
    @SuppressWarnings("unchecked")
    private <T> T readValueOrError(final String json, JavaType type) {
        try {
            JsonNode node = maybeOpenEnvelope(json);
            return (T) jackson.readValue(
                jackson.treeAsTokens(node), type
            );
        } catch (final IOException mapperException) {
            throw new ConverterException(mapperException.getMessage());
        }
    }

    /**
     * Possibly unwrap the JSON from an envelope (if its in one.)
     *
     * @param json possibly wrapped in envelope
     *
     * @return JsonNode representing the content, without any envelope
     *         (if there were one)
     *
     * @throws IOException
     *         If the JSON cannot be deserialized as a regular object
     *         nor as an object within an envelope, *either*
     */
    private JsonNode maybeOpenEnvelope(final String json) throws IOException {
        JsonNode node = null;

        try {
            node = jackson.readTree(json).get(ENVELOPE_FIELD_NAME);
        } catch (final IOException mapperException) {
            // It's okay, maybe it wasn't in an enveloper.
        } catch (final NullPointerException nullPointerException) {
            // Ditto.
        }
        
        if (null == node) {
            // We at exceptions above, but now we'll throw if need be
            node = jackson.readTree(json);
        }

        return node;
    }

    /**
     * Gets the JavaType which is a list of items which are of a given
     * type.
     *
     * @param memberClass the class of the members of the list
     *
     * @return the list of items type as a JavaType
     */
    private JavaType listOf(final Class<?> memberClass) {
        return TypeFactory.defaultInstance()
            .constructCollectionType(List.class, memberClass);
    }
}

