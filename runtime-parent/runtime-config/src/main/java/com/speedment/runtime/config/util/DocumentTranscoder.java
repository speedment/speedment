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
package com.speedment.runtime.config.util;

import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.ProjectUtil;
import com.speedment.runtime.config.exception.SpeedmentConfigException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

/**
 * Various utility methods for transcoding and decoding documents into JSON.
 * 
 * @author  Emil Forslund
 * @since   2.3.0
 */
public final class DocumentTranscoder {

    private DocumentTranscoder() {}

    /**
     * The element name of the root node in the JSON configuration file. Every 
     * setting should be located in this element.
     */
    public static final String ROOT = "config";
    
    /**
     * A functional interface describing a method that encodes a map of 
     * key-value pairs into a JSON String.
     */
    @FunctionalInterface
    public interface Encoder {
        
        /**
         * Encodes the specified map into a JSON string.
         * 
         * @param map  the map to encode
         * @return     the resulting JSON string
         */
        String encode(Map<String, Object> map);
    }
    
    /**
     * A functional interface describing a method that decodes a JSON String
     * into a map of key-value pairs.
     */
    @FunctionalInterface
    public interface Decoder {
        
        /**
         * Decodes the specified JSON string into a map.
         * 
         * @param text  the JSON string to decode
         * @return      the resulting java map
         */
        Map<String, Object> decode(String text);
    }

    /**
     * Returns a JSON representation of the specified project node.
     * 
     * @param project  the project
     * @param encoder  the encoder to use
     * @return         the JSON representation
     * 
     * @throws SpeedmentConfigException  if the input object is not valid
     */
    public static String save(Project project, Encoder encoder) {
        
        if (project == null) {
            return "null";
        } else {
            try {
                final Map<String, Object> root = new LinkedHashMap<>();
                root.put(ROOT, project.getData());
                return encoder.encode(root);
            } catch (final IllegalArgumentException ex) {
                throw new SpeedmentConfigException(ex);
            }
        }
    }

    /**
     * Saves the project in a UTF-8 encoded file.
     *
     * @param project   to save
     * @param encoder   the encoder to use
     * @param location  for the UTF-8 encoded file
     * 
     * @throws SpeedmentConfigException if the file could not be saved
     */
    public static void save(Project project, Path location, Encoder encoder) {
        
        try {
            Files.write(location, save(project, encoder)
                .getBytes(StandardCharsets.UTF_8));
            
        } catch (final IOException ex) {
            throw new SpeedmentConfigException(
                "Could not save json-file to path '" + location + "'.", ex
            );
        }
    }

    /**
     * Loads a new {@link Project} from the specified JSON string.
     * 
     * @param json      the input json
     * @param decoder   the decoder to use
     * @return          the parsed project
     * 
     * @throws SpeedmentConfigException  if the file couldn't be loaded
     */
    public static Project load(String json, Decoder decoder) {
        
        requireNonNull(json, "No json value specified.");
        
        try {
            final Map<String, Object> root = decoder.decode(json);
            
            @SuppressWarnings("unchecked")
            final Map<String, Object> data = 
                (Map<String, Object>) root.get(ROOT);

            if (!data.containsKey(ProjectUtil.APP_ID)) {
                data.put(ProjectUtil.APP_ID, UUID.randomUUID().toString());
            }
            
            return Project.create(data);
        } catch (final Exception ex) {
            throw new SpeedmentConfigException(ex);
        }
    }

    /**
     * Loads a project from a UTF-8 encoded file.
     *
     * @param location  for the UTF-8 encoded file
     * @param decoder   the decoder to use
     * @return          that was loaded
     * 
     * @throws SpeedmentConfigException if the file could not be loaded
     */
    public static Project load(Path location, Decoder decoder) {
        
        try {
            final byte[] content = Files.readAllBytes(location);
            return load(new String(content, StandardCharsets.UTF_8), decoder);
            
        } catch (final IOException ex) {
            throw new SpeedmentConfigException(
                "Could not load json-file from path '" + location + "'.", ex
            );
        }
    }

}