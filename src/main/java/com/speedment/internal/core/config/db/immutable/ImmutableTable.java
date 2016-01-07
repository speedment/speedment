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
package com.speedment.internal.core.config.db.immutable;

import com.speedment.config.db.Column;
import com.speedment.config.db.ForeignKey;
import com.speedment.config.db.Index;
import com.speedment.config.db.PrimaryKeyColumn;
import com.speedment.config.db.Schema;
import com.speedment.config.db.Table;
import com.speedment.config.aspects.Child;
import com.speedment.config.aspects.Nameable;
import com.speedment.config.aspects.Ordinable;
import com.speedment.config.aspects.Parent;
import com.speedment.config.parameters.ColumnCompressionType;
import com.speedment.config.parameters.FieldStorageType;
import com.speedment.config.parameters.StorageEngineType;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.config.ChildHolder;
import static com.speedment.internal.util.Cast.castOrFail;
import groovy.lang.Closure;
import java.util.Optional;
import static com.speedment.internal.core.config.db.immutable.ImmutableUtil.throwNewUnsupportedOperationExceptionImmutable;
import static com.speedment.internal.core.config.utils.ConfigUtil.thereIsNo;
import java.util.List;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
public final class ImmutableTable extends ImmutableAbstractNamedConfigEntity
        implements Table, ImmutableParentHelper<Child<Table>> {

    private final Optional<Schema> parent;
    private final ChildHolder<Column> columns;
    private final ChildHolder<PrimaryKeyColumn> primaryKeyColumns;
    private final ChildHolder<Index> indexes;
    private final ChildHolder<ForeignKey> foreignKeys;

    private final Optional<String> tableName;
    private final FieldStorageType fieldStorageType;
    private final ColumnCompressionType columnCompressionType;
    private final StorageEngineType storageEngineType;
    private final boolean exposedInRest;
    private final String restPath;

    /// Optimized 
    private final List<Child<Table>> streamList;

    public ImmutableTable(Schema parent, Table table) {
        super(requireNonNull(table).getName(), table.isExpanded(), table.isEnabled());
        requireNonNull(parent);
        
        // Fields
        this.parent                = table.getParent();
        this.tableName             = table.getTableName();
        this.fieldStorageType      = table.getFieldStorageType();
        this.columnCompressionType = table.getColumnCompressionType();
        this.storageEngineType     = table.getStorageEngineType();
        this.exposedInRest         = table.isExposedInRest();
        this.restPath              = table.getRestPath().orElse(null);
        
        // Children
        this.columns           = ImmutableChildHolder.of(Column.class, table.streamOfColumns().map(this::toImmutable).collect(toList()));
        this.primaryKeyColumns = ImmutableChildHolder.of(PrimaryKeyColumn.class, table.streamOfPrimaryKeyColumns().map(this::toImmutable).collect(toList()));
        this.indexes           = ImmutableChildHolder.of(Index.class, table.streamOfIndexes().map(this::toImmutable).collect(toList()));
        this.foreignKeys       = ImmutableChildHolder.of(ForeignKey.class, table.streamOfForeignKeys().map(this::toImmutable).collect(toList()));

        this.streamList = Stream.of(columns, primaryKeyColumns, indexes, foreignKeys)
            .flatMap(ChildHolder::stream)
            .collect(toList());
    }

    private Column toImmutable(Column child) {
        return new ImmutableColumn(this, castOrFail(child, Column.class));
    }

    private PrimaryKeyColumn toImmutable(PrimaryKeyColumn child) {
        return new ImmutablePrimaryKeyColumn(this, castOrFail(child, PrimaryKeyColumn.class));
    }

    private Index toImmutable(Index child) {
        return new ImmutableIndex(this, castOrFail(child, Index.class));
    }

    private ForeignKey toImmutable(ForeignKey child) {
        return new ImmutableForeignKey(this, castOrFail(child, ForeignKey.class));
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
    public Optional<String> getTableName() {
        return tableName;
    }

    @Override
    public void setTableName(String tableName) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setParent(Parent<?> parent) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public ChildHolder<Child<Table>> getChildren() {
        throw new IllegalStateException(Table.class.getSimpleName() + " has several child types");
    }

    @Override
    public Optional<Schema> getParent() {
        return parent;
    }

    @Override
    public Column addNewColumn() {
        return throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Index addNewIndex() {
        return throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public PrimaryKeyColumn addNewPrimaryKeyColumn() {
        return throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public ForeignKey addNewForeignKey() {
        return throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public PrimaryKeyColumn primaryKeyColumn(Closure<?> c) {
        return throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Column column(Closure<?> c) {
        return throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Index index(Closure<?> c) {
        return throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public ForeignKey foreignKey(Closure<?> c) {
        return throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Stream<? extends Child<Table>> stream() {
        return streamList.stream();
    }

    @Override
    public <T extends Child<Table>> Stream<T> streamOf(Class<T> childClass) {
        if (Column.class.equals(childClass)) {
            @SuppressWarnings("unchecked")
            final Stream<T> result = (Stream<T>) columns.stream().sorted(Ordinable.COMPARATOR);
            return result;
        }
        if (PrimaryKeyColumn.class.equals(childClass)) {
            @SuppressWarnings("unchecked")
            final Stream<T> result = (Stream<T>) primaryKeyColumns.stream().sorted(Ordinable.COMPARATOR);
            return result;
        }
        if (Index.class.equals(childClass)) {
            @SuppressWarnings("unchecked")
            final Stream<T> result = (Stream<T>) indexes.stream().sorted(Nameable.COMPARATOR);
            return result;
        }
        if (Column.class.equals(childClass)) {
            @SuppressWarnings("unchecked")
            final Stream<T> result = (Stream<T>) foreignKeys.stream().sorted(Nameable.COMPARATOR);
            return result;
        }
        return Stream.empty();
    }

    @Override
    public <T extends Child<Table>> T find(Class<T> childClass, String name) throws SpeedmentException {
        if (Column.class.equals(childClass)) {
            @SuppressWarnings("unchecked")
            final T result = (T) columns.find(name);
            return result;
        }
        if (PrimaryKeyColumn.class.equals(childClass)) {
            @SuppressWarnings("unchecked")
            final T result = (T) primaryKeyColumns.find(name);
            return result;
        }
        if (Index.class.equals(childClass)) {
            @SuppressWarnings("unchecked")
            final T result = (T) indexes.find(name);
            return result;
        }
        if (Column.class.equals(childClass)) {
            @SuppressWarnings("unchecked")
            final T result = (T) foreignKeys.find(name);
            return result;
        }
        throw thereIsNo(childClass, this.getClass(), name).get();
    }

    // Optimized methods
    @Override
    public Stream<Column> streamOfColumns() {
        return columns.stream();
    }

    @Override
    public Stream<PrimaryKeyColumn> streamOfPrimaryKeyColumns() {
        return primaryKeyColumns.stream();
    }

    @Override
    public Stream<Index> streamOfIndexes() {
        return indexes.stream();
    }

    @Override
    public Stream<ForeignKey> streamOfForeignKeys() {
        return foreignKeys.stream();
    }

    @Override
    public Column findColumn(String name) throws SpeedmentException {
        return columns.find(name);
    }

    @Override
    public PrimaryKeyColumn findPrimaryKeyColumn(String name) throws SpeedmentException {
        return primaryKeyColumns.find(name);
    }

    @Override
    public Index findIndex(String name) throws SpeedmentException {
        return indexes.find(name);
    }

    @Override
    public ForeignKey findForeignKey(String name) throws SpeedmentException {
        return foreignKeys.find(name);
    }

    @Override
    public void setExposedInRest(boolean exposed) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public boolean isExposedInRest() {
        return exposedInRest;
    }

    @Override
    public void setRestPath(String restPath) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Optional<String> getRestPath() {
        return Optional.ofNullable(restPath);
    }
}