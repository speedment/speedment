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
package com.speedment.util.tuple;

import java.util.Arrays;
import java.util.Objects;
import static java.util.stream.Collectors.joining;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <T> The implementation type of the BasicTuple
 */
public abstract class BasicTuple<T extends BasicTuple<T>> {

    protected final Object[] values;
    @SuppressWarnings("rawtypes")
    private final Class<? extends BasicTuple> baseClass;

    @SuppressWarnings("rawtypes")
    public BasicTuple(Class<? extends BasicTuple> baseClass, int capacity) {
        this.baseClass = baseClass;
        this.values = new Object[capacity];
    }

    @SuppressWarnings("rawtypes")
    public BasicTuple(Class<? extends BasicTuple> baseClass, Object... values) {
        this.baseClass = baseClass;
        this.values = Arrays.copyOf(values, values.length);
    }

    public int capacity() {
        return values.length;
    }

    public Object get(int index) {
        if (index < 0 || index >= capacity()) {
            throw new IllegalArgumentException("index " + index + " is illegal. There is capacity for " + capacity() + " items in this class.");
        }
        return values[index];
    }

    //    We should not expose this method because then we can by-pass type checking.    
    @SuppressWarnings("unchecked")
    protected T set(int index, Object value) {
        values[index] = value;
        return (T) this;
    }

//    We should not expose this method because then we can by-pass type checking.
//    public T set(int index, Object value) {
//        values[index] = value;
//        return (T) this;
//    }
    @Override
    public int hashCode() {
        return Objects.hash(values);
    }

    @Override
    public boolean equals(Object obj) {
        if (!baseClass.isAssignableFrom(obj.getClass())) {
            return false;
        }
        @SuppressWarnings("unchecked")
        final T tuple = (T) obj;
        return Arrays.equals(this.values, tuple.values);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + Stream.of(values).map(Objects::toString).collect(joining(", ", "{", "}"));
    }

    public Stream<Object> stream() {
        return Stream.of(values);
    }

    public <T> Stream<T> streamOf(Class<T> clazz) {
        return Stream.of(values).filter(Objects::nonNull).filter(e -> clazz.isAssignableFrom(e.getClass())).map(clazz::cast);
    }

    // Perhaps a list of expected classes T0, T1, ..., TN so that streamOf() can return null also
}
