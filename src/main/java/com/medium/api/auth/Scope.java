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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The scopes for an authorized user's API requests.
 */
public enum Scope {
    /**
     * Grants basic access to a user’s profile (not including their
     * email).
     */
    BASIC_PROFILE("basicProfile"),

    /**
     * Grants the ability to list publications related to the user.
     */
    LIST_PUBLICATIONS("listPublications"),

    /**
     * Grants the ability to publish a post to the user’s profile.
     */
    PUBLISH_POST("publishPost"),

    /**
     * Grants the ability to upload an image for use within a Medium
     * post.
     */
    UPLOAD_IMAGE("uploadImage");

    private final String scopeString;

    /**
     * Gets the enumerated scope from a string representation.
     *
     * @param a string representation of a scope
     */
    @JsonCreator
    private Scope(final String scopeString) {
        this.scopeString = scopeString;
    }

    @JsonValue
    @Override
    public String toString() {
        return scopeString;
    }
}

