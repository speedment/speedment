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

import com.speedment.common.tuple.nullable.Tuple0OfNullables;
import com.speedment.common.tuple.nullable.Tuple10OfNullables;
import com.speedment.common.tuple.nullable.Tuple11OfNullables;
import com.speedment.common.tuple.nullable.Tuple12OfNullables;
import com.speedment.common.tuple.nullable.Tuple13OfNullables;
import com.speedment.common.tuple.nullable.Tuple14OfNullables;
import com.speedment.common.tuple.nullable.Tuple15OfNullables;
import com.speedment.common.tuple.nullable.Tuple16OfNullables;
import com.speedment.common.tuple.nullable.Tuple17OfNullables;
import com.speedment.common.tuple.nullable.Tuple18OfNullables;
import com.speedment.common.tuple.nullable.Tuple19OfNullables;
import com.speedment.common.tuple.nullable.Tuple1OfNullables;
import com.speedment.common.tuple.nullable.Tuple20OfNullables;
import com.speedment.common.tuple.nullable.Tuple21OfNullables;
import com.speedment.common.tuple.nullable.Tuple22OfNullables;
import com.speedment.common.tuple.nullable.Tuple23OfNullables;
import com.speedment.common.tuple.nullable.Tuple2OfNullables;
import com.speedment.common.tuple.nullable.Tuple3OfNullables;
import com.speedment.common.tuple.nullable.Tuple4OfNullables;
import com.speedment.common.tuple.nullable.Tuple5OfNullables;
import com.speedment.common.tuple.nullable.Tuple6OfNullables;
import com.speedment.common.tuple.nullable.Tuple7OfNullables;
import com.speedment.common.tuple.nullable.Tuple8OfNullables;
import com.speedment.common.tuple.nullable.Tuple9OfNullables;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

final class TuplesOfNullablesTest {
    
    @Test
    void ofNullables0() {
        assertTuple(TuplesOfNullables.ofNullables(), 0);
    }
    
    @Test
    void toTuple0OfNullables() {
        final Function<Integer, Tuple0OfNullables> mapper = TuplesOfNullables.toTupleOfNullables();
        assertTuple(mapper.apply(0), 0);
    }
    
    @Test
    void ofNullables1() {
        assertTuple(TuplesOfNullables.ofNullables(0), 1);
    }
    
    @Test
    void toTuple1OfNullables() {
        final Function<Integer, Tuple1OfNullables<Integer>> mapper = TuplesOfNullables.toTupleOfNullables(i -> i + 0);
        assertTuple(mapper.apply(0), 1);
    }
    
    @Test
    void ofNullables2() {
        assertTuple(TuplesOfNullables.ofNullables(0, 1), 2);
    }
    
    @Test
    void toTuple2OfNullables() {
        final Function<Integer, Tuple2OfNullables<Integer, Integer>> mapper = TuplesOfNullables.toTupleOfNullables(i -> i + 0, i -> i + 1);
        assertTuple(mapper.apply(0), 2);
    }
    
    @Test
    void ofNullables3() {
        assertTuple(TuplesOfNullables.ofNullables(0, 1, 2), 3);
    }
    
    @Test
    void toTuple3OfNullables() {
        final Function<Integer, Tuple3OfNullables<Integer, Integer, Integer>> mapper = TuplesOfNullables.toTupleOfNullables(i -> i + 0, i -> i + 1, i -> i + 2);
        assertTuple(mapper.apply(0), 3);
    }
    
    @Test
    void ofNullables4() {
        assertTuple(TuplesOfNullables.ofNullables(0, 1, 2, 3), 4);
    }
    
    @Test
    void toTuple4OfNullables() {
        final Function<Integer, Tuple4OfNullables<Integer, Integer, Integer, Integer>> mapper = TuplesOfNullables.toTupleOfNullables(i -> i + 0, i -> i + 1, i -> i + 2, i -> i + 3);
        assertTuple(mapper.apply(0), 4);
    }
    
    @Test
    void ofNullables5() {
        assertTuple(TuplesOfNullables.ofNullables(0, 1, 2, 3, 4), 5);
    }
    
    @Test
    void toTuple5OfNullables() {
        final Function<Integer, Tuple5OfNullables<Integer, Integer, Integer, Integer, Integer>> mapper = TuplesOfNullables.toTupleOfNullables(i -> i + 0, i -> i + 1, i -> i + 2, i -> i + 3, i -> i + 4);
        assertTuple(mapper.apply(0), 5);
    }
    
    @Test
    void ofNullables6() {
        assertTuple(TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5), 6);
    }
    
    @Test
    void toTuple6OfNullables() {
        final Function<Integer, Tuple6OfNullables<Integer, Integer, Integer, Integer, Integer, Integer>> mapper = TuplesOfNullables.toTupleOfNullables(i -> i + 0, i -> i + 1, i -> i + 2, i -> i + 3, i -> i + 4, i -> i + 5);
        assertTuple(mapper.apply(0), 6);
    }
    
    @Test
    void ofNullables7() {
        assertTuple(TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6), 7);
    }
    
    @Test
    void toTuple7OfNullables() {
        final Function<Integer, Tuple7OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> mapper = TuplesOfNullables.toTupleOfNullables(i -> i + 0, i -> i + 1, i -> i + 2, i -> i + 3, i -> i + 4, i -> i + 5, i -> i + 6);
        assertTuple(mapper.apply(0), 7);
    }
    
    @Test
    void ofNullables8() {
        assertTuple(TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7), 8);
    }
    
    @Test
    void toTuple8OfNullables() {
        final Function<Integer, Tuple8OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> mapper = TuplesOfNullables.toTupleOfNullables(i -> i + 0, i -> i + 1, i -> i + 2, i -> i + 3, i -> i + 4, i -> i + 5, i -> i + 6, i -> i + 7);
        assertTuple(mapper.apply(0), 8);
    }
    
    @Test
    void ofNullables9() {
        assertTuple(TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7, 8), 9);
    }
    
    @Test
    void toTuple9OfNullables() {
        final Function<Integer, Tuple9OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> mapper = TuplesOfNullables.toTupleOfNullables(i -> i + 0, i -> i + 1, i -> i + 2, i -> i + 3, i -> i + 4, i -> i + 5, i -> i + 6, i -> i + 7, i -> i + 8);
        assertTuple(mapper.apply(0), 9);
    }
    
    @Test
    void ofNullables10() {
        assertTuple(TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), 10);
    }
    
    @Test
    void toTuple10OfNullables() {
        final Function<Integer, Tuple10OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> mapper = TuplesOfNullables.toTupleOfNullables(i -> i + 0, i -> i + 1, i -> i + 2, i -> i + 3, i -> i + 4, i -> i + 5, i -> i + 6, i -> i + 7, i -> i + 8, i -> i + 9);
        assertTuple(mapper.apply(0), 10);
    }
    
    @Test
    void ofNullables11() {
        assertTuple(TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 11);
    }
    
    @Test
    void toTuple11OfNullables() {
        final Function<Integer, Tuple11OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> mapper = TuplesOfNullables.toTupleOfNullables(i -> i + 0, i -> i + 1, i -> i + 2, i -> i + 3, i -> i + 4, i -> i + 5, i -> i + 6, i -> i + 7, i -> i + 8, i -> i + 9, i -> i + 10);
        assertTuple(mapper.apply(0), 11);
    }
    
    @Test
    void ofNullables12() {
        assertTuple(TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), 12);
    }
    
    @Test
    void toTuple12OfNullables() {
        final Function<Integer, Tuple12OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> mapper = TuplesOfNullables.toTupleOfNullables(i -> i + 0, i -> i + 1, i -> i + 2, i -> i + 3, i -> i + 4, i -> i + 5, i -> i + 6, i -> i + 7, i -> i + 8, i -> i + 9, i -> i + 10, i -> i + 11);
        assertTuple(mapper.apply(0), 12);
    }
    
    @Test
    void ofNullables13() {
        assertTuple(TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), 13);
    }
    
    @Test
    void toTuple13OfNullables() {
        final Function<Integer, Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> mapper = TuplesOfNullables.toTupleOfNullables(i -> i + 0, i -> i + 1, i -> i + 2, i -> i + 3, i -> i + 4, i -> i + 5, i -> i + 6, i -> i + 7, i -> i + 8, i -> i + 9, i -> i + 10, i -> i + 11, i -> i + 12);
        assertTuple(mapper.apply(0), 13);
    }
    
    @Test
    void ofNullables14() {
        assertTuple(TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13), 14);
    }
    
    @Test
    void toTuple14OfNullables() {
        final Function<Integer, Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> mapper = TuplesOfNullables.toTupleOfNullables(i -> i + 0, i -> i + 1, i -> i + 2, i -> i + 3, i -> i + 4, i -> i + 5, i -> i + 6, i -> i + 7, i -> i + 8, i -> i + 9, i -> i + 10, i -> i + 11, i -> i + 12, i -> i + 13);
        assertTuple(mapper.apply(0), 14);
    }
    
    @Test
    void ofNullables15() {
        assertTuple(TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14), 15);
    }
    
    @Test
    void toTuple15OfNullables() {
        final Function<Integer, Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> mapper = TuplesOfNullables.toTupleOfNullables(i -> i + 0, i -> i + 1, i -> i + 2, i -> i + 3, i -> i + 4, i -> i + 5, i -> i + 6, i -> i + 7, i -> i + 8, i -> i + 9, i -> i + 10, i -> i + 11, i -> i + 12, i -> i + 13, i -> i + 14);
        assertTuple(mapper.apply(0), 15);
    }
    
    @Test
    void ofNullables16() {
        assertTuple(TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15), 16);
    }
    
    @Test
    void toTuple16OfNullables() {
        final Function<Integer, Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> mapper = TuplesOfNullables.toTupleOfNullables(i -> i + 0, i -> i + 1, i -> i + 2, i -> i + 3, i -> i + 4, i -> i + 5, i -> i + 6, i -> i + 7, i -> i + 8, i -> i + 9, i -> i + 10, i -> i + 11, i -> i + 12, i -> i + 13, i -> i + 14, i -> i + 15);
        assertTuple(mapper.apply(0), 16);
    }
    
    @Test
    void ofNullables17() {
        assertTuple(TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16), 17);
    }
    
    @Test
    void toTuple17OfNullables() {
        final Function<Integer, Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> mapper = TuplesOfNullables.toTupleOfNullables(i -> i + 0, i -> i + 1, i -> i + 2, i -> i + 3, i -> i + 4, i -> i + 5, i -> i + 6, i -> i + 7, i -> i + 8, i -> i + 9, i -> i + 10, i -> i + 11, i -> i + 12, i -> i + 13, i -> i + 14, i -> i + 15, i -> i + 16);
        assertTuple(mapper.apply(0), 17);
    }
    
    @Test
    void ofNullables18() {
        assertTuple(TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17), 18);
    }
    
    @Test
    void toTuple18OfNullables() {
        final Function<Integer, Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> mapper = TuplesOfNullables.toTupleOfNullables(i -> i + 0, i -> i + 1, i -> i + 2, i -> i + 3, i -> i + 4, i -> i + 5, i -> i + 6, i -> i + 7, i -> i + 8, i -> i + 9, i -> i + 10, i -> i + 11, i -> i + 12, i -> i + 13, i -> i + 14, i -> i + 15, i -> i + 16, i -> i + 17);
        assertTuple(mapper.apply(0), 18);
    }
    
    @Test
    void ofNullables19() {
        assertTuple(TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18), 19);
    }
    
    @Test
    void toTuple19OfNullables() {
        final Function<Integer, Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> mapper = TuplesOfNullables.toTupleOfNullables(i -> i + 0, i -> i + 1, i -> i + 2, i -> i + 3, i -> i + 4, i -> i + 5, i -> i + 6, i -> i + 7, i -> i + 8, i -> i + 9, i -> i + 10, i -> i + 11, i -> i + 12, i -> i + 13, i -> i + 14, i -> i + 15, i -> i + 16, i -> i + 17, i -> i + 18);
        assertTuple(mapper.apply(0), 19);
    }
    
    @Test
    void ofNullables20() {
        assertTuple(TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19), 20);
    }
    
    @Test
    void toTuple20OfNullables() {
        final Function<Integer, Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> mapper = TuplesOfNullables.toTupleOfNullables(i -> i + 0, i -> i + 1, i -> i + 2, i -> i + 3, i -> i + 4, i -> i + 5, i -> i + 6, i -> i + 7, i -> i + 8, i -> i + 9, i -> i + 10, i -> i + 11, i -> i + 12, i -> i + 13, i -> i + 14, i -> i + 15, i -> i + 16, i -> i + 17, i -> i + 18, i -> i + 19);
        assertTuple(mapper.apply(0), 20);
    }
    
    @Test
    void ofNullables21() {
        assertTuple(TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20), 21);
    }
    
    @Test
    void toTuple21OfNullables() {
        final Function<Integer, Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> mapper = TuplesOfNullables.toTupleOfNullables(i -> i + 0, i -> i + 1, i -> i + 2, i -> i + 3, i -> i + 4, i -> i + 5, i -> i + 6, i -> i + 7, i -> i + 8, i -> i + 9, i -> i + 10, i -> i + 11, i -> i + 12, i -> i + 13, i -> i + 14, i -> i + 15, i -> i + 16, i -> i + 17, i -> i + 18, i -> i + 19, i -> i + 20);
        assertTuple(mapper.apply(0), 21);
    }
    
    @Test
    void ofNullables22() {
        assertTuple(TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21), 22);
    }
    
    @Test
    void toTuple22OfNullables() {
        final Function<Integer, Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> mapper = TuplesOfNullables.toTupleOfNullables(i -> i + 0, i -> i + 1, i -> i + 2, i -> i + 3, i -> i + 4, i -> i + 5, i -> i + 6, i -> i + 7, i -> i + 8, i -> i + 9, i -> i + 10, i -> i + 11, i -> i + 12, i -> i + 13, i -> i + 14, i -> i + 15, i -> i + 16, i -> i + 17, i -> i + 18, i -> i + 19, i -> i + 20, i -> i + 21);
        assertTuple(mapper.apply(0), 22);
    }
    
    @Test
    void ofNullables23() {
        assertTuple(TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22), 23);
    }
    
    @Test
    void toTuple23OfNullables() {
        final Function<Integer, Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> mapper = TuplesOfNullables.toTupleOfNullables(i -> i + 0, i -> i + 1, i -> i + 2, i -> i + 3, i -> i + 4, i -> i + 5, i -> i + 6, i -> i + 7, i -> i + 8, i -> i + 9, i -> i + 10, i -> i + 11, i -> i + 12, i -> i + 13, i -> i + 14, i -> i + 15, i -> i + 16, i -> i + 17, i -> i + 18, i -> i + 19, i -> i + 20, i -> i + 21, i -> i + 22);
        assertTuple(mapper.apply(0), 23);
    }
    
    private void assertTuple(TupleOfNullables tuple, int degree) {
        assertEquals(degree, tuple.degree());
        for (int i = 0; i < degree; i++){
            assertEquals(i, tuple.get(i).orElseThrow(NoSuchElementException::new));
        }
    }
}