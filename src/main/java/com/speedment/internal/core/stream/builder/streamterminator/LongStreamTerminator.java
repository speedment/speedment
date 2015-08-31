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

import com.speedment.internal.core.stream.builder.pipeline.LongPipeline;
import java.util.LongSummaryStatistics;
import static java.util.Objects.requireNonNull;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public interface LongStreamTerminator extends BaseStreamTerminator {

    default <T> void forEach(LongPipeline pipeline, LongConsumer action) {
        requireNonNull(pipeline);
        requireNonNull(action);
        optimize(pipeline).getAsLongStream().forEach(action);
    }

    default void forEachOrdered(LongPipeline pipeline, LongConsumer action) {
        requireNonNull(pipeline);
        requireNonNull(action);
        optimize(pipeline).getAsLongStream().forEachOrdered(action);
    }

    default long[] toArray(LongPipeline pipeline) {
        requireNonNull(pipeline);
        return optimize(pipeline).getAsLongStream().toArray();
    }

    default long reduce(LongPipeline pipeline, long identity, LongBinaryOperator op) {
        requireNonNull(pipeline);
        requireNonNull(identity);
        requireNonNull(op);
        return optimize(pipeline).getAsLongStream().reduce(identity, op);
    }

    default OptionalLong reduce(LongPipeline pipeline, LongBinaryOperator op) {
        requireNonNull(pipeline);
        requireNonNull(op);
        return optimize(pipeline).getAsLongStream().reduce(op);
    }

    default <R> R collect(LongPipeline pipeline, Supplier<R> supplier,
        ObjLongConsumer<R> accumulator,
        BiConsumer<R, R> combiner) {
        requireNonNull(pipeline);
        requireNonNull(supplier);
        requireNonNull(accumulator);
        requireNonNull(combiner);
        return optimize(pipeline).getAsLongStream().collect(supplier, accumulator, combiner);
    }

    default long sum(LongPipeline pipeline) {
        requireNonNull(pipeline);
        return optimize(pipeline).getAsLongStream().sum();
    }

    default OptionalLong min(LongPipeline pipeline) {
        requireNonNull(pipeline);
        return optimize(pipeline).getAsLongStream().min();
    }

    default OptionalLong max(LongPipeline pipeline) {
        requireNonNull(pipeline);
        return optimize(pipeline).getAsLongStream().max();
    }

    default long count(LongPipeline pipeline) {
        requireNonNull(pipeline);
        return optimize(pipeline).getAsLongStream().count();
    }

    default OptionalDouble average(LongPipeline pipeline) {
        requireNonNull(pipeline);
        return optimize(pipeline).getAsLongStream().average();
    }

    default LongSummaryStatistics summaryStatistics(LongPipeline pipeline) {
        requireNonNull(pipeline);
        return optimize(pipeline).getAsLongStream().summaryStatistics();
    }

    default boolean anyMatch(LongPipeline pipeline, LongPredicate predicate) {
        requireNonNull(pipeline);
        requireNonNull(predicate);
        return optimize(pipeline).getAsLongStream().anyMatch(predicate);
    }

    default boolean allMatch(LongPipeline pipeline, LongPredicate predicate) {
        requireNonNull(pipeline);
        requireNonNull(predicate);
        return optimize(pipeline).getAsLongStream().allMatch(predicate);
    }

    default boolean noneMatch(LongPipeline pipeline, LongPredicate predicate) {
        requireNonNull(pipeline);
        requireNonNull(predicate);
        return optimize(pipeline).getAsLongStream().noneMatch(predicate);
    }

    default OptionalLong findFirst(LongPipeline pipeline) {
        requireNonNull(pipeline);
        return optimize(pipeline).getAsLongStream().findFirst();
    }

    default OptionalLong findAny(LongPipeline pipeline) {
        requireNonNull(pipeline);
        return optimize(pipeline).getAsLongStream().findAny();
    }

    default Stream<Long> boxed(LongPipeline pipeline) {
        requireNonNull(pipeline);
        return optimize(pipeline).getAsLongStream().boxed();
    }

    default PrimitiveIterator.OfLong iterator(LongPipeline pipeline) {
        requireNonNull(pipeline);
        return optimize(pipeline).getAsLongStream().iterator();
    }

    default Spliterator.OfLong spliterator(LongPipeline pipeline) {
        requireNonNull(pipeline);
        return optimize(pipeline).getAsLongStream().spliterator();
    }

}
