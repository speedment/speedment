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
package com.speedment.common.tuple;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

final class TupleBuilderTest {
    
    @Test
    void builder1() {
        final Tuple expected = Tuples.of(0);
        final Tuple actual = TupleBuilder.builder().add(0).build();
        assertEquals(expected, actual);
    }
    
    @Test
    void builder2() {
        final Tuple expected = Tuples.of(0, 1);
        final Tuple actual = TupleBuilder.builder().add(0).add(1).build();
        assertEquals(expected, actual);
    }
    
    @Test
    void builder3() {
        final Tuple expected = Tuples.of(0, 1, 2);
        final Tuple actual = TupleBuilder.builder().add(0).add(1).add(2).build();
        assertEquals(expected, actual);
    }
    
    @Test
    void builder4() {
        final Tuple expected = Tuples.of(0, 1, 2, 3);
        final Tuple actual = TupleBuilder.builder().add(0).add(1).add(2).add(3).build();
        assertEquals(expected, actual);
    }
    
    @Test
    void builder5() {
        final Tuple expected = Tuples.of(0, 1, 2, 3, 4);
        final Tuple actual = TupleBuilder.builder().add(0).add(1).add(2).add(3).add(4).build();
        assertEquals(expected, actual);
    }
    
    @Test
    void builder6() {
        final Tuple expected = Tuples.of(0, 1, 2, 3, 4, 5);
        final Tuple actual = TupleBuilder.builder().add(0).add(1).add(2).add(3).add(4).add(5).build();
        assertEquals(expected, actual);
    }
    
    @Test
    void builder7() {
        final Tuple expected = Tuples.of(0, 1, 2, 3, 4, 5, 6);
        final Tuple actual = TupleBuilder.builder().add(0).add(1).add(2).add(3).add(4).add(5).add(6).build();
        assertEquals(expected, actual);
    }
    
    @Test
    void builder8() {
        final Tuple expected = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7);
        final Tuple actual = TupleBuilder.builder().add(0).add(1).add(2).add(3).add(4).add(5).add(6).add(7).build();
        assertEquals(expected, actual);
    }
    
    @Test
    void builder9() {
        final Tuple expected = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8);
        final Tuple actual = TupleBuilder.builder().add(0).add(1).add(2).add(3).add(4).add(5).add(6).add(7).add(8).build();
        assertEquals(expected, actual);
    }
    
    @Test
    void builder10() {
        final Tuple expected = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        final Tuple actual = TupleBuilder.builder().add(0).add(1).add(2).add(3).add(4).add(5).add(6).add(7).add(8).add(9).build();
        assertEquals(expected, actual);
    }
    
    @Test
    void builder11() {
        final Tuple expected = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        final Tuple actual = TupleBuilder.builder().add(0).add(1).add(2).add(3).add(4).add(5).add(6).add(7).add(8).add(9).add(10).build();
        assertEquals(expected, actual);
    }
    
    @Test
    void builder12() {
        final Tuple expected = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
        final Tuple actual = TupleBuilder.builder().add(0).add(1).add(2).add(3).add(4).add(5).add(6).add(7).add(8).add(9).add(10).add(11).build();
        assertEquals(expected, actual);
    }
    
    @Test
    void builder13() {
        final Tuple expected = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        final Tuple actual = TupleBuilder.builder().add(0).add(1).add(2).add(3).add(4).add(5).add(6).add(7).add(8).add(9).add(10).add(11).add(12).build();
        assertEquals(expected, actual);
    }
    
    @Test
    void builder14() {
        final Tuple expected = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
        final Tuple actual = TupleBuilder.builder().add(0).add(1).add(2).add(3).add(4).add(5).add(6).add(7).add(8).add(9).add(10).add(11).add(12).add(13).build();
        assertEquals(expected, actual);
    }
    
    @Test
    void builder15() {
        final Tuple expected = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
        final Tuple actual = TupleBuilder.builder().add(0).add(1).add(2).add(3).add(4).add(5).add(6).add(7).add(8).add(9).add(10).add(11).add(12).add(13).add(14).build();
        assertEquals(expected, actual);
    }
    
    @Test
    void builder16() {
        final Tuple expected = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
        final Tuple actual = TupleBuilder.builder().add(0).add(1).add(2).add(3).add(4).add(5).add(6).add(7).add(8).add(9).add(10).add(11).add(12).add(13).add(14).add(15).build();
        assertEquals(expected, actual);
    }
    
    @Test
    void builder17() {
        final Tuple expected = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        final Tuple actual = TupleBuilder.builder().add(0).add(1).add(2).add(3).add(4).add(5).add(6).add(7).add(8).add(9).add(10).add(11).add(12).add(13).add(14).add(15).add(16).build();
        assertEquals(expected, actual);
    }
    
    @Test
    void builder18() {
        final Tuple expected = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17);
        final Tuple actual = TupleBuilder.builder().add(0).add(1).add(2).add(3).add(4).add(5).add(6).add(7).add(8).add(9).add(10).add(11).add(12).add(13).add(14).add(15).add(16).add(17).build();
        assertEquals(expected, actual);
    }
    
    @Test
    void builder19() {
        final Tuple expected = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18);
        final Tuple actual = TupleBuilder.builder().add(0).add(1).add(2).add(3).add(4).add(5).add(6).add(7).add(8).add(9).add(10).add(11).add(12).add(13).add(14).add(15).add(16).add(17).add(18).build();
        assertEquals(expected, actual);
    }
    
    @Test
    void builder20() {
        final Tuple expected = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19);
        final Tuple actual = TupleBuilder.builder().add(0).add(1).add(2).add(3).add(4).add(5).add(6).add(7).add(8).add(9).add(10).add(11).add(12).add(13).add(14).add(15).add(16).add(17).add(18).add(19).build();
        assertEquals(expected, actual);
    }
    
    @Test
    void builder21() {
        final Tuple expected = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        final Tuple actual = TupleBuilder.builder().add(0).add(1).add(2).add(3).add(4).add(5).add(6).add(7).add(8).add(9).add(10).add(11).add(12).add(13).add(14).add(15).add(16).add(17).add(18).add(19).add(20).build();
        assertEquals(expected, actual);
    }
    
    @Test
    void builder22() {
        final Tuple expected = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21);
        final Tuple actual = TupleBuilder.builder().add(0).add(1).add(2).add(3).add(4).add(5).add(6).add(7).add(8).add(9).add(10).add(11).add(12).add(13).add(14).add(15).add(16).add(17).add(18).add(19).add(20).add(21).build();
        assertEquals(expected, actual);
    }
    
    @Test
    void builder23() {
        final Tuple expected = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22);
        final Tuple actual = TupleBuilder.builder().add(0).add(1).add(2).add(3).add(4).add(5).add(6).add(7).add(8).add(9).add(10).add(11).add(12).add(13).add(14).add(15).add(16).add(17).add(18).add(19).add(20).add(21).add(22).build();
        assertEquals(expected, actual);
    }
}