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
import com.speedment.exception.SpeedmentException;
import java.util.Optional;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author Emil Forslund
 */
public final class ForeignKeyColumnProperty extends AbstractNodeProperty implements ForeignKeyColumn, ChildHelper<ForeignKeyColumn, ForeignKey> {
    
    private final StringProperty foreignColumnName;
    private final StringProperty foreignTableName;
    private final ObservableValue<Table> foreignTable;
    private final ObservableValue<Column> foreignColumn;
    
    private ForeignKey parent;
    private int ordinalPosition;
    
    public ForeignKeyColumnProperty(Speedment speedment) {
        super(speedment);
        foreignColumnName = new SimpleStringProperty();
        foreignTableName  = new SimpleStringProperty();
        foreignTable      = bindForeignTable();
        foreignColumn     = bindForeignColumn();
    }
    
    public ForeignKeyColumnProperty(Speedment speedment, ForeignKeyColumn prototype) {
        super(speedment, prototype);
        foreignColumnName = new SimpleStringProperty(prototype.getForeignColumnName());
        foreignTableName  = new SimpleStringProperty(prototype.getForeignTableName());
        foreignTable      = bindForeignTable();
        foreignColumn     = bindForeignColumn();
        ordinalPosition   = prototype.getOrdinalPosition();
    }
    
    private ObservableValue<Table> bindForeignTable() {
        return Bindings.createObjectBinding(() -> 
            getParent()
                .flatMap(ForeignKey::getParent)
                .flatMap(Table::getParent)
                .map(schema -> schema.find(Table.class, foreignTableName.getValue()))
                .orElse(null)
            , foreignTableName
        );
    }
    
    private ObservableValue<Column> bindForeignColumn() {
        return Bindings.createObjectBinding(() -> 
            foreignTable.getValue().findColumn(foreignColumnName.getValue()),
            foreignTable,
            foreignColumnName
        );
    }
    
    @Override
    public Optional<ForeignKey> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setParent(Parent<?> parent) {
        if (parent instanceof ForeignKey) {
            this.parent = (ForeignKey) parent;
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
        return foreignColumn.getValue();
    }
    
    public ObservableValue<Column> foreignColumnProperty() {
        return foreignColumn;
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
        return foreignTable.getValue();
    }
    
    public ObservableValue<Table> foreignTableProperty() {
        return foreignTable;
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