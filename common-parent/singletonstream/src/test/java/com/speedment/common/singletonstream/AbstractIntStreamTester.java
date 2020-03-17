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

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

abstract class AbstractIntStreamTester extends AbstractTester<Integer, IntStream> {

    @Override
    Function<IntStream, List<Integer>> finisher() {
        return s -> s.boxed().collect(Collectors.toList());
    }

    @Override
    IntStream referenceStream() {
        return IntStream.of(INITIAL_VALUE);
    }

    @Override
    IntStream initialStream() {
        return SingletonIntStream.of(INITIAL_VALUE);
    }
}