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
package com.speedment.runtime.internal.runtime;

import com.speedment.common.injector.Injector;
import com.speedment.runtime.Speedment;
import com.speedment.runtime.exception.SpeedmentException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 *
 * @author Emil Forslund
 */
public final class DefaultApplicationBuilder extends
    AbstractApplicationBuilder<Speedment, DefaultApplicationBuilder> {

    private final ApplicationMetadata metadata; // Can be null.

    /**
     * Constructs a new DefaultApplicationBuilder with an empty
     * domain model.
     */
    public DefaultApplicationBuilder() {
        this((File) null);
    }

    /**
     * Constructs a new DefaultApplicationBuilder from the UTF-8
     * encoded configuration file.
     *
     * @param configFile  UTF-8 encoded configuration file
     */
    public DefaultApplicationBuilder(File configFile) {
        this(loadMetadataFrom(configFile));
    }
    
    /**
     * Constructs a new DefaultApplicationBuilder from the specified
     * json string.
     *
     * @param json  json encoded domain model
     */
    public DefaultApplicationBuilder(String json) {
        super(SpeedmentImpl.class);
        if (json == null) metadata = null;
        else metadata = () -> json;
    }
    
    /**
     * Constructs a new DefaultApplicationBuilder from the specified
     * json string, using the builder specified instead of the default
     * one.
     *
     * @param injector  the injector builder to use
     * @param json      json encoded domain model
     */
    public DefaultApplicationBuilder(Injector.Builder injector, File configFile) {
        super(injector.canInject(SpeedmentImpl.class));
        final String json = loadMetadataFrom(configFile);
        if (json == null) metadata = null;
        else metadata = () -> json;
    }

    @Override
    protected Speedment build(Injector injector) {
        return injector.getOrThrow(Speedment.class);
    }

    @Override
    protected ApplicationMetadata getSpeedmentApplicationMetadata() {
        return metadata;
    }

    @Override
    protected void printWelcomeMessage(Injector injector) {}

    private static String loadMetadataFrom(File configFile) {
        if (configFile != null) {
            final String json;
            try {
                final byte[] content = Files.readAllBytes(configFile.toPath());
                json = new String(content, StandardCharsets.UTF_8);
            } catch (final IOException ex) {
                throw new SpeedmentException(
                    "Could not load json-file from path '" + configFile.getAbsolutePath() + "'.", ex
                );
            }

            return json;
        } else {
            return null;
        }
    }
}
