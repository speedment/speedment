/**
 *
 * Copyright (c) 2006-2018, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.config.util;

import com.speedment.common.json.Json;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.exception.SpeedmentConfigException;
import com.speedment.runtime.config.internal.ProjectImpl;
import com.speedment.runtime.config.resolver.DocumentResolver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

import static com.speedment.runtime.config.util.DocumentLoaders.jsonLoader;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * Various utility methods for transcoding and decoding documents into JSON.
 * 
 * @author  Emil Forslund
 * @since   2.3.0
 */
public final class DocumentTranscoder {

    /**
     * The element name of the root node in the JSON configuration file. Before
     * version {@code 3.1.6}, the project node was a value to this attribute in
     * the root object. In {@code 3.1.6}, this was changed so that the project
     * is the root. This attribute is still scanned however for backward
     * compatibility reasons.
     */
    private static final String ROOT = "config";

    /**
     * Returns a JSON representation of the specified project node normalized by
     * the default project model.
     * 
     * @param project  the project
     * @return         the JSON representation
     * 
     * @throws SpeedmentConfigException  if the inputed object is not valid
     */
    public static String save(Project project)
            throws SpeedmentConfigException {

        if (project == null) {
            return "null";
        }

        final DocumentResolver resolver = DocumentResolver.create(jsonLoader());
        try {
            final Map<String, Object> normalized = resolver.normalize(resolver.resolve(project.getData()));
            return Json.toJson(normalized);
        } catch (final IllegalArgumentException ex) {
            throw new SpeedmentConfigException(ex);
        }
    }

    /**
     * Saves the project in a UTF-8 encoded file.
     *
     * @param project   to save
     * @param location  for the UTF-8 encoded file
     * 
     * @throws SpeedmentConfigException if the file could not be saved
     */
    public static void save(Project project, Path location)
            throws SpeedmentConfigException {

        final DocumentResolver resolver = DocumentResolver.create(jsonLoader());
        final Map<String, Object> normalized = resolver.normalize(resolver.resolve(project.getData()));

        try (final OutputStream out = Files.newOutputStream(location)) {
            Json.toJson(normalized, out);
        } catch (final IOException ex) {
            throw new SpeedmentConfigException(
                format("Could not save json-file to path '%s'.", location), ex
            );
        }
    }

    /**
     * Loads a new {@link Project} from the specified JSON string.
     * 
     * @param json  the input json
     * @return      the parsed project
     * 
     * @throws SpeedmentConfigException  if the file couldn't be loaded
     */
    public static Project load(String json) throws SpeedmentConfigException {
        
        requireNonNull(json, "No json value specified.");
        final DocumentResolver resolver = DocumentResolver.create(jsonLoader());
        
        try {
            @SuppressWarnings("unchecked")
            final Map<String, Object> root = (Map<String, Object>) Json.fromJson(json);

            // Unwrap 'root' if it exists (<3.1.6 compatibility)
            final Map<String, Object> project;
            if (root.containsKey(ROOT)) {
                @SuppressWarnings("unchecked")
                final Map<String, Object> data = (Map<String, Object>) root.get(ROOT);
                project = data;
            } else {
                project = root;
            }

            if (!project.containsKey(Project.APP_ID)) {
                project.put(Project.APP_ID, UUID.randomUUID().toString());
            }

            return new ProjectImpl(resolver.resolve(project));
        } catch (final Exception ex) {
            throw new SpeedmentConfigException(ex);
        }
    }

    /**
     * Loads a project from a UTF-8 encoded file.
     *
     * @param location  for the UTF-8 encoded file
     * @return          that was loaded
     * 
     * @throws SpeedmentConfigException if the file could not be loaded
     */
    public static Project load(Path location) throws SpeedmentConfigException {
        requireNonNull(location, "No path specified.");
        final DocumentResolver resolver = DocumentResolver.create(jsonLoader());

        try (final InputStream in = Files.newInputStream(location)) {
            @SuppressWarnings("unchecked")
            final Map<String, Object> root = (Map<String, Object>) Json.fromJson(in);

            // Unwrap 'root' if it exists (<3.1.6 compatibility)
            final Map<String, Object> project;
            if (root.containsKey(ROOT)) {
                @SuppressWarnings("unchecked")
                final Map<String, Object> data = (Map<String, Object>) root.get(ROOT);
                project = data;
            } else {
                project = root;
            }

            if (!project.containsKey(Project.APP_ID)) {
                project.put(Project.APP_ID, UUID.randomUUID().toString());
            }

            return new ProjectImpl(resolver.resolve(project));
        } catch (final Exception ex) {
            throw new SpeedmentConfigException(ex);
        }
    }

    /**
     * Utility classes should never be instantiated.
     */
    private DocumentTranscoder() {
        throw new UnsupportedOperationException();
    }
}