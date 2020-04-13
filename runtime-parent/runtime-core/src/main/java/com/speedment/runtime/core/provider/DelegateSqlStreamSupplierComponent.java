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
package com.speedment.runtime.core.provider;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Config;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.ManagerComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.component.StreamSupplierComponent;
import com.speedment.runtime.core.component.sql.SqlStreamOptimizerComponent;
import com.speedment.runtime.core.component.sql.SqlStreamSupplierComponent;
import com.speedment.runtime.core.component.sql.override.SqlStreamTerminatorComponent;
import com.speedment.runtime.core.internal.component.sql.SqlStreamSupplierComponentImpl;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;
import com.speedment.runtime.field.trait.HasComparableOperators;

import java.util.Optional;
import java.util.stream.Stream;

import static com.speedment.common.injector.State.STARTED;
import static com.speedment.common.injector.State.STOPPED;

/**
 * The default implementation of the
 * {@link SqlStreamSupplierComponent}-interface.
 *
 * @author Per Minborg
 * @since 3.0.1
 */
public final class DelegateSqlStreamSupplierComponent implements SqlStreamSupplierComponent {

    private final SqlStreamSupplierComponentImpl inner;

    public DelegateSqlStreamSupplierComponent(
        @Config(name = "allowStreamIteratorAndSpliterator", value = "false") final boolean allowStreamIteratorAndSpliterator
    ) {
        this.inner = new SqlStreamSupplierComponentImpl(allowStreamIteratorAndSpliterator);
    }

    @ExecuteBefore(STARTED)
    public void startStreamSuppliers(Injector injector, ProjectComponent projectComponent, DbmsHandlerComponent dbmsHandlerComponent, ManagerComponent managerComponent, SqlStreamOptimizerComponent sqlStreamOptimizerComponent, SqlStreamTerminatorComponent sqlStreamTerminatorComponent) {
        inner.startStreamSuppliers(injector, projectComponent, dbmsHandlerComponent, managerComponent, sqlStreamOptimizerComponent, sqlStreamTerminatorComponent);
    }

    @ExecuteBefore(STOPPED)
    @Override
    public void stop() {
        inner.stop();
    }

    @Override
    public <ENTITY> Stream<ENTITY> stream(TableIdentifier<ENTITY> tableIdentifier, ParallelStrategy parallelStrategy) {
        return inner.stream(tableIdentifier, parallelStrategy);
    }

    @Override
    public <ENTITY> Stream<ENTITY> stream(TableIdentifier<ENTITY> tableId) {
        return inner.stream(tableId);
    }

    @Override
    public <ENTITY, V extends Comparable<? super V>> Optional<ENTITY> findAny(TableIdentifier<ENTITY> tableIdentifier, HasComparableOperators<ENTITY, V> field, V value) {
        return inner.findAny(tableIdentifier, field, value);
    }

    @Override
    public boolean isImmutable() {
        return inner.isImmutable();
    }

    @Override
    public void start() {
        inner.start();
    }

    @Override
    public Stream<StreamSupplierComponent> sourceStreamSupplierComponents() {
        return inner.sourceStreamSupplierComponents();
    }
}
