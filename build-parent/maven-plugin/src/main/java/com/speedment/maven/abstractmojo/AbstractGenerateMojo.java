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
package com.speedment.maven.abstractmojo;

import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.generator.translator.TranslatorManager;
import com.speedment.maven.parameter.ConfigParam;
import com.speedment.maven.typemapper.Mapping;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.core.ApplicationBuilder;
import com.speedment.runtime.core.Speedment;
import com.speedment.runtime.core.component.ProjectComponent;

import java.util.function.Consumer;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 *
 * @author Emil Forslund
 */
public abstract class AbstractGenerateMojo extends AbstractSpeedmentMojo {

    private static final Logger LOGGER = LoggerManager.getLogger(AbstractGenerateMojo.class);
    
    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject mavenProject;
    
    private @Parameter(defaultValue = "${debug}") Boolean debug;
    private @Parameter(defaultValue = "${dbms.host}") String dbmsHost;
    private @Parameter(defaultValue = "${dbms.port}") int dbmsPort;
    private @Parameter(defaultValue = "${dbms.username}") String dbmsUsername;
    private @Parameter(defaultValue = "${dbms.password}") String dbmsPassword;
    private @Parameter(defaultValue = "${components}") String[] components;
    private @Parameter(defaultValue = "${typeMappers}") Mapping[] typeMappers;
    private @Parameter ConfigParam[] parameters;
    private @Parameter(defaultValue = "${configFile}") String configFile;

    protected AbstractGenerateMojo() {}
    
    protected AbstractGenerateMojo(Consumer<ApplicationBuilder<?, ?>> configurer) { super(configurer); }
    
    @Override
    public void execute(Speedment speedment) throws MojoExecutionException, MojoFailureException {
        getLog().info("Generating code using JSON configuration file: '" + configLocation().toAbsolutePath() + "'.");
        
        if (hasConfigFile()) {
            try {
                final Project project = speedment.getOrThrow(ProjectComponent.class).getProject();
                speedment.getOrThrow(TranslatorManager.class).accept(project);

                // after generating the speedment code, the package location needs to be added as a source folder
                if (!mavenProject.getCompileSourceRoots().contains(mavenProject.getBasedir().getAbsolutePath() + "/" + project.getPackageLocation())) {
                    getLog().info("Adding new source location");
                    mavenProject.addCompileSourceRoot(mavenProject.getBasedir().getAbsolutePath() + "/" + project.getPackageLocation());
                }
            } catch (final Exception ex) {
                final String err = "Error parsing configFile file.";
                LOGGER.error(ex, err);
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
    protected ConfigParam[] parameters() {
        return parameters;
    }
    
    public String getConfigFile() {
        return configFile;
    }
    
    @Override
    protected boolean debug() {
        return (debug != null) && debug;
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
    
    public void setTypeMappers(Mapping[] typeMappers) {
        this.typeMappers = typeMappers;
    }

}