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

import com.speedment.common.tuple.TupleMapper;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public abstract class AbstractTupleMapper<T, R> implements TupleMapper<T, R> {

    private final Function<T, ?>[] mappers;

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected AbstractTupleMapper(int degree) {
        this.mappers = new Function[degree];
    }

    @Override
    public final int degree() {
        return mappers.length;
    }

    @Override
    public final Function<T, ?> get(int index) {
        return mappers[index];
    }

    @SuppressWarnings("unchecked")
    protected final <C> Function<T, C> getAndCast(int index) {
        return (Function<T, C>) mappers[index];
    }

    protected final void set(int index, Function<T, ?> mapper) {
        mappers[index] = requireNonNull(mapper);
    }

}
