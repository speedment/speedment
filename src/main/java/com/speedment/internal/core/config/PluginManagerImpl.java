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
import com.speedment.config.PluginManager;
import com.speedment.config.ProjectManager;
import com.speedment.config.aspects.Parent;
import com.speedment.internal.core.config.aspects.ParentHelper;
import com.speedment.internal.util.Cast;
import static java.util.Objects.requireNonNull;
import java.util.Optional;

/**
 *
 * @author Emil Forslund
 */
public final class PluginManagerImpl extends AbstractNamedConfigEntity implements PluginManager, ParentHelper<PluginData> {

    private final Speedment speedment;
    private final ChildHolder children;
    private ProjectManager parent;

    public PluginManagerImpl(Speedment speedment) {
        this.speedment = requireNonNull(speedment);
        this.children  = new ChildHolderImpl();
    }

    @Override
    protected void setDefaults() {
        
    }

    @Override
    public Speedment getSpeedment() {
        return speedment;
    }

    @Override
    public ChildHolder getChildren() {
        return children;
    }

    @Override
    public Optional<ProjectManager> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    public void setParent(Parent<?> parent) {
        this.parent = Cast.castOrFail(parent, ProjectManager.class);
    }
}