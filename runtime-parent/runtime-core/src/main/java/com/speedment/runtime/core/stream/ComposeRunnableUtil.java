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
                        // No op
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
