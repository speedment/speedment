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
package com.speedment.internal.ui.config;

import com.speedment.Speedment;
import com.speedment.component.DocumentPropertyComponent;
import com.speedment.config.db.ForeignKey;
import com.speedment.config.db.ForeignKeyColumn;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.ui.config.trait.HasColumnProperty;
import com.speedment.internal.ui.config.trait.HasNameProperty;
import com.speedment.internal.ui.config.trait.HasOrdinalPositionProperty;
import com.speedment.internal.ui.property.StringPropertyItem;
import java.util.stream.Stream;
import static javafx.beans.binding.Bindings.createObjectBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.StringProperty;
import org.controlsfx.control.PropertySheet;
import static com.speedment.component.DocumentPropertyComponent.concat;
import com.speedment.internal.ui.config.mutator.DocumentPropertyMutator;
import com.speedment.internal.ui.config.mutator.ForeignKeyColumnPropertyMutator;
import com.speedment.internal.ui.config.trait.HasExpandedProperty;
import java.util.Optional;

/**
 *
 * @author Emil Forslund
 */
public final class ForeignKeyColumnProperty extends AbstractChildDocumentProperty<ForeignKey, ForeignKeyColumnProperty> 
    implements ForeignKeyColumn, HasExpandedProperty, HasNameProperty, HasOrdinalPositionProperty, HasColumnProperty {

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
    protected String[] keyPathEndingWith(String key) {
        return concat(DocumentPropertyComponent.FOREIGN_KEY_COLUMNS, key);
    }
}