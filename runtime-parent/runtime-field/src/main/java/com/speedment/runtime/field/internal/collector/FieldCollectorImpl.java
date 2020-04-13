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
package com.speedment.runtime.field.internal.collector;

import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.collector.FieldCollector;
import static java.util.Objects.requireNonNull;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 *
 * @param <T>  the entity type to be collected
 * @param <A>  the intermediate accumulation type of the downstream collector
 * @param <R>  the collected result
 * 
 * @author Emil Forslund
 * @since  3.0.2
 */
public final class FieldCollectorImpl<T, A, R>
implements FieldCollector<T, A, R>  {

    private final Field<T> field;
    private final Supplier<A> supplier;
    private final BiConsumer<A, T> accumulator;
    private final BinaryOperator<A> combiner;
    private final Function<A, R> finisher;
    private final Set<Collector.Characteristics> characteristics;

    public FieldCollectorImpl(
                Field<T> field,
                Supplier<A> supplier,
                BiConsumer<A, T> accumulator,
                BinaryOperator<A> combiner,
                Function<A, R> finisher,
                Set<Collector.Characteristics> characteristics) {
        
        this.field           = requireNonNull(field);
        this.supplier        = requireNonNull(supplier);
        this.accumulator     = requireNonNull(accumulator);
        this.combiner        = requireNonNull(combiner);
        this.finisher        = requireNonNull(finisher);
        this.characteristics = requireNonNull(characteristics);
    }

    public FieldCollectorImpl(
            Field<T> field,
            Supplier<A> supplier,
            BiConsumer<A, T> accumulator,
            BinaryOperator<A> combiner,
            Set<Collector.Characteristics> characteristics) {
        
        this(field, supplier, accumulator, combiner, castingIdentity(), characteristics);
    }
    
    @Override
    public Field<T> getField() {
        return field;
    }

    @Override
    public BiConsumer<A, T> accumulator() {
        return accumulator;
    }

    @Override
    public Supplier<A> supplier() {
        return supplier;
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
    
    @SuppressWarnings("unchecked")
    private static <I, R> Function<I, R> castingIdentity() {
        return i -> (R) i;
    }
}