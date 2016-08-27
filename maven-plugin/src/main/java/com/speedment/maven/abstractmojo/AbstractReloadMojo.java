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
import com.speedment.runtime.ApplicationBuilder;
import com.speedment.runtime.Speedment;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.tool.internal.util.ConfigFileHelper;
import static com.speedment.tool.internal.util.ConfigFileHelper.DEFAULT_CONFIG_LOCATION;
import java.io.File;
import java.util.function.Consumer;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * A maven goal that reloads the JSON configuration file
 * from the database, overwriting all manual changes.
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public abstract class AbstractReloadMojo extends AbstractSpeedmentMojo {

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
    
    protected AbstractReloadMojo() {}
    
    protected AbstractReloadMojo(Consumer<ApplicationBuilder<?, ?>> configurer) { super(configurer);}
    
    @Override
    protected void execute(Speedment speedment) throws MojoExecutionException, MojoFailureException {
        getLog().info("Saving default configuration from database to '" + configFile.getAbsolutePath() + "'.");
        
        final ConfigFileHelper helper = speedment.getOrThrow(ConfigFileHelper.class);
        
        try {
            helper.loadFromDatabaseAndSaveToFile();
        } catch (final SpeedmentException ex) {
            final String err = "An error occured while reloading.";
            getLog().error(err);
            throw new MojoExecutionException(err, ex);
        }
    }
    
    @Override
    protected MavenProject project() {
        return mavenProject;
    }
    
    @Override
    protected boolean debug() {
        return debug;
    }

    @Override
    protected File configLocation() {
        return configFile;
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