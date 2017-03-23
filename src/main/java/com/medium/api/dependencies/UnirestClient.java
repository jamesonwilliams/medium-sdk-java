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


import java.io.IOException;

/**
 * Unirest client is an HTTP client used to perform GET and POST.
 */
public class UnirestClient extends Unirest implements HttpClient {

    /**
     * Constructs a new UnirestClient.
     */
    public UnirestClient() {
        super();

        // Set headers common to every Connect API reques
        setDefaultHeader("Authorization", "");
        setDefaultHeader("Content-Type", "application/json");
        setDefaultHeader("Accept", "application/json");
    
        setObjectMapper(new com.mashape.unirest.http.ObjectMapper() {
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
        });
    }

    @Override
    public <Q, A> A post(String url, Q item, Class<A> responseType) {
        try {
            return post(url).body(item).asObject(responseType).getBody();
        } catch (final UnirestException unirestException) {
            System.out.println(unirestException);
            return null;
        }
    }

    @Override
    public <A> A get(String url, Class<A> responseType) {
        try {
            return get(url).asObject(responseType).getBody();
        } catch (final UnirestException unirestException) {
            System.out.println(unirestException);
            return null;
        }
    }
}

