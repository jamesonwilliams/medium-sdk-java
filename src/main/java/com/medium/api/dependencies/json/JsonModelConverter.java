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

import com.medium.api.auth.AccessToken;
import com.medium.api.config.ConfigFile;
import com.medium.api.model.Contributor;
import com.medium.api.model.Error;
import com.medium.api.model.Image;
import com.medium.api.model.Post;
import com.medium.api.model.Publication;
import com.medium.api.model.Submission;
import com.medium.api.model.User;

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
     * Deserializes the specified JSON as an {@link AccessToken}.
     *
     * @param json the JSON to deserialize
     *
     * @return an AccessToken
     */
    AccessToken asAccessToken(final String json);

    /**
     * Deserializes the specified JSON as an {@link User}.
     *
     * @param json the JSON to deserialize.
     *
     * @return a User
     */
    User asUser(final String json);

    /**
     * Deserializes the specified JSON as an {@link Error}.
     *
     * @param json the JSON to deserialize.
     *
     * @return an Error
     */
    Error asError(final String json);

    /**
     * Deserializes the specified json as a {@link Post}.
     *
     * @param json the JSON to deserialize.
     *
     * @return a Post
     */
    Post asPost(final String json);

    /**
     * Deserializes the specified json as a list of {@link Publication}.
     *
     * @param json the JSON to deserialize
     *
     * @return a Publication list
     */
    List<Publication> asPublicationList(final String json);

    /**
     * Deserialized the specified json a {@link Submission}.
     *
     * @param json the JSON to deserialize
     *
     * @return a Submission
     */
    Submission asSubmission(final String json);

    /**
     * Deserializes the specified JSON as an {@link Image}.
     *
     * @param json the JSON to deserialize
     *
     * @return an Image
     */
    Image asImage(final String json);

    /**
     * Deserializes the specified JSON as a {@link ConfigFile}.
     *
     * @param json the JSON to deserialize
     *
     * @return a ConfigFile
     */
    ConfigFile asConfigFile(final String json);

    /**
     * Deserializes the specified JSON as a list of {@link Contributor}.
     *
     * @param json the JSON to deserialize
     *
     * @return a Contributor list
     */
    List<Contributor> asContributorList(final String json);
}
