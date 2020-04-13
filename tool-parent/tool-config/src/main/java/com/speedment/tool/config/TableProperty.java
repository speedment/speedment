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

import com.speedment.runtime.config.*;
import com.speedment.tool.config.component.DocumentPropertyComponentUtil;
import com.speedment.tool.config.mutator.DocumentPropertyMutator;
import com.speedment.tool.config.mutator.TablePropertyMutator;
import com.speedment.tool.config.trait.*;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Stream;

import static com.speedment.tool.config.internal.util.ImmutableListUtil.concat;

/**
 *
 * @author  Emil Forslund
 * @since   2.3.0
 */
public final class TableProperty
extends AbstractChildDocumentProperty<Schema, TableProperty>
implements Table,
    HasEnabledProperty,
    HasExpandedProperty,
    HasIdProperty,
    HasNameProperty,
    HasAliasProperty,
    HasPackageNameProperty,
    HasNameProtectedProperty {

    public TableProperty(Schema parent) {
        super(parent);
    }

    public ObservableList<ColumnProperty> columnsProperty() {
        return observableListOf(TableUtil.COLUMNS);
    }

    public ObservableList<IndexProperty> indexesProperty() {
        return observableListOf(TableUtil.INDEXES);
    }

    public ObservableList<ForeignKeyProperty> foreignKeysProperty() {
        return observableListOf(TableUtil.FOREIGN_KEYS);
    }

    public ObservableList<PrimaryKeyColumnProperty> primaryKeyColumnsProperty() {
        return observableListOf(TableUtil.PRIMARY_KEY_COLUMNS);
    }

    @Override
    public StringProperty nameProperty() {
        return HasPackageNameProperty.super.nameProperty();
    }

    @Override
    public Stream<Column> columns() {
        return columnsProperty().stream().map(Column.class::cast);
    }

    @Override
    public Stream<Index> indexes() {
        return indexesProperty().stream().map(Index.class::cast);
    }

    @Override
    public Stream<ForeignKey> foreignKeys() {
        return foreignKeysProperty().stream().map(ForeignKey.class::cast);
    }

    @Override
    public Stream<PrimaryKeyColumn> primaryKeyColumns() {
        return primaryKeyColumnsProperty().stream().map(PrimaryKeyColumn.class::cast);
    }

    @Override
    public TablePropertyMutator mutator() {
        return DocumentPropertyMutator.of(this);
    }

    @Override
    protected List<String> keyPathEndingWith(String key) {
        return concat(DocumentPropertyComponentUtil.TABLES, key);
    }
}
