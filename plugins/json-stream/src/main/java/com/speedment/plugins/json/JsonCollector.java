/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.plugins.json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collectors.joining;

/**
 * A specialized java {@link Collector} that converts streams of Speedment
 * entities into JSON arrays.
 * <p>
 * Example usage:
 * <code>
 *      app.managerOf(Employee.class).stream()
 *          .filter(Employee.AGE.greaterThan(35))
 *          .filter(Employee.NAME.startsWith("B"))
 *          .collect(JsonCollector.toJson());
 * </code>
 * 
 * @param <ENTITY> the entity type
 * 
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class JsonCollector<ENTITY> implements Collector<ENTITY, List<String>, String> {
    
    /**
     * Returns a collector that calls the {@link #toJson(JsonEncoder)}
     * method for each element in the stream and joins the resulting stream
     * separated by commas and surrounded by square brackets.
     * <p>
     * The result of the stream:
     * <pre>    a b c</pre> 
     * would be:
     * <pre>    ['a', 'b', 'c']</pre>
     *
     * @param <T> the type of the stream
     * @return the json string
     */
    @Deprecated // Need to be rewritten to work in 3.0.0
    public static <T> Collector<T, ?, String> toJson() {
        return new JsonCollector<>(JsonUtil::toJson, l -> "[" + l.stream().collect(joining(", ")) + "]");
    }

    /**
     * Returns a collector that calls the specified encoder for each element in
     * the stream and joins the resuling stream separated by commas and
     * surrounded by square brackets. Each element is also formatted using the 
     * specified {@link JsonEncoder}.
     *
     * @param <ENTITY>  the type of the stream
     * @param encoder   the enocder to use
     * @return          the json string
     */
    public static <ENTITY> Collector<ENTITY, ?, String> toJson(JsonEncoder<ENTITY> encoder) {
        requireNonNull(encoder);
        return new JsonCollector<>(encoder::apply, l -> "[" + l.stream().collect(joining(", ")) + "]");
    }

    @Override
    public Supplier<List<String>> supplier() {
        return () -> Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public BiConsumer<List<String>, ENTITY> accumulator() {
        return (l, t) -> {
            synchronized (l) {
                l.add(converter.apply(t));
            }
        };
    }

    @Override
    public BinaryOperator<List<String>> combiner() {
        return (l1, l2) -> {
            synchronized (l1) {
                l1.addAll(l2);
                return l1;
            }
        };
    }

    @Override
    public Function<List<String>, String> finisher() {
        return merger::apply;
    }

    @Override
    public Set<Collector.Characteristics> characteristics() {
        return EnumSet.of(CONCURRENT);
    }
    
    private JsonCollector(Function<ENTITY, String> converter, Function<List<String>, String> merger) {
        this.converter = requireNonNull(converter);
        this.merger = requireNonNull(merger);
    }
    
    private final Function<ENTITY, String> converter;
    private final Function<List<String>, String> merger;
}
