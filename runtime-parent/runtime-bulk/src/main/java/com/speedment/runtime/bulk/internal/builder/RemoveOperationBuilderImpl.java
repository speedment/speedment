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
import com.speedment.runtime.bulk.internal.operation.RemoveOperationImpl;
import com.speedment.runtime.core.manager.Manager;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> type
 */
public final class RemoveOperationBuilderImpl<ENTITY> extends AbstractOperationBuilder<ENTITY> implements Builder.Remove<ENTITY> {

    private final List<Predicate<ENTITY>> filters;

    public RemoveOperationBuilderImpl(Manager<ENTITY> manager, BulkOperationBuilder parent) {
        super(manager, parent);
        this.filters = new ArrayList<>();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Remove<ENTITY> where(Predicate<ENTITY> filter) {
        filters.add(filter);
        return this;
    }

    @Override
    Operation<ENTITY> buildCurrent() {
        return new RemoveOperationImpl<>(manager(), filters);
    }

}
