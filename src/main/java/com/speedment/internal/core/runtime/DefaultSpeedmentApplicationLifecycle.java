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
package com.speedment.internal.core.runtime;

import com.speedment.exception.SpeedmentException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 *
 * @author Emil Forslund
 */
public final class DefaultSpeedmentApplicationLifecycle extends
    SpeedmentApplicationLifecycle<DefaultSpeedmentApplicationLifecycle> {

    private final ApplicationMetadata metadata; // Can be null.
    private boolean printWelcomeMessage;

    public DefaultSpeedmentApplicationLifecycle() {
        this((File) null);
    }

    /**
     * Constructs a new DefaultSpeedmentApplicationLifecycle from the UTF-8
     * encoded configuration file.
     *
     * @param configFile UTF-8 encoded configuration file
     */
    public DefaultSpeedmentApplicationLifecycle(File configFile) {
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

            metadata = () -> json;
        } else {
            metadata = null;
        }
        this.printWelcomeMessage = true;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        loadAndSetProject();
    }

    public DefaultSpeedmentApplicationLifecycle(String json) {
        metadata = () -> json;
    }

    @Override
    protected ApplicationMetadata getSpeedmentApplicationMetadata() {
        return metadata;
    }

    public DefaultSpeedmentApplicationLifecycle withPrintWelcomeMessage(boolean printWelcomeMessage) {
        this.printWelcomeMessage = printWelcomeMessage;
        return this;
    }

    @Override
    protected void printWelcomeMessage() {
        if (printWelcomeMessage) {
            super.printWelcomeMessage();
        }
    }

}
