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
import com.speedment.config.db.Dbms;
import com.speedment.config.db.Schema;
import com.speedment.internal.ui.config.trait.HasAliasProperty;
import com.speedment.internal.ui.config.trait.HasEnabledProperty;
import com.speedment.internal.ui.config.trait.HasNameProperty;
import com.speedment.internal.ui.property.BooleanPropertyItem;
import static com.speedment.internal.util.document.DocumentUtil.toStringHelper;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public final class SchemaProperty extends AbstractChildDocumentProperty<Dbms> 
    implements Schema, HasEnabledProperty, HasNameProperty, HasAliasProperty {

    public SchemaProperty(Dbms parent, Map<String, Object> data) {
        super(parent, data);
    }
    
    @Override
    public Stream<PropertySheet.Item> getUiVisibleProperties(Speedment speedment) {
        return Stream.of(
            HasEnabledProperty.super.getUiVisibleProperties(speedment),
            HasNameProperty.super.getUiVisibleProperties(speedment),
            HasAliasProperty.super.getUiVisibleProperties(speedment),
            Stream.of(
                new BooleanPropertyItem(
                    defaultSchemaProperty(),       
                    "Is Default Schema",
                    "If this is the default schema that should be used if none other is specified."
                )
            )
        ).flatMap(s -> s);
    }
    
    @Override
    public StringProperty nameProperty() {
        return HasNameProperty.super.nameProperty();
    }
    
    public final BooleanProperty defaultSchemaProperty() {
        return booleanPropertyOf(DEFAULT_SCHEMA, Schema.super::isDefaultSchema);
    }
    
    public ObservableList<TableProperty> tablesProperty() {
        return observableListOf(TABLES, TableProperty::new);
    }

    @Override
    public BiFunction<Schema, Map<String, Object>, TableProperty> tableConstructor() {
        return TableProperty::new;
    }

    @Override
    protected final DocumentProperty createDocument(String key, Map<String, Object> data) {
        switch (key) {
            case TABLES : return new TableProperty(this, data);
            default     : return super.createDocument(key, data);
        }
    }
    
    @Override
    public Stream<TableProperty> tables() {
        return tablesProperty().stream();
    }
    
    @Override
    public TableProperty addNewTable() {
        final TableProperty created = new TableProperty(this, new ConcurrentHashMap<>());
        tablesProperty().add(created);
        return created;
    }
    
    
    @Override
    public String toString() {
        return toStringHelper(this);
    } 
    
}