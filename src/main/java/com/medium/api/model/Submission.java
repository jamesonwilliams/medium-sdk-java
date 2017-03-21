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

import java.util.Collection;

/**
 * A Submission is an immutable representation of a request to
 * Medium.com that certain content be made available as a published
 * post. 
 */
public class Submission {

    /**
     * The metadata title of the submission.
     */
    private final String title;

    /**
     * The format of the "content" field.
     */
    private final ContentFormat contentFormat;

    /**
     * The body of the subission, in a valid, semantic, HTML fragment,
     * or Markdown.
     */
    private final String content;

    /**
     * Tags to classify the submission.
     */
    private final Collection<String> tags;

    /**
     * The original home of this content, if it was originally published
     * elsewhere.
     */
    private final String canonicalUrl;

    /**
     * The requested publish status of the submission.
     */
    private final PublishStatus publishStatus;

    /**
     * The license of the submission.
     */
    private final License license;

    /**
     * Whether to notifyFollowers that the user has published.
     */
    private final boolean notifyFollowers;

    /**
     * Constructs a new immutable instance of a Submission from an
     * appropriately configured Builder.
     *
     * @param builder the instance of the builder from which submission
     *                attributes will be taken
     */
    private Submission(final Builder builder) {
        this.title = builder.title;
        this.contentFormat = builder.contentFormat;
        this.content = builder.content;
        this.tags = builder.tags;
        this.canonicalUrl = builder.canonicalUrl;
        this.publishStatus = builder.publishStatus;
        this.license = builder.license;
        this.notifyFollowers = builder.notifyFollowers;
    }

    /**
     * Gets the title of the submission.
     *
     * @return the submission's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the content format of the submission.
     *
     * @return the submission's content format
     */
    public ContentFormat getContentFormat() {
        return contentFormat;
    }

    /**
     * Gets the submission content.
     *
     * @return the submission content
     */
    public String getContent() {
        return content;
    }

    /**
     * Gets the tags used to classify the submission.
     *
     * @return the submission tags
     */
    public Collection<String> getTags() {
        return tags;
    }

    /**
     * Gets the URL of the original home of this content, if initially
     * posted elsewhere.
     *
     * @return the URL of the original home of this content.
     */
    public String getCanonicalUrl() {
        return canonicalUrl;
    }

    /**
     * Gets the requested publish status of the submission.
     *
     * @return the submission publish status
     */
    public PublishStatus getPublishStatus() {
        return publishStatus;
    }

    /**
     * Gets the license for the submission content.
     *
     * @return the license associatedd with the submission
     */
    public License getLicense() {
        return license;
    }

    /**
     * Whether or not to notify followers if the submission is
     * published.
     *
     * @return true if the followers should be notified on publication
     *         of submission; false, otherwise.
     */
    public boolean getNofifyFollowers() {
        return notifyFollowers;
    }

    /**
     * Submission.Builder fascilitates the construction of a Submission.
     */
    public static class Builder {

        private String title;
        private ContentFormat contentFormat;
        private String content;
        private Collection<String> tags;
        private String canonicalUrl;
        private PublishStatus publishStatus;
        private License license;
        private boolean notifyFollowers;

        /**
         * Sets the metadata title to associated with the submission
         * content.
         *
         * @param title the title associated with the submission content
         *
         * @return the updated instance of the submission builder
         */
        public Builder withTitle(final String title) {
            this.title = title;
            return this;
        }

        /**
         * Sets the format of the content being submitted.
         *
         * @param contentFormat the format of the content being
         *                      submitted
         *
         * @return the updated instance of the submission builder
         */
        public Builder withContentFormat(final ContentFormat contentFormat) {
            this.contentFormat = contentFormat;
            return this;
        }

        /**
         * Sets the main content being submitted.
         *
         * @param content the content being submitted
         *
         * @return the updated instance of the submission builder
         */
        public Builder withContent(final String content) {
            this.content = content;
            return this;
        }

        /**
         * Sets the tags used to classify the submission.
         *
         * @param tags the tags used to classify the submission
         *
         * @return the updated instance of the submission builder
         */
        public Builder withTags(final Collection<String> tags) {
            this.tags = tags;
            return this;
        }

        /**
         * Sets the URL to the original home of the submission content.
         *
         * @param canonicalUrl the URL to the original home of the
         *                     submission content
         *
         * @return the updated instance of the submission builder
         */
        public Builder withCanonicalUrl(final String canonicalUrl) {
            this.canonicalUrl = canonicalUrl;
            return this;
        }

        /**
         * Sets the requested publish status of the submission.
         *
         * @param publishStatus the requested publish status of the
         *                      submission
         *
         * @return the updated instance of the submission builder
         */
        public Builder withPublishStatus(final PublishStatus publishStatus) {
            this.publishStatus = publishStatus;
            return this;
        }

        /**
         * Sets the license associated with the submission content.
         *
         * @param license the license for the submission content
         *
         * @return the updated instance of the submission builder.
         */
        public Builder withLicense(final License license) {
            this.license = license;
            return this;
        }

        /**
         * Sets whether or not to notify followers when the submission
         * content is published as a post.
         *
         * @param notifyFollowers whether to notify followers on
         *                        publication
         *
         * @return the updated instance of the submission builder
         */
        public Builder withNotifyFollowers(final boolean notifyFollowers) {
            this.notifyFollowers = notifyFollowers;
            return this;
        }

        /**
         * Constructs a new Submission using the attributes configured
         * by the current instance of the Submission Builder.
         *
         * @return a new immutable instance of Submission
         */
        public Submission build() {
            return new Submission(this);
        }
    }
}

