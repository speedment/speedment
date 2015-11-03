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
package com.speedment.internal.core.config.immutable;

import com.speedment.internal.core.config.*;
import com.speedment.internal.core.config.aspects.ParentHelper;
import com.speedment.config.Project;
import com.speedment.config.ProjectManager;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author pemi
 */
public final class ImmutableProjectManagerImpl extends ImmutableAbstractNamedConfigEntity implements ProjectManager, ParentHelper<Project> {

    private final ChildHolder children;

    public ImmutableProjectManagerImpl(ProjectManager projectManager) {
        super(projectManager.getName(), true);
        children = ImmutableChildHolderImpl.of(
            projectManager.stream()
                .map(p -> new ImmutableProjectImpl(this, p))
                .collect(toList())
        );
    }

    @Override
    public ChildHolder getChildren() {
        return children;
    }
}
