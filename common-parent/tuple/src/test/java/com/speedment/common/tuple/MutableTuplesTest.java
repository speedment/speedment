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

import com.speedment.common.tuple.getter.TupleGetter;
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
import java.util.Optional;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

final class MutableTuplesTest {
    
    @Test
    void create0() {
        final MutableTuple0 tuple = MutableTuples.create0();
        test(tuple);
        final MutableTuple0 defaultTuple = new MutableTuple0() 
        {
            
            
        };
        test(defaultTuple);
    }
    
    @Test
    void constructor0() {
        final Supplier<MutableTuple0> constructor = MutableTuples.constructor();
        test(constructor.get());
    }
    
    void test(final MutableTuple0 tuple) {
        assertTuple(tuple, 0);
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(0));
    }
    
    @Test
    void create1() {
        final MutableTuple1<Integer> tuple = MutableTuples.create1();
        test(tuple);
        final MutableTuple1<Integer> defaultTuple = new MutableTuple1<Integer>() 
        {
            private Integer t0;
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public void set0(Integer val) {
                t0 = val;
            }
        };
        test(defaultTuple);
    }
    
    @Test
    void constructor1() {
        final Supplier<MutableTuple1<Integer>> constructor = MutableTuples.constructor(Integer.class);
        test(constructor.get());
    }
    
    void test(final MutableTuple1<Integer> tuple) {
        tuple.set0(0);
        assertTuple(tuple, 1);
        final TupleGetter<MutableTuple1<Integer>, Optional<Integer>> getter0 = MutableTuple1.getter0();
        final TupleGetter<MutableTuple1<Integer>, Integer> getterOrNull0 = MutableTuple1.getterOrNull0();
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(0, tuple.get0().orElseThrow(NoSuchElementException::new));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(1));
    }
    
    @Test
    void create2() {
        final MutableTuple2<Integer, Integer> tuple = MutableTuples.create2();
        test(tuple);
        final MutableTuple2<Integer, Integer> defaultTuple = new MutableTuple2<Integer, Integer>() 
        {
            private Integer t0;
            private Integer t1;
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public void set0(Integer val) {
                t0 = val;
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public void set1(Integer val) {
                t1 = val;
            }
        };
        test(defaultTuple);
    }
    
    @Test
    void constructor2() {
        final Supplier<MutableTuple2<Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class);
        test(constructor.get());
    }
    
    void test(final MutableTuple2<Integer, Integer> tuple) {
        tuple.set0(0);
        tuple.set1(1);
        assertTuple(tuple, 2);
        final TupleGetter<MutableTuple2<Integer, Integer>, Optional<Integer>> getter0 = MutableTuple2.getter0();
        final TupleGetter<MutableTuple2<Integer, Integer>, Optional<Integer>> getter1 = MutableTuple2.getter1();
        final TupleGetter<MutableTuple2<Integer, Integer>, Integer> getterOrNull0 = MutableTuple2.getterOrNull0();
        final TupleGetter<MutableTuple2<Integer, Integer>, Integer> getterOrNull1 = MutableTuple2.getterOrNull1();
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
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
        test(tuple);
        final MutableTuple3<Integer, Integer, Integer> defaultTuple = new MutableTuple3<Integer, Integer, Integer>() 
        {
            private Integer t0;
            private Integer t1;
            private Integer t2;
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public void set0(Integer val) {
                t0 = val;
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public void set1(Integer val) {
                t1 = val;
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public void set2(Integer val) {
                t2 = val;
            }
        };
        test(defaultTuple);
    }
    
    @Test
    void constructor3() {
        final Supplier<MutableTuple3<Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class);
        test(constructor.get());
    }
    
    void test(final MutableTuple3<Integer, Integer, Integer> tuple) {
        tuple.set0(0);
        tuple.set1(1);
        tuple.set2(2);
        assertTuple(tuple, 3);
        final TupleGetter<MutableTuple3<Integer, Integer, Integer>, Optional<Integer>> getter0 = MutableTuple3.getter0();
        final TupleGetter<MutableTuple3<Integer, Integer, Integer>, Optional<Integer>> getter1 = MutableTuple3.getter1();
        final TupleGetter<MutableTuple3<Integer, Integer, Integer>, Optional<Integer>> getter2 = MutableTuple3.getter2();
        final TupleGetter<MutableTuple3<Integer, Integer, Integer>, Integer> getterOrNull0 = MutableTuple3.getterOrNull0();
        final TupleGetter<MutableTuple3<Integer, Integer, Integer>, Integer> getterOrNull1 = MutableTuple3.getterOrNull1();
        final TupleGetter<MutableTuple3<Integer, Integer, Integer>, Integer> getterOrNull2 = MutableTuple3.getterOrNull2();
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
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
        test(tuple);
        final MutableTuple4<Integer, Integer, Integer, Integer> defaultTuple = new MutableTuple4<Integer, Integer, Integer, Integer>() 
        {
            private Integer t0;
            private Integer t1;
            private Integer t2;
            private Integer t3;
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public void set0(Integer val) {
                t0 = val;
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public void set1(Integer val) {
                t1 = val;
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public void set2(Integer val) {
                t2 = val;
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public void set3(Integer val) {
                t3 = val;
            }
        };
        test(defaultTuple);
    }
    
    @Test
    void constructor4() {
        final Supplier<MutableTuple4<Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class);
        test(constructor.get());
    }
    
    void test(final MutableTuple4<Integer, Integer, Integer, Integer> tuple) {
        tuple.set0(0);
        tuple.set1(1);
        tuple.set2(2);
        tuple.set3(3);
        assertTuple(tuple, 4);
        final TupleGetter<MutableTuple4<Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = MutableTuple4.getter0();
        final TupleGetter<MutableTuple4<Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = MutableTuple4.getter1();
        final TupleGetter<MutableTuple4<Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = MutableTuple4.getter2();
        final TupleGetter<MutableTuple4<Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = MutableTuple4.getter3();
        final TupleGetter<MutableTuple4<Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = MutableTuple4.getterOrNull0();
        final TupleGetter<MutableTuple4<Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = MutableTuple4.getterOrNull1();
        final TupleGetter<MutableTuple4<Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = MutableTuple4.getterOrNull2();
        final TupleGetter<MutableTuple4<Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = MutableTuple4.getterOrNull3();
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
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
        test(tuple);
        final MutableTuple5<Integer, Integer, Integer, Integer, Integer> defaultTuple = new MutableTuple5<Integer, Integer, Integer, Integer, Integer>() 
        {
            private Integer t0;
            private Integer t1;
            private Integer t2;
            private Integer t3;
            private Integer t4;
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public void set0(Integer val) {
                t0 = val;
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public void set1(Integer val) {
                t1 = val;
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public void set2(Integer val) {
                t2 = val;
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public void set3(Integer val) {
                t3 = val;
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public void set4(Integer val) {
                t4 = val;
            }
        };
        test(defaultTuple);
    }
    
    @Test
    void constructor5() {
        final Supplier<MutableTuple5<Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test(constructor.get());
    }
    
    void test(final MutableTuple5<Integer, Integer, Integer, Integer, Integer> tuple) {
        tuple.set0(0);
        tuple.set1(1);
        tuple.set2(2);
        tuple.set3(3);
        tuple.set4(4);
        assertTuple(tuple, 5);
        final TupleGetter<MutableTuple5<Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = MutableTuple5.getter0();
        final TupleGetter<MutableTuple5<Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = MutableTuple5.getter1();
        final TupleGetter<MutableTuple5<Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = MutableTuple5.getter2();
        final TupleGetter<MutableTuple5<Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = MutableTuple5.getter3();
        final TupleGetter<MutableTuple5<Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = MutableTuple5.getter4();
        final TupleGetter<MutableTuple5<Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = MutableTuple5.getterOrNull0();
        final TupleGetter<MutableTuple5<Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = MutableTuple5.getterOrNull1();
        final TupleGetter<MutableTuple5<Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = MutableTuple5.getterOrNull2();
        final TupleGetter<MutableTuple5<Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = MutableTuple5.getterOrNull3();
        final TupleGetter<MutableTuple5<Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = MutableTuple5.getterOrNull4();
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
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
        test(tuple);
        final MutableTuple6<Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new MutableTuple6<Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            private Integer t0;
            private Integer t1;
            private Integer t2;
            private Integer t3;
            private Integer t4;
            private Integer t5;
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public void set0(Integer val) {
                t0 = val;
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public void set1(Integer val) {
                t1 = val;
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public void set2(Integer val) {
                t2 = val;
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public void set3(Integer val) {
                t3 = val;
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public void set4(Integer val) {
                t4 = val;
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public void set5(Integer val) {
                t5 = val;
            }
        };
        test(defaultTuple);
    }
    
    @Test
    void constructor6() {
        final Supplier<MutableTuple6<Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test(constructor.get());
    }
    
    void test(final MutableTuple6<Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        tuple.set0(0);
        tuple.set1(1);
        tuple.set2(2);
        tuple.set3(3);
        tuple.set4(4);
        tuple.set5(5);
        assertTuple(tuple, 6);
        final TupleGetter<MutableTuple6<Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = MutableTuple6.getter0();
        final TupleGetter<MutableTuple6<Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = MutableTuple6.getter1();
        final TupleGetter<MutableTuple6<Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = MutableTuple6.getter2();
        final TupleGetter<MutableTuple6<Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = MutableTuple6.getter3();
        final TupleGetter<MutableTuple6<Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = MutableTuple6.getter4();
        final TupleGetter<MutableTuple6<Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = MutableTuple6.getter5();
        final TupleGetter<MutableTuple6<Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = MutableTuple6.getterOrNull0();
        final TupleGetter<MutableTuple6<Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = MutableTuple6.getterOrNull1();
        final TupleGetter<MutableTuple6<Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = MutableTuple6.getterOrNull2();
        final TupleGetter<MutableTuple6<Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = MutableTuple6.getterOrNull3();
        final TupleGetter<MutableTuple6<Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = MutableTuple6.getterOrNull4();
        final TupleGetter<MutableTuple6<Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = MutableTuple6.getterOrNull5();
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
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
        test(tuple);
        final MutableTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new MutableTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            private Integer t0;
            private Integer t1;
            private Integer t2;
            private Integer t3;
            private Integer t4;
            private Integer t5;
            private Integer t6;
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public void set0(Integer val) {
                t0 = val;
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public void set1(Integer val) {
                t1 = val;
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public void set2(Integer val) {
                t2 = val;
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public void set3(Integer val) {
                t3 = val;
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public void set4(Integer val) {
                t4 = val;
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public void set5(Integer val) {
                t5 = val;
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public void set6(Integer val) {
                t6 = val;
            }
        };
        test(defaultTuple);
    }
    
    @Test
    void constructor7() {
        final Supplier<MutableTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test(constructor.get());
    }
    
    void test(final MutableTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        tuple.set0(0);
        tuple.set1(1);
        tuple.set2(2);
        tuple.set3(3);
        tuple.set4(4);
        tuple.set5(5);
        tuple.set6(6);
        assertTuple(tuple, 7);
        final TupleGetter<MutableTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = MutableTuple7.getter0();
        final TupleGetter<MutableTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = MutableTuple7.getter1();
        final TupleGetter<MutableTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = MutableTuple7.getter2();
        final TupleGetter<MutableTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = MutableTuple7.getter3();
        final TupleGetter<MutableTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = MutableTuple7.getter4();
        final TupleGetter<MutableTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = MutableTuple7.getter5();
        final TupleGetter<MutableTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = MutableTuple7.getter6();
        final TupleGetter<MutableTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = MutableTuple7.getterOrNull0();
        final TupleGetter<MutableTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = MutableTuple7.getterOrNull1();
        final TupleGetter<MutableTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = MutableTuple7.getterOrNull2();
        final TupleGetter<MutableTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = MutableTuple7.getterOrNull3();
        final TupleGetter<MutableTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = MutableTuple7.getterOrNull4();
        final TupleGetter<MutableTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = MutableTuple7.getterOrNull5();
        final TupleGetter<MutableTuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = MutableTuple7.getterOrNull6();
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
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
        test(tuple);
        final MutableTuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new MutableTuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            private Integer t0;
            private Integer t1;
            private Integer t2;
            private Integer t3;
            private Integer t4;
            private Integer t5;
            private Integer t6;
            private Integer t7;
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public void set0(Integer val) {
                t0 = val;
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public void set1(Integer val) {
                t1 = val;
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public void set2(Integer val) {
                t2 = val;
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public void set3(Integer val) {
                t3 = val;
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public void set4(Integer val) {
                t4 = val;
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public void set5(Integer val) {
                t5 = val;
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public void set6(Integer val) {
                t6 = val;
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public void set7(Integer val) {
                t7 = val;
            }
        };
        test(defaultTuple);
    }
    
    @Test
    void constructor8() {
        final Supplier<MutableTuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test(constructor.get());
    }
    
    void test(final MutableTuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        tuple.set0(0);
        tuple.set1(1);
        tuple.set2(2);
        tuple.set3(3);
        tuple.set4(4);
        tuple.set5(5);
        tuple.set6(6);
        tuple.set7(7);
        assertTuple(tuple, 8);
        final TupleGetter<MutableTuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = MutableTuple8.getter0();
        final TupleGetter<MutableTuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = MutableTuple8.getter1();
        final TupleGetter<MutableTuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = MutableTuple8.getter2();
        final TupleGetter<MutableTuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = MutableTuple8.getter3();
        final TupleGetter<MutableTuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = MutableTuple8.getter4();
        final TupleGetter<MutableTuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = MutableTuple8.getter5();
        final TupleGetter<MutableTuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = MutableTuple8.getter6();
        final TupleGetter<MutableTuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = MutableTuple8.getter7();
        final TupleGetter<MutableTuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = MutableTuple8.getterOrNull0();
        final TupleGetter<MutableTuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = MutableTuple8.getterOrNull1();
        final TupleGetter<MutableTuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = MutableTuple8.getterOrNull2();
        final TupleGetter<MutableTuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = MutableTuple8.getterOrNull3();
        final TupleGetter<MutableTuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = MutableTuple8.getterOrNull4();
        final TupleGetter<MutableTuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = MutableTuple8.getterOrNull5();
        final TupleGetter<MutableTuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = MutableTuple8.getterOrNull6();
        final TupleGetter<MutableTuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = MutableTuple8.getterOrNull7();
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
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
        test(tuple);
        final MutableTuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new MutableTuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            private Integer t0;
            private Integer t1;
            private Integer t2;
            private Integer t3;
            private Integer t4;
            private Integer t5;
            private Integer t6;
            private Integer t7;
            private Integer t8;
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public void set0(Integer val) {
                t0 = val;
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public void set1(Integer val) {
                t1 = val;
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public void set2(Integer val) {
                t2 = val;
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public void set3(Integer val) {
                t3 = val;
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public void set4(Integer val) {
                t4 = val;
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public void set5(Integer val) {
                t5 = val;
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public void set6(Integer val) {
                t6 = val;
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public void set7(Integer val) {
                t7 = val;
            }
            @Override
            public Optional<Integer> get8() {
                return Optional.of(8);
            }
            @Override
            public void set8(Integer val) {
                t8 = val;
            }
        };
        test(defaultTuple);
    }
    
    @Test
    void constructor9() {
        final Supplier<MutableTuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test(constructor.get());
    }
    
    void test(final MutableTuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
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
        final TupleGetter<MutableTuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = MutableTuple9.getter0();
        final TupleGetter<MutableTuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = MutableTuple9.getter1();
        final TupleGetter<MutableTuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = MutableTuple9.getter2();
        final TupleGetter<MutableTuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = MutableTuple9.getter3();
        final TupleGetter<MutableTuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = MutableTuple9.getter4();
        final TupleGetter<MutableTuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = MutableTuple9.getter5();
        final TupleGetter<MutableTuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = MutableTuple9.getter6();
        final TupleGetter<MutableTuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = MutableTuple9.getter7();
        final TupleGetter<MutableTuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter8 = MutableTuple9.getter8();
        final TupleGetter<MutableTuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = MutableTuple9.getterOrNull0();
        final TupleGetter<MutableTuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = MutableTuple9.getterOrNull1();
        final TupleGetter<MutableTuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = MutableTuple9.getterOrNull2();
        final TupleGetter<MutableTuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = MutableTuple9.getterOrNull3();
        final TupleGetter<MutableTuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = MutableTuple9.getterOrNull4();
        final TupleGetter<MutableTuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = MutableTuple9.getterOrNull5();
        final TupleGetter<MutableTuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = MutableTuple9.getterOrNull6();
        final TupleGetter<MutableTuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = MutableTuple9.getterOrNull7();
        final TupleGetter<MutableTuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull8 = MutableTuple9.getterOrNull8();
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(8, getter8.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(8, getterOrNull8.apply(tuple));
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
        test(tuple);
        final MutableTuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new MutableTuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            private Integer t0;
            private Integer t1;
            private Integer t2;
            private Integer t3;
            private Integer t4;
            private Integer t5;
            private Integer t6;
            private Integer t7;
            private Integer t8;
            private Integer t9;
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public void set0(Integer val) {
                t0 = val;
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public void set1(Integer val) {
                t1 = val;
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public void set2(Integer val) {
                t2 = val;
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public void set3(Integer val) {
                t3 = val;
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public void set4(Integer val) {
                t4 = val;
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public void set5(Integer val) {
                t5 = val;
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public void set6(Integer val) {
                t6 = val;
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public void set7(Integer val) {
                t7 = val;
            }
            @Override
            public Optional<Integer> get8() {
                return Optional.of(8);
            }
            @Override
            public void set8(Integer val) {
                t8 = val;
            }
            @Override
            public Optional<Integer> get9() {
                return Optional.of(9);
            }
            @Override
            public void set9(Integer val) {
                t9 = val;
            }
        };
        test(defaultTuple);
    }
    
    @Test
    void constructor10() {
        final Supplier<MutableTuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test(constructor.get());
    }
    
    void test(final MutableTuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
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
        final TupleGetter<MutableTuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = MutableTuple10.getter0();
        final TupleGetter<MutableTuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = MutableTuple10.getter1();
        final TupleGetter<MutableTuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = MutableTuple10.getter2();
        final TupleGetter<MutableTuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = MutableTuple10.getter3();
        final TupleGetter<MutableTuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = MutableTuple10.getter4();
        final TupleGetter<MutableTuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = MutableTuple10.getter5();
        final TupleGetter<MutableTuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = MutableTuple10.getter6();
        final TupleGetter<MutableTuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = MutableTuple10.getter7();
        final TupleGetter<MutableTuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter8 = MutableTuple10.getter8();
        final TupleGetter<MutableTuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter9 = MutableTuple10.getter9();
        final TupleGetter<MutableTuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = MutableTuple10.getterOrNull0();
        final TupleGetter<MutableTuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = MutableTuple10.getterOrNull1();
        final TupleGetter<MutableTuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = MutableTuple10.getterOrNull2();
        final TupleGetter<MutableTuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = MutableTuple10.getterOrNull3();
        final TupleGetter<MutableTuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = MutableTuple10.getterOrNull4();
        final TupleGetter<MutableTuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = MutableTuple10.getterOrNull5();
        final TupleGetter<MutableTuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = MutableTuple10.getterOrNull6();
        final TupleGetter<MutableTuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = MutableTuple10.getterOrNull7();
        final TupleGetter<MutableTuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull8 = MutableTuple10.getterOrNull8();
        final TupleGetter<MutableTuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull9 = MutableTuple10.getterOrNull9();
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(8, getter8.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(9, getter9.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(8, getterOrNull8.apply(tuple));
        assertEquals(9, getterOrNull9.apply(tuple));
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
        test(tuple);
        final MutableTuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new MutableTuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            private Integer t0;
            private Integer t1;
            private Integer t2;
            private Integer t3;
            private Integer t4;
            private Integer t5;
            private Integer t6;
            private Integer t7;
            private Integer t8;
            private Integer t9;
            private Integer t10;
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public void set0(Integer val) {
                t0 = val;
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public void set1(Integer val) {
                t1 = val;
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public void set2(Integer val) {
                t2 = val;
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public void set3(Integer val) {
                t3 = val;
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public void set4(Integer val) {
                t4 = val;
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public void set5(Integer val) {
                t5 = val;
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public void set6(Integer val) {
                t6 = val;
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public void set7(Integer val) {
                t7 = val;
            }
            @Override
            public Optional<Integer> get8() {
                return Optional.of(8);
            }
            @Override
            public void set8(Integer val) {
                t8 = val;
            }
            @Override
            public Optional<Integer> get9() {
                return Optional.of(9);
            }
            @Override
            public void set9(Integer val) {
                t9 = val;
            }
            @Override
            public Optional<Integer> get10() {
                return Optional.of(10);
            }
            @Override
            public void set10(Integer val) {
                t10 = val;
            }
        };
        test(defaultTuple);
    }
    
    @Test
    void constructor11() {
        final Supplier<MutableTuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test(constructor.get());
    }
    
    void test(final MutableTuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
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
        final TupleGetter<MutableTuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = MutableTuple11.getter0();
        final TupleGetter<MutableTuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = MutableTuple11.getter1();
        final TupleGetter<MutableTuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = MutableTuple11.getter2();
        final TupleGetter<MutableTuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = MutableTuple11.getter3();
        final TupleGetter<MutableTuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = MutableTuple11.getter4();
        final TupleGetter<MutableTuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = MutableTuple11.getter5();
        final TupleGetter<MutableTuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = MutableTuple11.getter6();
        final TupleGetter<MutableTuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = MutableTuple11.getter7();
        final TupleGetter<MutableTuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter8 = MutableTuple11.getter8();
        final TupleGetter<MutableTuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter9 = MutableTuple11.getter9();
        final TupleGetter<MutableTuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter10 = MutableTuple11.getter10();
        final TupleGetter<MutableTuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = MutableTuple11.getterOrNull0();
        final TupleGetter<MutableTuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = MutableTuple11.getterOrNull1();
        final TupleGetter<MutableTuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = MutableTuple11.getterOrNull2();
        final TupleGetter<MutableTuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = MutableTuple11.getterOrNull3();
        final TupleGetter<MutableTuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = MutableTuple11.getterOrNull4();
        final TupleGetter<MutableTuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = MutableTuple11.getterOrNull5();
        final TupleGetter<MutableTuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = MutableTuple11.getterOrNull6();
        final TupleGetter<MutableTuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = MutableTuple11.getterOrNull7();
        final TupleGetter<MutableTuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull8 = MutableTuple11.getterOrNull8();
        final TupleGetter<MutableTuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull9 = MutableTuple11.getterOrNull9();
        final TupleGetter<MutableTuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull10 = MutableTuple11.getterOrNull10();
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(8, getter8.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(9, getter9.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(10, getter10.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(8, getterOrNull8.apply(tuple));
        assertEquals(9, getterOrNull9.apply(tuple));
        assertEquals(10, getterOrNull10.apply(tuple));
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
        test(tuple);
        final MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            private Integer t0;
            private Integer t1;
            private Integer t2;
            private Integer t3;
            private Integer t4;
            private Integer t5;
            private Integer t6;
            private Integer t7;
            private Integer t8;
            private Integer t9;
            private Integer t10;
            private Integer t11;
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public void set0(Integer val) {
                t0 = val;
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public void set1(Integer val) {
                t1 = val;
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public void set2(Integer val) {
                t2 = val;
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public void set3(Integer val) {
                t3 = val;
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public void set4(Integer val) {
                t4 = val;
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public void set5(Integer val) {
                t5 = val;
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public void set6(Integer val) {
                t6 = val;
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public void set7(Integer val) {
                t7 = val;
            }
            @Override
            public Optional<Integer> get8() {
                return Optional.of(8);
            }
            @Override
            public void set8(Integer val) {
                t8 = val;
            }
            @Override
            public Optional<Integer> get9() {
                return Optional.of(9);
            }
            @Override
            public void set9(Integer val) {
                t9 = val;
            }
            @Override
            public Optional<Integer> get10() {
                return Optional.of(10);
            }
            @Override
            public void set10(Integer val) {
                t10 = val;
            }
            @Override
            public Optional<Integer> get11() {
                return Optional.of(11);
            }
            @Override
            public void set11(Integer val) {
                t11 = val;
            }
        };
        test(defaultTuple);
    }
    
    @Test
    void constructor12() {
        final Supplier<MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test(constructor.get());
    }
    
    void test(final MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
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
        final TupleGetter<MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = MutableTuple12.getter0();
        final TupleGetter<MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = MutableTuple12.getter1();
        final TupleGetter<MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = MutableTuple12.getter2();
        final TupleGetter<MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = MutableTuple12.getter3();
        final TupleGetter<MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = MutableTuple12.getter4();
        final TupleGetter<MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = MutableTuple12.getter5();
        final TupleGetter<MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = MutableTuple12.getter6();
        final TupleGetter<MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = MutableTuple12.getter7();
        final TupleGetter<MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter8 = MutableTuple12.getter8();
        final TupleGetter<MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter9 = MutableTuple12.getter9();
        final TupleGetter<MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter10 = MutableTuple12.getter10();
        final TupleGetter<MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter11 = MutableTuple12.getter11();
        final TupleGetter<MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = MutableTuple12.getterOrNull0();
        final TupleGetter<MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = MutableTuple12.getterOrNull1();
        final TupleGetter<MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = MutableTuple12.getterOrNull2();
        final TupleGetter<MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = MutableTuple12.getterOrNull3();
        final TupleGetter<MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = MutableTuple12.getterOrNull4();
        final TupleGetter<MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = MutableTuple12.getterOrNull5();
        final TupleGetter<MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = MutableTuple12.getterOrNull6();
        final TupleGetter<MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = MutableTuple12.getterOrNull7();
        final TupleGetter<MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull8 = MutableTuple12.getterOrNull8();
        final TupleGetter<MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull9 = MutableTuple12.getterOrNull9();
        final TupleGetter<MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull10 = MutableTuple12.getterOrNull10();
        final TupleGetter<MutableTuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull11 = MutableTuple12.getterOrNull11();
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(8, getter8.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(9, getter9.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(10, getter10.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(11, getter11.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(8, getterOrNull8.apply(tuple));
        assertEquals(9, getterOrNull9.apply(tuple));
        assertEquals(10, getterOrNull10.apply(tuple));
        assertEquals(11, getterOrNull11.apply(tuple));
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
        test(tuple);
        final MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            private Integer t0;
            private Integer t1;
            private Integer t2;
            private Integer t3;
            private Integer t4;
            private Integer t5;
            private Integer t6;
            private Integer t7;
            private Integer t8;
            private Integer t9;
            private Integer t10;
            private Integer t11;
            private Integer t12;
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public void set0(Integer val) {
                t0 = val;
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public void set1(Integer val) {
                t1 = val;
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public void set2(Integer val) {
                t2 = val;
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public void set3(Integer val) {
                t3 = val;
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public void set4(Integer val) {
                t4 = val;
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public void set5(Integer val) {
                t5 = val;
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public void set6(Integer val) {
                t6 = val;
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public void set7(Integer val) {
                t7 = val;
            }
            @Override
            public Optional<Integer> get8() {
                return Optional.of(8);
            }
            @Override
            public void set8(Integer val) {
                t8 = val;
            }
            @Override
            public Optional<Integer> get9() {
                return Optional.of(9);
            }
            @Override
            public void set9(Integer val) {
                t9 = val;
            }
            @Override
            public Optional<Integer> get10() {
                return Optional.of(10);
            }
            @Override
            public void set10(Integer val) {
                t10 = val;
            }
            @Override
            public Optional<Integer> get11() {
                return Optional.of(11);
            }
            @Override
            public void set11(Integer val) {
                t11 = val;
            }
            @Override
            public Optional<Integer> get12() {
                return Optional.of(12);
            }
            @Override
            public void set12(Integer val) {
                t12 = val;
            }
        };
        test(defaultTuple);
    }
    
    @Test
    void constructor13() {
        final Supplier<MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test(constructor.get());
    }
    
    void test(final MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
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
        final TupleGetter<MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = MutableTuple13.getter0();
        final TupleGetter<MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = MutableTuple13.getter1();
        final TupleGetter<MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = MutableTuple13.getter2();
        final TupleGetter<MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = MutableTuple13.getter3();
        final TupleGetter<MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = MutableTuple13.getter4();
        final TupleGetter<MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = MutableTuple13.getter5();
        final TupleGetter<MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = MutableTuple13.getter6();
        final TupleGetter<MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = MutableTuple13.getter7();
        final TupleGetter<MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter8 = MutableTuple13.getter8();
        final TupleGetter<MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter9 = MutableTuple13.getter9();
        final TupleGetter<MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter10 = MutableTuple13.getter10();
        final TupleGetter<MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter11 = MutableTuple13.getter11();
        final TupleGetter<MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter12 = MutableTuple13.getter12();
        final TupleGetter<MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = MutableTuple13.getterOrNull0();
        final TupleGetter<MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = MutableTuple13.getterOrNull1();
        final TupleGetter<MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = MutableTuple13.getterOrNull2();
        final TupleGetter<MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = MutableTuple13.getterOrNull3();
        final TupleGetter<MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = MutableTuple13.getterOrNull4();
        final TupleGetter<MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = MutableTuple13.getterOrNull5();
        final TupleGetter<MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = MutableTuple13.getterOrNull6();
        final TupleGetter<MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = MutableTuple13.getterOrNull7();
        final TupleGetter<MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull8 = MutableTuple13.getterOrNull8();
        final TupleGetter<MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull9 = MutableTuple13.getterOrNull9();
        final TupleGetter<MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull10 = MutableTuple13.getterOrNull10();
        final TupleGetter<MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull11 = MutableTuple13.getterOrNull11();
        final TupleGetter<MutableTuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull12 = MutableTuple13.getterOrNull12();
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(8, getter8.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(9, getter9.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(10, getter10.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(11, getter11.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(12, getter12.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(8, getterOrNull8.apply(tuple));
        assertEquals(9, getterOrNull9.apply(tuple));
        assertEquals(10, getterOrNull10.apply(tuple));
        assertEquals(11, getterOrNull11.apply(tuple));
        assertEquals(12, getterOrNull12.apply(tuple));
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
        test(tuple);
        final MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            private Integer t0;
            private Integer t1;
            private Integer t2;
            private Integer t3;
            private Integer t4;
            private Integer t5;
            private Integer t6;
            private Integer t7;
            private Integer t8;
            private Integer t9;
            private Integer t10;
            private Integer t11;
            private Integer t12;
            private Integer t13;
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public void set0(Integer val) {
                t0 = val;
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public void set1(Integer val) {
                t1 = val;
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public void set2(Integer val) {
                t2 = val;
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public void set3(Integer val) {
                t3 = val;
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public void set4(Integer val) {
                t4 = val;
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public void set5(Integer val) {
                t5 = val;
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public void set6(Integer val) {
                t6 = val;
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public void set7(Integer val) {
                t7 = val;
            }
            @Override
            public Optional<Integer> get8() {
                return Optional.of(8);
            }
            @Override
            public void set8(Integer val) {
                t8 = val;
            }
            @Override
            public Optional<Integer> get9() {
                return Optional.of(9);
            }
            @Override
            public void set9(Integer val) {
                t9 = val;
            }
            @Override
            public Optional<Integer> get10() {
                return Optional.of(10);
            }
            @Override
            public void set10(Integer val) {
                t10 = val;
            }
            @Override
            public Optional<Integer> get11() {
                return Optional.of(11);
            }
            @Override
            public void set11(Integer val) {
                t11 = val;
            }
            @Override
            public Optional<Integer> get12() {
                return Optional.of(12);
            }
            @Override
            public void set12(Integer val) {
                t12 = val;
            }
            @Override
            public Optional<Integer> get13() {
                return Optional.of(13);
            }
            @Override
            public void set13(Integer val) {
                t13 = val;
            }
        };
        test(defaultTuple);
    }
    
    @Test
    void constructor14() {
        final Supplier<MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test(constructor.get());
    }
    
    void test(final MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
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
        final TupleGetter<MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = MutableTuple14.getter0();
        final TupleGetter<MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = MutableTuple14.getter1();
        final TupleGetter<MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = MutableTuple14.getter2();
        final TupleGetter<MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = MutableTuple14.getter3();
        final TupleGetter<MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = MutableTuple14.getter4();
        final TupleGetter<MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = MutableTuple14.getter5();
        final TupleGetter<MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = MutableTuple14.getter6();
        final TupleGetter<MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = MutableTuple14.getter7();
        final TupleGetter<MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter8 = MutableTuple14.getter8();
        final TupleGetter<MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter9 = MutableTuple14.getter9();
        final TupleGetter<MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter10 = MutableTuple14.getter10();
        final TupleGetter<MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter11 = MutableTuple14.getter11();
        final TupleGetter<MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter12 = MutableTuple14.getter12();
        final TupleGetter<MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter13 = MutableTuple14.getter13();
        final TupleGetter<MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = MutableTuple14.getterOrNull0();
        final TupleGetter<MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = MutableTuple14.getterOrNull1();
        final TupleGetter<MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = MutableTuple14.getterOrNull2();
        final TupleGetter<MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = MutableTuple14.getterOrNull3();
        final TupleGetter<MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = MutableTuple14.getterOrNull4();
        final TupleGetter<MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = MutableTuple14.getterOrNull5();
        final TupleGetter<MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = MutableTuple14.getterOrNull6();
        final TupleGetter<MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = MutableTuple14.getterOrNull7();
        final TupleGetter<MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull8 = MutableTuple14.getterOrNull8();
        final TupleGetter<MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull9 = MutableTuple14.getterOrNull9();
        final TupleGetter<MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull10 = MutableTuple14.getterOrNull10();
        final TupleGetter<MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull11 = MutableTuple14.getterOrNull11();
        final TupleGetter<MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull12 = MutableTuple14.getterOrNull12();
        final TupleGetter<MutableTuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull13 = MutableTuple14.getterOrNull13();
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(8, getter8.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(9, getter9.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(10, getter10.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(11, getter11.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(12, getter12.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(13, getter13.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(8, getterOrNull8.apply(tuple));
        assertEquals(9, getterOrNull9.apply(tuple));
        assertEquals(10, getterOrNull10.apply(tuple));
        assertEquals(11, getterOrNull11.apply(tuple));
        assertEquals(12, getterOrNull12.apply(tuple));
        assertEquals(13, getterOrNull13.apply(tuple));
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
        test(tuple);
        final MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            private Integer t0;
            private Integer t1;
            private Integer t2;
            private Integer t3;
            private Integer t4;
            private Integer t5;
            private Integer t6;
            private Integer t7;
            private Integer t8;
            private Integer t9;
            private Integer t10;
            private Integer t11;
            private Integer t12;
            private Integer t13;
            private Integer t14;
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public void set0(Integer val) {
                t0 = val;
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public void set1(Integer val) {
                t1 = val;
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public void set2(Integer val) {
                t2 = val;
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public void set3(Integer val) {
                t3 = val;
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public void set4(Integer val) {
                t4 = val;
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public void set5(Integer val) {
                t5 = val;
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public void set6(Integer val) {
                t6 = val;
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public void set7(Integer val) {
                t7 = val;
            }
            @Override
            public Optional<Integer> get8() {
                return Optional.of(8);
            }
            @Override
            public void set8(Integer val) {
                t8 = val;
            }
            @Override
            public Optional<Integer> get9() {
                return Optional.of(9);
            }
            @Override
            public void set9(Integer val) {
                t9 = val;
            }
            @Override
            public Optional<Integer> get10() {
                return Optional.of(10);
            }
            @Override
            public void set10(Integer val) {
                t10 = val;
            }
            @Override
            public Optional<Integer> get11() {
                return Optional.of(11);
            }
            @Override
            public void set11(Integer val) {
                t11 = val;
            }
            @Override
            public Optional<Integer> get12() {
                return Optional.of(12);
            }
            @Override
            public void set12(Integer val) {
                t12 = val;
            }
            @Override
            public Optional<Integer> get13() {
                return Optional.of(13);
            }
            @Override
            public void set13(Integer val) {
                t13 = val;
            }
            @Override
            public Optional<Integer> get14() {
                return Optional.of(14);
            }
            @Override
            public void set14(Integer val) {
                t14 = val;
            }
        };
        test(defaultTuple);
    }
    
    @Test
    void constructor15() {
        final Supplier<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test(constructor.get());
    }
    
    void test(final MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
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
        final TupleGetter<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = MutableTuple15.getter0();
        final TupleGetter<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = MutableTuple15.getter1();
        final TupleGetter<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = MutableTuple15.getter2();
        final TupleGetter<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = MutableTuple15.getter3();
        final TupleGetter<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = MutableTuple15.getter4();
        final TupleGetter<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = MutableTuple15.getter5();
        final TupleGetter<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = MutableTuple15.getter6();
        final TupleGetter<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = MutableTuple15.getter7();
        final TupleGetter<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter8 = MutableTuple15.getter8();
        final TupleGetter<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter9 = MutableTuple15.getter9();
        final TupleGetter<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter10 = MutableTuple15.getter10();
        final TupleGetter<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter11 = MutableTuple15.getter11();
        final TupleGetter<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter12 = MutableTuple15.getter12();
        final TupleGetter<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter13 = MutableTuple15.getter13();
        final TupleGetter<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter14 = MutableTuple15.getter14();
        final TupleGetter<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = MutableTuple15.getterOrNull0();
        final TupleGetter<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = MutableTuple15.getterOrNull1();
        final TupleGetter<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = MutableTuple15.getterOrNull2();
        final TupleGetter<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = MutableTuple15.getterOrNull3();
        final TupleGetter<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = MutableTuple15.getterOrNull4();
        final TupleGetter<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = MutableTuple15.getterOrNull5();
        final TupleGetter<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = MutableTuple15.getterOrNull6();
        final TupleGetter<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = MutableTuple15.getterOrNull7();
        final TupleGetter<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull8 = MutableTuple15.getterOrNull8();
        final TupleGetter<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull9 = MutableTuple15.getterOrNull9();
        final TupleGetter<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull10 = MutableTuple15.getterOrNull10();
        final TupleGetter<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull11 = MutableTuple15.getterOrNull11();
        final TupleGetter<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull12 = MutableTuple15.getterOrNull12();
        final TupleGetter<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull13 = MutableTuple15.getterOrNull13();
        final TupleGetter<MutableTuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull14 = MutableTuple15.getterOrNull14();
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(8, getter8.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(9, getter9.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(10, getter10.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(11, getter11.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(12, getter12.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(13, getter13.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(14, getter14.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(8, getterOrNull8.apply(tuple));
        assertEquals(9, getterOrNull9.apply(tuple));
        assertEquals(10, getterOrNull10.apply(tuple));
        assertEquals(11, getterOrNull11.apply(tuple));
        assertEquals(12, getterOrNull12.apply(tuple));
        assertEquals(13, getterOrNull13.apply(tuple));
        assertEquals(14, getterOrNull14.apply(tuple));
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
        test(tuple);
        final MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            private Integer t0;
            private Integer t1;
            private Integer t2;
            private Integer t3;
            private Integer t4;
            private Integer t5;
            private Integer t6;
            private Integer t7;
            private Integer t8;
            private Integer t9;
            private Integer t10;
            private Integer t11;
            private Integer t12;
            private Integer t13;
            private Integer t14;
            private Integer t15;
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public void set0(Integer val) {
                t0 = val;
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public void set1(Integer val) {
                t1 = val;
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public void set2(Integer val) {
                t2 = val;
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public void set3(Integer val) {
                t3 = val;
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public void set4(Integer val) {
                t4 = val;
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public void set5(Integer val) {
                t5 = val;
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public void set6(Integer val) {
                t6 = val;
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public void set7(Integer val) {
                t7 = val;
            }
            @Override
            public Optional<Integer> get8() {
                return Optional.of(8);
            }
            @Override
            public void set8(Integer val) {
                t8 = val;
            }
            @Override
            public Optional<Integer> get9() {
                return Optional.of(9);
            }
            @Override
            public void set9(Integer val) {
                t9 = val;
            }
            @Override
            public Optional<Integer> get10() {
                return Optional.of(10);
            }
            @Override
            public void set10(Integer val) {
                t10 = val;
            }
            @Override
            public Optional<Integer> get11() {
                return Optional.of(11);
            }
            @Override
            public void set11(Integer val) {
                t11 = val;
            }
            @Override
            public Optional<Integer> get12() {
                return Optional.of(12);
            }
            @Override
            public void set12(Integer val) {
                t12 = val;
            }
            @Override
            public Optional<Integer> get13() {
                return Optional.of(13);
            }
            @Override
            public void set13(Integer val) {
                t13 = val;
            }
            @Override
            public Optional<Integer> get14() {
                return Optional.of(14);
            }
            @Override
            public void set14(Integer val) {
                t14 = val;
            }
            @Override
            public Optional<Integer> get15() {
                return Optional.of(15);
            }
            @Override
            public void set15(Integer val) {
                t15 = val;
            }
        };
        test(defaultTuple);
    }
    
    @Test
    void constructor16() {
        final Supplier<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test(constructor.get());
    }
    
    void test(final MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
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
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = MutableTuple16.getter0();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = MutableTuple16.getter1();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = MutableTuple16.getter2();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = MutableTuple16.getter3();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = MutableTuple16.getter4();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = MutableTuple16.getter5();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = MutableTuple16.getter6();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = MutableTuple16.getter7();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter8 = MutableTuple16.getter8();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter9 = MutableTuple16.getter9();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter10 = MutableTuple16.getter10();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter11 = MutableTuple16.getter11();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter12 = MutableTuple16.getter12();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter13 = MutableTuple16.getter13();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter14 = MutableTuple16.getter14();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter15 = MutableTuple16.getter15();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = MutableTuple16.getterOrNull0();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = MutableTuple16.getterOrNull1();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = MutableTuple16.getterOrNull2();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = MutableTuple16.getterOrNull3();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = MutableTuple16.getterOrNull4();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = MutableTuple16.getterOrNull5();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = MutableTuple16.getterOrNull6();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = MutableTuple16.getterOrNull7();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull8 = MutableTuple16.getterOrNull8();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull9 = MutableTuple16.getterOrNull9();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull10 = MutableTuple16.getterOrNull10();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull11 = MutableTuple16.getterOrNull11();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull12 = MutableTuple16.getterOrNull12();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull13 = MutableTuple16.getterOrNull13();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull14 = MutableTuple16.getterOrNull14();
        final TupleGetter<MutableTuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull15 = MutableTuple16.getterOrNull15();
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(8, getter8.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(9, getter9.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(10, getter10.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(11, getter11.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(12, getter12.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(13, getter13.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(14, getter14.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(15, getter15.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(8, getterOrNull8.apply(tuple));
        assertEquals(9, getterOrNull9.apply(tuple));
        assertEquals(10, getterOrNull10.apply(tuple));
        assertEquals(11, getterOrNull11.apply(tuple));
        assertEquals(12, getterOrNull12.apply(tuple));
        assertEquals(13, getterOrNull13.apply(tuple));
        assertEquals(14, getterOrNull14.apply(tuple));
        assertEquals(15, getterOrNull15.apply(tuple));
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
        test(tuple);
        final MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            private Integer t0;
            private Integer t1;
            private Integer t2;
            private Integer t3;
            private Integer t4;
            private Integer t5;
            private Integer t6;
            private Integer t7;
            private Integer t8;
            private Integer t9;
            private Integer t10;
            private Integer t11;
            private Integer t12;
            private Integer t13;
            private Integer t14;
            private Integer t15;
            private Integer t16;
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public void set0(Integer val) {
                t0 = val;
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public void set1(Integer val) {
                t1 = val;
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public void set2(Integer val) {
                t2 = val;
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public void set3(Integer val) {
                t3 = val;
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public void set4(Integer val) {
                t4 = val;
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public void set5(Integer val) {
                t5 = val;
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public void set6(Integer val) {
                t6 = val;
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public void set7(Integer val) {
                t7 = val;
            }
            @Override
            public Optional<Integer> get8() {
                return Optional.of(8);
            }
            @Override
            public void set8(Integer val) {
                t8 = val;
            }
            @Override
            public Optional<Integer> get9() {
                return Optional.of(9);
            }
            @Override
            public void set9(Integer val) {
                t9 = val;
            }
            @Override
            public Optional<Integer> get10() {
                return Optional.of(10);
            }
            @Override
            public void set10(Integer val) {
                t10 = val;
            }
            @Override
            public Optional<Integer> get11() {
                return Optional.of(11);
            }
            @Override
            public void set11(Integer val) {
                t11 = val;
            }
            @Override
            public Optional<Integer> get12() {
                return Optional.of(12);
            }
            @Override
            public void set12(Integer val) {
                t12 = val;
            }
            @Override
            public Optional<Integer> get13() {
                return Optional.of(13);
            }
            @Override
            public void set13(Integer val) {
                t13 = val;
            }
            @Override
            public Optional<Integer> get14() {
                return Optional.of(14);
            }
            @Override
            public void set14(Integer val) {
                t14 = val;
            }
            @Override
            public Optional<Integer> get15() {
                return Optional.of(15);
            }
            @Override
            public void set15(Integer val) {
                t15 = val;
            }
            @Override
            public Optional<Integer> get16() {
                return Optional.of(16);
            }
            @Override
            public void set16(Integer val) {
                t16 = val;
            }
        };
        test(defaultTuple);
    }
    
    @Test
    void constructor17() {
        final Supplier<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test(constructor.get());
    }
    
    void test(final MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
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
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = MutableTuple17.getter0();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = MutableTuple17.getter1();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = MutableTuple17.getter2();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = MutableTuple17.getter3();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = MutableTuple17.getter4();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = MutableTuple17.getter5();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = MutableTuple17.getter6();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = MutableTuple17.getter7();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter8 = MutableTuple17.getter8();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter9 = MutableTuple17.getter9();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter10 = MutableTuple17.getter10();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter11 = MutableTuple17.getter11();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter12 = MutableTuple17.getter12();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter13 = MutableTuple17.getter13();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter14 = MutableTuple17.getter14();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter15 = MutableTuple17.getter15();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter16 = MutableTuple17.getter16();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = MutableTuple17.getterOrNull0();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = MutableTuple17.getterOrNull1();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = MutableTuple17.getterOrNull2();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = MutableTuple17.getterOrNull3();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = MutableTuple17.getterOrNull4();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = MutableTuple17.getterOrNull5();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = MutableTuple17.getterOrNull6();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = MutableTuple17.getterOrNull7();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull8 = MutableTuple17.getterOrNull8();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull9 = MutableTuple17.getterOrNull9();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull10 = MutableTuple17.getterOrNull10();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull11 = MutableTuple17.getterOrNull11();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull12 = MutableTuple17.getterOrNull12();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull13 = MutableTuple17.getterOrNull13();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull14 = MutableTuple17.getterOrNull14();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull15 = MutableTuple17.getterOrNull15();
        final TupleGetter<MutableTuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull16 = MutableTuple17.getterOrNull16();
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(8, getter8.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(9, getter9.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(10, getter10.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(11, getter11.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(12, getter12.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(13, getter13.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(14, getter14.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(15, getter15.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(16, getter16.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(8, getterOrNull8.apply(tuple));
        assertEquals(9, getterOrNull9.apply(tuple));
        assertEquals(10, getterOrNull10.apply(tuple));
        assertEquals(11, getterOrNull11.apply(tuple));
        assertEquals(12, getterOrNull12.apply(tuple));
        assertEquals(13, getterOrNull13.apply(tuple));
        assertEquals(14, getterOrNull14.apply(tuple));
        assertEquals(15, getterOrNull15.apply(tuple));
        assertEquals(16, getterOrNull16.apply(tuple));
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
        test(tuple);
        final MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            private Integer t0;
            private Integer t1;
            private Integer t2;
            private Integer t3;
            private Integer t4;
            private Integer t5;
            private Integer t6;
            private Integer t7;
            private Integer t8;
            private Integer t9;
            private Integer t10;
            private Integer t11;
            private Integer t12;
            private Integer t13;
            private Integer t14;
            private Integer t15;
            private Integer t16;
            private Integer t17;
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public void set0(Integer val) {
                t0 = val;
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public void set1(Integer val) {
                t1 = val;
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public void set2(Integer val) {
                t2 = val;
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public void set3(Integer val) {
                t3 = val;
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public void set4(Integer val) {
                t4 = val;
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public void set5(Integer val) {
                t5 = val;
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public void set6(Integer val) {
                t6 = val;
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public void set7(Integer val) {
                t7 = val;
            }
            @Override
            public Optional<Integer> get8() {
                return Optional.of(8);
            }
            @Override
            public void set8(Integer val) {
                t8 = val;
            }
            @Override
            public Optional<Integer> get9() {
                return Optional.of(9);
            }
            @Override
            public void set9(Integer val) {
                t9 = val;
            }
            @Override
            public Optional<Integer> get10() {
                return Optional.of(10);
            }
            @Override
            public void set10(Integer val) {
                t10 = val;
            }
            @Override
            public Optional<Integer> get11() {
                return Optional.of(11);
            }
            @Override
            public void set11(Integer val) {
                t11 = val;
            }
            @Override
            public Optional<Integer> get12() {
                return Optional.of(12);
            }
            @Override
            public void set12(Integer val) {
                t12 = val;
            }
            @Override
            public Optional<Integer> get13() {
                return Optional.of(13);
            }
            @Override
            public void set13(Integer val) {
                t13 = val;
            }
            @Override
            public Optional<Integer> get14() {
                return Optional.of(14);
            }
            @Override
            public void set14(Integer val) {
                t14 = val;
            }
            @Override
            public Optional<Integer> get15() {
                return Optional.of(15);
            }
            @Override
            public void set15(Integer val) {
                t15 = val;
            }
            @Override
            public Optional<Integer> get16() {
                return Optional.of(16);
            }
            @Override
            public void set16(Integer val) {
                t16 = val;
            }
            @Override
            public Optional<Integer> get17() {
                return Optional.of(17);
            }
            @Override
            public void set17(Integer val) {
                t17 = val;
            }
        };
        test(defaultTuple);
    }
    
    @Test
    void constructor18() {
        final Supplier<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test(constructor.get());
    }
    
    void test(final MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
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
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = MutableTuple18.getter0();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = MutableTuple18.getter1();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = MutableTuple18.getter2();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = MutableTuple18.getter3();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = MutableTuple18.getter4();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = MutableTuple18.getter5();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = MutableTuple18.getter6();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = MutableTuple18.getter7();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter8 = MutableTuple18.getter8();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter9 = MutableTuple18.getter9();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter10 = MutableTuple18.getter10();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter11 = MutableTuple18.getter11();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter12 = MutableTuple18.getter12();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter13 = MutableTuple18.getter13();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter14 = MutableTuple18.getter14();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter15 = MutableTuple18.getter15();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter16 = MutableTuple18.getter16();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter17 = MutableTuple18.getter17();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = MutableTuple18.getterOrNull0();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = MutableTuple18.getterOrNull1();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = MutableTuple18.getterOrNull2();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = MutableTuple18.getterOrNull3();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = MutableTuple18.getterOrNull4();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = MutableTuple18.getterOrNull5();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = MutableTuple18.getterOrNull6();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = MutableTuple18.getterOrNull7();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull8 = MutableTuple18.getterOrNull8();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull9 = MutableTuple18.getterOrNull9();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull10 = MutableTuple18.getterOrNull10();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull11 = MutableTuple18.getterOrNull11();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull12 = MutableTuple18.getterOrNull12();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull13 = MutableTuple18.getterOrNull13();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull14 = MutableTuple18.getterOrNull14();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull15 = MutableTuple18.getterOrNull15();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull16 = MutableTuple18.getterOrNull16();
        final TupleGetter<MutableTuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull17 = MutableTuple18.getterOrNull17();
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(8, getter8.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(9, getter9.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(10, getter10.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(11, getter11.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(12, getter12.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(13, getter13.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(14, getter14.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(15, getter15.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(16, getter16.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(17, getter17.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(8, getterOrNull8.apply(tuple));
        assertEquals(9, getterOrNull9.apply(tuple));
        assertEquals(10, getterOrNull10.apply(tuple));
        assertEquals(11, getterOrNull11.apply(tuple));
        assertEquals(12, getterOrNull12.apply(tuple));
        assertEquals(13, getterOrNull13.apply(tuple));
        assertEquals(14, getterOrNull14.apply(tuple));
        assertEquals(15, getterOrNull15.apply(tuple));
        assertEquals(16, getterOrNull16.apply(tuple));
        assertEquals(17, getterOrNull17.apply(tuple));
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
        test(tuple);
        final MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            private Integer t0;
            private Integer t1;
            private Integer t2;
            private Integer t3;
            private Integer t4;
            private Integer t5;
            private Integer t6;
            private Integer t7;
            private Integer t8;
            private Integer t9;
            private Integer t10;
            private Integer t11;
            private Integer t12;
            private Integer t13;
            private Integer t14;
            private Integer t15;
            private Integer t16;
            private Integer t17;
            private Integer t18;
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public void set0(Integer val) {
                t0 = val;
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public void set1(Integer val) {
                t1 = val;
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public void set2(Integer val) {
                t2 = val;
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public void set3(Integer val) {
                t3 = val;
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public void set4(Integer val) {
                t4 = val;
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public void set5(Integer val) {
                t5 = val;
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public void set6(Integer val) {
                t6 = val;
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public void set7(Integer val) {
                t7 = val;
            }
            @Override
            public Optional<Integer> get8() {
                return Optional.of(8);
            }
            @Override
            public void set8(Integer val) {
                t8 = val;
            }
            @Override
            public Optional<Integer> get9() {
                return Optional.of(9);
            }
            @Override
            public void set9(Integer val) {
                t9 = val;
            }
            @Override
            public Optional<Integer> get10() {
                return Optional.of(10);
            }
            @Override
            public void set10(Integer val) {
                t10 = val;
            }
            @Override
            public Optional<Integer> get11() {
                return Optional.of(11);
            }
            @Override
            public void set11(Integer val) {
                t11 = val;
            }
            @Override
            public Optional<Integer> get12() {
                return Optional.of(12);
            }
            @Override
            public void set12(Integer val) {
                t12 = val;
            }
            @Override
            public Optional<Integer> get13() {
                return Optional.of(13);
            }
            @Override
            public void set13(Integer val) {
                t13 = val;
            }
            @Override
            public Optional<Integer> get14() {
                return Optional.of(14);
            }
            @Override
            public void set14(Integer val) {
                t14 = val;
            }
            @Override
            public Optional<Integer> get15() {
                return Optional.of(15);
            }
            @Override
            public void set15(Integer val) {
                t15 = val;
            }
            @Override
            public Optional<Integer> get16() {
                return Optional.of(16);
            }
            @Override
            public void set16(Integer val) {
                t16 = val;
            }
            @Override
            public Optional<Integer> get17() {
                return Optional.of(17);
            }
            @Override
            public void set17(Integer val) {
                t17 = val;
            }
            @Override
            public Optional<Integer> get18() {
                return Optional.of(18);
            }
            @Override
            public void set18(Integer val) {
                t18 = val;
            }
        };
        test(defaultTuple);
    }
    
    @Test
    void constructor19() {
        final Supplier<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test(constructor.get());
    }
    
    void test(final MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
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
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = MutableTuple19.getter0();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = MutableTuple19.getter1();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = MutableTuple19.getter2();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = MutableTuple19.getter3();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = MutableTuple19.getter4();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = MutableTuple19.getter5();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = MutableTuple19.getter6();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = MutableTuple19.getter7();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter8 = MutableTuple19.getter8();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter9 = MutableTuple19.getter9();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter10 = MutableTuple19.getter10();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter11 = MutableTuple19.getter11();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter12 = MutableTuple19.getter12();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter13 = MutableTuple19.getter13();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter14 = MutableTuple19.getter14();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter15 = MutableTuple19.getter15();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter16 = MutableTuple19.getter16();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter17 = MutableTuple19.getter17();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter18 = MutableTuple19.getter18();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = MutableTuple19.getterOrNull0();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = MutableTuple19.getterOrNull1();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = MutableTuple19.getterOrNull2();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = MutableTuple19.getterOrNull3();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = MutableTuple19.getterOrNull4();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = MutableTuple19.getterOrNull5();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = MutableTuple19.getterOrNull6();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = MutableTuple19.getterOrNull7();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull8 = MutableTuple19.getterOrNull8();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull9 = MutableTuple19.getterOrNull9();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull10 = MutableTuple19.getterOrNull10();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull11 = MutableTuple19.getterOrNull11();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull12 = MutableTuple19.getterOrNull12();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull13 = MutableTuple19.getterOrNull13();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull14 = MutableTuple19.getterOrNull14();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull15 = MutableTuple19.getterOrNull15();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull16 = MutableTuple19.getterOrNull16();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull17 = MutableTuple19.getterOrNull17();
        final TupleGetter<MutableTuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull18 = MutableTuple19.getterOrNull18();
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(8, getter8.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(9, getter9.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(10, getter10.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(11, getter11.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(12, getter12.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(13, getter13.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(14, getter14.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(15, getter15.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(16, getter16.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(17, getter17.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(18, getter18.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(8, getterOrNull8.apply(tuple));
        assertEquals(9, getterOrNull9.apply(tuple));
        assertEquals(10, getterOrNull10.apply(tuple));
        assertEquals(11, getterOrNull11.apply(tuple));
        assertEquals(12, getterOrNull12.apply(tuple));
        assertEquals(13, getterOrNull13.apply(tuple));
        assertEquals(14, getterOrNull14.apply(tuple));
        assertEquals(15, getterOrNull15.apply(tuple));
        assertEquals(16, getterOrNull16.apply(tuple));
        assertEquals(17, getterOrNull17.apply(tuple));
        assertEquals(18, getterOrNull18.apply(tuple));
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
        test(tuple);
        final MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            private Integer t0;
            private Integer t1;
            private Integer t2;
            private Integer t3;
            private Integer t4;
            private Integer t5;
            private Integer t6;
            private Integer t7;
            private Integer t8;
            private Integer t9;
            private Integer t10;
            private Integer t11;
            private Integer t12;
            private Integer t13;
            private Integer t14;
            private Integer t15;
            private Integer t16;
            private Integer t17;
            private Integer t18;
            private Integer t19;
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public void set0(Integer val) {
                t0 = val;
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public void set1(Integer val) {
                t1 = val;
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public void set2(Integer val) {
                t2 = val;
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public void set3(Integer val) {
                t3 = val;
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public void set4(Integer val) {
                t4 = val;
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public void set5(Integer val) {
                t5 = val;
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public void set6(Integer val) {
                t6 = val;
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public void set7(Integer val) {
                t7 = val;
            }
            @Override
            public Optional<Integer> get8() {
                return Optional.of(8);
            }
            @Override
            public void set8(Integer val) {
                t8 = val;
            }
            @Override
            public Optional<Integer> get9() {
                return Optional.of(9);
            }
            @Override
            public void set9(Integer val) {
                t9 = val;
            }
            @Override
            public Optional<Integer> get10() {
                return Optional.of(10);
            }
            @Override
            public void set10(Integer val) {
                t10 = val;
            }
            @Override
            public Optional<Integer> get11() {
                return Optional.of(11);
            }
            @Override
            public void set11(Integer val) {
                t11 = val;
            }
            @Override
            public Optional<Integer> get12() {
                return Optional.of(12);
            }
            @Override
            public void set12(Integer val) {
                t12 = val;
            }
            @Override
            public Optional<Integer> get13() {
                return Optional.of(13);
            }
            @Override
            public void set13(Integer val) {
                t13 = val;
            }
            @Override
            public Optional<Integer> get14() {
                return Optional.of(14);
            }
            @Override
            public void set14(Integer val) {
                t14 = val;
            }
            @Override
            public Optional<Integer> get15() {
                return Optional.of(15);
            }
            @Override
            public void set15(Integer val) {
                t15 = val;
            }
            @Override
            public Optional<Integer> get16() {
                return Optional.of(16);
            }
            @Override
            public void set16(Integer val) {
                t16 = val;
            }
            @Override
            public Optional<Integer> get17() {
                return Optional.of(17);
            }
            @Override
            public void set17(Integer val) {
                t17 = val;
            }
            @Override
            public Optional<Integer> get18() {
                return Optional.of(18);
            }
            @Override
            public void set18(Integer val) {
                t18 = val;
            }
            @Override
            public Optional<Integer> get19() {
                return Optional.of(19);
            }
            @Override
            public void set19(Integer val) {
                t19 = val;
            }
        };
        test(defaultTuple);
    }
    
    @Test
    void constructor20() {
        final Supplier<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test(constructor.get());
    }
    
    void test(final MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
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
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = MutableTuple20.getter0();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = MutableTuple20.getter1();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = MutableTuple20.getter2();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = MutableTuple20.getter3();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = MutableTuple20.getter4();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = MutableTuple20.getter5();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = MutableTuple20.getter6();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = MutableTuple20.getter7();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter8 = MutableTuple20.getter8();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter9 = MutableTuple20.getter9();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter10 = MutableTuple20.getter10();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter11 = MutableTuple20.getter11();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter12 = MutableTuple20.getter12();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter13 = MutableTuple20.getter13();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter14 = MutableTuple20.getter14();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter15 = MutableTuple20.getter15();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter16 = MutableTuple20.getter16();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter17 = MutableTuple20.getter17();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter18 = MutableTuple20.getter18();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter19 = MutableTuple20.getter19();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = MutableTuple20.getterOrNull0();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = MutableTuple20.getterOrNull1();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = MutableTuple20.getterOrNull2();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = MutableTuple20.getterOrNull3();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = MutableTuple20.getterOrNull4();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = MutableTuple20.getterOrNull5();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = MutableTuple20.getterOrNull6();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = MutableTuple20.getterOrNull7();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull8 = MutableTuple20.getterOrNull8();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull9 = MutableTuple20.getterOrNull9();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull10 = MutableTuple20.getterOrNull10();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull11 = MutableTuple20.getterOrNull11();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull12 = MutableTuple20.getterOrNull12();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull13 = MutableTuple20.getterOrNull13();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull14 = MutableTuple20.getterOrNull14();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull15 = MutableTuple20.getterOrNull15();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull16 = MutableTuple20.getterOrNull16();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull17 = MutableTuple20.getterOrNull17();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull18 = MutableTuple20.getterOrNull18();
        final TupleGetter<MutableTuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull19 = MutableTuple20.getterOrNull19();
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(8, getter8.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(9, getter9.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(10, getter10.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(11, getter11.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(12, getter12.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(13, getter13.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(14, getter14.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(15, getter15.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(16, getter16.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(17, getter17.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(18, getter18.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(19, getter19.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(8, getterOrNull8.apply(tuple));
        assertEquals(9, getterOrNull9.apply(tuple));
        assertEquals(10, getterOrNull10.apply(tuple));
        assertEquals(11, getterOrNull11.apply(tuple));
        assertEquals(12, getterOrNull12.apply(tuple));
        assertEquals(13, getterOrNull13.apply(tuple));
        assertEquals(14, getterOrNull14.apply(tuple));
        assertEquals(15, getterOrNull15.apply(tuple));
        assertEquals(16, getterOrNull16.apply(tuple));
        assertEquals(17, getterOrNull17.apply(tuple));
        assertEquals(18, getterOrNull18.apply(tuple));
        assertEquals(19, getterOrNull19.apply(tuple));
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
        test(tuple);
        final MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            private Integer t0;
            private Integer t1;
            private Integer t2;
            private Integer t3;
            private Integer t4;
            private Integer t5;
            private Integer t6;
            private Integer t7;
            private Integer t8;
            private Integer t9;
            private Integer t10;
            private Integer t11;
            private Integer t12;
            private Integer t13;
            private Integer t14;
            private Integer t15;
            private Integer t16;
            private Integer t17;
            private Integer t18;
            private Integer t19;
            private Integer t20;
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public void set0(Integer val) {
                t0 = val;
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public void set1(Integer val) {
                t1 = val;
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public void set2(Integer val) {
                t2 = val;
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public void set3(Integer val) {
                t3 = val;
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public void set4(Integer val) {
                t4 = val;
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public void set5(Integer val) {
                t5 = val;
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public void set6(Integer val) {
                t6 = val;
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public void set7(Integer val) {
                t7 = val;
            }
            @Override
            public Optional<Integer> get8() {
                return Optional.of(8);
            }
            @Override
            public void set8(Integer val) {
                t8 = val;
            }
            @Override
            public Optional<Integer> get9() {
                return Optional.of(9);
            }
            @Override
            public void set9(Integer val) {
                t9 = val;
            }
            @Override
            public Optional<Integer> get10() {
                return Optional.of(10);
            }
            @Override
            public void set10(Integer val) {
                t10 = val;
            }
            @Override
            public Optional<Integer> get11() {
                return Optional.of(11);
            }
            @Override
            public void set11(Integer val) {
                t11 = val;
            }
            @Override
            public Optional<Integer> get12() {
                return Optional.of(12);
            }
            @Override
            public void set12(Integer val) {
                t12 = val;
            }
            @Override
            public Optional<Integer> get13() {
                return Optional.of(13);
            }
            @Override
            public void set13(Integer val) {
                t13 = val;
            }
            @Override
            public Optional<Integer> get14() {
                return Optional.of(14);
            }
            @Override
            public void set14(Integer val) {
                t14 = val;
            }
            @Override
            public Optional<Integer> get15() {
                return Optional.of(15);
            }
            @Override
            public void set15(Integer val) {
                t15 = val;
            }
            @Override
            public Optional<Integer> get16() {
                return Optional.of(16);
            }
            @Override
            public void set16(Integer val) {
                t16 = val;
            }
            @Override
            public Optional<Integer> get17() {
                return Optional.of(17);
            }
            @Override
            public void set17(Integer val) {
                t17 = val;
            }
            @Override
            public Optional<Integer> get18() {
                return Optional.of(18);
            }
            @Override
            public void set18(Integer val) {
                t18 = val;
            }
            @Override
            public Optional<Integer> get19() {
                return Optional.of(19);
            }
            @Override
            public void set19(Integer val) {
                t19 = val;
            }
            @Override
            public Optional<Integer> get20() {
                return Optional.of(20);
            }
            @Override
            public void set20(Integer val) {
                t20 = val;
            }
        };
        test(defaultTuple);
    }
    
    @Test
    void constructor21() {
        final Supplier<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test(constructor.get());
    }
    
    void test(final MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
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
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = MutableTuple21.getter0();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = MutableTuple21.getter1();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = MutableTuple21.getter2();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = MutableTuple21.getter3();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = MutableTuple21.getter4();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = MutableTuple21.getter5();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = MutableTuple21.getter6();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = MutableTuple21.getter7();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter8 = MutableTuple21.getter8();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter9 = MutableTuple21.getter9();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter10 = MutableTuple21.getter10();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter11 = MutableTuple21.getter11();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter12 = MutableTuple21.getter12();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter13 = MutableTuple21.getter13();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter14 = MutableTuple21.getter14();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter15 = MutableTuple21.getter15();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter16 = MutableTuple21.getter16();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter17 = MutableTuple21.getter17();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter18 = MutableTuple21.getter18();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter19 = MutableTuple21.getter19();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter20 = MutableTuple21.getter20();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = MutableTuple21.getterOrNull0();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = MutableTuple21.getterOrNull1();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = MutableTuple21.getterOrNull2();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = MutableTuple21.getterOrNull3();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = MutableTuple21.getterOrNull4();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = MutableTuple21.getterOrNull5();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = MutableTuple21.getterOrNull6();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = MutableTuple21.getterOrNull7();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull8 = MutableTuple21.getterOrNull8();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull9 = MutableTuple21.getterOrNull9();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull10 = MutableTuple21.getterOrNull10();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull11 = MutableTuple21.getterOrNull11();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull12 = MutableTuple21.getterOrNull12();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull13 = MutableTuple21.getterOrNull13();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull14 = MutableTuple21.getterOrNull14();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull15 = MutableTuple21.getterOrNull15();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull16 = MutableTuple21.getterOrNull16();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull17 = MutableTuple21.getterOrNull17();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull18 = MutableTuple21.getterOrNull18();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull19 = MutableTuple21.getterOrNull19();
        final TupleGetter<MutableTuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull20 = MutableTuple21.getterOrNull20();
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(8, getter8.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(9, getter9.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(10, getter10.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(11, getter11.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(12, getter12.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(13, getter13.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(14, getter14.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(15, getter15.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(16, getter16.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(17, getter17.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(18, getter18.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(19, getter19.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(20, getter20.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(8, getterOrNull8.apply(tuple));
        assertEquals(9, getterOrNull9.apply(tuple));
        assertEquals(10, getterOrNull10.apply(tuple));
        assertEquals(11, getterOrNull11.apply(tuple));
        assertEquals(12, getterOrNull12.apply(tuple));
        assertEquals(13, getterOrNull13.apply(tuple));
        assertEquals(14, getterOrNull14.apply(tuple));
        assertEquals(15, getterOrNull15.apply(tuple));
        assertEquals(16, getterOrNull16.apply(tuple));
        assertEquals(17, getterOrNull17.apply(tuple));
        assertEquals(18, getterOrNull18.apply(tuple));
        assertEquals(19, getterOrNull19.apply(tuple));
        assertEquals(20, getterOrNull20.apply(tuple));
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
        test(tuple);
        final MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            private Integer t0;
            private Integer t1;
            private Integer t2;
            private Integer t3;
            private Integer t4;
            private Integer t5;
            private Integer t6;
            private Integer t7;
            private Integer t8;
            private Integer t9;
            private Integer t10;
            private Integer t11;
            private Integer t12;
            private Integer t13;
            private Integer t14;
            private Integer t15;
            private Integer t16;
            private Integer t17;
            private Integer t18;
            private Integer t19;
            private Integer t20;
            private Integer t21;
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public void set0(Integer val) {
                t0 = val;
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public void set1(Integer val) {
                t1 = val;
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public void set2(Integer val) {
                t2 = val;
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public void set3(Integer val) {
                t3 = val;
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public void set4(Integer val) {
                t4 = val;
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public void set5(Integer val) {
                t5 = val;
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public void set6(Integer val) {
                t6 = val;
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public void set7(Integer val) {
                t7 = val;
            }
            @Override
            public Optional<Integer> get8() {
                return Optional.of(8);
            }
            @Override
            public void set8(Integer val) {
                t8 = val;
            }
            @Override
            public Optional<Integer> get9() {
                return Optional.of(9);
            }
            @Override
            public void set9(Integer val) {
                t9 = val;
            }
            @Override
            public Optional<Integer> get10() {
                return Optional.of(10);
            }
            @Override
            public void set10(Integer val) {
                t10 = val;
            }
            @Override
            public Optional<Integer> get11() {
                return Optional.of(11);
            }
            @Override
            public void set11(Integer val) {
                t11 = val;
            }
            @Override
            public Optional<Integer> get12() {
                return Optional.of(12);
            }
            @Override
            public void set12(Integer val) {
                t12 = val;
            }
            @Override
            public Optional<Integer> get13() {
                return Optional.of(13);
            }
            @Override
            public void set13(Integer val) {
                t13 = val;
            }
            @Override
            public Optional<Integer> get14() {
                return Optional.of(14);
            }
            @Override
            public void set14(Integer val) {
                t14 = val;
            }
            @Override
            public Optional<Integer> get15() {
                return Optional.of(15);
            }
            @Override
            public void set15(Integer val) {
                t15 = val;
            }
            @Override
            public Optional<Integer> get16() {
                return Optional.of(16);
            }
            @Override
            public void set16(Integer val) {
                t16 = val;
            }
            @Override
            public Optional<Integer> get17() {
                return Optional.of(17);
            }
            @Override
            public void set17(Integer val) {
                t17 = val;
            }
            @Override
            public Optional<Integer> get18() {
                return Optional.of(18);
            }
            @Override
            public void set18(Integer val) {
                t18 = val;
            }
            @Override
            public Optional<Integer> get19() {
                return Optional.of(19);
            }
            @Override
            public void set19(Integer val) {
                t19 = val;
            }
            @Override
            public Optional<Integer> get20() {
                return Optional.of(20);
            }
            @Override
            public void set20(Integer val) {
                t20 = val;
            }
            @Override
            public Optional<Integer> get21() {
                return Optional.of(21);
            }
            @Override
            public void set21(Integer val) {
                t21 = val;
            }
        };
        test(defaultTuple);
    }
    
    @Test
    void constructor22() {
        final Supplier<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test(constructor.get());
    }
    
    void test(final MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
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
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = MutableTuple22.getter0();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = MutableTuple22.getter1();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = MutableTuple22.getter2();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = MutableTuple22.getter3();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = MutableTuple22.getter4();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = MutableTuple22.getter5();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = MutableTuple22.getter6();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = MutableTuple22.getter7();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter8 = MutableTuple22.getter8();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter9 = MutableTuple22.getter9();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter10 = MutableTuple22.getter10();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter11 = MutableTuple22.getter11();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter12 = MutableTuple22.getter12();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter13 = MutableTuple22.getter13();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter14 = MutableTuple22.getter14();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter15 = MutableTuple22.getter15();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter16 = MutableTuple22.getter16();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter17 = MutableTuple22.getter17();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter18 = MutableTuple22.getter18();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter19 = MutableTuple22.getter19();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter20 = MutableTuple22.getter20();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter21 = MutableTuple22.getter21();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = MutableTuple22.getterOrNull0();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = MutableTuple22.getterOrNull1();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = MutableTuple22.getterOrNull2();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = MutableTuple22.getterOrNull3();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = MutableTuple22.getterOrNull4();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = MutableTuple22.getterOrNull5();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = MutableTuple22.getterOrNull6();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = MutableTuple22.getterOrNull7();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull8 = MutableTuple22.getterOrNull8();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull9 = MutableTuple22.getterOrNull9();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull10 = MutableTuple22.getterOrNull10();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull11 = MutableTuple22.getterOrNull11();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull12 = MutableTuple22.getterOrNull12();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull13 = MutableTuple22.getterOrNull13();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull14 = MutableTuple22.getterOrNull14();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull15 = MutableTuple22.getterOrNull15();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull16 = MutableTuple22.getterOrNull16();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull17 = MutableTuple22.getterOrNull17();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull18 = MutableTuple22.getterOrNull18();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull19 = MutableTuple22.getterOrNull19();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull20 = MutableTuple22.getterOrNull20();
        final TupleGetter<MutableTuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull21 = MutableTuple22.getterOrNull21();
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(8, getter8.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(9, getter9.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(10, getter10.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(11, getter11.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(12, getter12.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(13, getter13.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(14, getter14.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(15, getter15.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(16, getter16.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(17, getter17.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(18, getter18.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(19, getter19.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(20, getter20.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(21, getter21.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(8, getterOrNull8.apply(tuple));
        assertEquals(9, getterOrNull9.apply(tuple));
        assertEquals(10, getterOrNull10.apply(tuple));
        assertEquals(11, getterOrNull11.apply(tuple));
        assertEquals(12, getterOrNull12.apply(tuple));
        assertEquals(13, getterOrNull13.apply(tuple));
        assertEquals(14, getterOrNull14.apply(tuple));
        assertEquals(15, getterOrNull15.apply(tuple));
        assertEquals(16, getterOrNull16.apply(tuple));
        assertEquals(17, getterOrNull17.apply(tuple));
        assertEquals(18, getterOrNull18.apply(tuple));
        assertEquals(19, getterOrNull19.apply(tuple));
        assertEquals(20, getterOrNull20.apply(tuple));
        assertEquals(21, getterOrNull21.apply(tuple));
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
        test(tuple);
        final MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            private Integer t0;
            private Integer t1;
            private Integer t2;
            private Integer t3;
            private Integer t4;
            private Integer t5;
            private Integer t6;
            private Integer t7;
            private Integer t8;
            private Integer t9;
            private Integer t10;
            private Integer t11;
            private Integer t12;
            private Integer t13;
            private Integer t14;
            private Integer t15;
            private Integer t16;
            private Integer t17;
            private Integer t18;
            private Integer t19;
            private Integer t20;
            private Integer t21;
            private Integer t22;
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public void set0(Integer val) {
                t0 = val;
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public void set1(Integer val) {
                t1 = val;
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public void set2(Integer val) {
                t2 = val;
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public void set3(Integer val) {
                t3 = val;
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public void set4(Integer val) {
                t4 = val;
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public void set5(Integer val) {
                t5 = val;
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public void set6(Integer val) {
                t6 = val;
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public void set7(Integer val) {
                t7 = val;
            }
            @Override
            public Optional<Integer> get8() {
                return Optional.of(8);
            }
            @Override
            public void set8(Integer val) {
                t8 = val;
            }
            @Override
            public Optional<Integer> get9() {
                return Optional.of(9);
            }
            @Override
            public void set9(Integer val) {
                t9 = val;
            }
            @Override
            public Optional<Integer> get10() {
                return Optional.of(10);
            }
            @Override
            public void set10(Integer val) {
                t10 = val;
            }
            @Override
            public Optional<Integer> get11() {
                return Optional.of(11);
            }
            @Override
            public void set11(Integer val) {
                t11 = val;
            }
            @Override
            public Optional<Integer> get12() {
                return Optional.of(12);
            }
            @Override
            public void set12(Integer val) {
                t12 = val;
            }
            @Override
            public Optional<Integer> get13() {
                return Optional.of(13);
            }
            @Override
            public void set13(Integer val) {
                t13 = val;
            }
            @Override
            public Optional<Integer> get14() {
                return Optional.of(14);
            }
            @Override
            public void set14(Integer val) {
                t14 = val;
            }
            @Override
            public Optional<Integer> get15() {
                return Optional.of(15);
            }
            @Override
            public void set15(Integer val) {
                t15 = val;
            }
            @Override
            public Optional<Integer> get16() {
                return Optional.of(16);
            }
            @Override
            public void set16(Integer val) {
                t16 = val;
            }
            @Override
            public Optional<Integer> get17() {
                return Optional.of(17);
            }
            @Override
            public void set17(Integer val) {
                t17 = val;
            }
            @Override
            public Optional<Integer> get18() {
                return Optional.of(18);
            }
            @Override
            public void set18(Integer val) {
                t18 = val;
            }
            @Override
            public Optional<Integer> get19() {
                return Optional.of(19);
            }
            @Override
            public void set19(Integer val) {
                t19 = val;
            }
            @Override
            public Optional<Integer> get20() {
                return Optional.of(20);
            }
            @Override
            public void set20(Integer val) {
                t20 = val;
            }
            @Override
            public Optional<Integer> get21() {
                return Optional.of(21);
            }
            @Override
            public void set21(Integer val) {
                t21 = val;
            }
            @Override
            public Optional<Integer> get22() {
                return Optional.of(22);
            }
            @Override
            public void set22(Integer val) {
                t22 = val;
            }
        };
        test(defaultTuple);
    }
    
    @Test
    void constructor23() {
        final Supplier<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> constructor = MutableTuples.constructor(Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class);
        test(constructor.get());
    }
    
    void test(final MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
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
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = MutableTuple23.getter0();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = MutableTuple23.getter1();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = MutableTuple23.getter2();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = MutableTuple23.getter3();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = MutableTuple23.getter4();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = MutableTuple23.getter5();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = MutableTuple23.getter6();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = MutableTuple23.getter7();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter8 = MutableTuple23.getter8();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter9 = MutableTuple23.getter9();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter10 = MutableTuple23.getter10();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter11 = MutableTuple23.getter11();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter12 = MutableTuple23.getter12();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter13 = MutableTuple23.getter13();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter14 = MutableTuple23.getter14();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter15 = MutableTuple23.getter15();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter16 = MutableTuple23.getter16();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter17 = MutableTuple23.getter17();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter18 = MutableTuple23.getter18();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter19 = MutableTuple23.getter19();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter20 = MutableTuple23.getter20();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter21 = MutableTuple23.getter21();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter22 = MutableTuple23.getter22();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = MutableTuple23.getterOrNull0();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = MutableTuple23.getterOrNull1();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = MutableTuple23.getterOrNull2();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = MutableTuple23.getterOrNull3();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = MutableTuple23.getterOrNull4();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = MutableTuple23.getterOrNull5();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = MutableTuple23.getterOrNull6();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = MutableTuple23.getterOrNull7();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull8 = MutableTuple23.getterOrNull8();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull9 = MutableTuple23.getterOrNull9();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull10 = MutableTuple23.getterOrNull10();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull11 = MutableTuple23.getterOrNull11();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull12 = MutableTuple23.getterOrNull12();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull13 = MutableTuple23.getterOrNull13();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull14 = MutableTuple23.getterOrNull14();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull15 = MutableTuple23.getterOrNull15();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull16 = MutableTuple23.getterOrNull16();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull17 = MutableTuple23.getterOrNull17();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull18 = MutableTuple23.getterOrNull18();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull19 = MutableTuple23.getterOrNull19();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull20 = MutableTuple23.getterOrNull20();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull21 = MutableTuple23.getterOrNull21();
        final TupleGetter<MutableTuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull22 = MutableTuple23.getterOrNull22();
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(8, getter8.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(9, getter9.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(10, getter10.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(11, getter11.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(12, getter12.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(13, getter13.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(14, getter14.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(15, getter15.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(16, getter16.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(17, getter17.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(18, getter18.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(19, getter19.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(20, getter20.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(21, getter21.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(22, getter22.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(8, getterOrNull8.apply(tuple));
        assertEquals(9, getterOrNull9.apply(tuple));
        assertEquals(10, getterOrNull10.apply(tuple));
        assertEquals(11, getterOrNull11.apply(tuple));
        assertEquals(12, getterOrNull12.apply(tuple));
        assertEquals(13, getterOrNull13.apply(tuple));
        assertEquals(14, getterOrNull14.apply(tuple));
        assertEquals(15, getterOrNull15.apply(tuple));
        assertEquals(16, getterOrNull16.apply(tuple));
        assertEquals(17, getterOrNull17.apply(tuple));
        assertEquals(18, getterOrNull18.apply(tuple));
        assertEquals(19, getterOrNull19.apply(tuple));
        assertEquals(20, getterOrNull20.apply(tuple));
        assertEquals(21, getterOrNull21.apply(tuple));
        assertEquals(22, getterOrNull22.apply(tuple));
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