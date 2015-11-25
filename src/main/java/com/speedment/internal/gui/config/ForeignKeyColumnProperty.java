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
import com.speedment.config.ForeignKeyColumn;
import com.speedment.config.Table;
import com.speedment.config.aspects.Parent;
import com.speedment.config.parameters.ColumnCompressionType;
import com.speedment.config.parameters.FieldStorageType;
import com.speedment.config.parameters.StorageEngineType;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.newgui.property.EnumPropertyItem;
import com.speedment.internal.newgui.property.StringPropertyItem;
import java.util.Optional;
import java.util.stream.Stream;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public final class ForeignKeyColumnProperty extends AbstractNodeProperty implements ForeignKeyColumn, ChildHelper<ForeignKeyColumn, ForeignKey> {
    
    private final StringProperty foreignColumnName;
    private final StringProperty foreignTableName;
    
    private int ordinalPosition;
    private ForeignKey parent;
    
    public ForeignKeyColumnProperty(Speedment speedment) {
        super(speedment);
        foreignColumnName = new SimpleStringProperty();
        foreignTableName  = new SimpleStringProperty();
    }
    
    public ForeignKeyColumnProperty(Speedment speedment, ForeignKey parent, ForeignKeyColumn prototype) {
        super(speedment, prototype);
        this.foreignColumnName = new SimpleStringProperty(prototype.getForeignColumnName());
        this.foreignTableName  = new SimpleStringProperty(prototype.getForeignTableName());
        this.ordinalPosition   = prototype.getOrdinalPosition();
        this.parent            = parent;
    }
    
    @Override
    protected Stream<PropertySheet.Item> guiVisibleProperties() {
        return Stream.of(
            new StringPropertyItem(
                foreignTableName, 
                "Foreign Table Name",
                "The name of the database table that this foreign key references."
            ),
            new StringPropertyItem(
                foreignColumnName, 
                "Foreign Column Name",
                "The name of the database column that this foreign key references."
            )
        );
    }
    
    @Override
    public Optional<ForeignKey> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setParent(Parent<?> parent) {
        if (parent instanceof ForeignKeyProperty) {
            this.parent = (ForeignKeyProperty) parent;
        } else {
            throw wrongParentClass(parent.getClass());
        }
    }

    @Override
    public void setForeignColumnName(String foreignColumnName) {
        this.foreignColumnName.setValue(foreignColumnName);
    }

    @Override
    public String getForeignColumnName() {
        return foreignColumnName.getValue();
    }
    
    public StringProperty foreignColumnNameProperty() {
        return foreignColumnName;
    }
    
    @Override
    public Column getForeignColumn() {
        return foreignColumnProperty().getValue();
    }
    
    public ObjectBinding<Column> foreignColumnProperty() {
        return Bindings.createObjectBinding(() -> 
            getForeignTable().findColumn(foreignColumnName.getValue()),
            foreignTableName,
            foreignColumnName
        );
    }
    
    @Override
    public void setForeignTableName(String foreignTableName) {
        this.foreignTableName.setValue(foreignTableName);
    }

    @Override
    public String getForeignTableName() {
        return foreignTableName.getValue();
    }
    
    public StringProperty foreignTableNameProperty() {
        return foreignTableName;
    }
    
    @Override
    public Table getForeignTable() {
        return foreignTableProperty().getValue();
    }
    
    public ObjectBinding<Table> foreignTableProperty() {
        return Bindings.createObjectBinding(() -> 
            getParent()
                .flatMap(ForeignKey::getParent)
                .flatMap(Table::getParent)
                .map(schema -> schema.find(Table.class, foreignTableName.getValue()))
                .orElse(null),
            foreignTableName
        );
    }

    @Override
    public void setOrdinalPosition(int ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    @Override
    public int getOrdinalPosition() {
        return ordinalPosition;
    }
    
    @Override
    public Column getColumn() {
        return ancestor(Table.class)
            .orElseThrow(() -> new SpeedmentException(
                "Found no ancestor table from this "
                + getClass().getSimpleName() + "."
            )).findColumn(getName());
    }
}