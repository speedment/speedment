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
package com.speedment.stream;

import com.speedment.annotation.Api;
import com.speedment.field.predicate.SpeedmentPredicate;
import com.speedment.internal.core.stream.ComposedStreamDecorator;
import com.speedment.internal.core.stream.builder.ReferenceStreamBuilder;
import com.speedment.internal.core.stream.builder.pipeline.Pipeline;
import com.speedment.internal.util.Cast;
import java.util.stream.Stream;

/**
 * Decorator that is applied to a various part of the custom streams of
 * Speedment before execution. This can for an example be used to perform
 * optimizations.
 *
 * @author Emil Forslund
 * @since 2.2
 */
@Api(version = "2.2")
public interface StreamDecorator {

    final static StreamDecorator IDENTITY = new StreamDecorator() {
        @Override
        public StreamDecorator and(StreamDecorator other) {
            return other;
        }

    };

    default StreamDecorator and(StreamDecorator other) {
        if (this instanceof ComposedStreamDecorator) {
            Cast.castOrFail(this, ComposedStreamDecorator.class).and(other);
        }
        return new ComposedStreamDecorator(this, other);
    }

    default <ENTITY> ReferenceStreamBuilder<ENTITY> apply(ReferenceStreamBuilder<ENTITY> stream) {
        return stream;
    }

    default <ENTITY, V> SpeedmentPredicate<ENTITY, V> apply(SpeedmentPredicate<ENTITY, V> predicate) {
        return predicate;
    }

    default <P extends Pipeline> P apply(P pipeline) {
        return pipeline;
    }

    /**
     * Method to be used to modify or configure the initial stream from the data
     * source.
     *
     * @param <ENTITY> entity type
     * @param stream from the data source
     * @return the modified or configured stream
     */
    default <ENTITY> Stream<ENTITY> apply(Stream<ENTITY> stream) {
        return stream;
    }

    /**
     * Returns the {@link ParallelStrategy} to use for this {@link Stream}. The
     * {@link ParallelStrategy} defines how parallel streams are divided amongst
     * the available execution threads.
     *
     * @param <H> type of strategy receiver
     * @param hasParallelStrategy to apply the strategy on
     * @return the object {@link HasParallelStrategy} to use for this
     * {@link Stream}
     */
    default <H extends HasParallelStrategy> H apply(H hasParallelStrategy) {
        return hasParallelStrategy;
    }

    /**
     * A {@link StreamDecorator} that modifies the stream according to the
     * {@link ParallelStrategy#COMPUTE_INTENSITY_MEDIUM COMPUTE_INTENSITY_MEDIUM}
     * parallel strategy.
     *
     * @see ParallelStrategy#COMPUTE_INTENSITY_MEDIUM COMPUTE_INTENSITY_MEDIUM
     */
    final static StreamDecorator COMPUTE_INTENSITY_MEDIUM = of(ParallelStrategy.COMPUTE_INTENSITY_MEDIUM);
    /**
     * A {@link StreamDecorator} that modifies the stream according to the
     * {@link ParallelStrategy#COMPUTE_INTENSITY_HIGH COMPUTE_INTENSITY_HIGH}
     * parallel strategy.
     *
     * @see ParallelStrategy#COMPUTE_INTENSITY_HIGH COMPUTE_INTENSITY_HIGH
     */

    final static StreamDecorator COMPUTE_INTENSITY_HIGH = of(ParallelStrategy.COMPUTE_INTENSITY_HIGH);
    /**
     * A {@link StreamDecorator} that modifies the stream according to the
     * {@link ParallelStrategy#COMPUTE_INTENSITY_EXTREME COMPUTE_INTENSITY_EXTREME}
     * parallel strategy.
     *
     * @see ParallelStrategy#COMPUTE_INTENSITY_EXTREME COMPUTE_INTENSITY_EXTREME
     */

    final static StreamDecorator COMPUTE_INTENSITY_EXTREAM = of(ParallelStrategy.COMPUTE_INTENSITY_EXTREME);

    static StreamDecorator of(final ParallelStrategy parallelStrategy) {
        return new StreamDecorator() {
            @Override
            public <H extends HasParallelStrategy> H apply(H hasParallelStrategy) {
                hasParallelStrategy.setParallelStrategy(parallelStrategy);
                return hasParallelStrategy;
            }

        };
    }

}
