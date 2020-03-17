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
package com.speedment.common.function.collector;

import com.speedment.common.function.ObjCharConsumer;

import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collector.Characteristics;
import java.util.stream.Collector;

/**
 * Primitive collector that operates on {@code char} values, resulting in a
 * single {@code double}.
 * 
 * @param <A> the intermediary accumulating type
 * 
 * @author Emil Forslund
 * @since  1.0.3
 * 
 * @see Collector
 */
public interface CharToDoubleCollector<A> {
    
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
     * #supplier()} and adds a single {@code char} value to it.
     * 
     * @return the accumulator
     * 
     * @see Collector#accumulator()
     */
    ObjCharConsumer<A> accumulator();
    
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
    ToDoubleFunction<A> finisher();
    
    /**
     * Returns a set of characteristics for this collector.
     * 
     * @return the characteristics for this collector
     * 
     * @see Collector#characteristics()
     */
    Set<Characteristics> characteristics();
}