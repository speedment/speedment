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
package com.speedment.plugins.json.internal;

import com.speedment.plugins.json.JsonCollector;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 * @param <ENTITY> type of the entity
 * @since 1.0.0
 */
public final class JsonCollectorImpl<ENTITY> implements JsonCollector<ENTITY> {

    public static <ENTITY> JsonCollector<ENTITY> collect(Function<ENTITY, String> converter) {
        return new JsonCollectorImpl<>(converter);
    }

    @Override
    public Supplier<StringJoiner> supplier() {
        return () -> new StringJoiner(",", "[", "]");
    }

    @Override
    public BiConsumer<StringJoiner, ENTITY> accumulator() {
        return (sj, t) -> sj.add(converter.apply(t));
    }

    @Override
    public BinaryOperator<StringJoiner> combiner() {
        return StringJoiner::merge;
    }

    @Override
    public Function<StringJoiner, String> finisher() {
        return StringJoiner::toString;
    }

    private static final Set<Collector.Characteristics> CHARACTERISTICS
        = Collections.unmodifiableSet(EnumSet.noneOf(Characteristics.class));

    @Override
    public Set<Collector.Characteristics> characteristics() {
        return CHARACTERISTICS;
    }

    private JsonCollectorImpl(Function<ENTITY, String> converter) {
        this.converter = requireNonNull(converter);
    }

    private final Function<ENTITY, String> converter;
}
