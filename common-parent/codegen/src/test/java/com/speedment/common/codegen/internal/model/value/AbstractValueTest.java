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
package com.speedment.common.codegen.internal.model.value;

import com.speedment.common.codegen.model.trait.HasCopy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public abstract class AbstractValueTest<V, T extends HasCopy<?>> {

    private final Function<T, ?>[] mutators;
    private final Supplier<T> constructor;

    private T instance;

    @SafeVarargs
    @SuppressWarnings("varargs")
    public AbstractValueTest(Supplier<T> constructor, Function<T, ?>... mutators) {
        this.constructor = requireNonNull(constructor);
        this.mutators = requireNonNull(mutators);
    }

    public T instance() {
        return instance;
    }

    @BeforeEach
    void setup() {
        instance = constructor.get();
    }

    @Test
    @SuppressWarnings("unchecked")
    void copy() {
        {
            final T copy = (T) instance.copy();
            assertEquals(instance, copy);
        }
        for (Function<T, ?> mutator : mutators) {
            final T copy = (T) instance.copy();
            assertEquals(instance, copy);
            mutator.apply(copy);
            assertNotEquals(instance, copy);
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    void testHashCode() {
        assertNotEquals(0, instance.hashCode());
        for (Function<T, ?> mutator:mutators) {
            final T copy = (T) instance.copy();
            final T mutatedCopy = (T) mutator.apply(copy);
            assertNotEquals(mutatedCopy.hashCode(), instance.hashCode());
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    void testEquals() {
        assertEquals(instance, instance);
        assertFalse(instance.equals(null));
        assertFalse(instance.equals(1));

        for (Function<T, ?> mutator:mutators) {
            System.out.println(mutator);
            final T copy = (T) instance.copy();
            final T mutatedCopy = (T) mutator.apply(copy);
            assertNotEquals(mutatedCopy, instance);
            assertNotEquals(instance, mutatedCopy);
        }
    }
}