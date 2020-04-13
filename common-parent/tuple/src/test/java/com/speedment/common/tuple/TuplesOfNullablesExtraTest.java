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
import com.speedment.common.tuple.nullable.Tuple3OfNullables;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.IntStream;

import static com.speedment.common.tuple.TuplesTestUtil.SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Emil Forslund
 * @since  1.0.5
 */
final class TuplesOfNullablesExtraTest {

    @Test
    void ofArray() {
        IntStream.range(0, SIZE).forEach(i -> {
            final TupleOfNullables tuple = TuplesTestUtil.createTupleOfNullableFilled(i);
            assertEquals(i, tuple.degree());

            final Integer[] array = TuplesTestUtil.array(i);
            for (int j = 0; j < i; j++) {
                final Integer expected = array[j];
                final Integer actual = (Integer) tuple.get(j).orElseThrow(NoSuchElementException::new);
                assertEquals(expected, actual);
            }

        });
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void getter(int index) {
        final Tuple3OfNullables<Integer, Integer, Integer> tuple = TuplesOfNullables.ofNullables(0, 1, 2);
        final TupleGetter<Tuple3OfNullables<Integer, Integer, Integer>, Optional<Integer>> getter = TupleOfNullables.getter(index);
        assertEquals(index, getter.index());
        assertEquals(index, getter.apply(tuple).orElseThrow(NoSuchElementException::new));
    }
}