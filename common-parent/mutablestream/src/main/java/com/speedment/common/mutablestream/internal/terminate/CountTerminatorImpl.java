/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.mutablestream.internal.terminate;

import java.util.stream.Stream;
import com.speedment.common.mutablestream.HasNext;
import com.speedment.common.mutablestream.terminate.CountTerminator;
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 *
 * @param <T>  the terminated stream type
 * @param <TS> the main stream interface
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class CountTerminatorImpl<T, TS extends BaseStream<T, TS>> 
extends AbstractTerminator<T, TS, Long> 
implements CountTerminator<T, TS> {

    public CountTerminatorImpl(HasNext<T, TS> previous, boolean parallel) {
        super(previous, parallel);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Long execute() {
        try (final TS built = buildPrevious()) {
            if (built instanceof Stream<?>) {
                return ((Stream<T>) built).count();
            } else if (built instanceof IntStream) {
                return ((IntStream) built).count();
            } else if (built instanceof LongStream) {
                return ((LongStream) built).count();
            } else if (built instanceof DoubleStream) {
                return ((DoubleStream) built).count();
            } else {
                throw new UnsupportedOperationException(
                    "Built stream did not match any known stream interface."
                );
            }
        }
    }
}