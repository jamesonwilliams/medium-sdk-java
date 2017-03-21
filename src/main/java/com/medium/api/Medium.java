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
package com.medium.api;

import com.medium.api.model.Contributor;
import com.medium.api.model.Image;
import com.medium.api.model.Post;
import com.medium.api.model.Publication;
import com.medium.api.model.Submission;
import com.medium.api.model.User;

import java.util.List;

/**
 * Described the interface to the Medium endpoint.
 */
public interface Medium {
    /**
     * Gets details about the user.
     *
     * @return details about the currently authenticated user.
     */
    User getUser();

    /**
     * Lists the user's publications.
     *
     * Returns a full list of publications that the user is related to
     * in some way: This includes all publications the user is
     * subscribed to, writes to, or edits.
     *
     * @return the full list of the user's publications
     */
    List<Publication> listPublications();

    /**
     * Lists the contributors to a publication.
     *
     * Returns a list of contributors for a given publication. In other
     * words, a list of Medium users who are allowed to publish under a
     * publication, as well as a description of their exact role in the
     * publication (for now, either an editor or a writer).
     *
     * @return the full list of a publication's contributors
     */
    List<Contributor> listPublicationContributors();

    /**
     * Creates a post under the authenticated user’s profile.
     *
     * @param request the publication request
     *
     * @return the newly published post
     */
    Post publishPost(Submission submission);

    /**
     * Publishes a post under a given publication.
     *
     * @param submission the post being submitted for publication
     * @param publication the publication to which the post should be
     *                    published
     *
     * @return the newly published post
     */
    Post publishPost(Submission submission, Publication publication);

    /**
     * Uploads an image to Medium.
     *
     * Most integrations will not need to use this resource. Medium will
     * automatically side-load any images specified by the src attribute
     * on an <img> tag in post content when creating a post. However, if
     * you are building a desktop integration and have local image files
     * that you wish to send, you may use the images endpoint.
     *
     * Unlike other API endpoints, this requires multipart form-encoded
     * data.
     */
    Image uploadImage();
}

