package com.speedment.runtime.core.stream;

import com.speedment.runtime.core.exception.SpeedmentException;

import java.util.Collection;

import static com.speedment.common.invariant.NullUtil.requireNonNullElements;
import static java.util.Objects.requireNonNull;

public final class ComposeRunnableUtil {

    private ComposeRunnableUtil() {}

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
        requireNonNullElements(closeables);
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
        requireNonNullElements(runnables);
        final AutoCloseable[] closables = new AutoCloseable[runnables.size()];
        int i = 0;
        for (final Runnable r : runnables) {
            closables[i++] = new CloseImpl(r);
        }
        composedClose(closables);
    }

    private static class CloseImpl implements AutoCloseable {

        private final Runnable r;

        private CloseImpl(Runnable r) {
            this.r = requireNonNull(r);
        }

        @Override
        public void close() { r.run(); }

    }

}
