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

package com.medium.api.util;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * FileReader is a simple utility to read the contents of a file.
 */
public final class FileReader {

    /**
     * Reads the contents of the file at the given path, using the
     * default platform encoding.
     *
     * @param path the path to the file to be read
     *
     * @return the contents of the file as a string
     * @throws IOException
     *         If the file cannot be read
     */
    public static String read(final String path) throws IOException {
        return new Scanner(new File(path)).useDelimiter("\\Z").next();
    }
}

