/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.core.stream.builder.streamterminator;

import com.speedment.core.stream.builder.pipeline.IntPipeline;
import java.util.IntSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public interface IntStreamTerminator extends BaseStreamTerminator {

    default <T> void forEach(IntPipeline pipeline, IntConsumer action) {
        optimize(pipeline).getAsIntStream().forEach(action);
    }

    default void forEachOrdered(IntPipeline pipeline, IntConsumer action) {
        optimize(pipeline).getAsIntStream().forEachOrdered(action);
    }

    default int[] toArray(IntPipeline pipeline) {
        return optimize(pipeline).getAsIntStream().toArray();
    }

    default int reduce(IntPipeline pipeline, int identity, IntBinaryOperator op) {
        return optimize(pipeline).getAsIntStream().reduce(identity, op);
    }

    default OptionalInt reduce(IntPipeline pipeline, IntBinaryOperator op) {
        return optimize(pipeline).getAsIntStream().reduce(op);
    }

    default <R> R collect(IntPipeline pipeline, Supplier<R> supplier,
            ObjIntConsumer<R> accumulator,
            BiConsumer<R, R> combiner) {
        return optimize(pipeline).getAsIntStream().collect(supplier, accumulator, combiner);
    }

    default int sum(IntPipeline pipeline) {
        return optimize(pipeline).getAsIntStream().sum();
    }

    default OptionalInt min(IntPipeline pipeline) {
        return optimize(pipeline).getAsIntStream().min();
    }

    default OptionalInt max(IntPipeline pipeline) {
        return optimize(pipeline).getAsIntStream().max();
    }

    default <T> long count(IntPipeline pipeline) {
        return optimize(pipeline).getAsIntStream().count();
    }

    default OptionalDouble average(IntPipeline pipeline) {
        return optimize(pipeline).getAsIntStream().average();
    }

    default IntSummaryStatistics summaryStatistics(IntPipeline pipeline) {
        return optimize(pipeline).getAsIntStream().summaryStatistics();
    }

    default boolean anyMatch(IntPipeline pipeline, IntPredicate predicate) {
        return optimize(pipeline).getAsIntStream().anyMatch(predicate);
    }

    default boolean allMatch(IntPipeline pipeline, IntPredicate predicate) {
        return optimize(pipeline).getAsIntStream().allMatch(predicate);
    }

    default boolean noneMatch(IntPipeline pipeline, IntPredicate predicate) {
        return optimize(pipeline).getAsIntStream().noneMatch(predicate);
    }

    default OptionalInt findFirst(IntPipeline pipeline) {
        return optimize(pipeline).getAsIntStream().findFirst();
    }

    default OptionalInt findAny(IntPipeline pipeline) {
        return optimize(pipeline).getAsIntStream().findAny();
    }

    default Stream<Integer> boxed(IntPipeline pipeline) {
        return optimize(pipeline).getAsIntStream().boxed();
    }

    default PrimitiveIterator.OfInt iterator(IntPipeline pipeline) {
        return optimize(pipeline).getAsIntStream().iterator();
    }

    default Spliterator.OfInt spliterator(IntPipeline pipeline) {
        return optimize(pipeline).getAsIntStream().spliterator();
    }

}
