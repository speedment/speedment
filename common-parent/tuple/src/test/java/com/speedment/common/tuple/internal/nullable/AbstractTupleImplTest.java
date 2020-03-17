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
package com.speedment.common.tuple.internal.nullable;

import com.speedment.common.tuple.TupleOfNullables;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author Per Minborg
 * @param <T> tuple of nullable type
 */
public abstract class AbstractTupleImplTest<T extends TupleOfNullables> {

    private final Supplier<T> constructor;
    private final int degree;
    protected T instance;

    public AbstractTupleImplTest(Supplier<T> constructor, int degree) {
        this.constructor = requireNonNull(constructor);
        this.degree = degree;
    }

    @BeforeEach
    void before() {
        instance = constructor.get();
    }

    @Test
    void testDegree() {
        assertEquals(degree, instance.degree());
    }

    @Test
    void testGet() {
        IntStream.range(0, degree).forEachOrdered(i -> {
            assertEquals(i, (int) instance.get(i).orElseThrow(NoSuchElementException::new));
        });
    }

    @Test
    void testStream() {
        final List<Integer> expected = IntStream.range(0, degree).boxed().collect(toList());
        final List<Object> actual = instance.stream().map(Optional::get).collect(toList());
        assertEquals(expected, actual);
    }

    @Test
    void testStreamOfType() {
        assertEquals(0, instance.streamOf(String.class).count());
        assertEquals(degree, instance.streamOf(Integer.class).count());
    }

}
