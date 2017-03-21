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
 * Content formats (as per a Submission) supported by Medium.
 */
public enum ContentFormat {

    /**
     * The body of the post, in a valid, semantic, HTML fragment.
     *
     * Supported tags are listed at
     * https://medium.com/@katie/a4367010924e .
     */
    HTML("html"),

    /**
     * Markdown.
     */
    MARKDOWN("markdown");

    private final String contentFormatString;

    /**
     * Gets the enumerated content format from a string representation
     * of the same.
     *
     * @param contentFormatString a content format represented in a
     *                            string
     */
    private ContentFormat(final String contentFormatString) {
        this.contentFormatString = contentFormatString;
    }

    @Override
    public String toString() {
        return contentFormatString;
    }
}

