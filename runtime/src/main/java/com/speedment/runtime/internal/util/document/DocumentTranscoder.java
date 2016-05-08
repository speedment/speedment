/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.internal.util.document;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.config.db.Project;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.internal.config.ProjectImpl;
import static com.speedment.runtime.util.StaticClassUtil.instanceNotAllowed;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public final class DocumentTranscoder {

    public static final String ROOT = "config";

    public static String save(Project project) throws SpeedmentException {
        final Gson gson = newGson();
        final String json = gson.toJson(project);
        return json;
    }

    /**
     * Saves the project in a UTF-8 encoded file.
     *
     * @param project to save
     * @param location for the UTF-8 encoded file
     * @throws SpeedmentException if the file could not be saved
     */
    public static void save(Project project, Path location) throws SpeedmentException {
        try {
            Files.write(location, save(project).getBytes(StandardCharsets.UTF_8));
        } catch (final IOException ex) {
            throw new SpeedmentException(
                "Could not save json-file to path '" + location + "'.", ex
            );
        }
    }

    public static Project load(String json) throws SpeedmentException {
        final Gson gson = newGson();
        return gson.fromJson(json, ProjectImpl.class);
    }

    /**
     * Loads a project from a UTF-8 encoded file.
     *
     * @param location for the UTF-8 encoded file
     * @return that was loaded
     * @throws SpeedmentException if the file could not be loaded
     */
    public static Project load(Path location) throws SpeedmentException {
        try {
            final byte[] content = Files.readAllBytes(location);
            return load(new String(content, StandardCharsets.UTF_8));
        } catch (final IOException ex) {
            throw new SpeedmentException(
                "Could not load json-file from path '" + location + "'.", ex
            );
        }
    }

    private static Gson newGson() {
        return new GsonBuilder()
            .setPrettyPrinting()
            .create();
    }

    private DocumentTranscoder() {
        instanceNotAllowed(getClass());
    }
}