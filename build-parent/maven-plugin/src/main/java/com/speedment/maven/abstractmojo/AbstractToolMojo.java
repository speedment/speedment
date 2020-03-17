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


import com.speedment.common.injector.Injector;
import com.speedment.maven.parameter.ConfigParam;
import com.speedment.maven.typemapper.Mapping;
import com.speedment.runtime.core.ApplicationBuilder;
import com.speedment.runtime.core.Speedment;
import com.speedment.tool.core.MainApp;
import com.speedment.tool.core.ToolBundle;
import javafx.application.Application;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.function.Consumer;

/**
 *
 * @author Emil Forslund
 */
public abstract class AbstractToolMojo extends AbstractSpeedmentMojo {
    
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

    protected AbstractToolMojo() {}
    
    protected AbstractToolMojo(Consumer<ApplicationBuilder<?, ?>> configurer) { super(configurer);}
    
    @Override
    public void execute(Speedment speedment) throws MojoExecutionException, MojoFailureException {
        final Injector injector = speedment.getOrThrow(Injector.class);
        MainApp.setInjector(injector);
        
        if (hasConfigFile()) {
            Application.launch(MainApp.class, configLocation().toAbsolutePath().toString());
        } else {
            Application.launch(MainApp.class);
        }
    }

    @Override
    protected void configureBuilder(ApplicationBuilder<?, ?> builder) {
        // Add tool specific items, #733
        builder.
            withBundle(ToolBundle.class);
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
    
    @Override
    protected String launchMessage() {
        return "Running speedment:tool";
    }
}