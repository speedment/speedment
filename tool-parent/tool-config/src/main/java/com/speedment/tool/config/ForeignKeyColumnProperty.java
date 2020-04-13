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
package com.speedment.tool.config;

import com.speedment.runtime.config.ForeignKey;
import com.speedment.runtime.config.ForeignKeyColumn;
import com.speedment.runtime.config.ForeignKeyColumnUtil;
import com.speedment.tool.config.component.DocumentPropertyComponentUtil;
import com.speedment.tool.config.mutator.DocumentPropertyMutator;
import com.speedment.tool.config.mutator.ForeignKeyColumnPropertyMutator;
import com.speedment.tool.config.trait.*;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.StringProperty;

import java.util.List;
import java.util.Optional;

import static com.speedment.tool.config.internal.util.ImmutableListUtil.concat;
import static javafx.beans.binding.Bindings.createObjectBinding;

/**
 *
 * @author  Emil Forslund
 * @since   2.3.0
 */
public final class ForeignKeyColumnProperty extends AbstractChildDocumentProperty<ForeignKey, ForeignKeyColumnProperty> 
implements ForeignKeyColumn,
           HasExpandedProperty,
           HasIdProperty,
           HasNameProperty,
           HasOrdinalPositionProperty,
           HasColumnProperty,
           HasNameProtectedProperty {
	
    public final StringProperty foreignDatabaseNameProperty() {
    	return stringPropertyOf(ForeignKeyColumnUtil.FOREIGN_DATABASE_NAME, ForeignKeyColumn.super::getForeignDatabaseName);
    }
    
    @Override
    public String getForeignDatabaseName() {
    	return foreignDatabaseNameProperty().get();
    }
    
    public final StringProperty foreignSchemaNameProperty() {
    	return stringPropertyOf(ForeignKeyColumnUtil.FOREIGN_SCHEMA_NAME, ForeignKeyColumn.super::getForeignSchemaName);
    }
    
    @Override
    public String getForeignSchemaName() {
    	return foreignSchemaNameProperty().get();
    }


    public ForeignKeyColumnProperty(ForeignKey parent) {
        super(parent);
    }

    public final StringProperty foreignTableNameProperty() {
        return stringPropertyOf(ForeignKeyColumnUtil.FOREIGN_TABLE_NAME, ForeignKeyColumn.super::getForeignTableName);
    }

    @Override
    public String getForeignTableName() {
        return foreignTableNameProperty().get();
    }

    public final StringProperty foreignColumnNameProperty() {
        return stringPropertyOf(ForeignKeyColumnUtil.FOREIGN_COLUMN_NAME, ForeignKeyColumn.super::getForeignColumnName);
    }

    @Override
    public String getForeignColumnName() {
        return foreignColumnNameProperty().get();
    }

    public final ObjectBinding<TableProperty> foreignTableProperty() {
        return createObjectBinding(
            () -> ForeignKeyColumn.super.findForeignTable()
                .map(TableProperty.class::cast)
                .orElse(null), 
            foreignTableNameProperty()
        );
    }

    @Override
    public Optional<TableProperty> findForeignTable() {
        return Optional.ofNullable(foreignTableProperty().get());
    }

    public final ObjectBinding<ColumnProperty> foreignColumnProperty() {
        return createObjectBinding(
            () -> ForeignKeyColumn.super.findForeignColumn()
                .map(ColumnProperty.class::cast)
                .orElse(null), 
            foreignTableNameProperty(),
            foreignColumnNameProperty()
        );
    }

    @Override
    public Optional<ColumnProperty> findForeignColumn() {
        return Optional.ofNullable(foreignColumnProperty().get());
    }
    
    @Override
    public ForeignKeyColumnPropertyMutator mutator() {
        return DocumentPropertyMutator.of(this);
    }
    
    @Override
    protected List<String> keyPathEndingWith(String key) {
        return concat(DocumentPropertyComponentUtil.FOREIGN_KEY_COLUMNS, key);
    }
}