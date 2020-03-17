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
package com.speedment.runtime.core.internal.stream.autoclose;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.*;
import java.util.stream.*;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
abstract class AbstractAutoClosingStream<T, S extends BaseStream<T, S>> implements AutoCloseable {

    private final S stream;
    private final boolean allowStreamIteratorAndSpliterator;
    private final AtomicBoolean closed;

    AbstractAutoClosingStream(
        final S stream,
        final boolean allowStreamIteratorAndSpliterator
    ) {
        this.stream = requireNonNull(stream);
        this.allowStreamIteratorAndSpliterator = allowStreamIteratorAndSpliterator;
        this.closed = new AtomicBoolean();
    }

    protected S stream(){ return stream; }

    @Override
    public void close() {
        if (closed.compareAndSet(false,true)) {
            stream().close();
        }
    }

    boolean isAllowStreamIteratorAndSpliterator() {
        return allowStreamIteratorAndSpliterator;
    }

    boolean finallyClose(BooleanSupplier bs) {
        try {
            return bs.getAsBoolean();
        } finally {
            close();
        }
    }

    long finallyClose(LongSupplier lp) {
        try {
            return lp.getAsLong();
        } finally {
            close();
        }
    }

    int finallyClose(IntSupplier is) {
        try {
            return is.getAsInt();
        } finally {
            close();
        }
    }

    double finallyClose(DoubleSupplier ds) {
        try {
            return ds.getAsDouble();
        } finally {
            close();
        }
    }

    void finallyClose(Runnable r) {
        try {
            r.run();
        } finally {
            close();
        }
    }

    <U> U finallyClose(Supplier<U> s) {
        try {
            return s.get();
        } finally {
            close();
        }
    }

    <U> Stream<U> wrap(Stream<U> stream) {
        return wrap(stream, /*getStreamSet(),*/ AutoClosingReferenceStream::new);
    }

    IntStream wrap(IntStream stream) {
        return wrap(stream, /*getStreamSet(),*/ AutoClosingIntStream::new);
    }

    LongStream wrap(LongStream stream) {
        return wrap(stream, /*getStreamSet(),*/ AutoClosingLongStream::new);
    }

    DoubleStream wrap(DoubleStream stream) {
        return wrap(stream, /*getStreamSet(),*/ AutoClosingDoubleStream::new);
    }

    private <U> U wrap(U stream, BiFunction<U, Boolean, U> wrapper) {
        if (stream instanceof AbstractAutoClosingStream) {
            return stream; // If we already are wrapped, then do not wrap again
        }
        return wrapper.apply(stream, allowStreamIteratorAndSpliterator);
    }

    static UnsupportedOperationException newUnsupportedException(String methodName) {
        return new UnsupportedOperationException("The " + methodName + "() method is unsupported because otherwise the AutoClose property cannot be guaranteed");
    }

}
