/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.component;

import com.speedment.annotation.Api;
import com.speedment.plugin.Plugin;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 * @since 2.3
 */
@Api(version = "2.3")
public interface PluginComponent extends Component {
    
    @Override
    default Class<PluginComponent> getComponentClass() {
        return PluginComponent.class;
    }
    
    /**
     * Installs the specified plugin in this component.
     * 
     * @param constructor  the constructor for a plugin to install
     */
    void install(Supplier<Plugin> constructor);
    
    /**
     * Streams over all the plugins installed in this component.
     * 
     * @return  all plugins
     */
    Stream<Plugin> stream();
    
    /**
     * Retreive and return the plugin with the specified name. If it is 
     * not installed, return an empty optional.
     * 
     * @param pluginName  the name as returned by {@code Plugin#getName()}
     * @return            the installed plugin or empty
     */
    Optional<Plugin> get(String pluginName);
}