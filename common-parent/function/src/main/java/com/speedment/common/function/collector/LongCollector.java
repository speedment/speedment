/**
 *
 * Copyright (c) 2006-2018, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.function.collector;

import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;

/**
 * Primitive collector that operates on {@code long} values, resulting in an
 * object of type {@code R}.
 *
 * @param <A> the intermediary accumulating type
 * @param <R> the result type
 *
 * @author Emil Forslund
 * @since  1.0.5
 */
public interface LongCollector<A, R> {

    /**
     * Returns a supplier that can create an intermediary accumulating object.
     *
     * @return the supplier for the accumulating object
     *
     * @see Collector#supplier()
     */
    Supplier<A> supplier();

    /**
     * Stateless function that takes an accumulating object returned by{@link
     * #supplier()} and adds a single {@code long} value to it.
     *
     * @return the accumulator
     *
     * @see Collector#accumulator()
     */
    ObjLongConsumer<A> accumulator();

    /**
     * Stateless function that takes two accumulating objects and returns a
     * single one representing the combined result. This can be either one of
     * the two instances or a completely new instance.
     *
     * @return the combiner
     *
     * @see Collector#combiner()
     */
    BinaryOperator<A> combiner();

    /**
     * Returns a finisher function that takes an accumulating object and turns
     * it into the final {@code double}.
     *
     * @return the finisher
     *
     * @see Collector#finisher()
     */
    Function<A, R> finisher();

    /**
     * Returns a set of characteristics for this collector.
     *
     * @return the characteristics for this collector
     *
     * @see Collector#characteristics()
     */
    Set<Characteristics> characteristics();

}