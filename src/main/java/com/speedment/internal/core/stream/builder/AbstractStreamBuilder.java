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
package com.speedment.internal.core.stream.builder;

import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.stream.autoclose.AbstractAutoClosingStream;
import com.speedment.internal.core.stream.builder.pipeline.PipelineImpl;
import com.speedment.internal.core.stream.builder.streamterminator.StreamTerminator;
import com.speedment.internal.core.stream.builder.action.Action;
import com.speedment.util.StreamComposition;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.stream.BaseStream;

/**
 *
 * @author pemi
 * @param <T> The type of the super class
 * @param <P> Pipeline type
 */
public abstract class AbstractStreamBuilder<T extends AbstractStreamBuilder<T, P>, P> {

    protected final String UNSUPPORTED_BECAUSE_OF_CLOSE_MAY_NOT_BE_CALLED = "This method has been disabled for this Stream type because improper use will "
        + "lead to resources not being freed up. "
        + "We regret any inconvenience caused by this. "
        + "If you want to concatenate two or more stream, please use the " + StreamComposition.class.getName() + "#concatAndAutoClose() method instead.";

    protected final PipelineImpl<?> pipeline;
    protected final StreamTerminator streamTerminator;
    protected final Set<BaseStream<?, ?>> streamSet; // Keeps track of the chain of streams so that we can auto-close them all
    private final List<Runnable> closeHandlers;  // The close handlers for this particular stream
    private boolean parallel;
    private boolean ordered;
    private boolean closed;

    protected AbstractStreamBuilder(PipelineImpl<?> pipeline, StreamTerminator streamTerminator, Set<BaseStream<?, ?>> streamSet) {
        this.pipeline = requireNonNull(pipeline);
        this.streamTerminator = requireNonNull(streamTerminator);
        this.closeHandlers = new ArrayList<>();
        this.ordered = true;
        this.streamSet = streamSet;
    }

    protected T append(Action<?, ?> newAction) {
        requireNonNull(newAction);
        pipeline.add(newAction);
        return self();
    }

    public T sequential() {
        parallel = false;
        return self();
    }

    public T parallel() {
        parallel = true;
        return self();
    }

    public boolean isParallel() {
        return parallel;
    }

    public T unordered() {
        ordered = false;
        return self();
    }

    public T onClose(Runnable closeHandler) {
        requireNonNull(closeHandler);
        closeHandlers.add(closeHandler);
        return self();
    }

    public void close() {
        if (!closed) {
            closed = true;
            try {
                if (!closeHandlers.isEmpty()) {
                    AbstractAutoClosingStream.composedRunnable(closeHandlers); // Run this stream's close handlers
                }
            } catch (Exception e) {
                throw new SpeedmentException(e);
            } finally {
                try {
                    AbstractAutoClosingStream.composedClose(streamSet.toArray(new AutoCloseable[0])); // Close the other streams
                } catch (Exception e) {
                    throw new SpeedmentException(e);
                }
            }
        }
    }

    public boolean isOrdered() {
        return ordered;
    }

    protected P pipeline() {
        @SuppressWarnings("unchecked")
        final P result = (P) pipeline;
        return result;
    }

    private T self() {
        @SuppressWarnings("unchecked")
        final T thizz = (T) this;
        return thizz;
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

    protected static Set<BaseStream<?, ?>> newStreamSet() {
        return new HashSet<>();
    }

}
