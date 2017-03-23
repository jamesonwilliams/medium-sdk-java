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
 * The publishing status of a submission or post.
 */
public enum PublishStatus {

    /**
     * Public stories may be distributed in streams, Medium digests, or
     * directly through their URL.
     */
    PUBLIC("public"),

    /**
     * Like unlisted stories, drafts can be seen by anyone with the
     * share link.
     */
    DRAFT("draft"),

    /**
     * Unlisted posts, or highlights on unlisted posts, do not appear in
     * the stream, digests, or other Medium-native distribution means.
     * This is like non-broadcast publishing. But anyone with the link
     * can view them. No password or token is needed.
     */
    UNLISTED("unlisted");

    private final String publishStatusString;

    /**
     * Gets the enumerated publish status from a string representation
     * of the same.
     */
    private PublishStatus(final String publishStatusString) {
        this.publishStatusString = publishStatusString;
    }

    @Override
    public String toString() {
        return publishStatusString;
    }
}

