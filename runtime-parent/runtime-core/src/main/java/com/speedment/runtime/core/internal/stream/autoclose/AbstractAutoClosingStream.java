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
package com.speedment.runtime.core.internal.stream.autoclose;

import com.speedment.common.function.TriFunction;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.stream.ComposeRunnableUtil;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.*;
import java.util.stream.*;

import static com.speedment.common.invariant.NullUtil.requireNonNullElements;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
abstract class AbstractAutoClosingStream implements AutoCloseable {

    private final Set<BaseStream<?, ?>> streamSet;
    private final boolean allowStreamIteratorAndSpliterator;

    AbstractAutoClosingStream(
        final Set<BaseStream<?, ?>> streamSet,
        final boolean allowStreamIteratorAndSpliterator
    ) {
        this.streamSet = requireNonNull(streamSet);
        this.allowStreamIteratorAndSpliterator = allowStreamIteratorAndSpliterator;
    }

    protected Set<BaseStream<?, ?>> getStreamSet() {
        return streamSet;
    }

    protected abstract BaseStream<?, ?> getStream();

    @Override
    public void close() {
        final Set<BaseStream<?, ?>> streamsToClose = new HashSet<>(streamSet); // Copy the set
        streamSet.clear(); // Clear the shared streamSet so that other streams will not close again
        try {
            ComposeRunnableUtil.composedClose(streamsToClose.toArray(new BaseStream<?, ?>[0]));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            getStream().close(); // Close the underlying stream
        }
    }

    boolean isAllowStreamIteratorAndSpliterator() {
        return allowStreamIteratorAndSpliterator;
    }

    <T> boolean finallyClose(BooleanSupplier bs) {
        try {
            return bs.getAsBoolean();
        } finally {
            close();
        }
    }

    <T> long finallyClose(LongSupplier lp) {
        try {
            return lp.getAsLong();
        } finally {
            close();
        }
    }

    <T> int finallyClose(IntSupplier is) {
        try {
            return is.getAsInt();
        } finally {
            close();
        }
    }

    <T> double finallyClose(DoubleSupplier ds) {
        try {
            return ds.getAsDouble();
        } finally {
            close();
        }
    }

    <T> void finallyClose(Runnable r) {
        try {
            r.run();
        } finally {
            close();
        }
    }

    <T> T finallyClose(Supplier<T> s) {
        try {
            return s.get();
        } finally {
            close();
        }
    }

    <T> Stream<T> wrap(Stream<T> stream) {
        return wrap(stream, getStreamSet(), AutoClosingReferenceStream::new);
    }

    IntStream wrap(IntStream stream) {
        return wrap(stream, getStreamSet(), AutoClosingIntStream::new);
    }

    LongStream wrap(LongStream stream) {
        return wrap(stream, getStreamSet(), AutoClosingLongStream::new);
    }

    DoubleStream wrap(DoubleStream stream) {
        return wrap(stream, getStreamSet(), AutoClosingDoubleStream::new);
    }

    private <T> T wrap(T stream, Set<BaseStream<?, ?>> streamSet, TriFunction<T, Set<BaseStream<?, ?>>, Boolean, T> wrapper) {
        if (stream instanceof AbstractAutoClosingStream) {
            return stream; // If we already are wrapped, then do not wrap again
        }
        return wrapper.apply(stream, streamSet, allowStreamIteratorAndSpliterator);
    }

    static UnsupportedOperationException newUnsupportedException(String methodName) {
        return new UnsupportedOperationException("The " + methodName + "() method is unsupported because otherwise the AutoClose property cannot be guaranteed");
    }

    static Set<BaseStream<?, ?>> newSet() {
        return new HashSet<>();
    }

}
