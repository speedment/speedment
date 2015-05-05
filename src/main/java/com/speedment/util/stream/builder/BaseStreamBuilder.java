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
package com.speedment.util.stream.builder;

import com.speedment.util.stream.builder.pipeline.BasePipeline;
import com.speedment.util.stream.builder.streamterminator.StreamTerminator;
import com.speedment.util.stream.builder.action.Action;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pemi
 * @param <T> The type of the super class
 * @param <P> Pipeline type
 */
public class BaseStreamBuilder<T extends BaseStreamBuilder<T, P>, P> {

    protected final BasePipeline<?> pipeline;
    protected final StreamTerminator streamTerminator;
    private final List<Runnable> closeHandlers;
    private boolean parallel;
    private boolean ordered;

    public BaseStreamBuilder(BasePipeline<?> pipeline, StreamTerminator streamTerminator) {
        this.pipeline = pipeline;
        this.streamTerminator = streamTerminator;
        this.closeHandlers = new ArrayList<>();
        this.ordered = true;
    }

    protected T append(Action<?, ?> newAction) {
        pipeline.add(newAction);
        return thizz();
    }

    public T sequential() {
        parallel = false;
        return thizz();
    }

    public T parallel() {
        parallel = true;
        return thizz();
    }

    public boolean isParallel() {
        return parallel;
    }

    public T unordered() {
        ordered = false;
        return thizz();
    }

    public T onClose(Runnable closeHandler) {
        closeHandlers.add(closeHandler);
        return thizz();
    }

    public void close() {
        closeHandlers.forEach(Runnable::run);
    }

    public boolean isOrdered() {
        return ordered;
    }

    protected P pipeline() {
        @SuppressWarnings("unchecked")
        final P result = (P) pipeline;
        return result;
    }

    private T thizz() {
        @SuppressWarnings("unchecked")
        T thizz = (T) this;
        return thizz;
    }

}
