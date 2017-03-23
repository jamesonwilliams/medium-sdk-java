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

import java.io.IOException;

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

        ConfigFile config = new ConfigFileReader("./medium-config.json").read();
        final String accessToken = config.getAccessToken();

        Medium medium = new MediumClient(accessToken);

        System.out.println(medium.getUser());
    }
}
