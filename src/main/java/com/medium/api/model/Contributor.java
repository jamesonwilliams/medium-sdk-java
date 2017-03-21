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

public class Contributor {
    
    /**
     *  An ID for the publication.
     */
    private final String publicationId;

    /**
     * A user ID of the contributor.
     */
    private final String userId;

    /**
     * Role of the user identified by userId in the publication
     * identified by publicationId. 
     */
    private final Role role;

    /**
     * Constructs a new contributor.
     */
    public Contributor(final String publicationId,
            final String userId, final Role role) {

        this.publicationId = publicationId;
        this.userId = userId;
        this.role = role;
    }

    /**
     * Gets the ID for the publication to which a contributions was
     * made.
     *
     * @return the publication id
     */
    public String getPublicationId() {
        return publicationId;
    }

    /**
     * Gets the user ID of the contributor.
     *
     * @return the user id of the contributor
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Gets the role of the contributor, within the context of the given
     * publication.
     *
     * @return the role of the user during this contribution
     */
    public Role getRole() {
        return role;
    }
}

