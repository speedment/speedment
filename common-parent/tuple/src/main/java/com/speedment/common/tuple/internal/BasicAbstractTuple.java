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

import com.speedment.common.tuple.BasicTuple;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 * The BasicAbstractTuple implements parts of a generic Tuple of any order.
 * Tuple elements are stored in an array.
 *
 * @author pemi
 * @param <T> type of BasicTuple
 * @param <R> The return type of {@link #get(int)}
 */
public abstract class BasicAbstractTuple<T extends BasicTuple<R>, R> implements BasicTuple<R> {

    protected final Object[] values;
    @SuppressWarnings("rawtypes")
    protected final Class<? extends T> baseClass;

    @SuppressWarnings("rawtypes")
    BasicAbstractTuple(Class<? extends T> baseClass, Object... values) {
        requireNonNull(values);
        this.baseClass = requireNonNull(baseClass);
        if (!isNullable()) {
            for (Object v : values) {
                requireNonNull(v, () -> getClass().getName() + " cannot hold null values.");
            }
        }

        // Defensive copying
        this.values = Arrays.copyOf(values, values.length);

        if (values.length != degree()) {
            throw new IllegalArgumentException(
                "A Tuple of degree " + degree() + " must contain exactly "
                + degree() + " elements. Element length was " + values.length);
        }
    }

    /**
     * Returns if this Tuple can contain null elements.
     *
     * @return if this Tuple can contain null elements
     */
    protected abstract boolean isNullable();

    protected int assertIndexBounds(int index) {
        if (index < 0 || index >= degree()) {
            throw new IndexOutOfBoundsException("index " + index + " is illegal. The degree of this Tuple is " + degree() + ".");
        }
        return index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!baseClass.isInstance(obj)) {
            return false;
        }
        if (obj instanceof BasicAbstractTuple) {
            @SuppressWarnings("unchecked")
            final BasicAbstractTuple<?, ?> tuple = (BasicAbstractTuple<?, ?>) obj;
            // Faster
            return Arrays.equals(this.values, tuple.values);
        }
        // Must be a BasicTuple since baseClass is a BasicTuple
        @SuppressWarnings("unchecked")
        final BasicTuple<?> tuple = (BasicTuple<?>) obj;
        final int capacity = tuple.degree();
        for (int i = 0; i < capacity; i++) {
            if (!Objects.equals(get(i), tuple.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " "
            + Stream.of(values)
                .map(Objects::toString)
                .collect(joining(", ", "{", "}"));
    }

    @Override
    public <C> Stream<C> streamOf(Class<C> clazz) {
        requireNonNull(clazz);
        return Stream.of(values)
            .filter(clazz::isInstance)
            .map(clazz::cast);
    }

}
