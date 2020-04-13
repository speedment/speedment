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

import java.util.stream.IntStream;

final class TuplesTestUtil {

    static final int SIZE = 100;

    static final Integer[] LARGE_ARRAY = IntStream.range(0, SIZE).boxed().toArray(Integer[]::new);
    static final Tuple LARGE_TUPLE = Tuples.ofArray((Object[]) LARGE_ARRAY);

    private TuplesTestUtil() {}

    static Tuple createTupleFilled(int i) {
        return Tuples.ofArray((Object[]) array(i));
    }

    static TupleOfNullables createTupleOfNullableFilled(int i) {
        return TuplesOfNullables.ofNullablesArray((Object[]) array(i));
    }

/*
    static MutableTuple createMutableTupleFilled(int i) {
        MutableTuple tuple = MutableTuples.
        return TuplesOfNullables.ofNullablesArray((Object[]) array(i));
    }
*/


    public static Integer[] array(int i) {
        final Integer[] array = new Integer[i];
        for (int j = 0; j < i; j++) {
            array[j] = j;
        }
        return array;
    }

}
