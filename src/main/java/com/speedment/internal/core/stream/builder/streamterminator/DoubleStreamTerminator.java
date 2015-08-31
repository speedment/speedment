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
package com.speedment.internal.core.stream.builder.streamterminator;

import com.speedment.internal.core.stream.builder.pipeline.DoublePipeline;
import java.util.DoubleSummaryStatistics;
import static java.util.Objects.requireNonNull;
import java.util.OptionalDouble;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public interface DoubleStreamTerminator extends BaseStreamTerminator {

    default <T> void forEach(DoublePipeline pipeline, DoubleConsumer action) {
        requireNonNull(pipeline);
        requireNonNull(action);
        optimize(pipeline).getAsDoubleStream().forEach(action);
    }

    default void forEachOrdered(DoublePipeline pipeline, DoubleConsumer action) {
        requireNonNull(pipeline);
        requireNonNull(action);
        optimize(pipeline).getAsDoubleStream().forEachOrdered(action);
    }

    default double[] toArray(DoublePipeline pipeline) {
        requireNonNull(pipeline);
        return optimize(pipeline).getAsDoubleStream().toArray();
    }

    default double reduce(DoublePipeline pipeline, double identity, DoubleBinaryOperator op) {
        requireNonNull(pipeline);
        requireNonNull(identity);
        requireNonNull(op);
        return optimize(pipeline).getAsDoubleStream().reduce(identity, op);
    }

    default OptionalDouble reduce(DoublePipeline pipeline, DoubleBinaryOperator op) {
        requireNonNull(pipeline);
        requireNonNull(op);
        return optimize(pipeline).getAsDoubleStream().reduce(op);
    }

    default <R> R collect(DoublePipeline pipeline, Supplier<R> supplier,
        ObjDoubleConsumer<R> accumulator,
        BiConsumer<R, R> combiner) {
        requireNonNull(pipeline);
        requireNonNull(supplier);
        requireNonNull(accumulator);
        requireNonNull(combiner);
        return optimize(pipeline).getAsDoubleStream().collect(supplier, accumulator, combiner);
    }

    default double sum(DoublePipeline pipeline) {
        requireNonNull(pipeline);

        return optimize(pipeline).getAsDoubleStream().sum();
    }

    default OptionalDouble min(DoublePipeline pipeline) {
        requireNonNull(pipeline);

        return optimize(pipeline).getAsDoubleStream().min();
    }

    default OptionalDouble max(DoublePipeline pipeline) {
        requireNonNull(pipeline);

        return optimize(pipeline).getAsDoubleStream().max();
    }

    default long count(DoublePipeline pipeline) {
        requireNonNull(pipeline);

        return optimize(pipeline).getAsDoubleStream().count();
    }

    default OptionalDouble average(DoublePipeline pipeline) {
        requireNonNull(pipeline);

        return optimize(pipeline).getAsDoubleStream().average();
    }

    default DoubleSummaryStatistics summaryStatistics(DoublePipeline pipeline) {
        requireNonNull(pipeline);

        return optimize(pipeline).getAsDoubleStream().summaryStatistics();
    }

//    default boolean anyMatch(DoublePipeline pipeline, DoublePredicate predicate) {
//        return anyMatch(pipeline, (pip, pre) -> pip.getAsDoubleStream().anyMatch(pre), predicate);
//        //    return optimize(pipeline).getAsReferenceStream().anyMatch(predicate);
//    }
//
    default boolean anyMatch(DoublePipeline pipeline, DoublePredicate predicate) {
        requireNonNull(pipeline);
        requireNonNull(predicate);
        return optimize(pipeline).getAsDoubleStream().anyMatch(predicate);
    }

    default boolean allMatch(DoublePipeline pipeline, DoublePredicate predicate) {
        requireNonNull(pipeline);
        requireNonNull(predicate);
        return optimize(pipeline).getAsDoubleStream().allMatch(predicate);
    }

    default boolean noneMatch(DoublePipeline pipeline, DoublePredicate predicate) {
        requireNonNull(pipeline);
        requireNonNull(predicate);
        return optimize(pipeline).getAsDoubleStream().noneMatch(predicate);
    }

    default OptionalDouble findFirst(DoublePipeline pipeline) {
        requireNonNull(pipeline);
        return optimize(pipeline).getAsDoubleStream().findFirst();
    }

    default OptionalDouble findAny(DoublePipeline pipeline) {
        requireNonNull(pipeline);
        return optimize(pipeline).getAsDoubleStream().findAny();
    }

    default Stream<Double> boxed(DoublePipeline pipeline) {
        requireNonNull(pipeline);
        return optimize(pipeline).getAsDoubleStream().boxed();
    }

    default PrimitiveIterator.OfDouble iterator(DoublePipeline pipeline) {
        requireNonNull(pipeline);
        return optimize(pipeline).getAsDoubleStream().iterator();
    }

    default Spliterator.OfDouble spliterator(DoublePipeline pipeline) {
        requireNonNull(pipeline);
        return optimize(pipeline).getAsDoubleStream().spliterator();
    }

}
