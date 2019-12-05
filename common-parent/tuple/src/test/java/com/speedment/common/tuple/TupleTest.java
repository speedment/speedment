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

import com.speedment.common.tuple.getter.TupleGetter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

final class TupleTest {
    
    @Test
    void tuple1() {
        final Tuple1<Integer> tuple = Tuples.of(0);
        TupleGetter<Tuple1<Integer>, Integer> getter0 = Tuple1.getter0();
        assertEquals(0, getter0.index());
        assertEquals(0, getter0.apply(tuple));
    }
    
    @Test
    void tuple2() {
        final Tuple2<Integer, Integer> tuple = Tuples.of(0, 1);
        TupleGetter<Tuple2<Integer, Integer>, Integer> getter0 = Tuple2.getter0();
        TupleGetter<Tuple2<Integer, Integer>, Integer> getter1 = Tuple2.getter1();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
    }
    
    @Test
    void tuple3() {
        final Tuple3<Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2);
        TupleGetter<Tuple3<Integer, Integer, Integer>, Integer> getter0 = Tuple3.getter0();
        TupleGetter<Tuple3<Integer, Integer, Integer>, Integer> getter1 = Tuple3.getter1();
        TupleGetter<Tuple3<Integer, Integer, Integer>, Integer> getter2 = Tuple3.getter2();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
    }
    
    @Test
    void tuple4() {
        final Tuple4<Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3);
        TupleGetter<Tuple4<Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple4.getter0();
        TupleGetter<Tuple4<Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple4.getter1();
        TupleGetter<Tuple4<Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple4.getter2();
        TupleGetter<Tuple4<Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple4.getter3();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
    }
    
    @Test
    void tuple5() {
        final Tuple5<Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4);
        TupleGetter<Tuple5<Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple5.getter0();
        TupleGetter<Tuple5<Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple5.getter1();
        TupleGetter<Tuple5<Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple5.getter2();
        TupleGetter<Tuple5<Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple5.getter3();
        TupleGetter<Tuple5<Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple5.getter4();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
    }
    
    @Test
    void tuple6() {
        final Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5);
        TupleGetter<Tuple6<Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple6.getter0();
        TupleGetter<Tuple6<Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple6.getter1();
        TupleGetter<Tuple6<Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple6.getter2();
        TupleGetter<Tuple6<Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple6.getter3();
        TupleGetter<Tuple6<Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple6.getter4();
        TupleGetter<Tuple6<Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple6.getter5();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
    }
    
    @Test
    void tuple7() {
        final Tuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6);
        TupleGetter<Tuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple7.getter0();
        TupleGetter<Tuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple7.getter1();
        TupleGetter<Tuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple7.getter2();
        TupleGetter<Tuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple7.getter3();
        TupleGetter<Tuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple7.getter4();
        TupleGetter<Tuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple7.getter5();
        TupleGetter<Tuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple7.getter6();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
    }
    
    @Test
    void tuple8() {
        final Tuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7);
        TupleGetter<Tuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple8.getter0();
        TupleGetter<Tuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple8.getter1();
        TupleGetter<Tuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple8.getter2();
        TupleGetter<Tuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple8.getter3();
        TupleGetter<Tuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple8.getter4();
        TupleGetter<Tuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple8.getter5();
        TupleGetter<Tuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple8.getter6();
        TupleGetter<Tuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple8.getter7();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
    }
    
    @Test
    void tuple9() {
        final Tuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8);
        TupleGetter<Tuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple9.getter0();
        TupleGetter<Tuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple9.getter1();
        TupleGetter<Tuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple9.getter2();
        TupleGetter<Tuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple9.getter3();
        TupleGetter<Tuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple9.getter4();
        TupleGetter<Tuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple9.getter5();
        TupleGetter<Tuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple9.getter6();
        TupleGetter<Tuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple9.getter7();
        TupleGetter<Tuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter8 = Tuple9.getter8();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(8, getter8.apply(tuple));
    }
    
    @Test
    void tuple10() {
        final Tuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        TupleGetter<Tuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple10.getter0();
        TupleGetter<Tuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple10.getter1();
        TupleGetter<Tuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple10.getter2();
        TupleGetter<Tuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple10.getter3();
        TupleGetter<Tuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple10.getter4();
        TupleGetter<Tuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple10.getter5();
        TupleGetter<Tuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple10.getter6();
        TupleGetter<Tuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple10.getter7();
        TupleGetter<Tuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter8 = Tuple10.getter8();
        TupleGetter<Tuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter9 = Tuple10.getter9();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(9, getter9.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(8, getter8.apply(tuple));
        assertEquals(9, getter9.apply(tuple));
    }
    
    @Test
    void tuple11() {
        final Tuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        TupleGetter<Tuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple11.getter0();
        TupleGetter<Tuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple11.getter1();
        TupleGetter<Tuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple11.getter2();
        TupleGetter<Tuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple11.getter3();
        TupleGetter<Tuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple11.getter4();
        TupleGetter<Tuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple11.getter5();
        TupleGetter<Tuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple11.getter6();
        TupleGetter<Tuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple11.getter7();
        TupleGetter<Tuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter8 = Tuple11.getter8();
        TupleGetter<Tuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter9 = Tuple11.getter9();
        TupleGetter<Tuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter10 = Tuple11.getter10();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(9, getter9.index());
        assertEquals(10, getter10.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(8, getter8.apply(tuple));
        assertEquals(9, getter9.apply(tuple));
        assertEquals(10, getter10.apply(tuple));
    }
    
    @Test
    void tuple12() {
        final Tuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
        TupleGetter<Tuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple12.getter0();
        TupleGetter<Tuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple12.getter1();
        TupleGetter<Tuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple12.getter2();
        TupleGetter<Tuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple12.getter3();
        TupleGetter<Tuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple12.getter4();
        TupleGetter<Tuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple12.getter5();
        TupleGetter<Tuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple12.getter6();
        TupleGetter<Tuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple12.getter7();
        TupleGetter<Tuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter8 = Tuple12.getter8();
        TupleGetter<Tuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter9 = Tuple12.getter9();
        TupleGetter<Tuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter10 = Tuple12.getter10();
        TupleGetter<Tuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter11 = Tuple12.getter11();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(9, getter9.index());
        assertEquals(10, getter10.index());
        assertEquals(11, getter11.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(8, getter8.apply(tuple));
        assertEquals(9, getter9.apply(tuple));
        assertEquals(10, getter10.apply(tuple));
        assertEquals(11, getter11.apply(tuple));
    }
    
    @Test
    void tuple13() {
        final Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        TupleGetter<Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple13.getter0();
        TupleGetter<Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple13.getter1();
        TupleGetter<Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple13.getter2();
        TupleGetter<Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple13.getter3();
        TupleGetter<Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple13.getter4();
        TupleGetter<Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple13.getter5();
        TupleGetter<Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple13.getter6();
        TupleGetter<Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple13.getter7();
        TupleGetter<Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter8 = Tuple13.getter8();
        TupleGetter<Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter9 = Tuple13.getter9();
        TupleGetter<Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter10 = Tuple13.getter10();
        TupleGetter<Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter11 = Tuple13.getter11();
        TupleGetter<Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter12 = Tuple13.getter12();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(9, getter9.index());
        assertEquals(10, getter10.index());
        assertEquals(11, getter11.index());
        assertEquals(12, getter12.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(8, getter8.apply(tuple));
        assertEquals(9, getter9.apply(tuple));
        assertEquals(10, getter10.apply(tuple));
        assertEquals(11, getter11.apply(tuple));
        assertEquals(12, getter12.apply(tuple));
    }
    
    @Test
    void tuple14() {
        final Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
        TupleGetter<Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple14.getter0();
        TupleGetter<Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple14.getter1();
        TupleGetter<Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple14.getter2();
        TupleGetter<Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple14.getter3();
        TupleGetter<Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple14.getter4();
        TupleGetter<Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple14.getter5();
        TupleGetter<Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple14.getter6();
        TupleGetter<Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple14.getter7();
        TupleGetter<Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter8 = Tuple14.getter8();
        TupleGetter<Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter9 = Tuple14.getter9();
        TupleGetter<Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter10 = Tuple14.getter10();
        TupleGetter<Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter11 = Tuple14.getter11();
        TupleGetter<Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter12 = Tuple14.getter12();
        TupleGetter<Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter13 = Tuple14.getter13();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(9, getter9.index());
        assertEquals(10, getter10.index());
        assertEquals(11, getter11.index());
        assertEquals(12, getter12.index());
        assertEquals(13, getter13.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(8, getter8.apply(tuple));
        assertEquals(9, getter9.apply(tuple));
        assertEquals(10, getter10.apply(tuple));
        assertEquals(11, getter11.apply(tuple));
        assertEquals(12, getter12.apply(tuple));
        assertEquals(13, getter13.apply(tuple));
    }
    
    @Test
    void tuple15() {
        final Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
        TupleGetter<Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple15.getter0();
        TupleGetter<Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple15.getter1();
        TupleGetter<Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple15.getter2();
        TupleGetter<Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple15.getter3();
        TupleGetter<Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple15.getter4();
        TupleGetter<Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple15.getter5();
        TupleGetter<Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple15.getter6();
        TupleGetter<Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple15.getter7();
        TupleGetter<Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter8 = Tuple15.getter8();
        TupleGetter<Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter9 = Tuple15.getter9();
        TupleGetter<Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter10 = Tuple15.getter10();
        TupleGetter<Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter11 = Tuple15.getter11();
        TupleGetter<Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter12 = Tuple15.getter12();
        TupleGetter<Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter13 = Tuple15.getter13();
        TupleGetter<Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter14 = Tuple15.getter14();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(9, getter9.index());
        assertEquals(10, getter10.index());
        assertEquals(11, getter11.index());
        assertEquals(12, getter12.index());
        assertEquals(13, getter13.index());
        assertEquals(14, getter14.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(8, getter8.apply(tuple));
        assertEquals(9, getter9.apply(tuple));
        assertEquals(10, getter10.apply(tuple));
        assertEquals(11, getter11.apply(tuple));
        assertEquals(12, getter12.apply(tuple));
        assertEquals(13, getter13.apply(tuple));
        assertEquals(14, getter14.apply(tuple));
    }
    
    @Test
    void tuple16() {
        final Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
        TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple16.getter0();
        TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple16.getter1();
        TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple16.getter2();
        TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple16.getter3();
        TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple16.getter4();
        TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple16.getter5();
        TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple16.getter6();
        TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple16.getter7();
        TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter8 = Tuple16.getter8();
        TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter9 = Tuple16.getter9();
        TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter10 = Tuple16.getter10();
        TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter11 = Tuple16.getter11();
        TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter12 = Tuple16.getter12();
        TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter13 = Tuple16.getter13();
        TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter14 = Tuple16.getter14();
        TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter15 = Tuple16.getter15();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(9, getter9.index());
        assertEquals(10, getter10.index());
        assertEquals(11, getter11.index());
        assertEquals(12, getter12.index());
        assertEquals(13, getter13.index());
        assertEquals(14, getter14.index());
        assertEquals(15, getter15.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(8, getter8.apply(tuple));
        assertEquals(9, getter9.apply(tuple));
        assertEquals(10, getter10.apply(tuple));
        assertEquals(11, getter11.apply(tuple));
        assertEquals(12, getter12.apply(tuple));
        assertEquals(13, getter13.apply(tuple));
        assertEquals(14, getter14.apply(tuple));
        assertEquals(15, getter15.apply(tuple));
    }
    
    @Test
    void tuple17() {
        final Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple17.getter0();
        TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple17.getter1();
        TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple17.getter2();
        TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple17.getter3();
        TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple17.getter4();
        TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple17.getter5();
        TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple17.getter6();
        TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple17.getter7();
        TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter8 = Tuple17.getter8();
        TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter9 = Tuple17.getter9();
        TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter10 = Tuple17.getter10();
        TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter11 = Tuple17.getter11();
        TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter12 = Tuple17.getter12();
        TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter13 = Tuple17.getter13();
        TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter14 = Tuple17.getter14();
        TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter15 = Tuple17.getter15();
        TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter16 = Tuple17.getter16();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(9, getter9.index());
        assertEquals(10, getter10.index());
        assertEquals(11, getter11.index());
        assertEquals(12, getter12.index());
        assertEquals(13, getter13.index());
        assertEquals(14, getter14.index());
        assertEquals(15, getter15.index());
        assertEquals(16, getter16.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(8, getter8.apply(tuple));
        assertEquals(9, getter9.apply(tuple));
        assertEquals(10, getter10.apply(tuple));
        assertEquals(11, getter11.apply(tuple));
        assertEquals(12, getter12.apply(tuple));
        assertEquals(13, getter13.apply(tuple));
        assertEquals(14, getter14.apply(tuple));
        assertEquals(15, getter15.apply(tuple));
        assertEquals(16, getter16.apply(tuple));
    }
    
    @Test
    void tuple18() {
        final Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17);
        TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple18.getter0();
        TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple18.getter1();
        TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple18.getter2();
        TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple18.getter3();
        TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple18.getter4();
        TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple18.getter5();
        TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple18.getter6();
        TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple18.getter7();
        TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter8 = Tuple18.getter8();
        TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter9 = Tuple18.getter9();
        TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter10 = Tuple18.getter10();
        TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter11 = Tuple18.getter11();
        TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter12 = Tuple18.getter12();
        TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter13 = Tuple18.getter13();
        TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter14 = Tuple18.getter14();
        TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter15 = Tuple18.getter15();
        TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter16 = Tuple18.getter16();
        TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter17 = Tuple18.getter17();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(9, getter9.index());
        assertEquals(10, getter10.index());
        assertEquals(11, getter11.index());
        assertEquals(12, getter12.index());
        assertEquals(13, getter13.index());
        assertEquals(14, getter14.index());
        assertEquals(15, getter15.index());
        assertEquals(16, getter16.index());
        assertEquals(17, getter17.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(8, getter8.apply(tuple));
        assertEquals(9, getter9.apply(tuple));
        assertEquals(10, getter10.apply(tuple));
        assertEquals(11, getter11.apply(tuple));
        assertEquals(12, getter12.apply(tuple));
        assertEquals(13, getter13.apply(tuple));
        assertEquals(14, getter14.apply(tuple));
        assertEquals(15, getter15.apply(tuple));
        assertEquals(16, getter16.apply(tuple));
        assertEquals(17, getter17.apply(tuple));
    }
    
    @Test
    void tuple19() {
        final Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18);
        TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple19.getter0();
        TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple19.getter1();
        TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple19.getter2();
        TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple19.getter3();
        TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple19.getter4();
        TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple19.getter5();
        TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple19.getter6();
        TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple19.getter7();
        TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter8 = Tuple19.getter8();
        TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter9 = Tuple19.getter9();
        TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter10 = Tuple19.getter10();
        TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter11 = Tuple19.getter11();
        TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter12 = Tuple19.getter12();
        TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter13 = Tuple19.getter13();
        TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter14 = Tuple19.getter14();
        TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter15 = Tuple19.getter15();
        TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter16 = Tuple19.getter16();
        TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter17 = Tuple19.getter17();
        TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter18 = Tuple19.getter18();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(9, getter9.index());
        assertEquals(10, getter10.index());
        assertEquals(11, getter11.index());
        assertEquals(12, getter12.index());
        assertEquals(13, getter13.index());
        assertEquals(14, getter14.index());
        assertEquals(15, getter15.index());
        assertEquals(16, getter16.index());
        assertEquals(17, getter17.index());
        assertEquals(18, getter18.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(8, getter8.apply(tuple));
        assertEquals(9, getter9.apply(tuple));
        assertEquals(10, getter10.apply(tuple));
        assertEquals(11, getter11.apply(tuple));
        assertEquals(12, getter12.apply(tuple));
        assertEquals(13, getter13.apply(tuple));
        assertEquals(14, getter14.apply(tuple));
        assertEquals(15, getter15.apply(tuple));
        assertEquals(16, getter16.apply(tuple));
        assertEquals(17, getter17.apply(tuple));
        assertEquals(18, getter18.apply(tuple));
    }
    
    @Test
    void tuple20() {
        final Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19);
        TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple20.getter0();
        TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple20.getter1();
        TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple20.getter2();
        TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple20.getter3();
        TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple20.getter4();
        TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple20.getter5();
        TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple20.getter6();
        TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple20.getter7();
        TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter8 = Tuple20.getter8();
        TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter9 = Tuple20.getter9();
        TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter10 = Tuple20.getter10();
        TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter11 = Tuple20.getter11();
        TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter12 = Tuple20.getter12();
        TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter13 = Tuple20.getter13();
        TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter14 = Tuple20.getter14();
        TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter15 = Tuple20.getter15();
        TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter16 = Tuple20.getter16();
        TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter17 = Tuple20.getter17();
        TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter18 = Tuple20.getter18();
        TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter19 = Tuple20.getter19();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(9, getter9.index());
        assertEquals(10, getter10.index());
        assertEquals(11, getter11.index());
        assertEquals(12, getter12.index());
        assertEquals(13, getter13.index());
        assertEquals(14, getter14.index());
        assertEquals(15, getter15.index());
        assertEquals(16, getter16.index());
        assertEquals(17, getter17.index());
        assertEquals(18, getter18.index());
        assertEquals(19, getter19.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(8, getter8.apply(tuple));
        assertEquals(9, getter9.apply(tuple));
        assertEquals(10, getter10.apply(tuple));
        assertEquals(11, getter11.apply(tuple));
        assertEquals(12, getter12.apply(tuple));
        assertEquals(13, getter13.apply(tuple));
        assertEquals(14, getter14.apply(tuple));
        assertEquals(15, getter15.apply(tuple));
        assertEquals(16, getter16.apply(tuple));
        assertEquals(17, getter17.apply(tuple));
        assertEquals(18, getter18.apply(tuple));
        assertEquals(19, getter19.apply(tuple));
    }
    
    @Test
    void tuple21() {
        final Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple21.getter0();
        TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple21.getter1();
        TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple21.getter2();
        TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple21.getter3();
        TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple21.getter4();
        TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple21.getter5();
        TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple21.getter6();
        TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple21.getter7();
        TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter8 = Tuple21.getter8();
        TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter9 = Tuple21.getter9();
        TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter10 = Tuple21.getter10();
        TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter11 = Tuple21.getter11();
        TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter12 = Tuple21.getter12();
        TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter13 = Tuple21.getter13();
        TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter14 = Tuple21.getter14();
        TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter15 = Tuple21.getter15();
        TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter16 = Tuple21.getter16();
        TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter17 = Tuple21.getter17();
        TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter18 = Tuple21.getter18();
        TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter19 = Tuple21.getter19();
        TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter20 = Tuple21.getter20();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(9, getter9.index());
        assertEquals(10, getter10.index());
        assertEquals(11, getter11.index());
        assertEquals(12, getter12.index());
        assertEquals(13, getter13.index());
        assertEquals(14, getter14.index());
        assertEquals(15, getter15.index());
        assertEquals(16, getter16.index());
        assertEquals(17, getter17.index());
        assertEquals(18, getter18.index());
        assertEquals(19, getter19.index());
        assertEquals(20, getter20.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(8, getter8.apply(tuple));
        assertEquals(9, getter9.apply(tuple));
        assertEquals(10, getter10.apply(tuple));
        assertEquals(11, getter11.apply(tuple));
        assertEquals(12, getter12.apply(tuple));
        assertEquals(13, getter13.apply(tuple));
        assertEquals(14, getter14.apply(tuple));
        assertEquals(15, getter15.apply(tuple));
        assertEquals(16, getter16.apply(tuple));
        assertEquals(17, getter17.apply(tuple));
        assertEquals(18, getter18.apply(tuple));
        assertEquals(19, getter19.apply(tuple));
        assertEquals(20, getter20.apply(tuple));
    }
    
    @Test
    void tuple22() {
        final Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21);
        TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple22.getter0();
        TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple22.getter1();
        TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple22.getter2();
        TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple22.getter3();
        TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple22.getter4();
        TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple22.getter5();
        TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple22.getter6();
        TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple22.getter7();
        TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter8 = Tuple22.getter8();
        TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter9 = Tuple22.getter9();
        TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter10 = Tuple22.getter10();
        TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter11 = Tuple22.getter11();
        TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter12 = Tuple22.getter12();
        TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter13 = Tuple22.getter13();
        TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter14 = Tuple22.getter14();
        TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter15 = Tuple22.getter15();
        TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter16 = Tuple22.getter16();
        TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter17 = Tuple22.getter17();
        TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter18 = Tuple22.getter18();
        TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter19 = Tuple22.getter19();
        TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter20 = Tuple22.getter20();
        TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter21 = Tuple22.getter21();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(9, getter9.index());
        assertEquals(10, getter10.index());
        assertEquals(11, getter11.index());
        assertEquals(12, getter12.index());
        assertEquals(13, getter13.index());
        assertEquals(14, getter14.index());
        assertEquals(15, getter15.index());
        assertEquals(16, getter16.index());
        assertEquals(17, getter17.index());
        assertEquals(18, getter18.index());
        assertEquals(19, getter19.index());
        assertEquals(20, getter20.index());
        assertEquals(21, getter21.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(8, getter8.apply(tuple));
        assertEquals(9, getter9.apply(tuple));
        assertEquals(10, getter10.apply(tuple));
        assertEquals(11, getter11.apply(tuple));
        assertEquals(12, getter12.apply(tuple));
        assertEquals(13, getter13.apply(tuple));
        assertEquals(14, getter14.apply(tuple));
        assertEquals(15, getter15.apply(tuple));
        assertEquals(16, getter16.apply(tuple));
        assertEquals(17, getter17.apply(tuple));
        assertEquals(18, getter18.apply(tuple));
        assertEquals(19, getter19.apply(tuple));
        assertEquals(20, getter20.apply(tuple));
        assertEquals(21, getter21.apply(tuple));
    }
    
    @Test
    void tuple23() {
        final Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22);
        TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple23.getter0();
        TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple23.getter1();
        TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple23.getter2();
        TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple23.getter3();
        TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple23.getter4();
        TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple23.getter5();
        TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple23.getter6();
        TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple23.getter7();
        TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter8 = Tuple23.getter8();
        TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter9 = Tuple23.getter9();
        TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter10 = Tuple23.getter10();
        TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter11 = Tuple23.getter11();
        TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter12 = Tuple23.getter12();
        TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter13 = Tuple23.getter13();
        TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter14 = Tuple23.getter14();
        TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter15 = Tuple23.getter15();
        TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter16 = Tuple23.getter16();
        TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter17 = Tuple23.getter17();
        TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter18 = Tuple23.getter18();
        TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter19 = Tuple23.getter19();
        TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter20 = Tuple23.getter20();
        TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter21 = Tuple23.getter21();
        TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter22 = Tuple23.getter22();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(9, getter9.index());
        assertEquals(10, getter10.index());
        assertEquals(11, getter11.index());
        assertEquals(12, getter12.index());
        assertEquals(13, getter13.index());
        assertEquals(14, getter14.index());
        assertEquals(15, getter15.index());
        assertEquals(16, getter16.index());
        assertEquals(17, getter17.index());
        assertEquals(18, getter18.index());
        assertEquals(19, getter19.index());
        assertEquals(20, getter20.index());
        assertEquals(21, getter21.index());
        assertEquals(22, getter22.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(8, getter8.apply(tuple));
        assertEquals(9, getter9.apply(tuple));
        assertEquals(10, getter10.apply(tuple));
        assertEquals(11, getter11.apply(tuple));
        assertEquals(12, getter12.apply(tuple));
        assertEquals(13, getter13.apply(tuple));
        assertEquals(14, getter14.apply(tuple));
        assertEquals(15, getter15.apply(tuple));
        assertEquals(16, getter16.apply(tuple));
        assertEquals(17, getter17.apply(tuple));
        assertEquals(18, getter18.apply(tuple));
        assertEquals(19, getter19.apply(tuple));
        assertEquals(20, getter20.apply(tuple));
        assertEquals(21, getter21.apply(tuple));
        assertEquals(22, getter22.apply(tuple));
    }
}