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

import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.config.SchemaUtil;
import com.speedment.runtime.config.Table;
import com.speedment.tool.config.component.DocumentPropertyComponentUtil;
import com.speedment.tool.config.mutator.DocumentPropertyMutator;
import com.speedment.tool.config.mutator.SchemaPropertyMutator;
import com.speedment.tool.config.trait.*;
import javafx.beans.property.BooleanProperty;
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
public final class SchemaProperty 
extends AbstractChildDocumentProperty<Dbms, SchemaProperty>
implements Schema,
    HasEnabledProperty,
    HasExpandedProperty,
    HasIdProperty,
    HasNameProperty,
    HasAliasProperty,
    HasNameProtectedProperty {

    public SchemaProperty(Dbms parent) {
        super(parent);
    }

    @Override
    public StringProperty nameProperty() {
        return HasNameProtectedProperty.super.nameProperty();
    }

    public final BooleanProperty defaultSchemaProperty() {
        return booleanPropertyOf(SchemaUtil.DEFAULT_SCHEMA, Schema.super::isDefaultSchema);
    }

    @Override
    public boolean isDefaultSchema() {
        return defaultSchemaProperty().get();
    }

    public ObservableList<TableProperty> tablesProperty() {
        return observableListOf(SchemaUtil.TABLES);
    }

    @Override
    public Stream<Table> tables() {
        return tablesProperty().stream().map(Table.class::cast);
    }

    @Override
    public SchemaPropertyMutator mutator() {
        return DocumentPropertyMutator.of(this);
    }

    @Override
    protected List<String> keyPathEndingWith(String key) {
        return concat(DocumentPropertyComponentUtil.SCHEMAS, key);
    }
}