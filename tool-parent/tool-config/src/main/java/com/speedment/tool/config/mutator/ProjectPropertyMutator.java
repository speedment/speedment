/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.tool.config.mutator;


import com.speedment.runtime.config.mutator.ProjectMutator;
import com.speedment.tool.config.DbmsProperty;
import com.speedment.tool.config.ProjectProperty;
import com.speedment.tool.config.mutator.trait.HasEnabledPropertyMutator;
import com.speedment.tool.config.mutator.trait.HasNamePropertyMutator;

import java.nio.file.Paths;

/**
 *
 * @author  Emil Forslund
 * @since   2.3.0
 */

public final class ProjectPropertyMutator extends ProjectMutator<ProjectProperty> implements 
        HasEnabledPropertyMutator<ProjectProperty>,
        HasNamePropertyMutator<ProjectProperty> {

    ProjectPropertyMutator(ProjectProperty project) {
        super(project);
    }
    
    @Override
    public void setPackageName(String packageName) {
        document().packageNameProperty().setValue(packageName);
    }

    @Override
    public void setPackageLocation(String packageLocation) {
        document().packageLocationProperty().setValue(packageLocation);
    }

    @Override
    public void setConfigPath(String configPath) {
        document().configPathProperty().setValue(Paths.get(configPath));
    }

    @Override
    public void setCompanyName(String companyName) {
        document().companyNameProperty().setValue(companyName);
    }
    
    @Override
    public DbmsProperty addNewDbms() {
        final DbmsProperty child = new DbmsProperty(document());
        document().dbmsesProperty().add(child);
        return child;
    }
}