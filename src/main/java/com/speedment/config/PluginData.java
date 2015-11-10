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
package com.speedment.config;

import com.speedment.HasSpeedment;
import com.speedment.annotation.Api;
import com.speedment.annotation.External;
import com.speedment.component.PluginComponent;
import com.speedment.config.aspects.Child;
import com.speedment.config.aspects.Enableable;
import com.speedment.config.aspects.Parent;
import com.speedment.config.plugin.Plugin;
import com.speedment.exception.SpeedmentException;

/**
 *
 * @author Emil Forslund
 * @since 2.3
 */
@Api(version = "2.3")
public interface PluginData extends Node, Enableable, HasSpeedment, Parent<Child<PluginData>>, Child<PluginManager> {

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    default Class<PluginData> getInterfaceMainClass() {
        return PluginData.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Class<PluginManager> getParentInterfaceMainClass() {
        return PluginManager.class;
    }
    
    /**
     * Sets the name of the {@link Plugin} that should be used to manage
     * this node.
     * <p>
     * This property is editable in the GUI through reflection.
     *
     * @param pluginName the name as returned by {@link Plugin#getName()}
     */
    @External(type = String.class)
    void setPluginName(String pluginName);
    
    /**
     * Returns the name of the {@link Plugin} that should be used to manage
     * this node.
     * <p>
     * This property is editable in the GUI through reflection.
     *
     * @return  the name as returned by {@link Plugin#getName()}
     */
    @External(type = String.class)
    String getPluginName();
    
    /**
     * Contacts the {@link PluginComponent} to find a {@link Plugin} that
     * matches the name specified by {@link #getPluginName()}.
     * 
     * @return                     the plugin 
     * @throws SpeedmentException  if the plugin could not be found
     */
    default Plugin findPlugin() throws SpeedmentException {
        return getSpeedment()
            .get(PluginComponent.class)
            .get(getPluginName())
            .orElseThrow(() -> new SpeedmentException(
                "Could not find plugin '" + getPluginName() + "'."
            ));
    }
}