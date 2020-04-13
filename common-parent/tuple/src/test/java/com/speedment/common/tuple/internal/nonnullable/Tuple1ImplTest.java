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
package com.speedment.common.tuple.internal.nonnullable;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

final class Tuple1ImplTest<T0> extends AbstractTupleImplTest<Tuple1Impl<Integer>> {
    
    Tuple1ImplTest() {
        super(() -> new Tuple1Impl<>(0), 1);
    }
    
    @Test
    void get0Test() {
        assertEquals(0, (int) instance.get0());
    }
    
    @Test
    void get() {
        IntStream.range(0, 1).forEach(i -> assertEquals(i, instance.get(i)));
        assertThrows(IndexOutOfBoundsException.class, () -> instance.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> instance.get(1));
    }
}