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
package com.medium.api.auth;

import com.medium.api.config.ConfigFile;
import com.medium.api.dependencies.JacksonJsonConverter;
import com.medium.api.dependencies.JsonConverter;
import com.medium.api.util.FileReader;

import java.io.IOException;
import java.security.ProviderException;

/**
 * ConfigFileCredentialsProvider provides credentials by obtaining
 * them from a local configuration file.
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
public class ConfigFileCredentialsProvider implements CredentialsProvider {

    /**
     * A handle to the file which contains the credentials.
     */
    private final String configFilePath;

    /**
     * A JSON converter to use to deserialize the the JSON.
     */
    private final JsonConverter jsonConverter;

    /**
     * Constructs a new ConfigFileCredentialsProvider.
     *
     * @param configFilePath
     *        the path to the file containing the credentials
     */
    public ConfigFileCredentialsProvider(final String configFilePath) {
        this(configFilePath, new JacksonJsonConverter());
    }

    /**
     * Constructs a new ConfigFileCredentialsProvider.
     *
     * @param configFilePath
     *        the path to the file containing the credentials
     * @param jsonConveter an alternate implementation of JsonConveter
     */
    protected ConfigFileCredentialsProvider(
            final String configFilePath,
            final JsonConverter jsonConverter) {

        this.configFilePath = configFilePath;
        this.jsonConverter = jsonConverter;
    }

    @Override
    public Credentials getCredentials() {
        try {
            final ConfigFile configFile = jsonConverter.fromJson(
                FileReader.read(configFilePath), ConfigFile.class
            );

            return configFile.getCredentials();
        } catch (final IOException ioException) {
            throw new ProviderException(ioException);
        }
    }
}

