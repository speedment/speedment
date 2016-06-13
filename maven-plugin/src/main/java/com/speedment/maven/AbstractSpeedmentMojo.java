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

import com.speedment.generator.internal.component.CodeGenerationComponentImpl;
import com.speedment.runtime.Speedment;
import com.speedment.runtime.SpeedmentBuilder;
import com.speedment.runtime.component.Component;
import com.speedment.runtime.internal.runtime.DefaultApplicationBuilder;
import com.speedment.tool.internal.component.UserInterfaceComponentImpl;
import static com.speedment.tool.internal.util.ConfigFileHelper.DEFAULT_CONFIG_LOCATION;
import java.io.File;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 *
 * @author Emil Forslund
 */
abstract class AbstractSpeedmentMojo extends AbstractMojo {
    
    private final static File DEFAULT_CONFIG = new File(DEFAULT_CONFIG_LOCATION);
    private final SpeedmentBuilder<?, ?> builder;

    protected abstract File configLocation();
    protected abstract Class<? extends Component>[] components();
    protected abstract String launchMessage();
    protected abstract void execute(Speedment speedment) 
        throws MojoExecutionException, MojoFailureException;
    
    protected AbstractSpeedmentMojo() {
        builder = createBuilder();
    }

    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info(launchMessage());    
        execute(builder.build());
    }
    
    protected final boolean hasConfigFile() {
        return hasConfigFile(configLocation());
    }
    
    protected final boolean hasConfigFile(File file) {
        if (file == null) {
            final String err = "The specified .json-file is null.";
            getLog().error(err);
            return false;
        } else if (!file.exists()) {
            final String err = "The specified .json-file '" + file.getAbsolutePath() + "' does not exist.";
            getLog().error(err);
            return false;
        } else if (!file.canRead()) {
            final String err = "The specified .json-file '" + file.getAbsolutePath() + "' is not readable.";
            getLog().error(err);
            return false;
        } else return true;
    }
    
    private SpeedmentBuilder<?, ?> createBuilder() {
        final SpeedmentBuilder<?, ?> result;
        
        if (hasConfigFile()) {
            result = new DefaultApplicationBuilder(configLocation());
        } else if (hasConfigFile(DEFAULT_CONFIG)) {
            result = new DefaultApplicationBuilder(DEFAULT_CONFIG);
        } else {
            result = new DefaultApplicationBuilder();
        }
        
        result.with(CodeGenerationComponentImpl.class);
        result.with(UserInterfaceComponentImpl.class);
        
        final Class<? extends Component>[] components = components();
        if (components != null) {
            for (final Class<? extends Component> component : components()) {
                result.with(component);
            }
        }
        
        return result;
    }
}