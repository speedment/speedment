/**
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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.common.tuple;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
final class MappingTest {

    @Test
    void testTuple2() {

        final List<Tuple2<String, Integer>> expected = Arrays.asList(
            Tuples.of("Arne", "Arne".length()),
            Tuples.of("Tryggve", "tryggve".length())
        );

        final List<Tuple2<String, Integer>> actual = Stream.of("Arne", "Tryggve")
            .map(Tuples.toTuple(Function.identity(), String::length))
            .collect(toList());

        assertEquals(expected, actual);
    }

    @Test
    void testTuple4() {
        Tuple4<Integer, Integer, Integer, Integer> t4 = Tuples.of(1, 2, 3, 4);
        consume(t4);
    }

    @Test
    void testTuple1() {
        Stream.of("Arne", "Tryggve")
            .map(Tuples.toTuple(String::length))
            .forEach(MappingTest::consume);
    }

    @Test
    void testTuple2b() {
        Stream.of("Arne", "Tryggve")
            .map(Tuples.toTuple(Function.identity(), String::length))
            .forEach(MappingTest::consume);
    }

    @Test
    void testTuple2Variant() {
        Stream.of("Arne", "Tryggve")
            .map(Tuples.toTuple(Function.identity(), String::length))
            .map(Tuples.toTuple(Tuple2::get0, t2 -> t2.get1() + 10))
            .forEach(MappingTest::consume);

    }

    @Test
    void testTuple2Nulls() {
        assertThrows(NullPointerException.class, () -> {
            Stream.of("Arne", "Tryggve")
                .map(Tuples.toTuple(Function.identity(), s -> null))
                .forEach(MappingTest::consume);
        });
    }

    @Test()
    void testTuple2OfNullablesNulls() {
        Stream.of("Arne", "Tryggve")
            .map(TuplesOfNullables.toTupleOfNullables(Function.identity(), s -> null))
            .forEach(MappingTest::consume);
    }

    private static <T> void consume(T t) {

    }

}
