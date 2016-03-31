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
import com.speedment.config.db.Schema;
import com.speedment.config.db.Table;
import com.speedment.config.db.mutator.TableMutator;
import com.speedment.internal.ui.config.mutator.DocumentPropertyMutator;
import com.speedment.internal.ui.config.mutator.TablePropertyMutator;
import com.speedment.ui.config.trait.HasAliasProperty;
import com.speedment.ui.config.trait.HasEnabledProperty;
import com.speedment.ui.config.trait.HasExpandedProperty;
import com.speedment.ui.config.trait.HasNameProperty;
import static com.speedment.internal.util.ImmutableListUtil.*;
import java.util.List;
import java.util.stream.Stream;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public final class TableProperty
    extends AbstractChildDocumentProperty<Schema, TableProperty>
    implements
    Table,
    HasEnabledProperty,
    HasExpandedProperty,
    HasNameProperty,
    HasAliasProperty {

    public TableProperty(Schema parent) {
        super(parent);
    }

    public ObservableList<ColumnProperty> columnsProperty() {
        return observableListOf(COLUMNS);
    }

    public ObservableList<IndexProperty> indexesProperty() {
        return observableListOf(INDEXES);
    }

    public ObservableList<ForeignKeyProperty> foreignKeysProperty() {
        return observableListOf(FOREIGN_KEYS);
    }

    public ObservableList<PrimaryKeyColumnProperty> primaryKeyColumnsProperty() {
        return observableListOf(PRIMARY_KEY_COLUMNS);
    }

    @Override
    public StringProperty nameProperty() {
        return HasNameProperty.super.nameProperty();
    }

    @Override
    public Stream<? extends ColumnProperty> columns() {
        return columnsProperty().stream();
    }

    @Override
    public Stream<? extends IndexProperty> indexes() {
        return indexesProperty().stream();
    }

    @Override
    public Stream<? extends ForeignKeyProperty> foreignKeys() {
        return foreignKeysProperty().stream();
    }

    @Override
    public Stream<? extends PrimaryKeyColumnProperty> primaryKeyColumns() {
        return primaryKeyColumnsProperty().stream();
    }

    @Override
    public TablePropertyMutator mutator() {
        return DocumentPropertyMutator.of(this);
    }

    @Override
    public Stream<PropertySheet.Item> getUiVisibleProperties(Speedment speedment) {
        return Stream.of(
            HasEnabledProperty.super.getUiVisibleProperties(speedment),
            HasNameProperty.super.getUiVisibleProperties(speedment),
            HasAliasProperty.super.getUiVisibleProperties(speedment)
        ).flatMap(s -> s);
    }

    @Override
    protected List<String> keyPathEndingWith(String key) {
        return concat(DocumentPropertyComponent.TABLES, key);
    }
}
