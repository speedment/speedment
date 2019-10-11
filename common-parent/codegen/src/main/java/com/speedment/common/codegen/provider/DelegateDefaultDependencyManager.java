/*
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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

package com.speedment.common.codegen.provider;

import com.speedment.common.codegen.DependencyManager;
import com.speedment.common.codegen.internal.DefaultDependencyManager;

import java.util.Optional;

public class DelegateDefaultDependencyManager implements DependencyManager {

    private final DefaultDependencyManager dependencyManager;

    public DelegateDefaultDependencyManager(
            DefaultDependencyManager dependencyManager) {
        this.dependencyManager = dependencyManager;
    }

    @Override
    public boolean load(String fullname) {
        return dependencyManager.load(fullname);
    }

    @Override
    public boolean isLoaded(String fullname) {
        return dependencyManager.isLoaded(fullname);
    }

    @Override
    public void clearDependencies() {
        dependencyManager.clearDependencies();
    }

    @Override
    public boolean isIgnored(String fullname) {
        return dependencyManager.isIgnored(fullname);
    }

    @Override
    public boolean setCurrentPackage(String pack) {
        return dependencyManager.setCurrentPackage(pack);
    }

    @Override
    public boolean unsetCurrentPackage(String pack) {
        return dependencyManager.unsetCurrentPackage(pack);
    }

    @Override
    public Optional<String> getCurrentPackage() {
        return dependencyManager.getCurrentPackage();
    }
}
