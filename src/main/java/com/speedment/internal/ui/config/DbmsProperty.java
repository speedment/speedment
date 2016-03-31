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
import com.speedment.config.db.Dbms;
import static com.speedment.config.db.Dbms.IP_ADDRESS;
import static com.speedment.config.db.Dbms.PORT;
import static com.speedment.config.db.Dbms.USERNAME;
import com.speedment.config.db.Project;
import com.speedment.config.db.mutator.DbmsMutator;
import com.speedment.config.db.parameters.DbmsType;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.stream.OptionalUtil;
import com.speedment.internal.ui.config.mutator.DbmsPropertyMutator;
import com.speedment.internal.ui.config.mutator.DocumentPropertyMutator;
import static com.speedment.internal.util.ImmutableListUtil.concat;
import com.speedment.ui.config.trait.HasEnabledProperty;
import com.speedment.ui.config.trait.HasExpandedProperty;
import com.speedment.ui.config.trait.HasNameProperty;
import com.speedment.ui.config.db.StringChoicePropertyItem;
import com.speedment.ui.config.db.DefaultIntegerPropertyItem;
import com.speedment.ui.config.db.DefaultStringPropertyItem;
import com.speedment.ui.config.db.DefaultTextAreaPropertyItem;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import org.controlsfx.control.PropertySheet;
import static javafx.collections.FXCollections.observableList;

/**
 *
 * @author Emil Forslund
 */
public final class DbmsProperty extends AbstractChildDocumentProperty<Project, DbmsProperty> 
    implements Dbms, HasEnabledProperty, HasExpandedProperty, HasNameProperty {

    public DbmsProperty(Project parent) {
        super(parent);
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
    
    protected IntegerBinding defaultPortProperty(Speedment speedment) {
        return Bindings.createIntegerBinding(() -> 
            Dbms.super.defaultPort(speedment),
            typeNameProperty()
        );
    }

    @Override
    public OptionalInt getPort() {
        return OptionalUtil.ofNullable(portProperty().get());
    }
    
    public StringProperty connectionUrlProperty() {
        return stringPropertyOf(CONNECTION_URL,  () -> Dbms.super.getConnectionUrl().orElse(null));
    }

    @Override
    public Optional<String> getConnectionUrl() {
        return Optional.ofNullable(connectionUrlProperty().get());
    }

    protected StringBinding defaultConnectionUrlProperty(Speedment speedment) throws SpeedmentException {
        return Bindings.createStringBinding(() -> 
            Dbms.super.defaultConnectionUrl(speedment), 
            typeNameProperty(),
            ipAddressProperty(),
            portProperty(),
            usernameProperty()
        );
    }

    public StringProperty usernameProperty() {
        return stringPropertyOf(USERNAME, () -> Dbms.super.getUsername().orElse(null));
    }

    @Override
    public Optional<String> getUsername() {
        return Optional.ofNullable(usernameProperty().get());
    }
    
    public ObservableList<SchemaProperty> schemasProperty() {
        return observableListOf(SCHEMAS);
    }

    @Override
    public Stream<SchemaProperty> schemas() {
        return schemasProperty().stream();
    }
    
    @Override
    public DbmsPropertyMutator mutator() {
        return DocumentPropertyMutator.of(this);
    }
    
    @Override
    public Stream<PropertySheet.Item> getUiVisibleProperties(Speedment speedment) {
        
        final ObservableList<String> supportedTypes = observableList(
            speedment.getDbmsHandlerComponent()
                .supportedDbmsTypes()
                .map(DbmsType::getName)
                .collect(toList())
        );
        
        return Stream.of(HasEnabledProperty.super.getUiVisibleProperties(speedment),
            HasNameProperty.super.getUiVisibleProperties(speedment),
            Stream.of(new StringChoicePropertyItem(
                    supportedTypes,
                    typeNameProperty(),
                    "Dbms Type", 
                    "Which type of database this is. If the type you are looking " +
                    "for is missing, make sure it has been loaded properly in " +
                    "the pom.xml-file."
                ), 
                new DefaultStringPropertyItem(
                    ipAddressProperty(), 
                    new SimpleStringProperty("127.0.0.1"),
                    "IP Address",
                    "The ip of the database host."
                ),
                new DefaultIntegerPropertyItem(
                    portProperty(),
                    defaultPortProperty(speedment),
                    "Port",                  
                    "The port of the database on the database host."
                ),
                new DefaultStringPropertyItem(
                    usernameProperty(),
                    new SimpleStringProperty("root"),
                    "Username",
                    "The username to use when connecting to the database."
                ),
                new DefaultTextAreaPropertyItem(
                    connectionUrlProperty(),
                    defaultConnectionUrlProperty(speedment),
                    "Connection URL",
                    "The connection URL that should be used when establishing " +
                    "connection with the database. If this is set to Auto, the " +
                    "DbmsType will generate one."
                )
            )
        ).flatMap(s -> s);
    }
    
    @Override
    protected List<String> keyPathEndingWith(String key) {
        return concat(DocumentPropertyComponent.DBMSES, key);
    }
}