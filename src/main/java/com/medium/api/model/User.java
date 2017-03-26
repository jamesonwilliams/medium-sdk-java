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

package com.medium.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User is an immutable representation of a human interactor with
 * Medium.
 */
public class User {

    /**
     * The name of the JSON key for the id.
     */
    private static final String JSON_KEY_ID = "id";

    /**
     * The name of the JSON key for the username.
     */
    private static final String JSON_KEY_USERNAME = "username";

    /**
     * The name of the JSON key for the name.
     */
    private static final String JSON_KEY_NAME = "name";

    /**
     * The name of the JSON key for the URL.
     */
    private static final String JSON_KEY_URL = "url";

    /**
     * The name of the JSON key for the image URL.
     */
    private static final String JSON_KEY_IMAGE_URL = "imageUrl";

    /**
     * A unique identifier for the user.
     */
    private String id;

    /**
     * The user’s username on Medium.
     */
    private String username;

    /**
     * The user’s name on Medium.
     */
    private String name;

    /**
     * The URL to the user’s profile on Medium.
     */
    private String url;

    /**
     * The URL to the user’s avatar on Medium.
     */
    private String imageUrl;

    /**
     * Constructs a new user.
     *
     * @param id the id of the user
     * @param username the username of the user
     * @param name the person name of the user
     * @param url url to the user's profile on Medium
     * @param imageUrl url to the user's avatar
     */
    @JsonCreator
    private User(
            @JsonProperty(JSON_KEY_ID)       final String id,
            @JsonProperty(JSON_KEY_USERNAME) final String username,
            @JsonProperty(JSON_KEY_NAME)     final String name,
            @JsonProperty(JSON_KEY_URL)      final String url,
            @JsonProperty(JSON_KEY_IMAGE_URL)final String imageUrl) {

        this.id = id;
        this.username = username;
        this.name = name;
        this.url = url;
        this.imageUrl = imageUrl;
    }

    /**
     * Gets the unique identifier for the user.
     *
     * @return the user's unique identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the user's username on Medium.
     *
     * @return the user's username on Medium
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the user's name on Medium
     *
     * @return the user's name on Medium
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the URL to the user's profile on Medium.
     *
     * @return the URL to the user's profile on Medium
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets the URL to the user's avatar on Medium.
     *
     * @return the URL to user's avatar on Medium
     */
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String toString() {
        return String.format(
            "id: %s\nusername: %s\nname: %s\nurl: %s\nimageUrl: %s\n",
            id, username, name, url, imageUrl
        );
    }
}

