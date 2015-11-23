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
import com.speedment.config.Dbms;
import com.speedment.config.Schema;
import com.speedment.config.Table;
import com.speedment.config.aspects.Parent;
import com.speedment.config.parameters.ColumnCompressionType;
import com.speedment.config.parameters.FieldStorageType;
import com.speedment.config.parameters.StorageEngineType;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.config.utils.ConfigUtil;
import com.speedment.stream.MapStream;
import groovy.lang.Closure;
import static java.util.Collections.newSetFromMap;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Stream;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import static javafx.collections.FXCollections.observableSet;
import javafx.collections.ObservableSet;

/**
 *
 * @author Emil Forslund
 */
public final class SchemaProperty extends AbstractParentProperty<Schema, Table> implements Schema, ChildHelper<Schema, Dbms> {
    
    private final ObservableSet<Table> tableChildren;
    private final StringProperty schemaName;
    private final StringProperty catalogName;
    private final BooleanProperty defaultSchema;
    private final Property<FieldStorageType> fieldStorageType;
    private final Property<ColumnCompressionType> columnCompressionType;
    private final Property<StorageEngineType> storageEngineType;
    
    private Dbms parent;
    
    public SchemaProperty(Speedment speedment) {
        super(speedment);
        tableChildren         = observableSet(newSetFromMap(new ConcurrentSkipListMap<>()));
        schemaName            = new SimpleStringProperty();
        catalogName           = new SimpleStringProperty();
        defaultSchema         = new SimpleBooleanProperty();
        fieldStorageType      = new SimpleObjectProperty<>();
        columnCompressionType = new SimpleObjectProperty<>();
        storageEngineType     = new SimpleObjectProperty<>();
    }
    
    public SchemaProperty(Speedment speedment, Schema prototype) {
        super(speedment, prototype);
        tableChildren         = copyChildrenFrom(prototype, Table.class, TableProperty::new);
        schemaName            = new SimpleStringProperty(prototype.getSchemaName().orElse(null));
        catalogName           = new SimpleStringProperty(prototype.getCatalogName().orElse(null));
        defaultSchema         = new SimpleBooleanProperty(prototype.isDefaultSchema());
        fieldStorageType      = new SimpleObjectProperty<>(prototype.getFieldStorageType());
        columnCompressionType = new SimpleObjectProperty<>(prototype.getColumnCompressionType());
        storageEngineType     = new SimpleObjectProperty<>(prototype.getStorageEngineType());
    }
    
    @Override
    public Optional<Dbms> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setParent(Parent<?> parent) {
        if (parent instanceof Dbms) {
            this.parent = (Dbms) parent;
        } else {
            throw wrongParentClass(parent.getClass());
        }
    }

    @Override
    public void setSchemaName(String schemaName) {
        this.schemaName.setValue(schemaName);
    }

    @Override
    public Optional<String> getSchemaName() {
        return Optional.ofNullable(schemaName.getValue());
    }
    
    public StringProperty schemaNameProperty() {
        return schemaName;
    }
    
    @Override
    public void setCatalogName(String catalogName) {
        this.catalogName.setValue(catalogName);
    }

    @Override
    public Optional<String> getCatalogName() {
        return Optional.ofNullable(catalogName.getValue());
    }
    
    public StringProperty catalogNameProperty() {
        return catalogName;
    }
    
    @Override
    public void setDefaultSchema(Boolean defaultSchema) {
        this.defaultSchema.setValue(defaultSchema);
    }

    @Override
    public Boolean isDefaultSchema() {
        return defaultSchema.getValue();
    }
    
    public BooleanProperty defaultSchemaProperty() {
        return defaultSchema;
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
    public Table addNewTable() {
        final Table table = new TableProperty(getSpeedment());
        add(table);
        return table;
    }
    
    @Override
    public Table table(Closure<?> c) {
        return ConfigUtil.groovyDelegatorHelper(c, () -> addNewTable());
    }
    
    @Override
    public Optional<Table> add(Table child) throws IllegalStateException {
        requireNonNull(child);
        return tableChildren.add(child) ? Optional.empty() : Optional.of(child);
    }

    @Override
    public Stream<Table> stream() {
        return tableChildren.stream();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Table> Stream<T> streamOf(Class<T> childType) {
        requireNonNull(childType);
        
        if (Table.class.isAssignableFrom(childType)) {
            return (Stream<T>) tableChildren.stream();
        } else {
            throw wrongChildTypeException(childType);
        }
    }
    
    @Override
    public int count() {
        return tableChildren.size();
    }

    @Override
    public int countOf(Class<? extends Table> childType) {
        if (Table.class.isAssignableFrom(childType)) {
            return tableChildren.size();
        } else {
            throw wrongChildTypeException(childType);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Table> T find(Class<T> childType, String name) throws SpeedmentException {
        requireNonNull(childType);
        requireNonNull(name);
        
        if (Table.class.isAssignableFrom(childType)) {
            return (T) tableChildren.stream().filter(child -> name.equals(child.getName()))
                .findAny().orElseThrow(() -> noChildWithNameException(childType, name));
        } else {
            throw wrongChildTypeException(childType);
        }
    }
}