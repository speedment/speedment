/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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

import com.speedment.runtime.Speedment;
import com.speedment.tool.component.DocumentPropertyComponent;
import com.speedment.runtime.config.ForeignKey;
import com.speedment.runtime.config.ForeignKeyColumn;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.tool.config.mutator.DocumentPropertyMutator;
import com.speedment.tool.config.mutator.ForeignKeyColumnPropertyMutator;
import static com.speedment.runtime.internal.util.ImmutableListUtil.*;
import com.speedment.tool.property.StringPropertyItem;
import com.speedment.tool.config.trait.HasColumnProperty;
import com.speedment.tool.config.trait.HasExpandedProperty;
import com.speedment.tool.config.trait.HasNameProperty;
import com.speedment.tool.config.trait.HasOrdinalPositionProperty;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import static javafx.beans.binding.Bindings.createObjectBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.StringProperty;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public final class ForeignKeyColumnProperty extends AbstractChildDocumentProperty<ForeignKey, ForeignKeyColumnProperty> 
    implements ForeignKeyColumn, HasExpandedProperty, HasNameProperty, HasOrdinalPositionProperty, HasColumnProperty {
	
    public final StringProperty foreignDatabaseNameProperty() {
    	return stringPropertyOf(FOREIGN_DATABASE_NAME, ForeignKeyColumn.super::getForeignDatabaseName);
    }
    
    @Override
    public String getForeignDatabaseName() {
    	return foreignDatabaseNameProperty().get();
    }
    
    public final StringProperty foreignSchemaNameProperty() {
    	return stringPropertyOf(FOREIGN_SCHEMA_NAME, ForeignKeyColumn.super::getForeignSchemaName);
    }
    
    @Override
    public String getForeignSchemaName() {
    	return foreignSchemaNameProperty().get();
    }


    public ForeignKeyColumnProperty(ForeignKey parent) {
        super(parent);
    }

    public final StringProperty foreignTableNameProperty() {
        return stringPropertyOf(FOREIGN_TABLE_NAME, ForeignKeyColumn.super::getForeignTableName);
    }

    @Override
    public String getForeignTableName() {
        return foreignTableNameProperty().get();
    }

    public final StringProperty foreignColumnNameProperty() {
        return stringPropertyOf(FOREIGN_COLUMN_NAME, ForeignKeyColumn.super::getForeignColumnName);
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
    public Optional<TableProperty> findForeignTable() throws SpeedmentException {
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
    public Optional<ColumnProperty> findForeignColumn() throws SpeedmentException {
        return Optional.ofNullable(foreignColumnProperty().get());
    }
    
    @Override
    public ForeignKeyColumnPropertyMutator mutator() {
        return DocumentPropertyMutator.of(this);
    }
    
    @Override
    public Stream<PropertySheet.Item> getUiVisibleProperties(Speedment speedment) {
        return Stream.of(
            HasColumnProperty.super.getUiVisibleProperties(speedment),
            Stream.of(
               new StringPropertyItem(
                            foreignDatabaseNameProperty(), 
                            "Foreign Database Name",
                            "The name of the database that this foreign key references."
                        ),
               new StringPropertyItem(
                                foreignSchemaNameProperty(), 
                                "Foreign Schema Name",
                                "The name of the schema that this foreign key references."
                         ),
                new StringPropertyItem(
                    foreignTableNameProperty(), 
                    "Foreign Table Name",
                    "The name of the database table that this foreign key references."
                ),
                new StringPropertyItem(
                    foreignColumnNameProperty(), 
                    "Foreign Column Name",
                    "The name of the database column that this foreign key references."
                )
            )
        ).flatMap(s -> s);
    }
    
    @Override
    protected List<String> keyPathEndingWith(String key) {
        return concat(DocumentPropertyComponent.FOREIGN_KEY_COLUMNS, key);
    }
}