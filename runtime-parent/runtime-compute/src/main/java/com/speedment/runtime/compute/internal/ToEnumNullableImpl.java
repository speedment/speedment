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
package com.speedment.runtime.compute.internal;

import com.speedment.runtime.compute.ToEnumNullable;
import com.speedment.runtime.compute.ToIntNullable;
import com.speedment.runtime.compute.ToStringNullable;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class ToEnumNullableImpl<T, E extends Enum<E>>
    implements ToEnumNullable<T, E> {

    private final Class<E> enumClass;
    private final Function<T, E> inner;

    public ToEnumNullableImpl(Class<E> enumClass, Function<T, E> inner) {
        this.enumClass = requireNonNull(enumClass);
        this.inner     = requireNonNull(inner);
    }

    @Override
    public Class<E> enumClass() {
        return enumClass;
    }

    @Override
    public E apply(T t) {
        return inner.apply(t);
    }

    @Override
    public ToIntNullable<T> asOrdinal() {
        return object -> isNotNull(object)
            ? apply(object).ordinal()
            : null;
    }

    @Override
    public ToStringNullable<T> asName() {
        return object -> isNotNull(object)
            ? apply(object).name()
            : null;
    }

    @Override
    public long hash(T object) {
        return object == null ? 0 : object.hashCode();
    }

    @Override
    public int compare(T first, T second) {
        final E f = apply(first);
        final E s = apply(second);
        if (f == null && s == null) {
            return 0;
        } else if (f == null) {
            return 1;
        } else if (s == null) {
            return -1;
        } else {
            return Integer.compare(f.ordinal(), s.ordinal());
        }
    }
}