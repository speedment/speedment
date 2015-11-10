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
import com.speedment.config.aspects.Child;
import com.speedment.config.aspects.Enableable;
import com.speedment.config.aspects.Parent;
import com.speedment.internal.core.config.PluginDataImpl;
import com.speedment.internal.core.config.PluginManagerImpl;
import java.util.function.Function;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 * @since 2.3
 */
@Api(version = "2.3")
public interface PluginManager extends Node, Enableable, HasSpeedment, Parent<PluginData>, Child<ProjectManager> {
    
    /**
     * Factory holder.
     */
    enum Holder {
        HOLDER;
        private Function<Speedment, PluginManager> provider = PluginManagerImpl::new;
    }

    /**
     * Sets the instantiation method used to create new instances of this
     * interface.
     *
     * @param provider the new constructor
     */
    static void setSupplier(Function<Speedment, PluginManager> provider) {
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
    static PluginManager newPluginManager(Speedment speedment) {
        return Holder.HOLDER.provider.apply(speedment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    default Class<PluginManager> getInterfaceMainClass() {
        return PluginManager.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Class<ProjectManager> getParentInterfaceMainClass() {
        return ProjectManager.class;
    }

    /**
     * Creates and adds a new {@link PluginData} as a child to this node in the
     * configuration tree.
     *
     * @param speedment the {@link Speedment} instance
     * @return the newly added child
     */
    default PluginData addNewPluginData(Speedment speedment) {
        return new PluginDataImpl(speedment);
    }
}
