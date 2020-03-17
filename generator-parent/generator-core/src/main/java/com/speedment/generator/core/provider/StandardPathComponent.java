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
package com.speedment.generator.core.provider;

import com.speedment.generator.core.component.PathComponent;
import com.speedment.generator.core.internal.component.PathComponentImpl;
import com.speedment.runtime.core.component.ProjectComponent;

import java.nio.file.Path;

public final class StandardPathComponent implements PathComponent {

    private final PathComponent inner;

    public StandardPathComponent(ProjectComponent projectComponent) {
        this.inner = new PathComponentImpl(projectComponent);
    }

    @Override
    public Path baseDir() {
        return inner.baseDir();
    }

    @Override
    public Path packageLocation() {
        return inner.packageLocation();
    }
}
