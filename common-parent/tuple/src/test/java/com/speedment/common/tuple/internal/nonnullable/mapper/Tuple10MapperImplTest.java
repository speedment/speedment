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

import static org.junit.jupiter.api.Assertions.*;

final class Tuple10MapperImplTest {
    
    private final Function<Integer, Integer> m0 = i -> i + 0;
    private final Function<Integer, Integer> m1 = i -> i + 1;
    private final Function<Integer, Integer> m2 = i -> i + 2;
    private final Function<Integer, Integer> m3 = i -> i + 3;
    private final Function<Integer, Integer> m4 = i -> i + 4;
    private final Function<Integer, Integer> m5 = i -> i + 5;
    private final Function<Integer, Integer> m6 = i -> i + 6;
    private final Function<Integer, Integer> m7 = i -> i + 7;
    private final Function<Integer, Integer> m8 = i -> i + 8;
    private final Function<Integer, Integer> m9 = i -> i + 9;
    private final Tuple10MapperImpl<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> instance = new Tuple10MapperImpl<>(m0, m1, m2, m3, m4, m5, m6, m7, m8, m9);
    
    @Test
    void degree() {
        assertEquals(10, instance.degree());
    }
    
    @Test
    void apply() {
        assertEquals(Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), instance.apply(0));
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
    void get2() {
        assertEquals(m2, instance.get2());
    }
    
    @Test
    void get3() {
        assertEquals(m3, instance.get3());
    }
    
    @Test
    void get4() {
        assertEquals(m4, instance.get4());
    }
    
    @Test
    void get5() {
        assertEquals(m5, instance.get5());
    }
    
    @Test
    void get6() {
        assertEquals(m6, instance.get6());
    }
    
    @Test
    void get7() {
        assertEquals(m7, instance.get7());
    }
    
    @Test
    void get8() {
        assertEquals(m8, instance.get8());
    }
    
    @Test
    void get9() {
        assertEquals(m9, instance.get9());
    }
}