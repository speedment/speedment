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
package com.speedment.internal.core.stream.autoclose;

import com.speedment.exception.SpeedmentException;
import static com.speedment.internal.util.NullUtil.requireNonNulls;
import com.speedment.internal.util.holder.Holder;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public abstract class AbstractAutoClosingStream implements AutoCloseable {

    private final Set<BaseStream<?, ?>> streamSet;

    protected AbstractAutoClosingStream(Set<BaseStream<?, ?>> streamSet) {
        this.streamSet = streamSet;
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
            composedClose(streamsToClose.toArray(new BaseStream<?, ?>[0]));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            getStream().close(); // Close the underlying stream
        }
    }

    protected <T> boolean finallyClose(BooleanSupplier bs) {
        try {
            return bs.getAsBoolean();
        } finally {
            close();
        }
    }

    protected <T> long finallyClose(LongSupplier lp) {
        try {
            return lp.getAsLong();
        } finally {
            close();
        }
    }

    protected <T> int finallyClose(IntSupplier is) {
        try {
            return is.getAsInt();
        } finally {
            close();
        }
    }

    protected <T> double finallyClose(DoubleSupplier ds) {
        try {
            return ds.getAsDouble();
        } finally {
            close();
        }
    }

    protected <T> void finallyClose(Runnable r) {
        try {
            r.run();
        } finally {
            close();
        }
    }

    protected <T> T finallyClose(Supplier<T> s) {
        try {
            return s.get();
        } finally {
            close();
        }
    }

    public <T> Stream<T> wrap(Stream<T> stream) {
        return wrap(stream, getStreamSet(), AutoClosingReferenceStream::new);
    }

    public IntStream wrap(IntStream stream) {
        return wrap(stream, getStreamSet(), AutoClosingIntStream::new);
    }

    public LongStream wrap(LongStream stream) {
        return wrap(stream, getStreamSet(), AutoClosingLongStream::new);
    }

    public DoubleStream wrap(DoubleStream stream) {
        return wrap(stream, getStreamSet(), AutoClosingDoubleStream::new);
    }

    private <T> T wrap(T stream, Set<BaseStream<?, ?>> streamSet, BiFunction<T, Set<BaseStream<?, ?>>, T> wrapper) {
        if (stream instanceof AbstractAutoClosingStream) {
            return stream; // If we allready are wrapped, then do not wrap again
        }
        return wrapper.apply(stream, streamSet);
    }

    public static UnsupportedOperationException newUnsupportedException(String methodName) {
        return new UnsupportedOperationException("The " + methodName + "() method is unsupported because otherwise the AutoClose property cannot be guaranteed");
    }

    protected static Set<BaseStream<?, ?>> newSet() {
        return new HashSet<>();
    }

    /**
     * Given a number of streams, closes the streams in sequence, even if one or
     * several throws an Exception. If several throw exceptions, the exceptions
     * will be added to the first exception.
     *
     * @param <T> Stream type
     * @param closeables to close
     * @throws SpeedmentException if at least one of the close() operations
     * throws an exception
     */
    @SafeVarargs // Creating a Stream of an array is safe.
    @SuppressWarnings({"unchecked", "varargs"})
    public static <T extends AutoCloseable> void composedClose(T... closeables) {
        requireNonNulls(closeables);
        Exception exception = null;

        for (final T closable : closeables) {
            try {
                closable.close();
            } catch (Exception e) {
                if (exception == null) {
                    exception = e;
                } else {
                    try {
                        exception.addSuppressed(e);
                    } catch (Exception ignored) {
                    }
                }
            }
        }

        if (exception != null) {
            throw new SpeedmentException(exception);
        }
    }

    /**
     * Given a number of Runnables, runs the run() method in sequence, even if
     * one or several throws an Exception. If several throw exceptions, the
     * exceptions will be added to the first exception.
     *
     * @param runnables to close
     * @throws SpeedmentException if at least one of the run() operations throws
     * an exception
     */
    public static void composedRunnable(Collection<Runnable> runnables) {
        requireNonNulls(runnables);
        final AutoCloseable[] closables = new AutoCloseable[runnables.size()];
        int i = 0;
        for (final Runnable r : runnables) {
            closables[i++] = new CloseImpl(r);
        }
        composedClose(closables);
    }

    private static class CloseImpl implements AutoCloseable {

        private final Runnable r;

        public CloseImpl(Runnable r) {
            this.r = r;
        }

        @Override
        public void close() {

                r.run();

        }

    }

}
