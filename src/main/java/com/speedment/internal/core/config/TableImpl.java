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
package com.speedment.internal.core.config;

import com.speedment.Speedment;
import com.speedment.internal.core.config.aspects.ParentHelper;
import com.speedment.config.Column;
import com.speedment.config.ForeignKey;
import com.speedment.config.Index;
import com.speedment.config.PrimaryKeyColumn;
import com.speedment.config.Schema;
import com.speedment.config.Table;
import com.speedment.config.aspects.Child;
import com.speedment.config.aspects.Parent;
import com.speedment.config.parameters.ColumnCompressionType;
import com.speedment.config.parameters.FieldStorageType;
import com.speedment.config.parameters.StorageEngineType;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.config.utils.ConfigUtil;
import static com.speedment.internal.core.config.utils.ConfigUtil.thereIsNo;
import com.speedment.internal.util.Cast;
import groovy.lang.Closure;
import java.util.Optional;
import java.util.stream.Stream;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
public final class TableImpl extends AbstractNamedNode implements Table, ParentHelper<Child<Table>> {

    private final Speedment speedment;
    private final ChildHolder<Column> columns;
    private final ChildHolder<PrimaryKeyColumn> primaryKeyColumns;
    private final ChildHolder<Index> indexes;
    private final ChildHolder<ForeignKey> foreignKeys;
    
    private Schema parent;
    private String tableName;
    private FieldStorageType fieldStorageType;
    private ColumnCompressionType columnCompressionType;
    private StorageEngineType storageEngineType;
    
    private boolean exposedInRest;
    private String restPath;

    public TableImpl(Speedment speedment) {
        this.speedment         = requireNonNull(speedment);
        this.columns           = new ChildHolderImpl<>(Column.class);
        this.primaryKeyColumns = new ChildHolderImpl<>(PrimaryKeyColumn.class);
        this.indexes           = new ChildHolderImpl<>(Index.class);
        this.foreignKeys       = new ChildHolderImpl<>(ForeignKey.class);
    }
    
    @Override
    protected void setDefaults() {
        setFieldStorageType(FieldStorageType.INHERIT);
        setColumnCompressionType(ColumnCompressionType.INHERIT);
        setStorageEngineType(StorageEngineType.INHERIT);
        setExpanded(false);
    }
    
    @Override
    public FieldStorageType getFieldStorageType() {
        return fieldStorageType;
    }
    
    @Override
    public void setFieldStorageType(FieldStorageType fieldStorageType) {
        this.fieldStorageType = fieldStorageType;
    }
    
    @Override
    public ColumnCompressionType getColumnCompressionType() {
        return columnCompressionType;
    }
    
    @Override
    public void setColumnCompressionType(ColumnCompressionType columnCompressionType) {
        this.columnCompressionType = columnCompressionType;
    }
    
    @Override
    public StorageEngineType getStorageEngineType() {
        return storageEngineType;
    }
    
    @Override
    public void setStorageEngineType(StorageEngineType storageEngineType) {
        this.storageEngineType = storageEngineType;
    }
    
    @Override
    public Optional<String> getTableName() {
        if (tableName == null) {
            return Optional.ofNullable(getName());
        }
        return Optional.of(tableName);
    }
    
    @Override
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void setParent(Parent<?> parent) {
        this.parent = Cast.castOrFail(parent, Schema.class);
    }
    
    @Override
    public ChildHolder<Child<Table>> getChildren() {
        throw new IllegalStateException(Table.class.getSimpleName() + " has several child types");
    }
    
    @Override
    public Optional<Schema> getParent() {
        return Optional.ofNullable(parent);
    }
    
    @Override
    public Column addNewColumn() {
        final Column e = Column.newColumn();
        add(e);
        return e;
    }
    
    @Override
    public  Index addNewIndex() {
        final Index e = Index.newIndex(speedment);
        add(e);
        return e;
    }
    
    @Override
    public PrimaryKeyColumn addNewPrimaryKeyColumn() {
        final PrimaryKeyColumn e = PrimaryKeyColumn.newPrimaryKeyColumn();
        add(e);
        return e;
    }
    
    @Override
    public  ForeignKey addNewForeignKey() {
        final ForeignKey e = ForeignKey.newForeignKey(speedment);
        add(e);
        return e;
    }
    
    @Override
    public PrimaryKeyColumn primaryKeyColumn(Closure<?> c) {
        return ConfigUtil.groovyDelegatorHelper(c, this::addNewPrimaryKeyColumn);
    }
    
    @Override
    public Column column(Closure<?> c) {
        return ConfigUtil.groovyDelegatorHelper(c, this::addNewColumn);
    }
    
    @Override
    public Index index(Closure<?> c) {
        return ConfigUtil.groovyDelegatorHelper(c, this::addNewIndex);
    }
    
    @Override
    public ForeignKey foreignKey(Closure<?> c) {
        return ConfigUtil.groovyDelegatorHelper(c, this::addNewForeignKey);
    }
    
    @Override
    public Stream<? extends Child<Table>> stream() {
        return Stream.of(columns, primaryKeyColumns, indexes, foreignKeys)
                .flatMap(ChildHolder::stream);
    }
    
    @Override
    public <T extends Child<Table>> Stream<T> streamOf(Class<T> childClass) {
        if (Column.class.equals(childClass)) {
            @SuppressWarnings("unchecked")
            final Stream<T> result = (Stream<T>) columns.stream();
            return result;
        } else if (PrimaryKeyColumn.class.equals(childClass)) {
            @SuppressWarnings("unchecked")
            final Stream<T> result = (Stream<T>) primaryKeyColumns.stream();
            return result;
        } else if (Index.class.equals(childClass)) {
            @SuppressWarnings("unchecked")
            final Stream<T> result = (Stream<T>) indexes.stream();
            return result;
        } else if (ForeignKey.class.equals(childClass)) {
            @SuppressWarnings("unchecked")
            final Stream<T> result = (Stream<T>) foreignKeys.stream();
            return result;
        } else {
            throw new SpeedmentException(
                "'" + childClass.getName() + 
                "' is not a child to '" + 
                getClass().getSimpleName() + "'."
            );
        }
    }
    
    @Override
    public <T extends Child<Table>> T find(Class<T> childClass, String name) throws SpeedmentException {
        if (Column.class.equals(childClass)) {
            @SuppressWarnings("unchecked")
            final T result = (T) columns.find(name);
            return result;
        } else if (PrimaryKeyColumn.class.equals(childClass)) {
            @SuppressWarnings("unchecked")
            final T result = (T) primaryKeyColumns.find(name);
            return result;
        } else if (Index.class.equals(childClass)) {
            @SuppressWarnings("unchecked")
            final T result = (T) indexes.find(name);
            return result;
        } else if (ForeignKey.class.equals(childClass)) {
            @SuppressWarnings("unchecked")
            final T result = (T) foreignKeys.find(name);
            return result;
        } else throw thereIsNo(childClass, this.getClass(), name).get();
    }
    
    @Override
    public Optional<Child<Table>> add(Child<Table> child) {
        if (Column.class.equals(child.getInterfaceMainClass())) {
            final Optional<Column> result = columns.put(Cast.castOrFail(child, Column.class), this);
            @SuppressWarnings("unchecked")
            final Optional<Child<Table>> castedResult = (Optional<Child<Table>>) (Optional<?>) result;
            return castedResult;
        } else if (PrimaryKeyColumn.class.equals(child.getInterfaceMainClass())) {
            final Optional<PrimaryKeyColumn> result = primaryKeyColumns.put(Cast.castOrFail(child, PrimaryKeyColumn.class), this);
            @SuppressWarnings("unchecked")
            final Optional<Child<Table>> castedResult = (Optional<Child<Table>>) (Optional<?>) result;
            return castedResult;
        } else if (Index.class.equals(child.getInterfaceMainClass())) {
            final Optional<Index> result = indexes.put(Cast.castOrFail(child, Index.class), this);
            @SuppressWarnings("unchecked")
            final Optional<Child<Table>> castedResult = (Optional<Child<Table>>) (Optional<?>) result;
            return castedResult;
        } else if (ForeignKey.class.equals(child.getInterfaceMainClass())) {
            final Optional<ForeignKey> result = foreignKeys.put(Cast.castOrFail(child, ForeignKey.class), this);
            @SuppressWarnings("unchecked")
            final Optional<Child<Table>> castedResult = (Optional<Child<Table>>) (Optional<?>) result;
            return castedResult;
        } else throw new IllegalArgumentException(
            "Cannot add a '" + child.getParentInterfaceMainClass() + 
            "' to a '" + getClass().getSimpleName() + "'."
        );
    }
    
    @Override
    public void setExposedInRest(boolean exposed) {
        this.exposedInRest = exposed;
    }

    @Override
    public boolean isExposedInRest() {
        return exposedInRest;
    }

    @Override
    public void setRestPath(String restPath) {
        this.restPath = restPath;
    }

    @Override
    public Optional<String> getRestPath() {
        return Optional.ofNullable(restPath);
    }
}
