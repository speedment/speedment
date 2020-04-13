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
package com.speedment.runtime.core.stream.java9;

import com.speedment.runtime.core.exception.SpeedmentException;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import static java.util.Objects.requireNonNull;
import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public final class Java9StreamUtil {

    private Java9StreamUtil() {}

    private static final String TAKE_WHILE = "takeWhile";
    private static final String DROP_WHILE = "dropWhile";
    private static final String FILTER = "filter";

    private static final MethodType DOUBLE_METHOD_TYPE = MethodType.methodType(DoubleStream.class, DoublePredicate.class);
    private static final MethodHandle DOUBLE_TAKE_WHILE_METHOD_HANDLE
        = createMethodHandle(TAKE_WHILE, DoubleStream.class, DOUBLE_METHOD_TYPE);

    private static final MethodHandle DOUBLE_DROP_WHILE_METHOD_HANDLE
        = createMethodHandle(DROP_WHILE, DoubleStream.class, DOUBLE_METHOD_TYPE);

    static final MethodHandle DOUBLE_FILTER_METHOD_HANDLE
        = createMethodHandle(FILTER, DoubleStream.class, DOUBLE_METHOD_TYPE); // Just for Java 8 testing

    private static final MethodType INT_METHOD_TYPE = MethodType.methodType(IntStream.class, IntPredicate.class);
    private static final MethodHandle INT_TAKE_WHILE_METHOD_HANDLE
        = createMethodHandle(TAKE_WHILE, IntStream.class, INT_METHOD_TYPE);
    private static final MethodHandle INT_DROP_WHILE_METHOD_HANDLE
        = createMethodHandle(DROP_WHILE, IntStream.class, INT_METHOD_TYPE);
    static final MethodHandle INT_FILTER_METHOD_HANDLE
        = createMethodHandle(FILTER, IntStream.class, INT_METHOD_TYPE); // Just for Java 8 testing

    private static final MethodType LONG_METHOD_TYPE = MethodType.methodType(LongStream.class, LongPredicate.class);
    private static final MethodHandle LONG_TAKE_WHILE_METHOD_HANDLE
        = createMethodHandle(TAKE_WHILE, LongStream.class, LONG_METHOD_TYPE);
    private static final MethodHandle LONG_DROP_WHILE_METHOD_HANDLE
        = createMethodHandle(DROP_WHILE, LongStream.class, LONG_METHOD_TYPE);
    static final MethodHandle LONG_FILTER_METHOD_HANDLE
        = createMethodHandle(FILTER, LongStream.class, LONG_METHOD_TYPE); // Just for Java 8 testing

    private static final MethodType METHOD_TYPE
        = MethodType.methodType(Stream.class, Predicate.class);
    private static final MethodHandle TAKE_WHILE_METHOD_HANDLE
        = createMethodHandle(TAKE_WHILE, Stream.class, METHOD_TYPE);
    private static final MethodHandle DROP_WHILE_METHOD_HANDLE
        = createMethodHandle(DROP_WHILE, Stream.class, METHOD_TYPE);
    static final MethodHandle FILTER_METHOD_HANDLE
        = createMethodHandle(FILTER, Stream.class, METHOD_TYPE); // Just for Java 8 testing

    /**
     * Delegates a DoubleStream::takeWhile operation to the Java platforms
     * underlying default Stream implementation. If run under Java 8, this
     * method will throw an UnsupportedOperationException.
     *
     * @param stream to apply the takeWhile operation to
     * @param predicate to use for takeWhile
     * @throws UnsupportedOperationException if run under Java 8
     * @return a Stream where the takeWhile(predicate) has been applied
     */
    @SuppressWarnings("unchecked")
    public static DoubleStream takeWhile(DoubleStream stream, DoublePredicate predicate) {
        requireNonNull(stream);
        requireNonNull(predicate);
        if (DOUBLE_TAKE_WHILE_METHOD_HANDLE == null) {
            throw newUnsupportedOperationException(TAKE_WHILE);
        }
        try {
            final Object obj = DOUBLE_TAKE_WHILE_METHOD_HANDLE.invoke(stream, predicate);
            return (DoubleStream) obj;
        } catch (Throwable t) {
            throw new SpeedmentException(t);
        }
    }

    /**
     * Delegates a DoubleStream::dropWhile operation to the Java platforms
     * underlying default Stream implementation. If run under Java 8, this
     * method will throw an UnsupportedOperationException.
     *
     * @param stream to apply the dropWhile operation to
     * @param predicate to use for dropWhile
     * @throws UnsupportedOperationException if run under Java 8
     * @return a Stream where the dropWhile(predicate) has been applied
     */
    @SuppressWarnings("unchecked")
    public static DoubleStream dropWhile(DoubleStream stream, DoublePredicate predicate) {
        requireNonNull(stream);
        requireNonNull(predicate);
        if (DOUBLE_DROP_WHILE_METHOD_HANDLE == null) {
            throw newUnsupportedOperationException(DROP_WHILE);
        }
        try {
            final Object obj = DOUBLE_DROP_WHILE_METHOD_HANDLE.invoke(stream, predicate);
            return (DoubleStream) obj;
        } catch (Throwable t) {
            throw new SpeedmentException(t);
        }
    }

    // just for Java 8 testing
    static DoubleStream filter(DoubleStream stream, DoublePredicate predicate) {
        requireNonNull(stream);
        requireNonNull(predicate);
        try {
            return (DoubleStream) DOUBLE_FILTER_METHOD_HANDLE.invoke(stream, predicate);
        } catch (Throwable t) {
            throw new SpeedmentException(t);
        }
    }

    /**
     * Delegates an IntStream::takeWhile operation to the Java platforms
     * underlying default Stream implementation. If run under Java 8, this
     * method will throw an UnsupportedOperationException.
     *
     * @param stream to apply the takeWhile operation to
     * @param predicate to use for takeWhile
     * @throws UnsupportedOperationException if run under Java 8
     * @return a Stream where the takeWhile(predicate) has been applied
     */
    @SuppressWarnings("unchecked")
    public static IntStream takeWhile(IntStream stream, IntPredicate predicate) {
        requireNonNull(stream);
        requireNonNull(predicate);
        if (INT_TAKE_WHILE_METHOD_HANDLE == null) {
            throw newUnsupportedOperationException(TAKE_WHILE);
        }
        try {
            final Object obj = INT_TAKE_WHILE_METHOD_HANDLE.invoke(stream, predicate);
            return (IntStream) obj;
        } catch (Throwable t) {
            throw new SpeedmentException(t);
        }
    }

    /**
     * Delegates an InteStream::dropWhile operation to the Java platforms
     * underlying default Stream implementation. If run under Java 8, this
     * method will throw an UnsupportedOperationException.
     *
     * @param stream to apply the dropWhile operation to
     * @param predicate to use for dropWhile
     * @throws UnsupportedOperationException if run under Java 8
     * @return a Stream where the dropWhile(predicate) has been applied
     */
    @SuppressWarnings("unchecked")
    public static IntStream dropWhile(IntStream stream, IntPredicate predicate) {
        requireNonNull(stream);
        requireNonNull(predicate);
        if (INT_DROP_WHILE_METHOD_HANDLE == null) {
            throw newUnsupportedOperationException(DROP_WHILE);
        }
        try {
            final Object obj = INT_DROP_WHILE_METHOD_HANDLE.invoke(stream, predicate);
            return (IntStream) obj;
        } catch (Throwable t) {
            throw new SpeedmentException(t);
        }
    }

    // just for Java 8 testing
    static IntStream filter(IntStream stream, IntPredicate predicate) {
        requireNonNull(stream);
        requireNonNull(predicate);
        try {
            return (IntStream) INT_FILTER_METHOD_HANDLE.invoke(stream, predicate);
        } catch (Throwable t) {
            throw new SpeedmentException(t);
        }
    }

    /**
     * Delegates a LongStream::takeWhile operation to the Java platforms
     * underlying default Stream implementation. If run under Java 8, this
     * method will throw an UnsupportedOperationException.
     *
     * @param stream to apply the takeWhile operation to
     * @param predicate to use for takeWhile
     * @throws UnsupportedOperationException if run under Java 8
     * @return a Stream where the takeWhile(predicate) has been applied
     */
    @SuppressWarnings("unchecked")
    public static LongStream takeWhile(LongStream stream, LongPredicate predicate) {
        requireNonNull(stream);
        requireNonNull(predicate);
        if (LONG_TAKE_WHILE_METHOD_HANDLE == null) {
            throw newUnsupportedOperationException(TAKE_WHILE);
        }
        try {
            final Object obj = LONG_TAKE_WHILE_METHOD_HANDLE.invoke(stream, predicate);
            return (LongStream) obj;
        } catch (Throwable t) {
            throw new SpeedmentException(t);
        }
    }

    /**
     * Delegates a LongStream::dropWhile operation to the Java platforms
     * underlying default Stream implementation. If run under Java 8, this
     * method will throw an UnsupportedOperationException.
     *
     * @param stream to apply the dropWhile operation to
     * @param predicate to use for dropWhile
     * @throws UnsupportedOperationException if run under Java 8
     * @return a Stream where the dropWhile(predicate) has been applied
     */
    @SuppressWarnings("unchecked")
    public static LongStream dropWhile(LongStream stream, LongPredicate predicate) {
        requireNonNull(stream);
        requireNonNull(predicate);
        if (LONG_DROP_WHILE_METHOD_HANDLE == null) {
            throw newUnsupportedOperationException(DROP_WHILE);
        }
        try {
            final Object obj = LONG_DROP_WHILE_METHOD_HANDLE.invoke(stream, predicate);
            return (LongStream) obj;
        } catch (Throwable t) {
            throw new SpeedmentException(t);
        }
    }

    // just for Java 8 testing
    static LongStream filter(LongStream stream, LongPredicate predicate) {
        requireNonNull(stream);
        requireNonNull(predicate);
        try {
            return (LongStream) LONG_FILTER_METHOD_HANDLE.invoke(stream, predicate);
        } catch (Throwable t) {
            throw new SpeedmentException(t);
        }
    }

    /**
     * Delegates a Stream::takeWhile operation to the Java platforms underlying
     * default Stream implementation. If run under Java 8, this method will
     * throw an UnsupportedOperationException.
     *
     * @param <T> Element type in the Stream
     * @param stream to apply the takeWhile operation to
     * @param predicate to use for takeWhile
     * @throws UnsupportedOperationException if run under Java 8
     * @return a Stream where the takeWhile(predicate) has been applied
     */
    @SuppressWarnings("unchecked")
    public static <T> Stream<T> takeWhile(Stream<T> stream, Predicate<? super T> predicate) {
        requireNonNull(stream);
        requireNonNull(predicate);
        if (TAKE_WHILE_METHOD_HANDLE == null) {
            throw newUnsupportedOperationException(TAKE_WHILE);
        }
        try {
            final Object obj = TAKE_WHILE_METHOD_HANDLE.invoke(stream, predicate);
            return (Stream<T>) obj;
        } catch (Throwable t) {
            throw new SpeedmentException(t);
        }
    }

    /**
     * Delegates a Stream::dropWhile operation to the Java platforms underlying
     * default Stream implementation. If run under Java 8, this method will
     * throw an UnsupportedOperationException.
     *
     * @param <T> Element type in the Stream
     * @param stream to apply the dropWhile operation to
     * @param predicate to use for dropWhile
     * @throws UnsupportedOperationException if run under Java 8
     * @return a Stream where the dropWhile(predicate) has been applied
     */
    @SuppressWarnings("unchecked")
    public static <T> Stream<T> dropWhile(Stream<T> stream, Predicate<? super T> predicate) {
        requireNonNull(stream);
        requireNonNull(predicate);
        if (DROP_WHILE_METHOD_HANDLE == null) {
            throw newUnsupportedOperationException(DROP_WHILE);
        }
        try {
            final Object obj = DROP_WHILE_METHOD_HANDLE.invoke(stream, predicate);
            return (Stream<T>) obj;
        } catch (Throwable t) {
            throw new SpeedmentException(t);
        }
    }

    // just for Java 8 testing
    static <T> Stream<T> filter(Stream<T> stream, Predicate<? super T> predicate) {
        requireNonNull(stream);
        requireNonNull(predicate);
        try {
            @SuppressWarnings("unchecked")
            final Stream<T> s = (Stream<T>) FILTER_METHOD_HANDLE.invoke(stream, predicate);
            return s;
        } catch (Throwable t) {
            throw new SpeedmentException(t);
        }
    }

    private static MethodHandle createMethodHandle(String methodName, Class<?> refc, MethodType methodType) {
        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            return lookup.findVirtual(refc, methodName, methodType);
        } catch (IllegalAccessException | NoSuchMethodException e) {
            // We are running under Java 8
            return null;
        }
    }

    private static UnsupportedOperationException newUnsupportedOperationException(String methodName) {
        return new UnsupportedOperationException("Stream::" + methodName + " is not supported by this Java version. Use Java 9 or greater.");
    }

}
