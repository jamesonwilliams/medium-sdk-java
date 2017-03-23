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
 * License types known to / supported by Medium.com.
 *
 * Summaries of each in lamens terms may be found at
 * https://tldrlegal.com .
 */
public enum License {
    /**
     * https://tldrlegal.com/license/all-rights-served
     */
    ALL_RIGHTS_RESERVED("all-rights-reserved"),

    /**
     * Creative Commons Attribution Only.
     */
    CC_40_BY("cc-40-by"),

    /**
     * Creative Commons Attribution and Share-Alike.
     */
    CC_40_BY_SA("cc-40-by-sa"),

    /**
     * Creative Commons Attribution and No Derivative works.
     */
    CC_40_BY_ND("cc-40-by-nd"),

    /**
     * Creative Commons Attribution and Non-Commercial usage.
     */
    CC_40_BY_NC("cc-40-by-nc"),

    /**
     * Creative Commons Attribution and No Derivative works and
     * Non-Commerical usage.
     */
    CC_40_BY_NC_ND("cc-40-by-nc-nd"),

    /**
     * Creative Commons Attribution and No Derivative works and
     * Non-Commerical usage and Share-Alike.
     */
    CC_40_BY_NC_SA("cc-40-by-nc-sa"),

    /**
     * Creative Commons CC0 1.0 Universal.
     */
    CC_40_ZERO("cc-40-zero"),

    /**
     * Public domain.
     */
    PUBLIC_DOMAIN("public-domain");

    /**
     * The type of license, stored as string.
     */
    private final String licenseAsString;

    /**
     * Converts a license string into an enumerated value.
     *
     * @param licenseAsString a string representation of an enumerated
     *                        license
     */
    private License(final String licenseAsString) {
        this.licenseAsString = licenseAsString;
    }

    @Override
    public String toString() {
        return licenseAsString;
    }
}

