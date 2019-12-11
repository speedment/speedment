/*
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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

import com.speedment.common.tuple.mutable.MutableTuple0;
import com.speedment.common.tuple.mutable.MutableTuple10;
import com.speedment.common.tuple.mutable.MutableTuple11;
import com.speedment.common.tuple.mutable.MutableTuple12;
import com.speedment.common.tuple.mutable.MutableTuple13;
import com.speedment.common.tuple.mutable.MutableTuple14;
import com.speedment.common.tuple.mutable.MutableTuple15;
import com.speedment.common.tuple.mutable.MutableTuple16;
import com.speedment.common.tuple.mutable.MutableTuple17;
import com.speedment.common.tuple.mutable.MutableTuple18;
import com.speedment.common.tuple.mutable.MutableTuple19;
import com.speedment.common.tuple.mutable.MutableTuple1;
import com.speedment.common.tuple.mutable.MutableTuple20;
import com.speedment.common.tuple.mutable.MutableTuple21;
import com.speedment.common.tuple.mutable.MutableTuple22;
import com.speedment.common.tuple.mutable.MutableTuple23;
import com.speedment.common.tuple.mutable.MutableTuple2;
import com.speedment.common.tuple.mutable.MutableTuple3;
import com.speedment.common.tuple.mutable.MutableTuple4;
import com.speedment.common.tuple.mutable.MutableTuple5;
import com.speedment.common.tuple.mutable.MutableTuple6;
import com.speedment.common.tuple.mutable.MutableTuple7;
import com.speedment.common.tuple.mutable.MutableTuple8;
import com.speedment.common.tuple.mutable.MutableTuple9;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

final class MutableTuplesTest {
    
    @Test
    void create0() {
        final MutableTuple0 tuple = MutableTuples.create0();
        test0(tuple);
    }
    
    @Test
    void constructor0() {
        final Supplier<MutableTuple0> constructor = MutableTuples.constructor();
        test0(constructor.get());
    }
    
    void test0(final MutableTuple0 tuple) {
        assertTuple(tuple, 0);
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(0));
    }
    
    @Test
    void create1() {
        final MutableTuple1<Integer> tuple = MutableTuples.create1();
        test1(tuple);
    }
    
    @Test
    void constructor1() {
        final Supplier<MutableTuple1<Integer>> constructor = MutableTuples.constructor(Integer.class);
        test1(constructor.get());
    }
    
    void test1(final MutableTuple1<Integer> tuple) {
        tuple.set0(0);
        assertTuple(tuple, 1);
        assertEquals(0, tuple.get0().orElseThrow(NoSuchElementException::new));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(1));
    }
    
    @Test
    void create2() {
        final MutableTuple2<Integer, Integer> tuple = MutableTuples.create2();
        test2(tuple);
    }
    
    @Test
    void constructor2() {
        final Supplier<MutableTuple2<Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class);
        test2(constructor.get());
    }
    
    void test2(final MutableTuple2<Integer, Integer> tuple) {
        tuple.set0(0);
        tuple.set1(1);
        assertTuple(tuple, 2);
        assertEquals(0, tuple.get0().orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get1().orElseThrow(NoSuchElementException::new));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(2));
    }
    
    @Test
    void create3() {
        final MutableTuple3<Integer, Integer, Integer> tuple = MutableTuples.create3();
        test3(tuple);
    }
    
    @Test
    void constructor3() {
        final Supplier<MutableTuple3<Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class);
        test3(constructor.get());
    }
    
    void test3(final MutableTuple3<Integer, Integer, Integer> tuple) {
        tuple.set0(0);
        tuple.set1(1);
        tuple.set2(2);
        assertTuple(tuple, 3);
        assertEquals(0, tuple.get0().orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get1().orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get2().orElseThrow(NoSuchElementException::new));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(3));
    }
    
    @Test
    void create4() {
        final MutableTuple4<Integer, Integer, Integer, Integer> tuple = MutableTuples.create4();
        test4(tuple);
    }
    
    @Test
    void constructor4() {
        final Supplier<MutableTuple4<Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class);
        test4(constructor.get());
    }
    
    void test4(final MutableTuple4<Integer, Integer, Integer, Integer> tuple) {
        tuple.set0(0);
        tuple.set1(1);
        tuple.set2(2);
        tuple.set3(3);
        assertTuple(tuple, 4);
        assertEquals(0, tuple.get0().orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get1().orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get2().orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get3().orElseThrow(NoSuchElementException::new));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(4));
    }
    
    @Test
    void create5() {
        final MutableTuple5<Integer, Integer, Integer, Integer, Integer> tuple = MutableTuples.create5();
        test5(tuple);
    }
    
    @Test
    void constructor5() {
        final Supplier<MutableTuple5<Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test5(constructor.get());
    }
    
    void test5(final MutableTuple5<Integer, Integer, Integer, Integer, Integer> tuple) {
        tuple.set0(0);
        tuple.set1(1);
        tuple.set2(2);
        tuple.set3(3);
        tuple.set4(4);
        assertTuple(tuple, 5);
        assertEquals(0, tuple.get0().orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get1().orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get2().orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get3().orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get4().orElseThrow(NoSuchElementException::new));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(5));
    }
    
    @Test
    void create6() {
        final MutableTuple6<Integer, Integer, Integer, Integer, Integer, Integer> tuple = MutableTuples.create6();
        test6(tuple);
    }
    
    @Test
    void constructor6() {
        final Supplier<MutableTuple6<Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test6(constructor.get());
    }
    
    void test6(final MutableTuple6<Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        tuple.set0(0);
        tuple.set1(1);
        tuple.set2(2);
        tuple.set3(3);
        tuple.set4(4);
        tuple.set5(5);
        assertTuple(tuple, 6);
        assertEquals(0, tuple.get0().orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get1().orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get2().orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get3().orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get4().orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get5().orElseThrow(NoSuchElementException::new));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(6));
    }
    
    @Test
    void create7() {
        final MutableTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = MutableTuples.create7();
        test7(tuple);
    }
    
    @Test
    void constructor7() {
        final Supplier<MutableTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test7(constructor.get());
    }
    
    void test7(final MutableTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        tuple.set0(0);
        tuple.set1(1);
        tuple.set2(2);
        tuple.set3(3);
        tuple.set4(4);
        tuple.set5(5);
        tuple.set6(6);
        assertTuple(tuple, 7);
        assertEquals(0, tuple.get0().orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get1().orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get2().orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get3().orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get4().orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get5().orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get6().orElseThrow(NoSuchElementException::new));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(7));
    }
    
    @Test
    void create8() {
        final MutableTuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = MutableTuples.create8();
        test8(tuple);
    }
    
    @Test
    void constructor8() {
        final Supplier<MutableTuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test8(constructor.get());
    }
    
    void test8(final MutableTuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        tuple.set0(0);
        tuple.set1(1);
        tuple.set2(2);
        tuple.set3(3);
        tuple.set4(4);
        tuple.set5(5);
        tuple.set6(6);
        tuple.set7(7);
        assertTuple(tuple, 8);
        assertEquals(0, tuple.get0().orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get1().orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get2().orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get3().orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get4().orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get5().orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get6().orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get7().orElseThrow(NoSuchElementException::new));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(8));
    }
    
    @Test
    void create9() {
        final MutableTuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = MutableTuples.create9();
        test9(tuple);
    }
    
    @Test
    void constructor9() {
        final Supplier<MutableTuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test9(constructor.get());
    }
    
    void test9(final MutableTuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        tuple.set0(0);
        tuple.set1(1);
        tuple.set2(2);
        tuple.set3(3);
        tuple.set4(4);
        tuple.set5(5);
        tuple.set6(6);
        tuple.set7(7);
        tuple.set8(8);
        assertTuple(tuple, 9);
        assertEquals(0, tuple.get0().orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get1().orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get2().orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get3().orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get4().orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get5().orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get6().orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get7().orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get8().orElseThrow(NoSuchElementException::new));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get(8).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(9));
    }
    
    @Test
    void create10() {
        final MutableTuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = MutableTuples.create10();
        test10(tuple);
    }
    
    @Test
    void constructor10() {
        final Supplier<MutableTuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test10(constructor.get());
    }
    
    void test10(final MutableTuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        tuple.set0(0);
        tuple.set1(1);
        tuple.set2(2);
        tuple.set3(3);
        tuple.set4(4);
        tuple.set5(5);
        tuple.set6(6);
        tuple.set7(7);
        tuple.set8(8);
        tuple.set9(9);
        assertTuple(tuple, 10);
        assertEquals(0, tuple.get0().orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get1().orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get2().orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get3().orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get4().orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get5().orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get6().orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get7().orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get8().orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get9().orElseThrow(NoSuchElementException::new));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get(8).orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get(9).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(10));
    }
    
    @Test
    void create11() {
        final MutableTuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = MutableTuples.create11();
        test11(tuple);
    }
    
    @Test
    void constructor11() {
        final Supplier<MutableTuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test11(constructor.get());
    }
    
    void test11(final MutableTuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        tuple.set0(0);
        tuple.set1(1);
        tuple.set2(2);
        tuple.set3(3);
        tuple.set4(4);
        tuple.set5(5);
        tuple.set6(6);
        tuple.set7(7);
        tuple.set8(8);
        tuple.set9(9);
        tuple.set10(10);
        assertTuple(tuple, 11);
        assertEquals(0, tuple.get0().orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get1().orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get2().orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get3().orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get4().orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get5().orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get6().orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get7().orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get8().orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get9().orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get10().orElseThrow(NoSuchElementException::new));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get(8).orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get(9).orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get(10).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(11));
    }
    
    @Test
    void create12() {
        final MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = MutableTuples.create12();
        test12(tuple);
    }
    
    @Test
    void constructor12() {
        final Supplier<MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test12(constructor.get());
    }
    
    void test12(final MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        tuple.set0(0);
        tuple.set1(1);
        tuple.set2(2);
        tuple.set3(3);
        tuple.set4(4);
        tuple.set5(5);
        tuple.set6(6);
        tuple.set7(7);
        tuple.set8(8);
        tuple.set9(9);
        tuple.set10(10);
        tuple.set11(11);
        assertTuple(tuple, 12);
        assertEquals(0, tuple.get0().orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get1().orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get2().orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get3().orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get4().orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get5().orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get6().orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get7().orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get8().orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get9().orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get10().orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get11().orElseThrow(NoSuchElementException::new));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get(8).orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get(9).orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get(10).orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get(11).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(12));
    }
    
    @Test
    void create13() {
        final MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = MutableTuples.create13();
        test13(tuple);
    }
    
    @Test
    void constructor13() {
        final Supplier<MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test13(constructor.get());
    }
    
    void test13(final MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        tuple.set0(0);
        tuple.set1(1);
        tuple.set2(2);
        tuple.set3(3);
        tuple.set4(4);
        tuple.set5(5);
        tuple.set6(6);
        tuple.set7(7);
        tuple.set8(8);
        tuple.set9(9);
        tuple.set10(10);
        tuple.set11(11);
        tuple.set12(12);
        assertTuple(tuple, 13);
        assertEquals(0, tuple.get0().orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get1().orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get2().orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get3().orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get4().orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get5().orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get6().orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get7().orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get8().orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get9().orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get10().orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get11().orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get12().orElseThrow(NoSuchElementException::new));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get(8).orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get(9).orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get(10).orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get(11).orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get(12).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(13));
    }
    
    @Test
    void create14() {
        final MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = MutableTuples.create14();
        test14(tuple);
    }
    
    @Test
    void constructor14() {
        final Supplier<MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test14(constructor.get());
    }
    
    void test14(final MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        tuple.set0(0);
        tuple.set1(1);
        tuple.set2(2);
        tuple.set3(3);
        tuple.set4(4);
        tuple.set5(5);
        tuple.set6(6);
        tuple.set7(7);
        tuple.set8(8);
        tuple.set9(9);
        tuple.set10(10);
        tuple.set11(11);
        tuple.set12(12);
        tuple.set13(13);
        assertTuple(tuple, 14);
        assertEquals(0, tuple.get0().orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get1().orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get2().orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get3().orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get4().orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get5().orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get6().orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get7().orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get8().orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get9().orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get10().orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get11().orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get12().orElseThrow(NoSuchElementException::new));
        assertEquals(13, tuple.get13().orElseThrow(NoSuchElementException::new));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get(8).orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get(9).orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get(10).orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get(11).orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get(12).orElseThrow(NoSuchElementException::new));
        assertEquals(13, tuple.get(13).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(14));
    }
    
    @Test
    void create15() {
        final MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = MutableTuples.create15();
        test15(tuple);
    }
    
    @Test
    void constructor15() {
        final Supplier<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test15(constructor.get());
    }
    
    void test15(final MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        tuple.set0(0);
        tuple.set1(1);
        tuple.set2(2);
        tuple.set3(3);
        tuple.set4(4);
        tuple.set5(5);
        tuple.set6(6);
        tuple.set7(7);
        tuple.set8(8);
        tuple.set9(9);
        tuple.set10(10);
        tuple.set11(11);
        tuple.set12(12);
        tuple.set13(13);
        tuple.set14(14);
        assertTuple(tuple, 15);
        assertEquals(0, tuple.get0().orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get1().orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get2().orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get3().orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get4().orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get5().orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get6().orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get7().orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get8().orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get9().orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get10().orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get11().orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get12().orElseThrow(NoSuchElementException::new));
        assertEquals(13, tuple.get13().orElseThrow(NoSuchElementException::new));
        assertEquals(14, tuple.get14().orElseThrow(NoSuchElementException::new));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get(8).orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get(9).orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get(10).orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get(11).orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get(12).orElseThrow(NoSuchElementException::new));
        assertEquals(13, tuple.get(13).orElseThrow(NoSuchElementException::new));
        assertEquals(14, tuple.get(14).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(15));
    }
    
    @Test
    void create16() {
        final MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = MutableTuples.create16();
        test16(tuple);
    }
    
    @Test
    void constructor16() {
        final Supplier<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test16(constructor.get());
    }
    
    void test16(final MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        tuple.set0(0);
        tuple.set1(1);
        tuple.set2(2);
        tuple.set3(3);
        tuple.set4(4);
        tuple.set5(5);
        tuple.set6(6);
        tuple.set7(7);
        tuple.set8(8);
        tuple.set9(9);
        tuple.set10(10);
        tuple.set11(11);
        tuple.set12(12);
        tuple.set13(13);
        tuple.set14(14);
        tuple.set15(15);
        assertTuple(tuple, 16);
        assertEquals(0, tuple.get0().orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get1().orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get2().orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get3().orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get4().orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get5().orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get6().orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get7().orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get8().orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get9().orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get10().orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get11().orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get12().orElseThrow(NoSuchElementException::new));
        assertEquals(13, tuple.get13().orElseThrow(NoSuchElementException::new));
        assertEquals(14, tuple.get14().orElseThrow(NoSuchElementException::new));
        assertEquals(15, tuple.get15().orElseThrow(NoSuchElementException::new));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get(8).orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get(9).orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get(10).orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get(11).orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get(12).orElseThrow(NoSuchElementException::new));
        assertEquals(13, tuple.get(13).orElseThrow(NoSuchElementException::new));
        assertEquals(14, tuple.get(14).orElseThrow(NoSuchElementException::new));
        assertEquals(15, tuple.get(15).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(16));
    }
    
    @Test
    void create17() {
        final MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = MutableTuples.create17();
        test17(tuple);
    }
    
    @Test
    void constructor17() {
        final Supplier<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test17(constructor.get());
    }
    
    void test17(final MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        tuple.set0(0);
        tuple.set1(1);
        tuple.set2(2);
        tuple.set3(3);
        tuple.set4(4);
        tuple.set5(5);
        tuple.set6(6);
        tuple.set7(7);
        tuple.set8(8);
        tuple.set9(9);
        tuple.set10(10);
        tuple.set11(11);
        tuple.set12(12);
        tuple.set13(13);
        tuple.set14(14);
        tuple.set15(15);
        tuple.set16(16);
        assertTuple(tuple, 17);
        assertEquals(0, tuple.get0().orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get1().orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get2().orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get3().orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get4().orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get5().orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get6().orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get7().orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get8().orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get9().orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get10().orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get11().orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get12().orElseThrow(NoSuchElementException::new));
        assertEquals(13, tuple.get13().orElseThrow(NoSuchElementException::new));
        assertEquals(14, tuple.get14().orElseThrow(NoSuchElementException::new));
        assertEquals(15, tuple.get15().orElseThrow(NoSuchElementException::new));
        assertEquals(16, tuple.get16().orElseThrow(NoSuchElementException::new));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get(8).orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get(9).orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get(10).orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get(11).orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get(12).orElseThrow(NoSuchElementException::new));
        assertEquals(13, tuple.get(13).orElseThrow(NoSuchElementException::new));
        assertEquals(14, tuple.get(14).orElseThrow(NoSuchElementException::new));
        assertEquals(15, tuple.get(15).orElseThrow(NoSuchElementException::new));
        assertEquals(16, tuple.get(16).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(17));
    }
    
    @Test
    void create18() {
        final MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = MutableTuples.create18();
        test18(tuple);
    }
    
    @Test
    void constructor18() {
        final Supplier<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test18(constructor.get());
    }
    
    void test18(final MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        tuple.set0(0);
        tuple.set1(1);
        tuple.set2(2);
        tuple.set3(3);
        tuple.set4(4);
        tuple.set5(5);
        tuple.set6(6);
        tuple.set7(7);
        tuple.set8(8);
        tuple.set9(9);
        tuple.set10(10);
        tuple.set11(11);
        tuple.set12(12);
        tuple.set13(13);
        tuple.set14(14);
        tuple.set15(15);
        tuple.set16(16);
        tuple.set17(17);
        assertTuple(tuple, 18);
        assertEquals(0, tuple.get0().orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get1().orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get2().orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get3().orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get4().orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get5().orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get6().orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get7().orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get8().orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get9().orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get10().orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get11().orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get12().orElseThrow(NoSuchElementException::new));
        assertEquals(13, tuple.get13().orElseThrow(NoSuchElementException::new));
        assertEquals(14, tuple.get14().orElseThrow(NoSuchElementException::new));
        assertEquals(15, tuple.get15().orElseThrow(NoSuchElementException::new));
        assertEquals(16, tuple.get16().orElseThrow(NoSuchElementException::new));
        assertEquals(17, tuple.get17().orElseThrow(NoSuchElementException::new));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get(8).orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get(9).orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get(10).orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get(11).orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get(12).orElseThrow(NoSuchElementException::new));
        assertEquals(13, tuple.get(13).orElseThrow(NoSuchElementException::new));
        assertEquals(14, tuple.get(14).orElseThrow(NoSuchElementException::new));
        assertEquals(15, tuple.get(15).orElseThrow(NoSuchElementException::new));
        assertEquals(16, tuple.get(16).orElseThrow(NoSuchElementException::new));
        assertEquals(17, tuple.get(17).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(18));
    }
    
    @Test
    void create19() {
        final MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = MutableTuples.create19();
        test19(tuple);
    }
    
    @Test
    void constructor19() {
        final Supplier<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test19(constructor.get());
    }
    
    void test19(final MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        tuple.set0(0);
        tuple.set1(1);
        tuple.set2(2);
        tuple.set3(3);
        tuple.set4(4);
        tuple.set5(5);
        tuple.set6(6);
        tuple.set7(7);
        tuple.set8(8);
        tuple.set9(9);
        tuple.set10(10);
        tuple.set11(11);
        tuple.set12(12);
        tuple.set13(13);
        tuple.set14(14);
        tuple.set15(15);
        tuple.set16(16);
        tuple.set17(17);
        tuple.set18(18);
        assertTuple(tuple, 19);
        assertEquals(0, tuple.get0().orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get1().orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get2().orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get3().orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get4().orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get5().orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get6().orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get7().orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get8().orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get9().orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get10().orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get11().orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get12().orElseThrow(NoSuchElementException::new));
        assertEquals(13, tuple.get13().orElseThrow(NoSuchElementException::new));
        assertEquals(14, tuple.get14().orElseThrow(NoSuchElementException::new));
        assertEquals(15, tuple.get15().orElseThrow(NoSuchElementException::new));
        assertEquals(16, tuple.get16().orElseThrow(NoSuchElementException::new));
        assertEquals(17, tuple.get17().orElseThrow(NoSuchElementException::new));
        assertEquals(18, tuple.get18().orElseThrow(NoSuchElementException::new));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get(8).orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get(9).orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get(10).orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get(11).orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get(12).orElseThrow(NoSuchElementException::new));
        assertEquals(13, tuple.get(13).orElseThrow(NoSuchElementException::new));
        assertEquals(14, tuple.get(14).orElseThrow(NoSuchElementException::new));
        assertEquals(15, tuple.get(15).orElseThrow(NoSuchElementException::new));
        assertEquals(16, tuple.get(16).orElseThrow(NoSuchElementException::new));
        assertEquals(17, tuple.get(17).orElseThrow(NoSuchElementException::new));
        assertEquals(18, tuple.get(18).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(19));
    }
    
    @Test
    void create20() {
        final MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = MutableTuples.create20();
        test20(tuple);
    }
    
    @Test
    void constructor20() {
        final Supplier<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test20(constructor.get());
    }
    
    void test20(final MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        tuple.set0(0);
        tuple.set1(1);
        tuple.set2(2);
        tuple.set3(3);
        tuple.set4(4);
        tuple.set5(5);
        tuple.set6(6);
        tuple.set7(7);
        tuple.set8(8);
        tuple.set9(9);
        tuple.set10(10);
        tuple.set11(11);
        tuple.set12(12);
        tuple.set13(13);
        tuple.set14(14);
        tuple.set15(15);
        tuple.set16(16);
        tuple.set17(17);
        tuple.set18(18);
        tuple.set19(19);
        assertTuple(tuple, 20);
        assertEquals(0, tuple.get0().orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get1().orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get2().orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get3().orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get4().orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get5().orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get6().orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get7().orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get8().orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get9().orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get10().orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get11().orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get12().orElseThrow(NoSuchElementException::new));
        assertEquals(13, tuple.get13().orElseThrow(NoSuchElementException::new));
        assertEquals(14, tuple.get14().orElseThrow(NoSuchElementException::new));
        assertEquals(15, tuple.get15().orElseThrow(NoSuchElementException::new));
        assertEquals(16, tuple.get16().orElseThrow(NoSuchElementException::new));
        assertEquals(17, tuple.get17().orElseThrow(NoSuchElementException::new));
        assertEquals(18, tuple.get18().orElseThrow(NoSuchElementException::new));
        assertEquals(19, tuple.get19().orElseThrow(NoSuchElementException::new));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get(8).orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get(9).orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get(10).orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get(11).orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get(12).orElseThrow(NoSuchElementException::new));
        assertEquals(13, tuple.get(13).orElseThrow(NoSuchElementException::new));
        assertEquals(14, tuple.get(14).orElseThrow(NoSuchElementException::new));
        assertEquals(15, tuple.get(15).orElseThrow(NoSuchElementException::new));
        assertEquals(16, tuple.get(16).orElseThrow(NoSuchElementException::new));
        assertEquals(17, tuple.get(17).orElseThrow(NoSuchElementException::new));
        assertEquals(18, tuple.get(18).orElseThrow(NoSuchElementException::new));
        assertEquals(19, tuple.get(19).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(20));
    }
    
    @Test
    void create21() {
        final MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = MutableTuples.create21();
        test21(tuple);
    }
    
    @Test
    void constructor21() {
        final Supplier<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test21(constructor.get());
    }
    
    void test21(final MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        tuple.set0(0);
        tuple.set1(1);
        tuple.set2(2);
        tuple.set3(3);
        tuple.set4(4);
        tuple.set5(5);
        tuple.set6(6);
        tuple.set7(7);
        tuple.set8(8);
        tuple.set9(9);
        tuple.set10(10);
        tuple.set11(11);
        tuple.set12(12);
        tuple.set13(13);
        tuple.set14(14);
        tuple.set15(15);
        tuple.set16(16);
        tuple.set17(17);
        tuple.set18(18);
        tuple.set19(19);
        tuple.set20(20);
        assertTuple(tuple, 21);
        assertEquals(0, tuple.get0().orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get1().orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get2().orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get3().orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get4().orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get5().orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get6().orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get7().orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get8().orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get9().orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get10().orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get11().orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get12().orElseThrow(NoSuchElementException::new));
        assertEquals(13, tuple.get13().orElseThrow(NoSuchElementException::new));
        assertEquals(14, tuple.get14().orElseThrow(NoSuchElementException::new));
        assertEquals(15, tuple.get15().orElseThrow(NoSuchElementException::new));
        assertEquals(16, tuple.get16().orElseThrow(NoSuchElementException::new));
        assertEquals(17, tuple.get17().orElseThrow(NoSuchElementException::new));
        assertEquals(18, tuple.get18().orElseThrow(NoSuchElementException::new));
        assertEquals(19, tuple.get19().orElseThrow(NoSuchElementException::new));
        assertEquals(20, tuple.get20().orElseThrow(NoSuchElementException::new));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get(8).orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get(9).orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get(10).orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get(11).orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get(12).orElseThrow(NoSuchElementException::new));
        assertEquals(13, tuple.get(13).orElseThrow(NoSuchElementException::new));
        assertEquals(14, tuple.get(14).orElseThrow(NoSuchElementException::new));
        assertEquals(15, tuple.get(15).orElseThrow(NoSuchElementException::new));
        assertEquals(16, tuple.get(16).orElseThrow(NoSuchElementException::new));
        assertEquals(17, tuple.get(17).orElseThrow(NoSuchElementException::new));
        assertEquals(18, tuple.get(18).orElseThrow(NoSuchElementException::new));
        assertEquals(19, tuple.get(19).orElseThrow(NoSuchElementException::new));
        assertEquals(20, tuple.get(20).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(21));
    }
    
    @Test
    void create22() {
        final MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = MutableTuples.create22();
        test22(tuple);
    }
    
    @Test
    void constructor22() {
        final Supplier<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test22(constructor.get());
    }
    
    void test22(final MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        tuple.set0(0);
        tuple.set1(1);
        tuple.set2(2);
        tuple.set3(3);
        tuple.set4(4);
        tuple.set5(5);
        tuple.set6(6);
        tuple.set7(7);
        tuple.set8(8);
        tuple.set9(9);
        tuple.set10(10);
        tuple.set11(11);
        tuple.set12(12);
        tuple.set13(13);
        tuple.set14(14);
        tuple.set15(15);
        tuple.set16(16);
        tuple.set17(17);
        tuple.set18(18);
        tuple.set19(19);
        tuple.set20(20);
        tuple.set21(21);
        assertTuple(tuple, 22);
        assertEquals(0, tuple.get0().orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get1().orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get2().orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get3().orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get4().orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get5().orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get6().orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get7().orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get8().orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get9().orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get10().orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get11().orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get12().orElseThrow(NoSuchElementException::new));
        assertEquals(13, tuple.get13().orElseThrow(NoSuchElementException::new));
        assertEquals(14, tuple.get14().orElseThrow(NoSuchElementException::new));
        assertEquals(15, tuple.get15().orElseThrow(NoSuchElementException::new));
        assertEquals(16, tuple.get16().orElseThrow(NoSuchElementException::new));
        assertEquals(17, tuple.get17().orElseThrow(NoSuchElementException::new));
        assertEquals(18, tuple.get18().orElseThrow(NoSuchElementException::new));
        assertEquals(19, tuple.get19().orElseThrow(NoSuchElementException::new));
        assertEquals(20, tuple.get20().orElseThrow(NoSuchElementException::new));
        assertEquals(21, tuple.get21().orElseThrow(NoSuchElementException::new));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get(8).orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get(9).orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get(10).orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get(11).orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get(12).orElseThrow(NoSuchElementException::new));
        assertEquals(13, tuple.get(13).orElseThrow(NoSuchElementException::new));
        assertEquals(14, tuple.get(14).orElseThrow(NoSuchElementException::new));
        assertEquals(15, tuple.get(15).orElseThrow(NoSuchElementException::new));
        assertEquals(16, tuple.get(16).orElseThrow(NoSuchElementException::new));
        assertEquals(17, tuple.get(17).orElseThrow(NoSuchElementException::new));
        assertEquals(18, tuple.get(18).orElseThrow(NoSuchElementException::new));
        assertEquals(19, tuple.get(19).orElseThrow(NoSuchElementException::new));
        assertEquals(20, tuple.get(20).orElseThrow(NoSuchElementException::new));
        assertEquals(21, tuple.get(21).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(22));
    }
    
    @Test
    void create23() {
        final MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = MutableTuples.create23();
        test23(tuple);
    }
    
    @Test
    void constructor23() {
        final Supplier<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test23(constructor.get());
    }
    
    void test23(final MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        tuple.set0(0);
        tuple.set1(1);
        tuple.set2(2);
        tuple.set3(3);
        tuple.set4(4);
        tuple.set5(5);
        tuple.set6(6);
        tuple.set7(7);
        tuple.set8(8);
        tuple.set9(9);
        tuple.set10(10);
        tuple.set11(11);
        tuple.set12(12);
        tuple.set13(13);
        tuple.set14(14);
        tuple.set15(15);
        tuple.set16(16);
        tuple.set17(17);
        tuple.set18(18);
        tuple.set19(19);
        tuple.set20(20);
        tuple.set21(21);
        tuple.set22(22);
        assertTuple(tuple, 23);
        assertEquals(0, tuple.get0().orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get1().orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get2().orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get3().orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get4().orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get5().orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get6().orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get7().orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get8().orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get9().orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get10().orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get11().orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get12().orElseThrow(NoSuchElementException::new));
        assertEquals(13, tuple.get13().orElseThrow(NoSuchElementException::new));
        assertEquals(14, tuple.get14().orElseThrow(NoSuchElementException::new));
        assertEquals(15, tuple.get15().orElseThrow(NoSuchElementException::new));
        assertEquals(16, tuple.get16().orElseThrow(NoSuchElementException::new));
        assertEquals(17, tuple.get17().orElseThrow(NoSuchElementException::new));
        assertEquals(18, tuple.get18().orElseThrow(NoSuchElementException::new));
        assertEquals(19, tuple.get19().orElseThrow(NoSuchElementException::new));
        assertEquals(20, tuple.get20().orElseThrow(NoSuchElementException::new));
        assertEquals(21, tuple.get21().orElseThrow(NoSuchElementException::new));
        assertEquals(22, tuple.get22().orElseThrow(NoSuchElementException::new));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get(8).orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get(9).orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get(10).orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get(11).orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get(12).orElseThrow(NoSuchElementException::new));
        assertEquals(13, tuple.get(13).orElseThrow(NoSuchElementException::new));
        assertEquals(14, tuple.get(14).orElseThrow(NoSuchElementException::new));
        assertEquals(15, tuple.get(15).orElseThrow(NoSuchElementException::new));
        assertEquals(16, tuple.get(16).orElseThrow(NoSuchElementException::new));
        assertEquals(17, tuple.get(17).orElseThrow(NoSuchElementException::new));
        assertEquals(18, tuple.get(18).orElseThrow(NoSuchElementException::new));
        assertEquals(19, tuple.get(19).orElseThrow(NoSuchElementException::new));
        assertEquals(20, tuple.get(20).orElseThrow(NoSuchElementException::new));
        assertEquals(21, tuple.get(21).orElseThrow(NoSuchElementException::new));
        assertEquals(22, tuple.get(22).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(23));
    }
    
    private void assertTuple(MutableTuple tuple, int degree) {
        assertEquals(degree, tuple.degree());
        for (int i = 0; i < degree; i++){
            assertEquals(i, tuple.get(i).orElseThrow(NoSuchElementException::new));
        }
    }
}