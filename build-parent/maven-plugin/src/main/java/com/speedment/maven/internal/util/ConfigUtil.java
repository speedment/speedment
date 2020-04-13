/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.maven.internal.util;

import org.apache.maven.plugin.logging.Log;

import java.nio.file.Files;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

public final class ConfigUtil {

    private ConfigUtil() {}

    /**
     * Returns if the specified file is non-null, exists and is readable. If,
     * not, {@code false} is returned and an appropriate message is shown in the
     * console.
     *
     * @param file the config file to check
     * @param log the log for outputting messages
     * @return {@code true} if available, else {@code false}
     */
    public static boolean hasConfigFile(Path file, Log log) {
        requireNonNull(log);

        if (file == null) {
            final String msg = "The expected .json-file is null.";
            log.info(msg);
            return false;
        } else if (!file.toFile().exists()) {
            final String msg = "The expected .json-file '"
                + file + "' does not exist.";
            log.info(msg);
            return false;
        } else if (!Files.isReadable(file)) {
            final String err = "The expected .json-file '"
                + file + "' is not readable.";
            log.error(err);
            return false;
        } else {
            return true;
        }
    }
}
