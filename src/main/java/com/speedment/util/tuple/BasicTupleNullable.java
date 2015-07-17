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
import java.util.Optional;
import static java.util.stream.Collectors.joining;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <T> The implementation type of the BasicTuple
 */
public abstract class BasicTupleNullable<T extends BasicTupleNullable<T>> {

    protected final Object[] values;
    @SuppressWarnings("rawtypes")
    protected final Class[] classes;
    @SuppressWarnings("rawtypes")
    private final Class<? extends BasicTupleNullable> baseClass;

    @SuppressWarnings("rawtypes")
    public BasicTupleNullable(Class<? extends BasicTupleNullable> baseClass, Class[] classes) {
        this.baseClass = baseClass;
        this.values = new Object[classes.length];
        this.classes = classes;
    }

    @SuppressWarnings("rawtypes")
    public BasicTupleNullable(Class<? extends BasicTupleNullable> baseClass, Class[] classes, Object... values) {
        this.baseClass = baseClass;
        this.classes = Arrays.copyOf(classes, classes.length);
        this.values = Arrays.copyOf(values, values.length);
    }

    public int capacity() {
        return classes.length;
    }

    public Optional<Object> get(int index) {
        if (index < 0 || index >= capacity()) {
            throw new IllegalArgumentException("index " + index + " is illegal. There is capacity for " + capacity() + " items in this class.");
        }
        return Optional.ofNullable(values[index]);
    }

    public Object remove(int index) {
        final Object previousValue = get(index);
        values[index] = null;
        return previousValue;
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

    public Stream<Optional<Object>> stream() {
        return Stream.of(values).map(Optional::ofNullable);
    }

    public <C> Stream<Optional<C>> streamOf(Class<C> clazz) {
        final Stream.Builder<Optional<C>> sb = Stream.builder();
        for (int i = 0; i < classes.length; i++) {
            if (clazz.isAssignableFrom(classes[i])) {
                @SuppressWarnings("unchecked")
                final Optional<C> item = Optional.ofNullable((C) values[i]);
                sb.add(item);
            }
        }
        return sb.build();
    }

    // Perhaps a list of expected classes T0, T1, ..., TN so that streamOf() can return null also
}
