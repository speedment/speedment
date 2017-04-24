package com.speedment.runtime.core.internal.util.java9;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import static java.util.Objects.requireNonNull;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public final class Java9StreamUtil {

    private static final MethodHandle TAKE_WHILE_METHOD_HANDLE = createMethodHandle("takeWhile");
    private static final MethodHandle DROP_WHILE_METHOD_HANDLE = createMethodHandle("dropWhile");
    static final MethodHandle FILTER_METHOD_HANDLE = createMethodHandle("filter"); // Just for Java 8 testing

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
            throw new UnsupportedOperationException("Stream::takeWhile is not supported by this Java version. Use Java 9 or greater.");
        }
        try {
            final Object obj = TAKE_WHILE_METHOD_HANDLE.invoke(stream, predicate);
            return (Stream<T>) obj;
        } catch (Throwable t) {
            throw new RuntimeException(t);
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
            throw new UnsupportedOperationException("Stream::dropWhile is not supported by this Java version. Use Java 9 or greater.");
        }
        try {
            final Object obj = DROP_WHILE_METHOD_HANDLE.invoke(stream, predicate);
            return (Stream<T>) obj;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    // just for Java 8 testing
    static <T> Stream<T> filter(Stream<T> stream, Predicate<? super T> predicate) {
        requireNonNull(stream);
        requireNonNull(predicate);
        try {
            return (Stream<T>) FILTER_METHOD_HANDLE.invoke(stream, predicate);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private Java9StreamUtil() {
        throw new UnsupportedOperationException();
    }

    private static MethodHandle createMethodHandle(String methodName) {
        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        final MethodType mt = MethodType.methodType(Stream.class, Predicate.class);
        try {
            final MethodHandle mh = lookup.findVirtual(Stream.class, methodName, mt);
            return mh;
        } catch (IllegalAccessException | NoSuchMethodException e) {
            return null;
        }
    }

}
