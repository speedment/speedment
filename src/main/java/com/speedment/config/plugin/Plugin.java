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
package com.speedment.config.plugin;

import com.speedment.annotation.Api;
import com.speedment.config.PluginData;
import com.speedment.config.aspects.Child;
import groovy.lang.Closure;

/**
 *
 * @author Emil Forslund
 * @since 2.3
 */
@Api(version = "2.3")
public interface Plugin {
    
    /**
     * Returns the name of this plugin as declared in the groovy file.
     * 
     * @return  the unique name
     */
    String getName();
    
    /**
     * Returns the label of this plugin as shown visible to the user in
     * the GUI.
     * 
     * @return  the label
     */
    String getLabel();
    
    /**
     * Returns the path of the icon to use for this node in the GUI.
     * 
     * @return  the icon path
     */
    String getIconPath();
    
    /**
     * Method that will be invoked by groovy parser if a child to the
     * {@link PluginData} closure is detected.
     * 
     * @param closure  the groovy closure
     * @param parent   the parent
     * @return         a new child node
     */
    Child<PluginData> newChildToPluginData(Closure<?> closure, PluginData parent);
}