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
package com.speedment.runtime.field.collector;

import com.speedment.runtime.field.BooleanField;
import com.speedment.runtime.field.ByteField;
import com.speedment.runtime.field.CharField;
import com.speedment.runtime.field.DoubleField;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.FloatField;
import com.speedment.runtime.field.IntField;
import com.speedment.runtime.field.LongField;
import com.speedment.runtime.field.ReferenceField;
import com.speedment.runtime.field.ShortField;
import com.speedment.runtime.field.StringField;
import com.speedment.runtime.field.internal.collector.FieldCollectorImpl;
import static java.util.Collections.emptySet;
import static java.util.Collections.unmodifiableSet;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.*;
import java.util.stream.Collector;
import static java.util.stream.Collectors.toList;

/**
 * A number of collectors specialized for entities. These are inspired by the
 * {@link java.util.stream.Collectors} class in the java standard libraries.
 * 
 * @author Emil Forslund
 * @since  3.0.2
 */
public final class FieldCollectors {
    private FieldCollectors() {}

    public static <ENTITY, D> Collector<ENTITY, ?, Map<Long, List<ENTITY>>>
    groupingBy(LongField<ENTITY, D> field) {
        return groupingBy(field, field.getter()::apply, HashMap::new, toList());
    }
    
    public static <ENTITY, D> Collector<ENTITY, ?, Map<Integer, List<ENTITY>>>
    groupingBy(IntField<ENTITY, D> field) {
        return groupingBy(field, field.getter()::apply, HashMap::new, toList());
    }
    
    public static <ENTITY, D> Collector<ENTITY, ?, Map<Short, List<ENTITY>>>
    groupingBy(ShortField<ENTITY, D> field) {
        return groupingBy(field, field.getter()::apply, HashMap::new, toList());
    }
    
    public static <ENTITY, D> Collector<ENTITY, ?, Map<Byte, List<ENTITY>>>
    groupingBy(ByteField<ENTITY, D> field) {
        return groupingBy(field, field.getter()::apply, HashMap::new, toList());
    }
    
    public static <ENTITY, D> Collector<ENTITY, ?, Map<Double, List<ENTITY>>>
    groupingBy(DoubleField<ENTITY, D> field) {
        return groupingBy(field, field.getter()::apply, HashMap::new, toList());
    }
    
    public static <ENTITY, D> Collector<ENTITY, ?, Map<Float, List<ENTITY>>>
    groupingBy(FloatField<ENTITY, D> field) {
        return groupingBy(field, field.getter()::apply, HashMap::new, toList());
    }
    
    public static <ENTITY, D> Collector<ENTITY, ?, Map<Boolean, List<ENTITY>>>
    groupingBy(BooleanField<ENTITY, D> field) {
        return groupingBy(field, field.getter()::apply, HashMap::new, toList());
    }

    public static <ENTITY, D> Collector<ENTITY, ?, Map<Character, List<ENTITY>>>
    groupingBy(CharField<ENTITY, D> field) {
        return groupingBy(field, field.getter()::apply, HashMap::new, toList());
    }
    
    public static <ENTITY, D> Collector<ENTITY, ?, Map<String, List<ENTITY>>>
    groupingBy(StringField<ENTITY, D> field) {
        return groupingBy(field, field.getter()::apply, HashMap::new, toList());
    }
    
    public static <ENTITY, D, T> Collector<ENTITY, ?, Map<T, List<ENTITY>>>
    groupingBy(ReferenceField<ENTITY, D, T> field) {
        return groupingBy(field, field.getter()::apply, HashMap::new, toList());
    }
    
    public static <ENTITY, D, A, R> Collector<ENTITY, ?, Map<Long, R>>
    groupingBy(LongField<ENTITY, D> field, Collector<ENTITY, A, R> downstream) {
        return groupingBy(field, field.getter()::apply, HashMap::new, downstream);
    }
    
    public static <ENTITY, D, A, R> Collector<ENTITY, ?, Map<Integer, R>>
    groupingBy(IntField<ENTITY, D> field, Collector<ENTITY, A, R> downstream) {
        return groupingBy(field, field.getter()::apply, HashMap::new, downstream);
    }
    
    public static <ENTITY, D, A, R> Collector<ENTITY, ?, Map<Short, R>>
    groupingBy(ShortField<ENTITY, D> field, Collector<ENTITY, A, R> downstream) {
        return groupingBy(field, field.getter()::apply, HashMap::new, downstream);
    }
    
    public static <ENTITY, D, A, R> Collector<ENTITY, ?, Map<Byte, R>>
    groupingBy(ByteField<ENTITY, D> field, Collector<ENTITY, A, R> downstream) {
        return groupingBy(field, field.getter()::apply, HashMap::new, downstream);
    }
    
    public static <ENTITY, D, A, R> Collector<ENTITY, ?, Map<Double, R>>
    groupingBy(DoubleField<ENTITY, D> field, Collector<ENTITY, A, R> downstream) {
        return groupingBy(field, field.getter()::apply, HashMap::new, downstream);
    }
    
    public static <ENTITY, D, A, R> Collector<ENTITY, ?, Map<Float, R>>
    groupingBy(FloatField<ENTITY, D> field, Collector<ENTITY, A, R> downstream) {
        return groupingBy(field, field.getter()::apply, HashMap::new, downstream);
    }
    
    public static <ENTITY, D, A, R> Collector<ENTITY, ?, Map<Boolean, R>>
    groupingBy(BooleanField<ENTITY, D> field, Collector<ENTITY, A, R> downstream) {
        return groupingBy(field, field.getter()::apply, HashMap::new, downstream);
    }
    
    public static <ENTITY, D, A, R> Collector<ENTITY, ?, Map<Character, R>>
    groupingBy(CharField<ENTITY, D> field, Collector<ENTITY, A, R> downstream) {
        return groupingBy(field, field.getter()::apply, HashMap::new, downstream);
    }
    
    public static <ENTITY, D, A, R> Collector<ENTITY, ?, Map<String, R>>
    groupingBy(StringField<ENTITY, D> field, Collector<ENTITY, A, R> downstream) {
        return groupingBy(field, field.getter()::apply, HashMap::new, downstream);
    }
    
    public static <ENTITY, T, D, A, R> Collector<ENTITY, ?, Map<T, R>>
    groupingBy(ReferenceField<ENTITY, D, T> field, Collector<ENTITY, A, R> downstream) {
        return groupingBy(field, field.getter()::apply, HashMap::new, downstream);
    }
    
    public static <T, K, D, A, M extends Map<K, D>>
    FieldCollector<T, ?, M> groupingBy(
            Field<T> field,
            Function<T, K> classifier,
            Supplier<M> mapFactory,
            Collector<? super T, A, D> downstream) {

        Supplier<A> downstreamSupplier = downstream.supplier();
        BiConsumer<A, ? super T> downstreamAccumulator = downstream.accumulator();
        BiConsumer<Map<K, A>, T> accumulator = (m, t) -> {
            K key = Objects.requireNonNull(classifier.apply(t), "element cannot be mapped to a null key");
            A container = m.computeIfAbsent(key, k -> downstreamSupplier.get());
            downstreamAccumulator.accept(container, t);
        };
        BinaryOperator<Map<K, A>> merger = FieldCollectors.mapMerger(downstream.combiner());
        @SuppressWarnings("unchecked")
        Supplier<Map<K, A>> mangledFactory = (Supplier<Map<K, A>>) mapFactory;

        if (downstream.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH)) {
            return new FieldCollectorImpl<>(field, mangledFactory, accumulator, merger, CH_ID);
        }
        else {
            @SuppressWarnings("unchecked")
            UnaryOperator<A> downstreamFinisher = (UnaryOperator<A>) downstream.finisher();
            Function<Map<K, A>, M> finisher = intermediate -> {
                intermediate.replaceAll((k, v) -> downstreamFinisher.apply(v));
                @SuppressWarnings("unchecked")
                M castResult = (M) intermediate;
                return castResult;
            };
            return new FieldCollectorImpl<>(field, mangledFactory, accumulator, merger, finisher, CH_NOID);
        }
    }

    private static <K, V, M extends Map<K,V>>
    BinaryOperator<M> mapMerger(BinaryOperator<V> mergeFunction) {
        return (m1, m2) -> {
            for (Map.Entry<K,V> e : m2.entrySet()) {
                m1.merge(e.getKey(), e.getValue(), mergeFunction);
            }
            return m1;
        };
    }

    private static final Set<Collector.Characteristics> CH_ID = 
        unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
    
    private static final Set<Collector.Characteristics> CH_NOID = emptySet();
}