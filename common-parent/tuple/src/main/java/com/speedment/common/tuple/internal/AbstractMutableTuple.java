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
package com.speedment.common.tuple.internal;

import com.speedment.common.tuple.MutableTuple;

import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
public abstract class AbstractMutableTuple extends BasicAbstractTuple<AbstractMutableTuple, Optional<Object>> implements MutableTuple {

    protected AbstractMutableTuple(Class<? extends AbstractMutableTuple> baseClass, int degree) {
        super(baseClass, new Object[degree]);
    }

    @Override
    protected boolean isNullable() {
        return true;
    }

    @Override
    public Optional<Object> get(int index) {
        return Optional.ofNullable(values[assertIndexBounds(index)]);
    }

    @Override
    public Stream<Optional<Object>> stream() {
        return Stream.of(values).map(Optional::ofNullable);
    }

    @Override
    public <C> Stream<C> streamOf(Class<C> clazz) {
        requireNonNull(clazz);
        return Stream.of(values)
            .filter(clazz::isInstance)
            .map(clazz::cast);
    }

}