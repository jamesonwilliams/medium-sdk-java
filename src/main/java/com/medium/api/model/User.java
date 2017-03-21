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

/**
 * User is an immutable representation of a human interactor with
 * Medium.
 */
public class User {

    /**
     * Constructs a new user from a builder.
     *
     * @param builder the builder from which to construct the user
     */
    private User(final User.Builder builder) {
        this.id = builder.id;
        this.userName = builder.userName;
        this.name = builder.name;
        this.url = builder.url;
        this.imageUrl = imageUrl;
    }

    /**
     * A unique identifier for the user.
     */
    private String id;

    /**
     * The user’s username on Medium.
     */
    private String userName;

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
     * User.Builder provides a simplified method of constructing
     * instances of User.
     */
    public static class Builder {

        private String id;
        private String userName;
        private String name;
        private String url;
        private String imageUrl;

        /**
         * Sets the user's unique identifier.
         *
         * @param id a unique identifier for the user.
         *
         * @return the updated instance of the user builder
         */
        public Builder withId(final String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the username used by the user on Medium.
         *
         * @param userName the user's username on Medium
         *
         * @return the updated instance of the user builder
         */
        public Builder withUserName(final String userName) {
            this.userName = userName;
            return this;
        }

        /**
         * Sets the name of the user as used on Medium.
         *
         * @param name the user's name as used on Medium.
         *
         * @return the updated instance of the user builder
         */
        public Builder withName(final String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the URL of the user's profile on Medium.
         *
         * @param url the URL of the user's profile on Medium.
         *
         * @return the updated instance of the user builder
         */
        public Builder withUrl(final String url) {
            this.url = url;
            return this;
        }

        /**
         * Sets the URL of user's avatar on Medium.
         *
         * @param imageUrl the URL of the user's avatar on Medium
         *
         * @return the updated instance of the user builder
         */
        public Builder withImageUrl(final String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        /**
         * Builds a new User with the various configured attributes.
         *
         * @return a new immutable instance of a user, whose attributes
         *         were set by configuration of the builder
         */
        public User build() {
            return new User(this);
        }
    }
}

