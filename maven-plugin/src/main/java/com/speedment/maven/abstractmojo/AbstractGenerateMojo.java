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
package com.speedment.maven.abstractmojo;

import com.speedment.maven.typemapper.Mapping;
import com.speedment.generator.TranslatorManager;
import com.speedment.runtime.Speedment;
import com.speedment.runtime.SpeedmentBuilder;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.exception.SpeedmentException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

import static com.speedment.tool.internal.util.ConfigFileHelper.DEFAULT_CONFIG_LOCATION;
import java.util.function.Consumer;
import org.apache.maven.project.MavenProject;

/**
 *
 * @author Emil Forslund
 */
public abstract class AbstractGenerateMojo extends AbstractSpeedmentMojo {

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject mavenProject;
    
    private @Parameter(defaultValue = "false") boolean debug;
    private @Parameter(defaultValue = "${dbms.host}") String dbmsHost;
    private @Parameter(defaultValue = "${dbms.port}") int dbmsPort;
    private @Parameter(defaultValue = "${dbms.username}") String dbmsUsername;
    private @Parameter(defaultValue = "${dbms.password}") String dbmsPassword;
    private @Parameter String[] components;
    private @Parameter Mapping[] typeMappers;
    private @Parameter(defaultValue = DEFAULT_CONFIG_LOCATION) File configFile;

    protected AbstractGenerateMojo() {}
    
    protected AbstractGenerateMojo(Consumer<SpeedmentBuilder<?, ?>> configurer) { super(configurer);}
    
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
    protected MavenProject project() {
        return mavenProject;
    }

    @Override
    protected String[] components() {
        return components;
    }
    
    @Override
    protected Mapping[] typeMappers() {
        return typeMappers;
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
    protected String dbmsHost() {
        return dbmsHost;
    }

    @Override
    protected int dbmsPort() {
        return dbmsPort;
    }

    @Override
    protected String dbmsUsername() {
        return dbmsUsername;
    }

    @Override
    protected String dbmsPassword() {
        return dbmsPassword;
    }

}