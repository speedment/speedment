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
package com.speedment.common.tuple.internal.nonnullable.mapper;

import com.speedment.common.tuple.Tuples;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class ManualTuple2MapperImplTest {

    private final Function<Integer, Integer> m0 = i -> i + 0;
    private final Function<Integer, Integer> m1 = i -> i + 1;
    private final Tuple2MapperImpl<Integer, Integer, Integer> instance = new Tuple2MapperImpl<>(m0, m1);

    @Test
    void degree() {
        assertEquals(2, instance.degree());
    }

    @Test
    void get0() {
        assertEquals(m0, instance.get0());
    }

    @Test
    void get1() {
        assertEquals(m1, instance.get1());
    }

    @Test
    void apply() {
        assertEquals(Tuples.of(0, 1), instance.apply(0));
    }

}