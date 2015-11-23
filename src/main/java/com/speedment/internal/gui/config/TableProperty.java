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
package com.speedment.internal.gui.config;

import com.speedment.Speedment;
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
import groovy.lang.Closure;
import static java.util.Collections.newSetFromMap;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import static javafx.collections.FXCollections.observableSet;
import javafx.collections.ObservableSet;

/**
 *
 * @author Emil Forslund
 */
public final class TableProperty extends AbstractParentProperty<Table, Child<Table>> implements Table, ChildHelper<Table, Schema> {
    
    private final ObservableSet<Column> columnChildren;
    private final ObservableSet<PrimaryKeyColumn> primaryKeyColumnChildren;
    private final ObservableSet<Index> indexChildren;
    private final ObservableSet<ForeignKey> foreignKeyChildren;
    private final Property<FieldStorageType> fieldStorageType;
    private final Property<ColumnCompressionType> columnCompressionType;
    private final Property<StorageEngineType> storageEngineType;
    private final StringProperty tableName;
    
    private Schema parent;
    
    public TableProperty(Speedment speedment) {
        super(speedment);
        columnChildren           = observableSet(newSetFromMap(new ConcurrentHashMap<>()));
        primaryKeyColumnChildren = observableSet(newSetFromMap(new ConcurrentHashMap<>()));
        indexChildren            = observableSet(newSetFromMap(new ConcurrentHashMap<>()));
        foreignKeyChildren       = observableSet(newSetFromMap(new ConcurrentHashMap<>()));
        fieldStorageType         = new SimpleObjectProperty<>();
        columnCompressionType    = new SimpleObjectProperty<>();
        storageEngineType        = new SimpleObjectProperty<>();
        tableName                = new SimpleStringProperty();
    }
    
    public TableProperty(Speedment speedment, Schema parent, Table prototype) {
        super(speedment, prototype);
        columnChildren           = copyChildrenFrom(prototype, Column.class, ColumnProperty::new);
        primaryKeyColumnChildren = copyChildrenFrom(prototype, PrimaryKeyColumn.class, PrimaryKeyColumnProperty::new);
        indexChildren            = copyChildrenFrom(prototype, Index.class, IndexProperty::new);
        foreignKeyChildren       = copyChildrenFrom(prototype, ForeignKey.class, ForeignKeyProperty::new);
        fieldStorageType         = new SimpleObjectProperty<>(prototype.getFieldStorageType());
        columnCompressionType    = new SimpleObjectProperty<>(prototype.getColumnCompressionType());
        storageEngineType        = new SimpleObjectProperty<>(prototype.getStorageEngineType());
        tableName                = new SimpleStringProperty(prototype.getTableName().orElse(null));
        this.parent = parent;
    }

    @Override
    public Optional<Schema> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setParent(Parent<?> parent) {
        if (parent instanceof Schema) {
            this.parent = (Schema) parent;
        } else {
            throw wrongParentClass(parent.getClass());
        }
    }
    
    public ObservableSet<Column> columnChildren() {
        return columnChildren;
    }
    
    public ObservableSet<PrimaryKeyColumn> primaryKeyColumnChildren() {
        return primaryKeyColumnChildren;
    }
    
    public ObservableSet<Index> indexChildren() {
        return indexChildren;
    }
    
    public ObservableSet<ForeignKey> foreignKeyChildren() {
        return foreignKeyChildren;
    }

    @Override
    public void setTableName(String tableName) {
        this.tableName.setValue(tableName);
    }

    @Override
    public Optional<String> getTableName() {
        return Optional.ofNullable(tableName.getValue());
    }
    
    public StringProperty tableNameProperty() {
        return tableName;
    }
    
    @Override
    public void setFieldStorageType(FieldStorageType fieldStorageType) {
        this.fieldStorageType.setValue(fieldStorageType);
    }

    @Override
    public FieldStorageType getFieldStorageType() {
        return fieldStorageType.getValue();
    }
    
    public Property<FieldStorageType> fieldStorageTypeProperty() {
        return fieldStorageType;
    }
    
    @Override
    public void setColumnCompressionType(ColumnCompressionType columnCompressionType) {
        this.columnCompressionType.setValue(columnCompressionType);
    }

    @Override
    public ColumnCompressionType getColumnCompressionType() {
        return columnCompressionType.getValue();
    }
    
    public Property<ColumnCompressionType> columnCompressionTypeProperty() {
        return columnCompressionType;
    }
    
    @Override
    public void setStorageEngineType(StorageEngineType storageEngineType) {
        this.storageEngineType.setValue(storageEngineType);
    }

    @Override
    public StorageEngineType getStorageEngineType() {
        return storageEngineType.getValue();
    }
    
    public Property<StorageEngineType> storageEngineTypeProperty() {
        return storageEngineType;
    }
    
    @Override
    public Column addNewColumn() {
        final Column column = new ColumnProperty(getSpeedment());
        addColumn(column);
        return column;
    }

    @Override
    public PrimaryKeyColumn addNewPrimaryKeyColumn() {
        final PrimaryKeyColumn primaryKeyColumn = new PrimaryKeyColumnProperty(getSpeedment());
        addPrimaryKeyColumn(primaryKeyColumn);
        return primaryKeyColumn;
    }
    
    @Override
    public Index addNewIndex() {
        final Index index = new IndexProperty(getSpeedment());
        addIndex(index);
        return index;
    }
    
    @Override
    public ForeignKey addNewForeignKey() {
        final ForeignKey foreignKey = new ForeignKeyProperty(getSpeedment());
        addForeignKey(foreignKey);
        return foreignKey;
    }

    @Override
    public Column column(Closure<?> c) {
        return ConfigUtil.groovyDelegatorHelper(c, () -> addNewColumn());
    }

    @Override
    public PrimaryKeyColumn primaryKeyColumn(Closure<?> c) {
        return ConfigUtil.groovyDelegatorHelper(c, () -> addNewPrimaryKeyColumn());
    }
    
    @Override
    public Index index(Closure<?> c) {
        return ConfigUtil.groovyDelegatorHelper(c, () -> addNewIndex());
    }
    
    @Override
    public ForeignKey foreignKey(Closure<?> c) {
        return ConfigUtil.groovyDelegatorHelper(c, () -> addNewForeignKey());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<? extends Child<Table>> add(Child<Table> child) {
        requireNonNull(child);
        
        if (child instanceof Column) {
            return addColumn((Column) child);
        } else if (child instanceof PrimaryKeyColumn) {
            return addPrimaryKeyColumn((PrimaryKeyColumn) child);
        } else if (child instanceof Index) {
            return addIndex((Index) child);
        } else if (child instanceof ForeignKey) {
            return addForeignKey((ForeignKey) child);
        } else {
            throw wrongChildTypeException(child.getClass());
        }
    }
    
    public Optional<Column> addColumn(Column child) {
        requireNonNull(child);
        return columnChildren.add(child) ? Optional.empty() : Optional.of(child);
    }
    
    public Optional<PrimaryKeyColumn> addPrimaryKeyColumn(PrimaryKeyColumn child) {
        requireNonNull(child);
        return primaryKeyColumnChildren.add(child) ? Optional.empty() : Optional.of(child);
    }
    
    public Optional<Index> addIndex(Index child) {
        requireNonNull(child);
        return indexChildren.add(child) ? Optional.empty() : Optional.of(child);
    }
    
    public Optional<ForeignKey> addForeignKey(ForeignKey child) {
        requireNonNull(child);
        return foreignKeyChildren.add(child) ? Optional.empty() : Optional.of(child);
    }

    @Override
    public Stream<? extends Child<Table>> stream() {
        return Stream.concat(
            Stream.concat(
                columnChildren.stream(),
                primaryKeyColumnChildren.stream()
            ), Stream.concat(
                indexChildren.stream(),
                foreignKeyChildren.stream()
            )
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Child<Table>> Stream<T> streamOf(Class<T> childType) {
        requireNonNull(childType);
        
        if (Column.class.isAssignableFrom(childType)) {
            return (Stream<T>) columnChildren.stream();
        } else if (PrimaryKeyColumn.class.isAssignableFrom(childType)) {
            return (Stream<T>) primaryKeyColumnChildren.stream();
        } else if (Index.class.isAssignableFrom(childType)) {
            return (Stream<T>) indexChildren.stream();
        } else if (ForeignKey.class.isAssignableFrom(childType)) {
            return (Stream<T>) foreignKeyChildren.stream();
        } else {
            throw wrongChildTypeException(childType);
        }
    }
    
    @Override
    public int count() {
        return columnChildren.size() + 
               primaryKeyColumnChildren.size() +
               indexChildren.size() +
               foreignKeyChildren.size();
    }

    @Override
    public int countOf(Class<? extends Child<Table>> childType) {
        requireNonNull(childType);
        
        if (Column.class.isAssignableFrom(childType)) {
            return columnChildren.size();
        } else if (PrimaryKeyColumn.class.isAssignableFrom(childType)) {
            return primaryKeyColumnChildren.size();
        } else if (Index.class.isAssignableFrom(childType)) {
            return indexChildren.size();
        } else if (ForeignKey.class.isAssignableFrom(childType)) {
            return foreignKeyChildren.size();
        } else {
            throw wrongChildTypeException(childType);
        }
    }

    @Override
    public <T extends Child<Table>> T find(Class<T> childType, String name) throws SpeedmentException {
        requireNonNull(childType);
        requireNonNull(name);
        
        final Stream<? extends Child<Table>> stream;
        if (Column.class.isAssignableFrom(childType)) {
            stream = columnChildren.stream();
        } else if (PrimaryKeyColumn.class.isAssignableFrom(childType)) {
            stream = primaryKeyColumnChildren.stream();
        } else if (Index.class.isAssignableFrom(childType)) {
            stream = indexChildren.stream();
        } else if (ForeignKey.class.isAssignableFrom(childType)) {
            stream = foreignKeyChildren.stream();
        } else {
            throw wrongChildTypeException(childType);
        }
        
        @SuppressWarnings("unchecked")
        final T result = (T) stream.filter(child -> name.equals(child.getName()))
            .findAny().orElseThrow(() -> noChildWithNameException(childType, name));
        
        return result;
    }
}