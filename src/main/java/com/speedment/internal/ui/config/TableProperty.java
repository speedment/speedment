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
package com.speedment.internal.ui.config;

import com.speedment.Speedment;
import com.speedment.config.Column;
import com.speedment.config.ForeignKey;
import com.speedment.config.Index;
import com.speedment.config.PrimaryKeyColumn;
import com.speedment.config.Schema;
import com.speedment.config.Table;
import com.speedment.config.aspects.Child;
import com.speedment.config.aspects.Nameable;
import com.speedment.config.aspects.Ordinable;
import com.speedment.config.aspects.Parent;
import com.speedment.config.parameters.ColumnCompressionType;
import com.speedment.config.parameters.FieldStorageType;
import com.speedment.config.parameters.StorageEngineType;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.config.utils.ConfigUtil;
import com.speedment.internal.ui.property.StringPropertyItem;
import groovy.lang.Closure;
import static java.util.Collections.newSetFromMap;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import static javafx.collections.FXCollections.observableSet;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public final class TableProperty extends AbstractParentProperty<Table, Child<Table>> implements Table, ChildHelper<Table, Schema>, RestExposableHelper {
    
    private final ObservableSet<Column> columnChildren;
    private final ObservableSet<PrimaryKeyColumn> primaryKeyColumnChildren;
    private final ObservableSet<Index> indexChildren;
    private final ObservableSet<ForeignKey> foreignKeyChildren;
    private final Property<FieldStorageType> fieldStorageType;
    private final Property<ColumnCompressionType> columnCompressionType;
    private final Property<StorageEngineType> storageEngineType;
    private final StringProperty tableName;
    
    private final BooleanProperty exposedInRest;
    private final StringProperty restPath;
    
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
        exposedInRest            = new SimpleBooleanProperty();
        restPath                 = new SimpleStringProperty();
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
        exposedInRest            = new SimpleBooleanProperty(prototype.isExposedInRest());
        restPath                 = new SimpleStringProperty(prototype.getRestPath());
        this.parent = parent;
    }
    
    @Override
    protected Stream<PropertySheet.Item> guiVisibleProperties() {
        return Stream.of(
            new StringPropertyItem(
                tableName,
                "Table Name",                  
                "The name that is used for this entity in the database."
            )
        );
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
    
    @Override
    public ObservableList<Child<Table>> children() {
        return createChildrenView(columnChildren);
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
    @SuppressWarnings("unchecked")
    public Optional<? extends Child<Table>> remove(Child<Table> child) {
        requireNonNull(child);
        
        if (child instanceof Column) {
            return removeColumn((Column) child);
        } else if (child instanceof PrimaryKeyColumn) {
            return removePrimaryKeyColumn((PrimaryKeyColumn) child);
        } else if (child instanceof Index) {
            return removeIndex((Index) child);
        } else if (child instanceof ForeignKey) {
            return removeForeignKey((ForeignKey) child);
        } else {
            throw wrongChildTypeException(child.getClass());
        }
    }
    
    public Optional<Column> removeColumn(Column child) {
        requireNonNull(child);
        if (columnChildren.remove(child)) {
            child.setParent(null);
            return Optional.of(child);
        } else return Optional.empty();
    }
    
    public Optional<PrimaryKeyColumn> removePrimaryKeyColumn(PrimaryKeyColumn child) {
        requireNonNull(child);
        if (primaryKeyColumnChildren.remove(child)) {
            child.setParent(null);
            return Optional.of(child);
        } else return Optional.empty();
    }
    
    public Optional<Index> removeIndex(Index child) {
        requireNonNull(child);
        if (indexChildren.remove(child)) {
            child.setParent(null);
            return Optional.of(child);
        } else return Optional.empty();
    }
    
    public Optional<ForeignKey> removeForeignKey(ForeignKey child) {
        requireNonNull(child);
        if (foreignKeyChildren.remove(child)) {
            child.setParent(null);
            return Optional.of(child);
        } else return Optional.empty();
    }

    @Override
    public Stream<? extends Child<Table>> stream() {
        return Stream.concat(
            Stream.concat(
                columnChildren.stream().sorted(Ordinable.COMPARATOR),
                primaryKeyColumnChildren.stream().sorted(Ordinable.COMPARATOR)
            ), Stream.concat(
                indexChildren.stream().sorted(Nameable.COMPARATOR),
                foreignKeyChildren.stream().sorted(Nameable.COMPARATOR)
            )
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Child<Table>> Stream<T> streamOf(Class<T> childType) {
        requireNonNull(childType);
        
        if (Column.class.isAssignableFrom(childType)) {
            return (Stream<T>) columnChildren.stream().sorted(Ordinable.COMPARATOR);
        } else if (PrimaryKeyColumn.class.isAssignableFrom(childType)) {
            return (Stream<T>) primaryKeyColumnChildren.stream().sorted(Ordinable.COMPARATOR);
        } else if (Index.class.isAssignableFrom(childType)) {
            return (Stream<T>) indexChildren.stream().sorted(Nameable.COMPARATOR);
        } else if (ForeignKey.class.isAssignableFrom(childType)) {
            return (Stream<T>) foreignKeyChildren.stream().sorted(Nameable.COMPARATOR);
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
  
    @Override
    public BooleanProperty exposedInRestProperty() {
        return exposedInRest;
    }

    public StringProperty restPathProperty() {
        return restPath;
    }

    @Override
    public void setRestPath(String path) {
        restPath.setValue(path);
    }

    @Override
    public String getRestPath() {
        return restPath.getValue();
    }
}