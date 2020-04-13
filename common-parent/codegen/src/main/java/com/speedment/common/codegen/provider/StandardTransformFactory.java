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

import com.speedment.common.codegen.Transform;
import com.speedment.common.codegen.TransformFactory;
import com.speedment.common.codegen.internal.TransformFactoryImpl;

import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Supplier;

public final class StandardTransformFactory implements TransformFactory {

    private final TransformFactoryImpl transformFactory;

    public StandardTransformFactory(final String name) {
        this.transformFactory = new TransformFactoryImpl(name);
    }

    @Override
    public String getName() {
        return transformFactory.getName();
    }

    @Override
    public <A, B, T extends Transform<A, B>> TransformFactory install(
            Class<A> from, Class<B> to, Supplier<T> transform) {
        return transformFactory.install(from, to, transform);
    }

    @Override
    public <A, T extends Transform<A, ?>> Set<Entry<Class<?>, T>> allFrom(
            Class<A> model) {
        return transformFactory.allFrom(model);
    }
}
