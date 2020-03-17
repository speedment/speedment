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
package com.speedment.common.combinatorics;

import com.speedment.common.combinatorics.internal.CombinationUtil;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * General Combination support. The class eagerly calculates all combinations.
 *
 * @author Per Minborg
 */
public final class Combination {

    private Combination() {}

    /**
     * Creates and returns all possible combinations of the given elements.
     *
     * The order of the combinations in the stream is unspecified.
     *
     * @param <T> element type
     * @param items to combine
     * @return all possible combinations of the given elements
     */
    @SafeVarargs
    @SuppressWarnings("varargs") // Creating a List from an array is safe
    public static <T> Stream<List<T>> of(final T... items) {
        return CombinationUtil.of(items);
    }

    /**
     * Creates and returns all possible combinations of the given elements.
     *
     * The order of the combinations in the stream is unspecified.
     *
     * @param <T> element type
     * @param items to combine
     * @return all possible combinations of the given elements
     */
    public static <T> Stream<List<T>> of(final Collection<T> items) {
        return CombinationUtil.of(items);
    }

}
