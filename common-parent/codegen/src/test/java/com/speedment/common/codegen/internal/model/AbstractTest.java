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
package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.model.trait.HasCopy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractTest<T extends HasCopy<T>> {

    private final Function<T, T>[] mutators;
    private final Supplier<T> constructor;

    private T instance;

    @SafeVarargs
    @SuppressWarnings("varargs")
    public AbstractTest(Supplier<T> constructor, Function<T, T>... mutators) {
        this.constructor = requireNonNull(constructor);
        this.mutators = requireNonNull(mutators);
        if (mutators.length == 0) {
            throw new IllegalArgumentException("Array must not be of length zero");
        }
    }

    public T instance() {
        return instance;
    }

    @BeforeEach
    void setup() {
        instance = constructor.get();
    }

    @Test
    void copy() {
        {
            final T copy = instance.copy();
            assertEquals(instance, copy);
        }
        for (Function<T, T> mutator : mutators) {
            final T copy = instance.copy();
            assertEquals(instance, copy);
            mutator.apply(copy);
            assertNotEquals(instance, copy);
        }
    }

    @Test
    void testHashCode() {
        assertNotEquals(0, instance.hashCode());
        for (Function<T, T> mutator : mutators) {
            final T copy = mutator.apply(instance.copy());
            assertNotEquals(copy.hashCode(), instance.hashCode());
        }
    }

    @Test
    void testEquals() {
        assertEquals(instance, instance);
        assertFalse(instance.equals(null));
        assertFalse(instance.equals(1));

        for (Function<T, T> mutator : mutators) {
            final T copy = mutator.apply(instance.copy());
            assertNotEquals(copy, instance);
            assertNotEquals(instance, copy);
        }
    }
}