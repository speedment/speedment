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
import com.speedment.Speedment;
import com.speedment.annotation.Api;
import com.speedment.annotation.External;
import com.speedment.component.PluginComponent;
import com.speedment.config.aspects.Child;
import com.speedment.config.aspects.Enableable;
import com.speedment.config.aspects.Parent;
import com.speedment.config.plugin.Plugin;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.config.PluginDataImpl;
import static java.util.Objects.requireNonNull;
import java.util.function.Function;

/**
 *
 * @author Emil Forslund
 * @since 2.3
 */
@Api(version = "2.3")
public interface PluginData extends Node, Enableable, HasSpeedment, Parent<Child<PluginData>>, Child<Project> {

    /**
     * Factory holder.
     */
    enum Holder {
        HOLDER;
        private Function<Speedment, PluginData> provider = PluginDataImpl::new;
    }

    /**
     * Sets the instantiation method used to create new instances of this
     * interface.
     *
     * @param provider the new constructor
     */
    static void setSupplier(Function<Speedment, PluginData> provider) {
        Holder.HOLDER.provider = requireNonNull(provider);
    }

    /**
     * Creates a new instance implementing this interface by using the class
     * supplied by the default factory. To change implementation, please use the
     * {@link #setSupplier(java.util.function.Function) setSupplier} method.
     *
     * @param speedment instance to use
     * @return the new instance
     */
    static PluginData newPluginData(Speedment speedment) {
        return Holder.HOLDER.provider.apply(speedment);
    }
    
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
    default Class<Project> getParentInterfaceMainClass() {
        return Project.class;
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