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
import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.Meta;
import com.speedment.common.codegen.RenderStack;
import com.speedment.common.codegen.Transform;
import com.speedment.common.codegen.TransformFactory;
import com.speedment.common.codegen.internal.DefaultDependencyManager;
import com.speedment.common.codegen.internal.DefaultGenerator;

import java.util.Optional;
import java.util.stream.Stream;

public class DelegateDefaultGenerator implements Generator {

    private final DefaultGenerator defaultGenerator;

    public DelegateDefaultGenerator(
            TransformFactory transformFactory) {
        this.defaultGenerator = new DefaultGenerator(
                new DelegateDefaultDependencyManager(new DefaultDependencyManager()),
                transformFactory);
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
