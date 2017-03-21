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
 * Implementation of the {@link Medium} API.
 */
public class MediumClient implements Medium {

    public MediumClient() {
    }

    public User getUser() {
        return null;
    }

    public List<Publication> listPublications() {
        return null;
    }

    public List<Contributor> listPublicationContributors() {
        return null;
    }

    public Post publishPost(Submission submission) {
        return null;
    }

    public Post publishPost(Submission submission, Publication publication) {
        return null;
    }

    public Image uploadImage() {
        return null;
    }
}

