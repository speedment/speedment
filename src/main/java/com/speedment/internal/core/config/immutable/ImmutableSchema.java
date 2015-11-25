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
package com.speedment.internal.core.config.immutable;

import com.speedment.internal.core.config.*;
import com.speedment.config.Dbms;
import com.speedment.config.Schema;
import com.speedment.config.Table;
import com.speedment.config.aspects.Nameable;
import com.speedment.config.aspects.Parent;
import com.speedment.config.parameters.ColumnCompressionType;
import com.speedment.config.parameters.FieldStorageType;
import com.speedment.config.parameters.StorageEngineType;
import groovy.lang.Closure;
import java.util.Optional;
import static com.speedment.internal.core.config.immutable.ImmutableUtil.throwNewUnsupportedOperationExceptionImmutable;
import static java.util.Objects.requireNonNull;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public final class ImmutableSchema extends ImmutableAbstractNamedConfigEntity implements Schema, ImmutableParentHelper<Table> {

    private final Optional<Dbms> parent;
    private final ChildHolder<Table> children;
    private final boolean defaultSchema;
    private final Optional<String> schemaName;
    private final Optional<String> catalogName;
    private final FieldStorageType fieldStorageType;
    private final ColumnCompressionType columnCompressionType;
    private final StorageEngineType storageEngineType;

    public ImmutableSchema(Dbms parent, Schema schema) {
        super(requireNonNull(schema).getName(), schema.isExpanded(), schema.isEnabled());
        // Fields
        this.parent = Optional.of(parent);
        this.defaultSchema = schema.isDefaultSchema();
        this.schemaName = schema.getSchemaName();
        this.catalogName = schema.getCatalogName();
        this.fieldStorageType = schema.getFieldStorageType();
        this.columnCompressionType = schema.getColumnCompressionType();
        this.storageEngineType = schema.getStorageEngineType();
        //Children
        children = childHolderOf(Table.class, schema.stream().map(s -> new ImmutableTable(this, s)));
    }

    @Override
    public Boolean isDefaultSchema() {
        return defaultSchema;
    }

    @Override
    public void setDefaultSchema(Boolean defaultSchema) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public FieldStorageType getFieldStorageType() {
        return fieldStorageType;
    }

    @Override
    public void setFieldStorageType(FieldStorageType fieldStorageType) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public ColumnCompressionType getColumnCompressionType() {
        return columnCompressionType;
    }

    @Override
    public void setColumnCompressionType(ColumnCompressionType columnCompressionType) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public StorageEngineType getStorageEngineType() {
        return storageEngineType;
    }

    @Override
    public void setStorageEngineType(StorageEngineType storageEngineType) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Optional<String> getCatalogName() {
        return catalogName;
    }

    @Override
    public void setCatalogName(String catalogName) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Optional<String> getSchemaName() {
        return schemaName;
    }

    @Override
    public void setSchemaName(String schemaName) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public void setParent(Parent<?> parent) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Optional<Dbms> getParent() {
        return parent;
    }

    @Override
    public ChildHolder<Table> getChildren() {
        return children;
    }
    
    @Override
    public Stream<? extends Table> stream() {
        return getChildren().stream().sorted(Nameable.COMPARATOR);
    }

    @Override
    public <T extends Table> Stream<T> streamOf(Class<T> childClass) {
        if (Table.class.isAssignableFrom(childClass)) {
            return getChildren().stream()
                .map(child -> {
                    @SuppressWarnings("unchecked")
                    final T cast = (T) child;
                    return cast;
                }).sorted(Nameable.COMPARATOR);
        } else {
            throw new IllegalArgumentException(
                getClass().getSimpleName() + 
                " does not have children of type " + 
                childClass.getSimpleName() + "."
            );
        }
    }

    @Override
    public Table addNewTable() {
        return throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Table table(Closure<?> c) {
        return throwNewUnsupportedOperationExceptionImmutable();
    }
}
