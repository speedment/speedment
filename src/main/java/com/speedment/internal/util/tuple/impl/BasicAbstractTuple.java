/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.util.tuple.impl;

import com.speedment.internal.util.tuple.BasicTuple;
import java.util.Arrays;
import java.util.Objects;
import static java.util.stream.Collectors.joining;
import java.util.stream.Stream;

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
    protected BasicAbstractTuple(Class<? extends T> baseClass, Object... values) {
        this.baseClass = baseClass;
        if (!isNullable()) {
            if (Stream.of(values).filter(Objects::isNull).findAny().isPresent()) {
                throw new NullPointerException(getClass().getName() + " can not hold null values.");
            }
        }
        this.values = Arrays.copyOf(values, values.length);
    }

    /**
     * Returns if this Tuple can contain null elements.
     *
     * @return if this Tuple can contain null elements
     */
    protected abstract boolean isNullable();

    protected int assertIndexBounds(int index) {
        if (index < 0 || index >= order()) {
            throw new IndexOutOfBoundsException("index " + index + " is illegal. There is capacity for " + order() + " items in this class.");
        }
        return index;
    }

    @Override
    public int order() {
        return values.length;
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }

    @Override
    public boolean equals(Object obj) {
        if (!baseClass.isAssignableFrom(obj.getClass())) {
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
        final int capacity = tuple.order();
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
        return Stream.of(values)
            .filter(Objects::nonNull)
            .filter(e -> clazz.isAssignableFrom(e.getClass()))
            .map(clazz::cast);
    }

}
