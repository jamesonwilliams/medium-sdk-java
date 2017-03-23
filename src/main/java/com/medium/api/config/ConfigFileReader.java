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

package com.medium.api.config;

import com.medium.api.auth.Credentials;
import com.medium.api.auth.CredentialsNotFoundException;
import com.medium.api.auth.CredentialsProvider;
import com.medium.api.dependencies.JacksonJsonConverter;
import com.medium.api.dependencies.JsonConverter;
import com.medium.api.util.FileReader;

import java.io.IOException;

/**
 * ConfigFileReader reads a configuration file and converts it to an
 * interactable Java POJO.
 *
 * The file is expected to be in JSON and have the following contents:
 *
 * {
 *   {
 *     "clientId": "_CLIENT_ID_",
 *     "clientSecret": "_CLIENT_SECRET_"
 *   },
 *   "callbackUrl": "http://example.com/foo"
 * }
 */
public class ConfigFileReader implements CredentialsProvider {

    /**
     * A handle to the file which contains the credentials.
     */
    private final String configFilePath;

    /**
     * A JSON converter to use to deserialize the the JSON.
     */
    private final JsonConverter jsonConverter;

    /**
     * Constructs a new ConfigFileReader.
     *
     * @param configFilePath
     *        the path to the file containing the credentials
     */
    public ConfigFileReader(final String configFilePath) {
        this(configFilePath, new JacksonJsonConverter());
    }

    /**
     * Constructs a new ConfigFileReader.
     *
     * @param configFilePath
     *        the path to the file containing the credentials
     * @param jsonConveter an alternate implementation of JsonConveter
     */
    protected ConfigFileReader(
            final String configFilePath,
            final JsonConverter jsonConverter) {

        this.configFilePath = configFilePath;
        this.jsonConverter = jsonConverter;
    }

    @Override
    public Credentials getCredentials() throws CredentialsNotFoundException {
        try {
            return read().getCredentials();
        } catch (final IOException ioException) {
            throw new CredentialsNotFoundException(ioException.getMessage());
        }
    }

    /**
     * Reads in the configuration file and makes a POJO.
     *
     * @return a new instance of ConfigFile which represents the
     *         configuration file on disk.
     *
     * @throws IOException
     *         If the file on disk cannot be read
     */
    public ConfigFile read() throws IOException {
        return jsonConverter.fromJson(
            FileReader.read(configFilePath), ConfigFile.class
        );
    };
}

