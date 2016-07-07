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


import com.speedment.common.injector.Injector;
import com.speedment.runtime.Speedment;
import com.speedment.tool.MainApp;
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
@Mojo(name = "tool", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public final class ToolMojo extends AbstractSpeedmentMojo {
    
    @Parameter
    private String[] components;
    
    @Parameter(defaultValue = DEFAULT_CONFIG_LOCATION)
    private File configFile;

    @Override
    public void execute(Speedment speedment) throws MojoExecutionException, MojoFailureException {
        final Injector injector = speedment.getOrThrow(Injector.class);
        MainApp.setInjector(injector);
        
        if (hasConfigFile()) {
            launch(MainApp.class, configFile.getAbsolutePath());
        } else {
            launch(MainApp.class);
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
    protected String launchMessage() {
        return "Running speedment:tool";
    }
}