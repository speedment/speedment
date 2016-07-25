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

import com.speedment.common.injector.Injector;
import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.component.DbmsHandlerComponent;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.parameter.DbmsType;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.util.OptionalUtil;
import com.speedment.runtime.internal.util.document.DocumentDbUtil;
import com.speedment.tool.component.DocumentPropertyComponent;
import com.speedment.tool.config.mutator.DbmsPropertyMutator;
import com.speedment.tool.config.mutator.DocumentPropertyMutator;
import com.speedment.tool.config.trait.HasAliasProperty;
import com.speedment.tool.config.trait.HasEnabledProperty;
import com.speedment.tool.config.trait.HasExpandedProperty;
import com.speedment.tool.config.trait.HasNameProperty;
import com.speedment.tool.property.DefaultIntegerPropertyItem;
import com.speedment.tool.property.DefaultStringPropertyItem;
import com.speedment.tool.property.DefaultTextAreaPropertyItem;
import com.speedment.tool.property.StringChoicePropertyItem;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import org.controlsfx.control.PropertySheet;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Stream;

import static com.speedment.runtime.internal.util.ImmutableListUtil.concat;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static javafx.collections.FXCollections.observableList;

/**
 *
 * @author  Emil Forslund
 * @since   2.3.0
 */
@Api(version = "3.0")
public final class DbmsProperty extends AbstractChildDocumentProperty<Project, DbmsProperty> 
    implements Dbms, HasEnabledProperty, HasExpandedProperty, HasNameProperty, HasAliasProperty {

    public DbmsProperty(Project parent) {
        super(parent);
    }
    
    public StringProperty typeNameProperty() {
        return stringPropertyOf(TYPE_NAME, () -> Dbms.super.getTypeName());
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
    
    protected IntegerBinding defaultPortProperty(DbmsHandlerComponent dbmsHandlerComponent) {
        return Bindings.createIntegerBinding(() -> 
            DocumentDbUtil.findDbmsType(dbmsHandlerComponent, this).getDefaultPort(),
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

    protected StringBinding defaultConnectionUrlProperty(DbmsHandlerComponent dbmsHandlerComponent) throws SpeedmentException {
        return Bindings.createStringBinding(() -> 
            DocumentDbUtil.findDbmsType(dbmsHandlerComponent, this).getConnectionUrlGenerator().from(this), 
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
    public Stream<PropertySheet.Item> getUiVisibleProperties(Injector injector) {
        
        final DbmsHandlerComponent dbmsHandler = injector.getOrThrow(DbmsHandlerComponent.class);
        final ObservableList<String> supportedTypes = observableList(
            dbmsHandler
                .supportedDbmsTypes()
                .map(DbmsType::getName)
                .collect(toList())
        );
        
        return Stream.of(HasEnabledProperty.super.getUiVisibleProperties(injector),
            HasNameProperty.super.getUiVisibleProperties(injector),
            HasAliasProperty.super.getUiVisibleProperties(injector),
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
                    defaultPortProperty(dbmsHandler),
                    "Port",                  
                    "The port of the database on the database host.",
                    editor -> {
                        editor.setMin(0); 
                        editor.setMax(65536);
                    }
                ),
                new DefaultStringPropertyItem(
                    usernameProperty(),
                    new SimpleStringProperty("root"),
                    "Username",
                    "The username to use when connecting to the database."
                ),
                new DefaultTextAreaPropertyItem(
                    connectionUrlProperty(),
                    defaultConnectionUrlProperty(dbmsHandler),
                    "Connection URL",
                    "The connection URL that should be used when establishing " +
                    "connection with the database. If this is set to Auto, the " +
                    "DbmsType will generate one."
                )
            )
        ).flatMap(identity());
    }
    
    @Override
    protected List<String> keyPathEndingWith(String key) {
        return concat(DocumentPropertyComponent.DBMSES, key);
    }

    @Override
    public StringProperty nameProperty() {
        return HasNameProperty.super.nameProperty();
    }
}