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
package com.speedment.maven;

import com.speedment.runtime.Speedment;
import java.io.File;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import com.speedment.runtime.component.ComponentConstructor;

/**
 *
 * @author Emil Forslund
 */
abstract class AbstractSpeedmentMojo extends AbstractMojo {
    
    private final SpeedmentInitializer initializer;

    protected abstract File configLocation();
    protected abstract ComponentConstructor<?>[] components();
    protected abstract String launchMessage();
    protected abstract void execute(Speedment speedment) throws MojoExecutionException, MojoFailureException;
    
    protected AbstractSpeedmentMojo() {
        initializer = createInitializer();
    }

    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info(launchMessage());    
        execute(initializer.build());
    }
    
    protected final boolean hasConfigFile() {
        if (configLocation() == null) {
            final String err = "The specified .json-file is null.";
            getLog().error(err);
            return false;
        } else if (!configLocation().exists()) {
            final String err = "The specified .json-file '" + configLocation().getAbsolutePath() + "' does not exist.";
            getLog().error(err);
            return false;
        } else if (!configLocation().canRead()) {
            final String err = "The specified .json-file '" + configLocation().getAbsolutePath() + "' is not readable.";
            getLog().error(err);
            return false;
        } else return true;
    }
    
    private SpeedmentInitializer createInitializer() {
        return new SpeedmentInitializer(
            super.getLog(), 
            configLocation(), 
            this::components
        );
    }
}