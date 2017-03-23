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

package com.medium.api.example;

import com.medium.api.Medium;
import com.medium.api.MediumClient;

import com.medium.api.config.ConfigFile;
import com.medium.api.config.ConfigFileReader;

import com.medium.api.model.ContentFormat;
import com.medium.api.model.License;
import com.medium.api.model.Post;
import com.medium.api.model.Publication;
import com.medium.api.model.PublishStatus;
import com.medium.api.model.Submission;
import com.medium.api.model.User;

import java.io.IOException;
import java.util.Arrays;

/**
 * A simple example of using an API token make a request.
 */
public class SimpleTokenExample {

    /**
     * Invoked the test program.
     *
     * @param args command line arguments; not used.
     *
     * @throws IOException
     *         If the config file cannot be read.
     */
    public static void main(final String[] args) throws IOException {

        // Get configuration information form local config file.
        ConfigFile config = new ConfigFileReader("./medium-config.json").read();

        // Obtain a handle to the Medium API.
        Medium medium = new MediumClient(config.getAccessToken());

        // Get information about the user.
        User user = medium.getUser();
        System.out.println("Hello, " + user.getName());

        // Submit a post for publication.
        Submission submission = new Submission.Builder()
            .withUserId(user.getId())
            .withTitle("A Great Day for a Java SDK!")
            .withContentFormat(ContentFormat.MARKDOWN)
            .withContent("```Medium medium = new MediumClient(TOKEN);```")
            .withTags(Arrays.asList("sdk"))
            .withPublishStatus(PublishStatus.UNLISTED)
            .withLicense(License.CC_40_BY)
            .withNotifyFollowers(false) // Just a test!
            .build();

        Post post = medium.publishPost(submission);

        System.out.println(String.format(
            "Published \"%s\" to \"%s\" at %s\n",
            post.getTitle(), post.getUrl(), post.getPublishedAt()
        ));

        for (Publication item : medium.listPublications(user.getId())) {
            System.out.println(item.getName());
        }
    }
}
