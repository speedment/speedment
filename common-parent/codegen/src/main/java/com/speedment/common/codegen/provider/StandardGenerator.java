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
package com.speedment.common.codegen.provider;

import com.speedment.common.codegen.*;
import com.speedment.common.codegen.internal.GeneratorImpl;

import java.util.Optional;
import java.util.stream.Stream;

public class StandardGenerator implements Generator {

    private final GeneratorImpl defaultGenerator;

    public StandardGenerator(
        final DependencyManager dependencyManager,
        final TransformFactory transformFactory
    ) {
        this.defaultGenerator = new GeneratorImpl(dependencyManager, transformFactory);
    }

    @Override
    public DependencyManager getDependencyMgr() {
        return defaultGenerator.getDependencyMgr();
    }

    @Override
    public RenderStack getRenderStack() {
        return defaultGenerator.getRenderStack();
    }

    @Override
    public <A, B> Stream<Meta<A, B>> metaOn(A from, Class<B> to) {
        return defaultGenerator.metaOn(from, to);
    }

    @Override
    public <A, B> Optional<Meta<A, B>> transform(Transform<A, B> transform, A model,
            TransformFactory factory) {
        return defaultGenerator.transform(transform, model, factory);
    }
}
