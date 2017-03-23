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
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The types of images that can be uploaded.
 */
public enum ImageType {
    /**
     * JPEG image.
     */
    JPEG("image/jpeg"),

    /**
     * PNG image.
     */
    PNG("image/png"),

    /**
     * Animated gifs are supported. Use your power for good.
     */
    GIF("image/gif"),

    /**
     * TIFF image.
     */
    TIFF("image/tiff");

    private final String imageTypeString;

    /**
     * Gets the enumerated image type from a string representation of
     * the same.
     *
     * @param imageTypeString an image type represented as a string
     */
    @JsonCreator
    private ImageType(final String imageTypeString) {
        this.imageTypeString = imageTypeString;
    }

    @JsonValue
    @Override
    public String toString() {
        return imageTypeString;
    }
}

