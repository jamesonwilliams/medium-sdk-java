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

import java.util.Collection;
import java.util.Date;

/**
 * A Post is an immutable representation of a primary published entity
 * on Medium.com. It contains markup or markdown content and metadtaa.
 * It may be associate either to a user or to a publication.
 *
 * A Post is different from an {@link Submission}; submissions may be
 * rejected and may never become Posts.
 */
public class Post {

    /**
     * A unique identifier for the post.
     */
    private final String id;

    /**
     * The post's title.
     */
    private final String title;

    /**
     * The user id of the post's author.
     */
    private final String authorId;

    /**
     * The post's tags.
     */
    private final Collection<String> tags;

    /**
     * The URL of the post on Medium.
     */
    private final String url;

    /**
     * The URL of the original publication of the post content, if
     * existant.
     */
    private final String canonicalUrl;

    /**
     * The publish status of the post.
     */
    private final PublishStatus publishStatus;

    /**
     * The post's published date, if publish status is not draft.
     */
    private final Date publishedAt;

    /**
     * The license of the post.
     */
    private final License license;

    /**
     * The URL to license of the post.
     */
    private final String licenseUrl;

    /**
     * Long args constructor mostly only for JSON serialization.
     *
     * @param id the post id
     * @param title the post title
     * @param authorId the id of the author of the post
     * @param tags the tags assocaited to the post
     * @param url the url of the post on Medium
     * @param canonicalUrl the URL to the content's original publication
     *                     location
     * @param publishStatus the post publish status
     * @param publishedAt the date of publication
     * @param license the license associated with the post
     * @param licenseUrl the url to the license
     */
    @JsonCreator
    public Post(
            @JsonProperty("id") final String id,
            @JsonProperty("title") final String title,
            @JsonProperty("authorId") final String authorId,
            @JsonProperty("tags") final Collection<String> tags,
            @JsonProperty("url") final String url,
            @JsonProperty("canonicalUrl") final String canonicalUrl,
            @JsonProperty("publishStatus") final PublishStatus publishStatus,
            @JsonProperty("publishedAt") final Date publishedAt,
            @JsonProperty("license") final License license,
            @JsonProperty("licenseUrl") final String licenseUrl) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.tags = tags;
        this.url = url;
        this.canonicalUrl = canonicalUrl;
        this.publishStatus = publishStatus;
        this.publishedAt = publishedAt;
        this.license = license;
        this.licenseUrl = licenseUrl;
    }

    /**
     * Constructs a new immutable instance of a Post from an
     * appropriately configured Builder.
     *
     * @param builder the builder from which to collect post attributes
     */
    private Post(final Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.authorId = builder.authorId;
        this.tags = builder.tags;
        this.url = builder.url;
        this.canonicalUrl = builder.canonicalUrl;
        this.publishStatus = builder.publishStatus;
        this.publishedAt = builder.publishedAt;
        this.license = builder.license;
        this.licenseUrl = builder.licenseUrl;
    }

    /**
     * Gets the unique identifier for the post.
     *
     * @return the post id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the post's metadata title.
     *
     * @return the title of the post
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the user id of the post's author.
     *
     * @return the author's user id
     */
    public String getAuthorId() {
        return authorId;
    }

    /**
     * Gets the post's tags.
     *
     * @return the post's tags
     */
    public Collection<String> getTags() {
        return tags;
    }

    /**
     * Gets the URL of the post on Medium.
     *
     * @return the URL of the post on Medium
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets the URL of the original location of the content's post, if
     * such a location exists.
     *
     * @return the URL to the post content's original location
     */
    public String getCanonicalUrl() {
        return canonicalUrl;
    }

    /**
     * Gets the publish status of the post.
     *
     * @return the publish status of the post
     */
    public PublishStatus getPublishStatus() {
        return publishStatus;
    }

    /**
     * Gets the post's published date, if the post is not a draft.
     *
     * @return the post's published date
     */
    public Date getPublishedAt() {
        return publishedAt;
    }

    /**
     * Gets the license associated with the post content.
     *
     * @return the post's license
     */
    public License getLicense() {
        return license;
    }

    /**
     * Fascilitates the construction of a Post.
     */
    public static class Builder {
        private String id;
        private String title;
        private String authorId;
        private Collection<String> tags;
        private String url;
        private String canonicalUrl;
        private PublishStatus publishStatus;
        private Date publishedAt;
        private License license;
        private String licenseUrl;

        /**
         * Sets the unique identifier for the post.
         *
         * @param id the post id
         *
         * @return the updated instance of the builder
         */
        public Builder withId(final String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the metadata title of the post.
         *
         * @param title the title of the post
         *
         * @return the updated instance of the builder
         */
        public Builder withTitle(final String title) {
            this.title = title;
            return this;
        }

        /**
         * Sets the id of the author of the post.
         *
         * @param authorId the user id of the post's author
         *
         * @return the updated instance of the builder
         */
        public Builder withAuthorId(final String authorId) {
            this.authorId = authorId;
            return this;
        }

        /**
         * Sets the tags which classify the post.
         *
         * @param tags the post's tags
         *
         * @return the updated instance of the builder
         */
        public Builder withTags(final Collection<String> tags) {
            this.tags = tags;
            return this;
        }

        /**
         * Sets the URL of the post on Medium.
         *
         * @param url the url of post on medium
         *
         * @return the updated instance of the builder
         */
        public Builder withUrl(final String url) {
            this.url = url;
            return this;
        }

        /**
         * Sets the URL of the original publication location of the post
         * content, if one exists.
         *
         * @param canonicalUrl the URL to the original publication
         *                     location of the post's content
         *
         * @return the updated instance of the builder
         */
        public Builder withCanonicalUrl(final String canonicalUrl) {
            this.canonicalUrl = canonicalUrl;
            return this;
        }

        /**
         * Sets the publish status of the post.
         *
         * @param publishStatus the post's publish status
         *
         * @return the updated instance of the builder
         */
        public Builder withPublishStatus(final PublishStatus publishStatus) {
            this.publishStatus = publishStatus;
            return this;
        }

        /**
         * Sets the post's published date; meaingful if it's not a
         * draft.
         *
         * @param publishedAt the post's published date
         *
         * @return the updated instance of the builder
         */
        public Builder withPublishAt(final Date publishedAt) {
            this.publishedAt = publishedAt;
            return this;
        }

        /**
         * Sets the license of the post.
         *
         * @param license the post's license
         *
         * @return the updated instance of the builder
         */
        public Builder withLicense(final License license) {
            this.license = license;
            return this;
        }

        /**
         * Sets the URL to the full text of the post's license.
         *
         * @param licenseUrl the URL to the license of the post
         *
         * @return the updated instance of the builder
         */
        public Builder withLicenseUrl(final String licenseUrl) {
            this.licenseUrl = licenseUrl;
            return this;
        }

        /**
         * Builds a new Post object using the attributes configured with
         * the builder.
         *
         * @return a new immutable instance of Post.
         */
        public Post build() {
            return new Post(this);
        }
    }
}

