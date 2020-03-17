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
package com.speedment.common.function.internal.collector;

import com.speedment.common.function.collector.LongCollector;

import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of the {@link LongCollector}-interface.
 *
 * @author Emil Forslund
 * @since  1.0.5
 */
public final class LongCollectorImpl<A, R> implements LongCollector<A, R> {

    private final Supplier<A> supplier;
    private final ObjLongConsumer<A> accumulator;
    private final BinaryOperator<A> combiner;
    private final Function<A, R> finisher;
    private final Set<Collector.Characteristics> characteristics;

    public LongCollectorImpl(
            final Supplier<A> supplier,
            final ObjLongConsumer<A> accumulator,
            final BinaryOperator<A> combiner,
            final Function<A, R> finisher,
            final Set<Collector.Characteristics> characteristics) {
        this.supplier        = requireNonNull(supplier);
        this.accumulator     = requireNonNull(accumulator);
        this.combiner        = requireNonNull(combiner);
        this.finisher        = requireNonNull(finisher);
        this.characteristics = requireNonNull(characteristics);
    }

    @Override
    public Supplier<A> supplier() {
        return supplier;
    }

    @Override
    public ObjLongConsumer<A> accumulator() {
        return accumulator;
    }

    @Override
    public BinaryOperator<A> combiner() {
        return combiner;
    }

    @Override
    public Function<A, R> finisher() {
        return finisher;
    }

    @Override
    public Set<Collector.Characteristics> characteristics() {
        return characteristics;
    }
}
