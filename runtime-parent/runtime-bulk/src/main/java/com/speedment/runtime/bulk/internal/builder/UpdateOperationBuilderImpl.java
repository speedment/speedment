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
package com.speedment.runtime.bulk.internal.builder;

import com.speedment.runtime.bulk.BulkOperation.Builder;
import com.speedment.runtime.bulk.Operation;
import com.speedment.runtime.bulk.internal.BulkOperationBuilder;
import com.speedment.runtime.bulk.internal.operation.UpdateOperationImpl;
import com.speedment.runtime.core.manager.Manager;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> type
 */
public final class UpdateOperationBuilderImpl<ENTITY> extends AbstractOperationBuilder<ENTITY> implements Builder.Update<ENTITY> {

    private final List<Predicate<ENTITY>> filters;
    private final List<Function<? super ENTITY, ? extends ENTITY>> mappers;
    private final List<Consumer<? super ENTITY>> consumers;

    public UpdateOperationBuilderImpl(Manager<ENTITY> manager, BulkOperationBuilder parent) {
        super(manager, parent);
        this.filters = new ArrayList<>();
        this.mappers = new ArrayList<>();
        this.consumers = new ArrayList<>();
    }

    @Override
    public Update<ENTITY> where(Predicate<ENTITY> filter) {
        filters.add(filter);
        return this;
    }

    @Override
    public Update<ENTITY> set(Consumer<? super ENTITY> consumer) {
        consumers.add(consumer);
        return this;
    }

    @Override
    public Update<ENTITY> compute(Function<? super ENTITY, ? extends ENTITY> mapper) {
        mappers.add(mapper);
        return this;
    }

    @Override
    Operation<ENTITY> buildCurrent() {
        return new UpdateOperationImpl<>(manager(), filters, mappers, consumers);
    }

}
