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

import com.speedment.runtime.bulk.BulkOperation;
import com.speedment.runtime.bulk.BulkOperation.Builder;
import com.speedment.runtime.bulk.Operation;
import com.speedment.runtime.bulk.internal.BulkOperationBuilder;
import com.speedment.runtime.core.manager.Manager;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> type
 */
public abstract class AbstractOperationBuilder<ENTITY> implements Builder {

    private final BulkOperationBuilder parent;
    private final Manager<ENTITY> manager;

    protected AbstractOperationBuilder(Manager<ENTITY> manager, BulkOperationBuilder parent) {
        this.manager = requireNonNull(manager);
        this.parent = requireNonNull(parent);
    }

    Manager<ENTITY> manager() {
        return manager;
    }

    BulkOperationBuilder parent() {
        return parent;
    }

    @Override
    public <NEXT_ENTITY> Persist<NEXT_ENTITY> persist(Manager<NEXT_ENTITY> manager) {
        requireNonNull(manager);
        parent().add(buildCurrent());
        return new PersistOperationBuilderImpl<>(manager, parent());
    }

    @Override
    public <NEXT_ENTITY> Update<NEXT_ENTITY> update(Manager<NEXT_ENTITY> manager) {
        requireNonNull(manager);
        parent().add(buildCurrent());
        return new UpdateOperationBuilderImpl<>(manager, parent());
    }

    @Override
    public <NEXT_ENTITY> Remove<NEXT_ENTITY> remove(Manager<NEXT_ENTITY> manager) {
        requireNonNull(manager);
        parent().add(buildCurrent());
        return new RemoveOperationBuilderImpl<>(manager, parent());
    }

    @Override
    public BulkOperation build() {
        parent().add(buildCurrent());
        return parent().build();
    }

    abstract Operation<ENTITY> buildCurrent();

}
