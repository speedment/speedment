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
package com.speedment.runtime.core.component.transaction;

import com.speedment.runtime.core.internal.component.transaction.DataSourceHandlerImpl;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 *
 * @author Per Minborg
 * @param <D> DataSource type (e.g. Dbms)
 * @param <T> Transaction aware object (e.g. Connection)
 * @since 3.0.17
 */
public interface DataSourceHandler<D, T> {

    Function<? super D, ? extends T> extractor();

    /**
     * Returns a BiFunction that, when applied, sets a new Isolation level and
     * then returns the previous Isolation level.
     *
     * @return a BiFunction that, when applied, sets a new Isolation level and
     * then returns the previous Isolation level
     */
    BiFunction<? super T, Isolation, Isolation> isolationConfigurator();

    Consumer<? super T> beginner();

    Consumer<? super T> rollbacker();

    Consumer<? super T> committer();

    Consumer<? super T> closer();

    static <D, T> DataSourceHandler<D, T> of(
        final Function<D, T> extractor,
        final BiFunction<? super T, Isolation, Isolation> isolationConfigurator,
        final Consumer<? super T> beginner,
        final Consumer<? super T> committer,
        final Consumer<? super T> rollbacker,
        final Consumer<? super T> closer
    ) {
        return new DataSourceHandlerImpl<>(extractor, isolationConfigurator, beginner, committer, rollbacker, closer);
    }

}
