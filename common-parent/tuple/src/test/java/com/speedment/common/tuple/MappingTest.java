/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Per Minborg
 */
public class MappingTest {

    @Test
    public void testTuple2() {

        final List<Tuple2<String, Integer>> expected = Arrays.asList(
            Tuples.of("Arne", "Arne".length()),
            Tuples.of("Tryggve", "tryggve".length())
        );

        final List<Tuple2<String, Integer>> actual = Stream.of("Arne", "Tryggve")
            .map(Tuples.toTuple(Function.identity(), String::length))
            .collect(toList());

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testTuple4() {
        Tuple4<Integer, Integer, Integer, Integer> t4 = Tuples.of(1, 2, 3, 4);
        System.out.println(t4);
    }

    @Test
    public void testTuple1() {
        Stream.of("Arne", "Tryggve")
            .map(Tuples.toTuple(String::length))
            .forEach(System.out::println);

    }

    @Test
    public void testTuple2b() {
        Stream.of("Arne", "Tryggve")
            .map(Tuples.toTuple(Function.identity(), String::length))
            .forEach(System.out::println);

    }

    @Test
    public void testTuple2Variant() {
        Stream.of("Arne", "Tryggve")
            .map(Tuples.toTuple(Function.identity(), String::length))
            .map(Tuples.toTuple(Tuple2::get0, t2 -> t2.get1() + 10))
            .forEach(System.out::println);

    }

    @Test(expected = NullPointerException.class)
    public void testTuple2Nulls() {
        Stream.of("Arne", "Tryggve")
            .map(Tuples.toTuple(Function.identity(), s -> null))
            .forEach(System.out::println);

    }

    @Test()
    public void testTuple2OfNullablesNulls() {
        Stream.of("Arne", "Tryggve")
            .map(TuplesOfNullables.toTupleOfNullables(Function.identity(), s -> null))
            .forEach(System.out::println);

    }

}
