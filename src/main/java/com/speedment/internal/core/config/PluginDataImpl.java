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
package com.speedment.internal.core.config;

import com.speedment.Speedment;
import com.speedment.config.PluginData;
import com.speedment.config.Project;
import com.speedment.config.aspects.Child;
import com.speedment.config.aspects.Parent;
import com.speedment.config.plugin.Plugin;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.config.aspects.ParentHelper;
import com.speedment.internal.util.Cast;
import groovy.lang.Closure;
import static java.util.Objects.requireNonNull;
import java.util.Optional;

/**
 *
 * @author Emil Forslund
 */
public final class PluginDataImpl extends AbstractNamedConfigEntity implements PluginData, ParentHelper<Child<PluginData>> {
    
    private final Speedment speedment;
    private final ChildHolder children;
    private Project parent;
    private String pluginName;

    public PluginDataImpl(Speedment speedment) {
        this.speedment = requireNonNull(speedment);
        this.children  = new ChildHolderImpl();
    }

    @Override
    public Speedment getSpeedment() {
        return speedment;
    }
    
    @Override
    protected void setDefaults() {
        
    }

    @Override
    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    @Override
    public String getPluginName() {
        return pluginName;
    }
    
    @Override
    public void setParent(Parent<?> parent) {
        this.parent = Cast.castOrFail(parent, Project.class);
    }

    @Override
    public Optional<Project> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    public ChildHolder getChildren() {
        return children;
    }

    @Override
    public Child<PluginData> metadata(Closure<?> c) {
        final String name = (String) c.getProperty("pluginName");
        final Plugin plugin = getSpeedment()
            .getPluginComponent()
            .get(name)
            .orElseThrow(() -> new SpeedmentException(
                "Could not find plugin '" + name + "'."
            ));
        
        return plugin.newChildToPluginData(c, this);
    }
}