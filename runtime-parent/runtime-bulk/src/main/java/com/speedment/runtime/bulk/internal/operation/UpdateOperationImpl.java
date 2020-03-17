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
package com.speedment.runtime.bulk.internal.operation;

import com.speedment.runtime.bulk.UpdateOperation;
import com.speedment.runtime.core.manager.Manager;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> type
 */
public final class UpdateOperationImpl<ENTITY> extends AbstractOperation<ENTITY> implements UpdateOperation<ENTITY> {

    private final List<Predicate<ENTITY>> predicates;
    private final List<Function<? super ENTITY, ? extends ENTITY>> mappers;
    private final List<Consumer<? super ENTITY>> consumers;

    public UpdateOperationImpl(
        final Manager<ENTITY> manager,
        final List<Predicate<ENTITY>> predicates,
        final List<Function<? super ENTITY, ? extends ENTITY>> mappers,
        final List<Consumer<? super ENTITY>> consumers
    ) {
        super(Type.UPDATE, manager);
        this.predicates = new ArrayList<>(requireNonNull(predicates));
        this.mappers = new ArrayList<>(requireNonNull(mappers));
        this.consumers = new ArrayList<>(requireNonNull(consumers));
    }

    @Override
    public Stream<Predicate<ENTITY>> predicates() {
        return predicates.stream();
    }

    @Override
    public Stream<Consumer<? super ENTITY>> consumers() {
        return consumers.stream();
    }

    @Override
    public Stream<Function<? super ENTITY, ? extends ENTITY>> mappers() {
        return mappers.stream();
    }

}
