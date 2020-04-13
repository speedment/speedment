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
package com.speedment.common.singletonstream;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.speedment.common.singletonstream.StreamOperator.create;

final class StreamCombined2Test extends AbstractStreamTester {

    @Override
    Stream<StreamOperator<Integer, Stream<Integer>>> streamOperators() {
        return Stream.of(
            create("filter", s -> s.filter(t -> t % 3 != 0)),
            create("map", s -> s.map(t -> t + 2)),
            create("mapToInt", s -> s.mapToInt(t -> t + 3).boxed()),
            create("flatMapToInt", s -> s.flatMapToInt(t -> IntStream.range(0, t)).boxed()),
            create("sorted", Stream::sorted),
            create("distinct", Stream::distinct)
        );
    }

}