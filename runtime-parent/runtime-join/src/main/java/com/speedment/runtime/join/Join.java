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
package com.speedment.runtime.join;

import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.join.trait.HasDefaultBuild;
import java.util.stream.Stream;

/**
 * This interface represent a Join operation from which Streams of the joined
 * tables can be obtained.
 *
 * @param <T> the composite type of entity types
 *
 * @author Per Minborg
 * @since  3.1.0
 */
public interface Join<T> {

    /**
     * Creates and returns a new {@link Stream} over joined Tuples of entities
     * in the underlying data source (e.g database).
     * <p>
     * The order in which elements are returned when the stream is eventually
     * consumed <em>is unspecified</em>. The order may even change from one
     * invocation to another. Thus, it is an error to assume any particular
     * element order even though is might appear, for some stream sources, that
     * there is a de-facto order.
     * <p>
     * If a deterministic order is required, then make sure to invoke the
     * {@link Stream#sorted(java.util.Comparator)} method on the {@link Stream}
     * returned.
     * <p>
     * For Joins obtained via the default {@link HasDefaultBuild#build() }
     * method, elements appearing in the stream may be <em>deeply immutable</em>
     * meaning that Tuples in the stream are immutable and that entities
     * contained in these Tuples may also be immutable. Thus, it is an error to
     * invoke setters on any objects obtained directly or indirectly from the
     * stream elements. If mutable objects are needed, the immutable objects
     * must be used to create a new mutable object.
     * <p>
     * The Stream will never contain <code>null</code> elements.
     * <p>
     * This is <em>an inexpensive O(1) operation</em> that will complete in
     * constant time regardless of the number of entities in the underlying
     * database.
     * <p>
     * The returned stream is aware of its own pipeline and will optionally
     * <em>optimize its own pipeline</em> whenever it encounters a <em>Terminal
     * Operation</em> so that it will only iterate over a minimum set of
     * matching entities.
     * <p>
     * When a Terminal Operation is eventually called on the {@link Stream},
     * that execution time of the Terminal Operation will depend on the
     * optimized pipeline and the entities in the underlying database.
     * <p>
     * The Stream will be automatically
     * {@link Stream#onClose(java.lang.Runnable) closed} after the Terminal
     * Operation is completed or if an Exception is thrown during the Terminal
     * Operation.
     * <p>
     * Any Terminating Operation may throw a {@link SpeedmentException} if the
     * underlying database throws an Exception (e.g. an SqlException)
     * <p>
     * Because the Stream may short-circuit operations in the Stream pipeline,
     * methods having side-effects (like
     * {@link Stream#peek(java.util.function.Consumer) peek(Consumer)} will
     * potentially be affected by the optimization.
     * <p>
     *
     * @return a new Stream over Tuples of entities in this table in unspecified
     * order
     *
     * @throws SpeedmentException if an error occurs during a Terminal Operation
     * (e.g. an SqlException is thrown by the underlying database)
     *
     * @see java.util.stream
     * @see Stream
     */
    Stream<T> stream();

}
