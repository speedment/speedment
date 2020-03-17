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
package com.speedment.runtime.core.internal.component.transaction;

import com.speedment.runtime.core.component.transaction.DataSourceHandler;
import com.speedment.runtime.core.component.transaction.Isolation;
import static java.util.Objects.requireNonNull;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 *
 * @author Per Minborg
 */
public final class DataSourceHandlerImpl<D, T> implements DataSourceHandler<D, T> {

    private final Function<? super D, ? extends T> extractor;
    private final BiFunction<? super T, Isolation, Isolation> isolationConfigurator;
    private final Consumer<? super T> beginner;
    private final Consumer<? super T> committer;
    private final Consumer<? super T> rollbacker;
    private final Consumer<? super T> closer;

    public DataSourceHandlerImpl(
        final Function<? super D, ? extends T> extractor,
        final BiFunction<? super T, Isolation, Isolation> isolationConfigurator,
        final Consumer<? super T> beginner,
        final Consumer<? super T> committer,
        final Consumer<? super T> rollbacker,
        final Consumer<? super T> closer
    ) {
        this.extractor = requireNonNull(extractor);
        this.isolationConfigurator = requireNonNull(isolationConfigurator);
        this.beginner = requireNonNull(beginner);
        this.committer = requireNonNull(committer);
        this.rollbacker = requireNonNull(rollbacker);
        this.closer = requireNonNull(closer);
    }

    @Override
    public Function<? super D, ? extends T> extractor() {
        return extractor;
    }

    @Override
    public BiFunction<? super T, Isolation, Isolation> isolationConfigurator() {
        return isolationConfigurator;
    }

    @Override
    public Consumer<? super T> beginner() {
        return beginner;
    }

    @Override
    public Consumer<? super T> committer() {
        return committer;
    }

    @Override
    public Consumer<? super T> rollbacker() {
        return rollbacker;
    }

    @Override
    public Consumer<? super T> closer() {
        return closer;
    }

}
