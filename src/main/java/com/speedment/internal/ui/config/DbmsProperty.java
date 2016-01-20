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
import static com.speedment.config.db.Dbms.IP_ADDRESS;
import static com.speedment.config.db.Dbms.PORT;
import static com.speedment.config.db.Dbms.USERNAME;
import com.speedment.config.db.Project;
import com.speedment.internal.core.stream.OptionalUtil;
import com.speedment.internal.ui.config.trait.HasEnabledProperty;
import com.speedment.internal.ui.config.trait.HasNameProperty;
import com.speedment.internal.ui.property.DefaultStringPropertyItem;
import com.speedment.internal.ui.property.IntegerPropertyItem;
import static com.speedment.internal.util.document.DocumentUtil.toStringHelper;
import static com.speedment.util.NullUtil.requireKeys;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
        super(parent, requireKeys(data, Dbms.TYPE_NAME));
    }
    
    @Override
    public Stream<PropertySheet.Item> getUiVisibleProperties(Speedment speedment) {
        return Stream.of(
            HasEnabledProperty.super.getUiVisibleProperties(speedment),
            HasNameProperty.super.getUiVisibleProperties(speedment),
            Stream.of(
                // TODO: Add DbmsType
                new DefaultStringPropertyItem(
                    ipAddressProperty(), 
                    new SimpleStringProperty("127.0.0.1"),
                    "IP Address",                  
                    "The ip of the database host."
                ),
                new IntegerPropertyItem(
                    portProperty(),       
                    "Port",                  
                    "The port of the database on the database host."
                ),
                new DefaultStringPropertyItem(
                    usernameProperty(),
                    new SimpleStringProperty("root"),
                    "Username",                  
                    "The username to use when connecting to the database."
                )
            )
        ).flatMap(s -> s);
    }
    
    public StringProperty typeNameProperty() {
        return stringPropertyOf(TYPE_NAME,  () -> Dbms.super.getTypeName());
    }

    @Override
    public String getTypeName() {
        return typeNameProperty().get();
    }

    public StringProperty ipAddressProperty() {
        return stringPropertyOf(IP_ADDRESS,  () -> Dbms.super.getIpAddress().orElse(null));
    }

    @Override
    public Optional<String> getIpAddress() {
        return Optional.ofNullable(ipAddressProperty().get());
    }

    public IntegerProperty portProperty() {
        return integerPropertyOf(PORT, () -> Dbms.super.getPort().orElse(0));
    }

    @Override
    public OptionalInt getPort() {
        return OptionalUtil.ofNullable(portProperty().get());
    }

    public StringProperty usernameProperty() {
        return stringPropertyOf(USERNAME, () -> Dbms.super.getUsername().orElse(null));
    }

    @Override
    public Optional<String> getUsername() {
        return Optional.ofNullable(usernameProperty().get());
    }
    
    public ObservableList<SchemaProperty> schemasProperty() {
        return observableListOf(SCHEMAS, SchemaProperty::new);
    }

    @Override
    public BiFunction<Dbms, Map<String, Object>, SchemaProperty> schemaConstructor() {
        return SchemaProperty::new;
    }
    
    @Override
    protected DocumentProperty createDocument(String key, Map<String, Object> data) {
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
        final SchemaProperty created = new SchemaProperty(this, new ConcurrentHashMap<>());
        schemasProperty().add(created);
        return created;
    }
    
    @Override
    public String toString() {
        return toStringHelper(this);
    }
}