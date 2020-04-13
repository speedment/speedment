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

package com.speedment.runtime.config.mutator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.mutator.trait.HasEnabledMutatorMixin;
import com.speedment.runtime.config.mutator.trait.HasIdMutatorMixin;
import com.speedment.runtime.config.mutator.trait.HasNameMutatorMixin;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

final class ProjectMutatorTest implements
        HasEnabledMutatorMixin<Project, ProjectMutator<Project>>,
        HasIdMutatorMixin<Project, ProjectMutator<Project>>,
        HasNameMutatorMixin<Project, ProjectMutator<Project>> {

    @Override
    @SuppressWarnings("unchecked")
    public ProjectMutator<Project> getMutatorInstance() {
        return (ProjectMutator<Project>) Project.create(new HashMap<>()).mutator();
    }

    @Test
    void setSpeedmentVersion() {
        assertDoesNotThrow(() -> getMutatorInstance().setSpeedmentVersion("3.2.9"));
    }

    @Test
    void setCompanyName() {
        assertDoesNotThrow(() -> getMutatorInstance().setCompanyName("3.2.9"));
    }

    @Test
    void setPackageName() {
        assertDoesNotThrow(() -> getMutatorInstance().setPackageName("package"));
    }

    @Test
    void setPackageLocation() {
        assertDoesNotThrow(() -> getMutatorInstance().setPackageLocation("location"));
    }

    @Test
    void setConfigPath() {
        assertDoesNotThrow(() -> getMutatorInstance().setConfigPath("path"));
    }

    @Test
    void addNewDbms() {
        assertNotNull(getMutatorInstance().addNewDbms());
    }
}
