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

import com.speedment.internal.core.stream.builder.pipeline.ReferencePipeline;
import java.util.Comparator;
import java.util.Iterator;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 *
 * @author pemi
 */
public interface ReferenceStreamTerminator extends BaseStreamTerminator {

//    default <T> ReferencePipeline<T> optimize(ReferencePipeline<T> initialPipeline) {
//        return initialPipeline;
//    }
    default <T> void forEach(ReferencePipeline<T> pipeline, Consumer<? super T> action) {
        requireNonNull(pipeline);
        requireNonNull(action);
        optimize(pipeline).getAsReferenceStream().forEach(action);
    }

    default <T> void forEachOrdered(ReferencePipeline<T> pipeline, Consumer<? super T> action) {
        requireNonNull(pipeline);
        requireNonNull(action);
        optimize(pipeline).getAsReferenceStream().forEachOrdered(action);
    }

    default <T> Object[] toArray(ReferencePipeline<T> pipeline) {
        requireNonNull(pipeline);
        return optimize(pipeline).getAsReferenceStream().toArray();
    }

    default <T, A> A[] toArray(ReferencePipeline<T> pipeline, IntFunction<A[]> generator) {
        requireNonNull(pipeline);
        requireNonNull(generator);
        return optimize(pipeline).getAsReferenceStream().toArray(generator);
    }

    default <T> T reduce(ReferencePipeline<T> pipeline, T identity, BinaryOperator<T> accumulator) {
        requireNonNull(pipeline);
        requireNonNull(identity);
        requireNonNull(accumulator);
        return optimize(pipeline).getAsReferenceStream().reduce(identity, accumulator);
    }

    default <T> Optional<T> reduce(ReferencePipeline<T> pipeline, BinaryOperator<T> accumulator) {
        requireNonNull(pipeline);
        requireNonNull(accumulator);
        return optimize(pipeline).getAsReferenceStream().reduce(accumulator);
    }

    default <T, U> U reduce(ReferencePipeline<T> pipeline, U identity,
        BiFunction<U, ? super T, U> accumulator,
        BinaryOperator<U> combiner) {
        requireNonNull(pipeline);
        requireNonNull(identity);
        requireNonNull(accumulator);
        requireNonNull(combiner);
        return optimize(pipeline).getAsReferenceStream().reduce(identity, accumulator, combiner);
    }

    default <T, R> R collect(ReferencePipeline<T> pipeline, Supplier<R> supplier,
        BiConsumer<R, ? super T> accumulator,
        BiConsumer<R, R> combiner) {
        requireNonNull(pipeline);
        requireNonNull(supplier);
        requireNonNull(accumulator);
        return optimize(pipeline).getAsReferenceStream().collect(supplier, accumulator, combiner);
    }

    default <T, R, A> R collect(ReferencePipeline<T> pipeline, Collector<? super T, A, R> collector) {
        requireNonNull(pipeline);
        requireNonNull(collector);
        return optimize(pipeline).getAsReferenceStream().collect(collector);
    }

    default <T> Optional<T> min(ReferencePipeline<T> pipeline, Comparator<? super T> comparator) {
        requireNonNull(pipeline);
        requireNonNull(comparator);
        return optimize(pipeline).getAsReferenceStream().min(comparator);
    }

    default <T> Optional<T> max(ReferencePipeline<T> pipeline, Comparator<? super T> comparator) {
        requireNonNull(pipeline);
        requireNonNull(comparator);
        return optimize(pipeline).getAsReferenceStream().max(comparator);
    }

    default <T> long count(ReferencePipeline<T> pipeline) {
        //return count(pipeline, p -> p.getAsReferenceStream().count());
        requireNonNull(pipeline);
        return optimize(pipeline).getAsReferenceStream().count();
    }

    default <T> boolean anyMatch(ReferencePipeline<T> pipeline, Predicate<? super T> predicate) {
        //return anyMatch(pipeline, (pip, pre) -> pip.getAsReferenceStream().anyMatch(pre), predicate);
        requireNonNull(pipeline);
        requireNonNull(predicate);
        return optimize(pipeline).getAsReferenceStream().anyMatch(predicate);
    }

    default <T> boolean allMatch(ReferencePipeline<T> pipeline, Predicate<? super T> predicate) {
        requireNonNull(pipeline);
        requireNonNull(predicate);
        return optimize(pipeline).getAsReferenceStream().allMatch(predicate);
    }

    default <T> boolean noneMatch(ReferencePipeline<T> pipeline, Predicate<? super T> predicate) {
        requireNonNull(pipeline);
        requireNonNull(predicate);
        return optimize(pipeline).getAsReferenceStream().noneMatch(predicate);
    }

    default <T> Optional<T> findFirst(ReferencePipeline<T> pipeline) {
        requireNonNull(pipeline);
        return optimize(pipeline).getAsReferenceStream().findFirst();
    }

    default <T> Optional<T> findAny(ReferencePipeline<T> pipeline) {
        requireNonNull(pipeline);
        return optimize(pipeline).getAsReferenceStream().findAny();
    }

    default <T> Iterator<T> iterator(ReferencePipeline<T> pipeline) {
        requireNonNull(pipeline);
        return optimize(pipeline).getAsReferenceStream().iterator();
    }

    default <T> Spliterator<T> spliterator(ReferencePipeline<T> pipeline) {
        requireNonNull(pipeline);
        return optimize(pipeline).getAsReferenceStream().spliterator();
    }

}
