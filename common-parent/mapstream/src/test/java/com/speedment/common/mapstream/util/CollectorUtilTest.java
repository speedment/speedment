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
package com.speedment.common.mapstream.util;

import com.speedment.common.mapstream.MapStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class CollectorUtilTest {

    private Collector<String, ?, MapStream<Integer, List<String>>> collector;

    @BeforeEach
    void setup() {
        collector = CollectorUtil.groupBy(String::length);
    }

    @Test
    void groupBy() {
        final Map<Integer, List<String>> expected = new HashMap<>();
        expected.put(1, Stream.of("A", "A").collect(toList()));
        expected.put(2, Collections.singletonList("BB"));
        final Map<Integer, List<String>> actual = Stream.of("A", "BB", "A").collect(collector).toMap();
        assertEquals(expected, actual);
    }

    @Test
    void groupHolder() {
        final CollectorUtil.GroupHolder<Integer, String> holder1 = new CollectorUtil.GroupHolder<>(String::length);
        final CollectorUtil.GroupHolder<Integer, String> holder2 = new CollectorUtil.GroupHolder<>(String::length);
        holder1.add("A");
        holder2.add("B");
        holder1.merge(holder2);

        final Map<Integer, List<String>> expected = new HashMap<>();
        expected.put(1, Stream.of("A", "B").collect(toList()));
        final Map<Integer, List<String>> actual = holder1.finisher().toMap();
        assertEquals(expected, actual);
    }

}