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
import com.speedment.config.Table;
import com.speedment.config.aspects.Parent;
import com.speedment.config.mapper.TypeMapper;
import com.speedment.config.parameters.ColumnCompressionType;
import com.speedment.config.parameters.FieldStorageType;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.config.mapper.identity.StringIdentityMapper;
import com.speedment.internal.ui.property.BooleanPropertyItem;
import com.speedment.internal.ui.property.EnumPropertyItem;
import com.speedment.internal.ui.property.StringPropertyItem;
import java.util.Optional;
import java.util.stream.Stream;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public final class ColumnProperty extends AbstractNodeProperty implements Column, ChildHelper<Column, Table> {
    
    private final Property<TypeMapper<?, ?>> typeMapper;
    private final BooleanProperty nullable;
    private final BooleanProperty autoIncrement;
    private final StringProperty alias;
    private final Property<FieldStorageType> fieldStorageType;
    private final Property<ColumnCompressionType> columnCompressionType;
    
    private Table parent;
    private int ordinalPosition;
    
    public ColumnProperty(Speedment speedment) {
        super(speedment);
        typeMapper            = new SimpleObjectProperty<>();
        nullable              = new SimpleBooleanProperty();
        autoIncrement         = new SimpleBooleanProperty();
        alias                 = new SimpleStringProperty();
        fieldStorageType      = new SimpleObjectProperty<>();
        columnCompressionType = new SimpleObjectProperty<>();
        setDefaults();
    }
    
    public ColumnProperty(Speedment speedment, Table parent, Column prototype) {
        super(speedment, prototype);
        
        this.typeMapper            = new SimpleObjectProperty<>(prototype.getTypeMapper());
        this.nullable              = new SimpleBooleanProperty(prototype.isNullable());
        this.autoIncrement         = new SimpleBooleanProperty(prototype.isAutoincrement());
        this.alias                 = new SimpleStringProperty(prototype.getAlias().orElse(null));
        this.fieldStorageType      = new SimpleObjectProperty<>(prototype.getFieldStorageType());
        this.columnCompressionType = new SimpleObjectProperty<>(prototype.getColumnCompressionType());
        this.ordinalPosition       = prototype.getOrdinalPosition();
        
        this.parent = parent;
    }
    
    private void setDefaults() {
        setNullable(true);
        setAutoincrement(false);
        setFieldStorageType(FieldStorageType.INHERIT);
        setColumnCompressionType(ColumnCompressionType.INHERIT);
        setTypeMapper(new StringIdentityMapper());
    }

    @Override
    protected Stream<PropertySheet.Item> guiVisibleProperties() {
        return Stream.of(
            // TODO: Add TypeMapper
            new StringPropertyItem(
                alias,       
                "Alias",                  
                "The name to use in the generated code to represent this entity."
            ),
            new BooleanPropertyItem(
                nullable,
                "Is Nullable",
                "If this column can hold 'null'-values or not."
            ),
            new BooleanPropertyItem(
                autoIncrement,
                "Is Auto Incrementing",
                "If this column will increment automatically for each new entity."
            )
        );
    }
    
    @Override
    public Optional<Table> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setParent(Parent<?> parent) {
        if (parent instanceof Table) {
            this.parent = (Table) parent;
        } else {
            throw wrongParentClass(parent.getClass());
        }
    }
    
    @Override
    public void setTypeMapper(TypeMapper<?, ?> typeMapper) {
        this.typeMapper.setValue(typeMapper);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public void setTypeMapper(Class<?> typeMapperClass) {
        if (typeMapperClass == null) {
            this.typeMapper.setValue(null);
        } else {
            try {
                setTypeMapper((TypeMapper<?, ?>) typeMapperClass.newInstance());
            } catch (InstantiationException | IllegalAccessException ex) {
                throw new SpeedmentException(
                    "Could not instantiate the specified mapper '" + 
                    typeMapperClass.getSimpleName() + 
                    "' using it's default constructor."
                );
            }
        }
    }

    @Override
    public TypeMapper<?, ?> getTypeMapper() {
        return typeMapper.getValue();
    }
    
    public Property<TypeMapper<?, ?>> typeMapperProperty() {
        return typeMapper;
    }

    @Override
    public void setNullable(Boolean nullable) {
        this.nullable.setValue(nullable);
    }

    @Override
    public Boolean isNullable() {
        return nullable.getValue();
    }
    
    public BooleanProperty nullableProperty() {
        return nullable;
    }

    @Override
    public void setAutoincrement(Boolean autoIncrement) {
        this.autoIncrement.setValue(autoIncrement);
    }

    @Override
    public Boolean isAutoincrement() {
        return autoIncrement.getValue();
    }
    
    public BooleanProperty autBooleanProperty() {
        return autoIncrement;
    }
    
    @Override
    public void setAlias(String alias) {
        this.alias.setValue(alias);
    }

    @Override
    public Optional<String> getAlias() {
        return Optional.ofNullable(alias.getValue());
    }
    
    public StringProperty aliasProperty() {
        return alias;
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
    public void setOrdinalPosition(int ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    @Override
    public int getOrdinalPosition() {
        return ordinalPosition;
    }
}