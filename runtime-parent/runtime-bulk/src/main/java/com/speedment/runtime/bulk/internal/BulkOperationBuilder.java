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
package com.speedment.runtime.bulk.internal;

import com.speedment.runtime.bulk.BulkOperation;
import com.speedment.runtime.bulk.Operation;
import com.speedment.runtime.bulk.internal.builder.PersistOperationBuilderImpl;
import com.speedment.runtime.bulk.internal.builder.RemoveOperationBuilderImpl;
import com.speedment.runtime.bulk.internal.builder.UpdateOperationBuilderImpl;
import com.speedment.runtime.core.manager.Manager;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
public final class BulkOperationBuilder implements BulkOperation.Builder {

    private final List<Operation<?>> operations;

    public BulkOperationBuilder() {
        this.operations = new ArrayList<>();
    }

    @Override
    public <ENTITY> Persist<ENTITY> persist(Manager<ENTITY> manager) {
        return new PersistOperationBuilderImpl<>(requireNonNull(manager), this);
    }

    @Override
    public <ENTITY> Update<ENTITY> update(Manager<ENTITY> manager) {
        return new UpdateOperationBuilderImpl<>(requireNonNull(manager), this);
    }

    @Override
    public <ENTITY> Remove<ENTITY> remove(Manager<ENTITY> manager) {
        return new RemoveOperationBuilderImpl<>(requireNonNull(manager), this);
    }

    @Override
    public BulkOperation build() {
        return new BulkOperationImpl(operations);
    }

    public <ENTITY> void add(Operation<ENTITY> operation) {
        operations.add(operation);
    }

}
