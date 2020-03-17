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
package com.speedment.runtime.core.internal.stream.builder;

import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.internal.stream.builder.pipeline.PipelineImpl;
import com.speedment.runtime.core.internal.stream.builder.streamterminator.StreamTerminator;
import com.speedment.runtime.core.stream.ComposeRunnableUtil;
import com.speedment.runtime.core.stream.action.Action;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.Set;
import java.util.function.*;
import java.util.stream.BaseStream;

/**
 *
 * @author pemi
 * @param <T> The type of the super class
 * @param <P> Pipeline type
 */
public abstract class AbstractStreamBuilder<T extends AbstractStreamBuilder<T, P>, P> {

    private static final Logger LOGGER = LoggerManager.getLogger(AbstractStreamBuilder.class);

    protected final PipelineImpl<?> pipeline;
    protected final StreamTerminator streamTerminator;
    protected final Set<BaseStream<?, ?>> streamSet; // Keeps track of the chain of streams so that we can auto-close them all
    private final List<Runnable> closeHandlers;  // The close handlers for this particular stream
    private boolean closed;
    private boolean linkedOrConsumed;

    AbstractStreamBuilder(PipelineImpl<?> pipeline, StreamTerminator streamTerminator, Set<BaseStream<?, ?>> streamSet) {
        this.pipeline = requireNonNull(pipeline);
        this.streamTerminator = requireNonNull(streamTerminator);
        this.closeHandlers = new ArrayList<>();
        this.streamSet = streamSet;
        this.linkedOrConsumed = false;
    }

    protected T append(Action<?, ?> newAction) {
        requireNonNull(newAction);
        pipeline.add(newAction);
        return self();
    }

    public T sequential() {
        pipeline.setParallel(false);
        return self();
    }

    public T parallel() {
        pipeline.setParallel(true);
        return self();
    }

    public boolean isParallel() {
        return pipeline.isParallel();
    }

    public T unordered() {
        pipeline.setOrdered(false);
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
                    ComposeRunnableUtil.composedRunnable(closeHandlers); // Run this stream's close handlers
                }
            } catch (Exception e) {
                throw new SpeedmentException(e);
            } finally {
                ComposeRunnableUtil.composedClose(streamSet.toArray(new AutoCloseable[0])); // Close the other streams
            }
        }
    }
    
    protected void assertNotLinkedOrConsumedAndSet() {
        if (linkedOrConsumed) {
            throw new IllegalStateException("stream has already been operated upon or has been consumed");
        }
        linkedOrConsumed = true;
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

    protected boolean finallyCloseBoolean(BooleanSupplier bs) {
        try {
            return bs.getAsBoolean();
        } catch (Exception e) {
            LOGGER.error(e);
            throw e;
        } finally {
            close();
        }
    }

    protected long finallyCloseLong(LongSupplier lp) {
        try {
            return lp.getAsLong();
        } catch (Exception e) {
            LOGGER.error(e);
            throw e;
        } finally {
            close();
        }
    }

    protected int finallyCloseInt(IntSupplier is) {
        try {
            return is.getAsInt();
        } catch (Exception e) {
            LOGGER.error(e);
            throw e;
        } finally {
            close();
        }
    }

    protected double finallyCloseDouble(DoubleSupplier ds) {
        try {
            return ds.getAsDouble();
        } catch (Exception e) {
            LOGGER.error(e);
            throw e;
        } finally {
            close();
        }
    }

    protected void finallyClose(Runnable r) {
        try {
            r.run();
        } catch (Exception e) {
            LOGGER.error(e);
            throw e;
        } finally {
            close();
        }
    }

    protected <T> T finallyCloseReference(Supplier<T> s) {
        try {
            return s.get();
        } catch (Exception e) {
            LOGGER.error(e);
            throw e;
        } finally {
            close();
        }
    }

    protected static Set<BaseStream<?, ?>> newStreamSet() {
        return new HashSet<>();
    }

}