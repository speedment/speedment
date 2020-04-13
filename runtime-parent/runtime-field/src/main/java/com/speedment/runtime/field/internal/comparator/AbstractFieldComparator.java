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
package com.speedment.runtime.field.internal.comparator;

import com.speedment.runtime.field.comparator.FieldComparator;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import static java.util.Collections.singletonList;

/**
 * Abstract base implementation of {@link FieldComparator}
 *
 * @author Emil Forslund
 * @since  3.0.11
 */
abstract class AbstractFieldComparator<ENTITY>
implements FieldComparator<ENTITY> {

    AbstractFieldComparator() {}

    @Override
    public Comparator<ENTITY> thenComparing(Comparator<? super ENTITY> other) {
        return asCombined().thenComparing(other);
    }

    @Override
    public <U> Comparator<ENTITY> thenComparing(
            Function<? super ENTITY, ? extends U> keyExtractor,
            Comparator<? super U> keyComparator) {

        return asCombined().thenComparing(keyExtractor, keyComparator);
    }

    @Override
    public <U extends Comparable<? super U>> Comparator<ENTITY>
    thenComparing(Function<? super ENTITY, ? extends U> keyExtractor) {
        return asCombined().thenComparing(keyExtractor);
    }

    @Override
    public Comparator<ENTITY> thenComparingInt(
            ToIntFunction<? super ENTITY> keyExtractor) {
        return asCombined().thenComparingInt(keyExtractor);
    }

    @Override
    public Comparator<ENTITY> thenComparingLong(
            ToLongFunction<? super ENTITY> keyExtractor) {
        return asCombined().thenComparingLong(keyExtractor);
    }

    @Override
    public Comparator<ENTITY> thenComparingDouble(
            ToDoubleFunction<? super ENTITY> keyExtractor) {
        return asCombined().thenComparingDouble(keyExtractor);
    }

    private Comparator<ENTITY> asCombined() {
        return new CombinedComparatorImpl<>(singletonList(this));
    }
}
