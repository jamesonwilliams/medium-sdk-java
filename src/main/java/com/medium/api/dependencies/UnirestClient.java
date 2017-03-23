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

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Unirest client is an HTTP client used to perform GET and POST.
 */
public class UnirestClient extends Unirest implements HttpClient {

    /**
     * The name of the JSON field which acts as a data envelope.
     */
    private static final String ENVELOPE_FIELD_NAME = "data";

    private static final com.mashape.unirest.http.ObjectMapper MAPPER =
        new com.mashape.unirest.http.ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper =
                new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        };

    public UnirestClient(final String bearerToken) {
        this();
        setDefaultHeader("Authorization", "Bearer " + bearerToken);
    }

    /**
     * Constructs a new UnirestClient.
     */
    public UnirestClient() {
        super();

        // Set headers common to every Connect API reques
        setDefaultHeader("Content-Type", "application/json");
        setDefaultHeader("Accept", "application/json");

        setObjectMapper(MAPPER);
    }

    @Override
    public <Q, A> A post(String url, Q item, Class<A> responseType) {
        try {
            return MAPPER.readValue(
                maybeUnwrap(
                    post(url).body(item).asJson().getBody().getObject()
                ).toString(),
                responseType
            );
        } catch (final UnirestException unirestException) {
            return null;
        }
    }

    @Override
    public <A> A get(String url, Class<A> responseType) {
        try {
            return MAPPER.readValue(
                maybeUnwrap(
                    get(url).asJson().getBody().getObject()
                ).toString(),
                responseType
            );
        } catch (final UnirestException unirestException) {
            return null;
        }
    }

    /**
     * Surely there is a more elegant way to go about this, but this is
     * our method to unwrap an enveloped response.
     *
     * @param data JSON that may or may not be wrapped in an envelope.
     *
     * @return if data was in an envelope, the contents of the envelope;
     *         otherwise, just return data.
     */
    private JSONObject maybeUnwrap(final JSONObject data) {
        final JSONObject dataInsideEnvelope =
            data.getJSONObject(ENVELOPE_FIELD_NAME);

        if (null != dataInsideEnvelope) {
            return dataInsideEnvelope;
        }

        return data;
    }
}

