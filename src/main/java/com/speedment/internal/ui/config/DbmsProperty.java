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

import com.speedment.config.db.Dbms;
import static com.speedment.config.db.Dbms.IP_ADDRESS;
import static com.speedment.config.db.Dbms.PORT;
import static com.speedment.config.db.Dbms.USERNAME;
import com.speedment.config.db.Project;
import com.speedment.internal.ui.config.trait.HasEnabledProperty;
import com.speedment.internal.ui.config.trait.HasNameProperty;
import com.speedment.internal.ui.property.IntegerPropertyItem;
import com.speedment.internal.ui.property.StringPropertyItem;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public final class DbmsProperty extends AbstractChildDocumentProperty<Project> 
    implements Dbms, HasEnabledProperty, HasNameProperty {

    public DbmsProperty(Project parent, Map<String, Object> data) {
        super(parent, data);
    }
    
    @Override
    public Stream<PropertySheet.Item> getUiVisibleProperties() {
        return Stream.of(
            HasNameProperty.super.getUiVisibleProperties(),
            HasEnabledProperty.super.getUiVisibleProperties(),
            Stream.of(
                // TODO: Add DbmsType
                new StringPropertyItem(
                    ipAddressProperty(),       
                    "IP Address",                  
                    "The ip of the database host."
                ),
                new IntegerPropertyItem(
                    portProperty(),       
                    "Port",                  
                    "The port of the database on the database host."
                ),
                new StringPropertyItem(
                    usernameProperty(),      
                    "Username",                  
                    "The username to use when connecting to the database."
                )
            )
        ).flatMap(s -> s);
    }
    
    public final StringProperty typeNameProperty() {
        return stringPropertyOf(TYPE_NAME);
    }

    public final StringProperty ipAddressProperty() {
        return stringPropertyOf(IP_ADDRESS);
    }

    public final IntegerProperty portProperty() {
        return integerPropertyOf(PORT);
    }

    public final StringProperty usernameProperty() {
        return stringPropertyOf(USERNAME);
    }
    
    public ObservableList<SchemaProperty> schemasProperty() {
        return observableListOf(SCHEMAS, SchemaProperty.class);
    }

    @Override
    public BiFunction<Dbms, Map<String, Object>, SchemaProperty> schemaConstructor() {
        return SchemaProperty::new;
    }
    
    @Override
    protected final DocumentProperty createDocument(String key, Map<String, Object> data) {
        switch (key) {
            case SCHEMAS : return new SchemaProperty(this, data);
            default      : return super.createDocument(key, data);
        }
    }
    
    @Override
    public Stream<SchemaProperty> schemas() {
        return schemasProperty().stream();
    }
    
    @Override
    public SchemaProperty addNewSchema() {
        return (SchemaProperty) Dbms.super.addNewSchema();
    }
}