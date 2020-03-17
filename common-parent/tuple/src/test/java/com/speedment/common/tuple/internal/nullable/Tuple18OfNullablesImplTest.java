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

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

final class Tuple18OfNullablesImplTest<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> extends AbstractTupleImplTest<Tuple18OfNullablesImpl<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> {
    
    Tuple18OfNullablesImplTest() {
        super(() -> new Tuple18OfNullablesImpl<>(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17), 18);
    }
    
    @Test
    void get0Test() {
        assertEquals(0, (int) instance.get0().orElseThrow(NoSuchElementException::new));
    }
    
    @Test
    void get1Test() {
        assertEquals(1, (int) instance.get1().orElseThrow(NoSuchElementException::new));
    }
    
    @Test
    void get2Test() {
        assertEquals(2, (int) instance.get2().orElseThrow(NoSuchElementException::new));
    }
    
    @Test
    void get3Test() {
        assertEquals(3, (int) instance.get3().orElseThrow(NoSuchElementException::new));
    }
    
    @Test
    void get4Test() {
        assertEquals(4, (int) instance.get4().orElseThrow(NoSuchElementException::new));
    }
    
    @Test
    void get5Test() {
        assertEquals(5, (int) instance.get5().orElseThrow(NoSuchElementException::new));
    }
    
    @Test
    void get6Test() {
        assertEquals(6, (int) instance.get6().orElseThrow(NoSuchElementException::new));
    }
    
    @Test
    void get7Test() {
        assertEquals(7, (int) instance.get7().orElseThrow(NoSuchElementException::new));
    }
    
    @Test
    void get8Test() {
        assertEquals(8, (int) instance.get8().orElseThrow(NoSuchElementException::new));
    }
    
    @Test
    void get9Test() {
        assertEquals(9, (int) instance.get9().orElseThrow(NoSuchElementException::new));
    }
    
    @Test
    void get10Test() {
        assertEquals(10, (int) instance.get10().orElseThrow(NoSuchElementException::new));
    }
    
    @Test
    void get11Test() {
        assertEquals(11, (int) instance.get11().orElseThrow(NoSuchElementException::new));
    }
    
    @Test
    void get12Test() {
        assertEquals(12, (int) instance.get12().orElseThrow(NoSuchElementException::new));
    }
    
    @Test
    void get13Test() {
        assertEquals(13, (int) instance.get13().orElseThrow(NoSuchElementException::new));
    }
    
    @Test
    void get14Test() {
        assertEquals(14, (int) instance.get14().orElseThrow(NoSuchElementException::new));
    }
    
    @Test
    void get15Test() {
        assertEquals(15, (int) instance.get15().orElseThrow(NoSuchElementException::new));
    }
    
    @Test
    void get16Test() {
        assertEquals(16, (int) instance.get16().orElseThrow(NoSuchElementException::new));
    }
    
    @Test
    void get17Test() {
        assertEquals(17, (int) instance.get17().orElseThrow(NoSuchElementException::new));
    }
    
    @Test
    void getOrNull0Test() {
        assertEquals(0, (int) instance.getOrNull0());
    }
    
    @Test
    void getOrNull1Test() {
        assertEquals(1, (int) instance.getOrNull1());
    }
    
    @Test
    void getOrNull2Test() {
        assertEquals(2, (int) instance.getOrNull2());
    }
    
    @Test
    void getOrNull3Test() {
        assertEquals(3, (int) instance.getOrNull3());
    }
    
    @Test
    void getOrNull4Test() {
        assertEquals(4, (int) instance.getOrNull4());
    }
    
    @Test
    void getOrNull5Test() {
        assertEquals(5, (int) instance.getOrNull5());
    }
    
    @Test
    void getOrNull6Test() {
        assertEquals(6, (int) instance.getOrNull6());
    }
    
    @Test
    void getOrNull7Test() {
        assertEquals(7, (int) instance.getOrNull7());
    }
    
    @Test
    void getOrNull8Test() {
        assertEquals(8, (int) instance.getOrNull8());
    }
    
    @Test
    void getOrNull9Test() {
        assertEquals(9, (int) instance.getOrNull9());
    }
    
    @Test
    void getOrNull10Test() {
        assertEquals(10, (int) instance.getOrNull10());
    }
    
    @Test
    void getOrNull11Test() {
        assertEquals(11, (int) instance.getOrNull11());
    }
    
    @Test
    void getOrNull12Test() {
        assertEquals(12, (int) instance.getOrNull12());
    }
    
    @Test
    void getOrNull13Test() {
        assertEquals(13, (int) instance.getOrNull13());
    }
    
    @Test
    void getOrNull14Test() {
        assertEquals(14, (int) instance.getOrNull14());
    }
    
    @Test
    void getOrNull15Test() {
        assertEquals(15, (int) instance.getOrNull15());
    }
    
    @Test
    void getOrNull16Test() {
        assertEquals(16, (int) instance.getOrNull16());
    }
    
    @Test
    void getOrNull17Test() {
        assertEquals(17, (int) instance.getOrNull17());
    }
    
    @Test
    void get() {
        IntStream.range(0, 18).forEach(i -> assertEquals(i, instance.get(i).orElseThrow(NoSuchElementException::new)));
        assertThrows(IndexOutOfBoundsException.class, () -> instance.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> instance.get(18));
    }
}