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

import com.speedment.common.json.Json;
import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.internal.config.ProjectImpl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.util.StaticClassUtil.instanceNotAllowed;

/**
 * Various utility methods for transcoding and decoding documents into JSON.
 * 
 * @author  Emil Forslund
 * @since   2.3.0
 */
@Api(version = "2.3")
public final class DocumentTranscoder {

    public static final String ROOT = "config";

    /**
     * Returns a JSON representation of the specified project node.
     * 
     * @param project              the project
     * @return                     the JSON representation
     * @throws SpeedmentException  if the inputed object is not valid
     */
    public static String save(Project project) throws SpeedmentException {
        if (project == null) {
            return "null";
        } else {
            try {
                final Map<String, Object> root = new HashMap<>();
                root.put(ROOT, project.getData());
                return Json.toJson(root);
            } catch (final IllegalArgumentException ex) {
                throw new SpeedmentException(ex);
            }
        }
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

    /**
     * Loads a new {@link Project} from the specified JSON string.
     * 
     * @param json  the input json
     * @return      the parsed project
     * 
     * @throws SpeedmentException  if the file couldn't be loaded
     */
    public static Project load(String json) throws SpeedmentException {
        try {
            @SuppressWarnings("unchecked")
            final Map<String, Object> root = (Map<String, Object>) Json.fromJson(json);
            
            @SuppressWarnings("unchecked")
            final Map<String, Object> data = (Map<String, Object>) root.get(ROOT);
            
            return new ProjectImpl(data);
        } catch (final Exception ex) {
            throw new SpeedmentException(ex);
        }
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

    /**
     * Utility classes should never be instantiated.
     */
    private DocumentTranscoder() {
        instanceNotAllowed(getClass());
    }
}