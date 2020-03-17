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
package com.speedment.tool.core.internal.component;

import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.generator.core.GeneratorBundle;
import com.speedment.runtime.application.ApplicationBuilders;
import com.speedment.runtime.core.Speedment;
import com.speedment.tool.core.TestInjectorProxy;
import com.speedment.tool.core.ToolBundle;
import com.speedment.tool.core.component.VersionComponent;
import org.junit.jupiter.api.Test;

import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Tests the default {@link VersionComponent} to make sure it can query the
 * public GitHub API.
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
final class VersionComponentImplTest {

    private final static Logger LOGGER =
        LoggerManager.getLogger(VersionComponentImplTest.class);
    
    /**
     * Test of latestVersion method, of class VersionComponentImpl.
     */
    @Test
    void testLatestVersion() {
        assertDoesNotThrow(() -> {
            LOGGER.info("Determining the latest version of Speedment.");
            final Speedment speedment = ApplicationBuilders.empty()
                .withBundle(GeneratorBundle.class)
                .withBundle(ToolBundle.class)
                .withSkipCheckDatabaseConnectivity()
                .withSkipValidateRuntimeConfig()
                .withInjectorProxy(new TestInjectorProxy())
                .build();

            final VersionComponent version = speedment.getOrThrow(VersionComponent.class);

            try {
                final String latest = version.latestVersion().get(2, TimeUnit.SECONDS);
                LOGGER.info("The latest released version of Speedment is " + latest + ".");
            } catch (final ExecutionException | InterruptedException ex) {
                if (hasCause(ex, UnknownHostException.class)) {
                    LOGGER.warn(ex, "Not connected to the Internet?");
                } else {
                    LOGGER.error(ex);
                }
            } catch (final TimeoutException ex) {
                LOGGER.error(ex, "Connection timed out before a version could be established.");
            }
        });
    }

    private static boolean hasCause(Exception ex, Class<? extends Throwable> c) {
        Throwable cause = ex.getCause();
        
        while (cause != null) {
            if (c.equals(cause.getClass())) {
                return true;
            }
            cause = cause.getCause();
        }
        
        return false;
    }
}
