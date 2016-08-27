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
package com.speedment.tool.internal.component;

import com.speedment.generator.GeneratorBundle;
import com.speedment.runtime.Speedment;
import com.speedment.runtime.internal.DefaultApplicationBuilder;
import com.speedment.runtime.internal.EmptyApplicationMetadata;
import com.speedment.tool.ToolBundle;
import com.speedment.tool.component.VersionComponent;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.Test;

/**
 *
 * @author  Emil Forslund
 * @since   3.0.0
 */
public class VersionComponentImplTest {

    /**
     * Test of latestVersion method, of class VersionComponentImpl.
     */
    @Test
    public void testLatestVersion() {
        System.out.println("Determining the latest version of Speedment.");
        final Speedment speedment = new DefaultApplicationBuilder(EmptyApplicationMetadata.class)
            .withBundle(GeneratorBundle.class)
            .withBundle(ToolBundle.class)
            .withCheckDatabaseConnectivity(false)
            .withValidateRuntimeConfig(false)
            .build();
        
        final VersionComponent version = speedment.getOrThrow(VersionComponent.class);
        
        try {
            final String latest = version.latestVersion().get(2, TimeUnit.SECONDS);
            System.out.println("The latest released version of Speedment is " + latest + ".");
        } catch (final ExecutionException | InterruptedException ex) {
            throw new RuntimeException(ex);
        } catch (final TimeoutException ex ) {
            System.out.println("Connection timed out before a version could be established");
        }
    }
    
}
