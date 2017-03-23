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
 * A Contributor contributes to a publication under a Role. The types of
 * roles that the contributor may assume are defined here.
 */
public enum Role {

    EDITOR("editor"),
    WRITER("writer");

    private final String roleName;

    /**
     * Gets the enumerated role from a string representation of the
     * same.
     *
     * @param the name of the role
     */
    private Role(final String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return roleName;
    }
}

