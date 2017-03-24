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

import com.medium.api.auth.AccessToken;
import com.medium.api.auth.Scope;

import com.medium.api.model.Contributor;
import com.medium.api.model.Image;
import com.medium.api.model.Post;
import com.medium.api.model.Publication;
import com.medium.api.model.Submission;
import com.medium.api.model.User;

import java.util.Collection;
import java.util.List;

/**
 * Describes the interface to the Medium endpoint.
 */
public interface Medium {

    /**
     * Returns the URL to which an application may send a user in order
     * to acquire authorization.
     *
     * @param state a request forgery token of your choosing
     * @param redirectUrl the URL where we will send the user after they
     *                    have completed the login dialog
     * @param scopes the scopes of access being requested
     *
     * @return the authorization URL to be consumed by the end user.
     */
    String getAuthorizationUrl(
            final String state, final String redirectUrl,
            final Collection<Scope> scopes);

    /**
     * Exchanges the supplied code for a long-lived access token.
     *
     * @param code the authorization code obtained from an OAuth2
     *             callback
     * @param redirectUri the uri at which the code was received
     *
     * @return a valid OAuth2 access token to use in subsequnce
     *         API requests
     */
    AccessToken exchangeAuthorizationCode(
            final String code, final String redirectUri);

    /**
     * Exchanges the supplied refresh token for a new access token.
     *
     * @param refreshToken
     *        a valid refresh token obtained from a prior token request
     *
     * @return a valid OAuth2 access token to use in subsequent API
     *         request
     */
    AccessToken exchangeRefreshToken(final String refreshToken);

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
     * @param userId the id of the user
     *
     * @return the full list of the user's publications
     */
    List<Publication> listPublications(final String userId);

    /**
     * Lists the contributors to a publication.
     *
     * Returns a list of contributors for a given publication. In other
     * words, a list of Medium users who are allowed to publish under a
     * publication, as well as a description of their exact role in the
     * publication (for now, either an editor or a writer).
     *
     * @param publicationId the publication for which to list
     *                      contributors
     *
     * @return the full list of a publication's contributors
     */
    List<Contributor> listContributors(final String publicationId);

    /**
     * Creates a post as content associated with a user account.
     *
     * @param submission the publication request
     * @param userId the id of the user for whom the post will be
     *               published
     *
     * @return the newly published post
     */
    Post createPost(
            final Submission submission, final String userId);

    /**
     * Creates a post as content associated with a publication.
     *
     * @param submission the post being submitted for publication
     * @param publicationId the id of the publication to which the post
     *                      should be published
     *
     * @return the newly published post
     */
    Post createPostForPublication(
            final Submission submission, final String publicationId);

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
     *
     * @return a represenation of the image that was uploaded
     */
    Image uploadImage();
}

