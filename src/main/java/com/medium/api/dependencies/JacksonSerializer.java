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

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * JacksonSerializer is a Jackson-library implementation of a
 * JsonSerializer.
 */
public class JacksonSerializer implements JsonSerializer {
    private final ObjectMapper mapper;

    public JacksonSerializer(final ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public <T> T fromJson(final String json, Class<T> classOfT)
            throws IOException {

        return mapper.readValue(json, classOfT);
    }

    @Override
    public String toJson(final Object object)
            throws IOException {

        return mapper.writeValueAsString(object);
    }
}

