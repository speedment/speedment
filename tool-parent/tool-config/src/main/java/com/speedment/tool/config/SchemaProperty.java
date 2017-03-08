/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
import com.speedment.tool.config.component.DocumentPropertyComponent;
import com.speedment.tool.config.mutator.DocumentPropertyMutator;
import com.speedment.tool.config.mutator.SchemaPropertyMutator;
import com.speedment.tool.config.trait.HasAliasProperty;
import com.speedment.tool.config.trait.HasEnabledProperty;
import com.speedment.tool.config.trait.HasExpandedProperty;
import com.speedment.tool.config.trait.HasNameProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Stream;

import static com.speedment.runtime.core.internal.util.ImmutableListUtil.concat;
import com.speedment.tool.config.trait.HasIdProperty;

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
        HasAliasProperty {

    public SchemaProperty(Dbms parent) {
        super(parent);
    }

    @Override
    public StringProperty nameProperty() {
        return HasNameProperty.super.nameProperty();
    }

    public final BooleanProperty defaultSchemaProperty() {
        return booleanPropertyOf(DEFAULT_SCHEMA, Schema.super::isDefaultSchema);
    }

    @Override
    public boolean isDefaultSchema() {
        return defaultSchemaProperty().get();
    }

    public ObservableList<TableProperty> tablesProperty() {
        return observableListOf(TABLES);
    }

    @Override
    public Stream<TableProperty> tables() {
        return tablesProperty().stream();
    }

    @Override
    public SchemaPropertyMutator mutator() {
        return DocumentPropertyMutator.of(this);
    }

    @Override
    protected List<String> keyPathEndingWith(String key) {
        return concat(DocumentPropertyComponent.SCHEMAS, key);
    }
}