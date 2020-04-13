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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class ManualTupleTest {

    @Test
    void tuple2() {
        final Tuple2<Integer, Integer> tuple2 = Tuples.of(0, 1);
        final TupleGetter<Tuple2<Integer, Integer>, Integer> getter0 = Tuple2.getter0();
        final TupleGetter<Tuple2<Integer, Integer>, Integer> getter1 = Tuple2.getter1();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(0, getter0.apply(tuple2));
        assertEquals(1, getter1.apply(tuple2));

    }

}
