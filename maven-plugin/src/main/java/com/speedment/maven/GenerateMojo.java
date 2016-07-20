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

import com.speedment.generator.TranslatorManager;
import com.speedment.runtime.Speedment;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.exception.SpeedmentException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

import static com.speedment.tool.internal.util.ConfigFileHelper.DEFAULT_CONFIG_LOCATION;

/**
 *
 * @author Emil Forslund
 */
@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public final class GenerateMojo extends AbstractSpeedmentMojo {

    @Parameter(defaultValue = "false")
    private boolean debug;
    
    @Parameter
    private String[] components;

    @Parameter(defaultValue = DEFAULT_CONFIG_LOCATION)
    private File configFile;

    @Override
    public void execute(Speedment speedment) throws MojoExecutionException, MojoFailureException {
        getLog().info("Generating code using JSON configuration file: '" + configFile.getAbsolutePath() + "'.");

        if (hasConfigFile()) {
            try {
                final Project project = speedment.project();
                speedment.getOrThrow(TranslatorManager.class).accept(project);
            } catch (final SpeedmentException ex) {
                final String err = "Error parsing configFile file.";
                getLog().error(err);
                throw new MojoExecutionException(err, ex);
            }
        } else {
            final String err = "To run speedment:generate a valid configFile needs to be specified.";
            getLog().error(err);
            throw new MojoExecutionException(err);
        }
    }

    @Override
    protected String[] components() {
        return components;
    }

    @Override
    protected File configLocation() {
        return configFile;
    }
    
    @Override
    protected boolean debug() {
        return debug;
    }

    @Override
    protected String launchMessage() {
        return "Starting speedment:generate";
    }
}