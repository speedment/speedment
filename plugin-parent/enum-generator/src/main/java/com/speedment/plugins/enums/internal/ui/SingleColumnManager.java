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
package com.speedment.plugins.enums.internal.ui;

import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.Config;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.common.singletonstream.SingletonStream;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.ManagerComponent;
import com.speedment.runtime.core.component.StreamSupplierComponent;
import com.speedment.runtime.core.manager.*;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.StringField;
import com.speedment.runtime.typemapper.TypeMapper;

import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

final class SingleColumnManager implements Manager<String> {

    private final StreamSupplierComponent streamSupplierComponent;
    private final StringField<String, String> field;
    private final TableIdentifier<String> tableId;

    SingleColumnManager(
        final StreamSupplierComponent streamSupplierComponent,
        @Config(name="temp.dbms", value="") final String dbms,
        @Config(name="temp.schema", value="") final String schema,
        @Config(name="temp.table", value="") final String table,
        @Config(name="temp.column", value="") final String column
    ) {
        this.streamSupplierComponent = requireNonNull(streamSupplierComponent);
        requireNonNull(dbms);
        requireNonNull(schema);
        requireNonNull(table);
        requireNonNull(column);
        this.tableId = TableIdentifier.of(dbms, schema, table);
        this.field   = StringField.create(
            new TempColumnIdentifier(dbms, schema, table, column),
            e -> e, (e, s) -> {},
            TypeMapper.identity(),
            false
        );
    }

    @ExecuteBefore(State.INITIALIZED)
    public void configureManagerComponent(@WithState(State.INITIALIZED) ManagerComponent managerComponent) {
        managerComponent.put(this);
    }

    @Override
    public TableIdentifier<String> getTableIdentifier() {
        return tableId;
    }

    @Override
    public Class<String> getEntityClass() {
        return String.class;
    }

    @Override
    public Stream<Field<String>> fields() {
        return SingletonStream.of(field);
    }

    @Override
    public Stream<Field<String>> primaryKeyFields() {
        return SingletonStream.of(field);
    }

    @Override
    public Stream<String> stream() {
        return streamSupplierComponent.stream(
            getTableIdentifier(),
            ParallelStrategy.computeIntensityDefault()
        );
    }

    @Override
    public String create() {
        throw newUnsupportedOperationExceptionReadOnly();
    }

    @Override
    public final String persist(String entity) {
        throw newUnsupportedOperationExceptionReadOnly();
    }

    @Override
    public Persister<String> persister() {
        throw newUnsupportedOperationExceptionReadOnly();
    }

    @Override
    public Persister<String> persister(HasLabelSet<String> fields) {
        throw newUnsupportedOperationExceptionReadOnly();
    }

    @Override
    public final String update(String entity) {
        throw newUnsupportedOperationExceptionReadOnly();
    }

    @Override
    public Updater<String> updater() {
        throw newUnsupportedOperationExceptionReadOnly();
    }

    @Override
    public Updater<String> updater(HasLabelSet<String> fields) {
        throw newUnsupportedOperationExceptionReadOnly();
    }

    @Override
    public final String remove(String entity) {
        throw newUnsupportedOperationExceptionReadOnly();
    }

    @Override
    public Remover<String> remover() {
        throw newUnsupportedOperationExceptionReadOnly();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{tableId: " +
            tableId.toString()
            + "}";
    }

    private static UnsupportedOperationException newUnsupportedOperationExceptionReadOnly() {
        return new UnsupportedOperationException("This manager is read-only.");
    }

}
