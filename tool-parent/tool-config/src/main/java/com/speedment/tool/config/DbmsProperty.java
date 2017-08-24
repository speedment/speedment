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
import com.speedment.runtime.config.Project;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.util.DatabaseUtil;
import com.speedment.runtime.core.util.OptionalUtil;
import com.speedment.tool.config.component.DocumentPropertyComponent;
import com.speedment.tool.config.mutator.DbmsPropertyMutator;
import com.speedment.tool.config.mutator.DocumentPropertyMutator;
import com.speedment.tool.config.trait.*;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Stream;

import static com.speedment.runtime.core.internal.util.ImmutableListUtil.concat;

/**
 *
 * @author  Emil Forslund
 * @since   2.3.0
 */
public final class DbmsProperty extends AbstractChildDocumentProperty<Project, DbmsProperty> 
    implements Dbms, 
    HasEnabledProperty, 
    HasExpandedProperty, 
    HasIdProperty,
    HasNameProperty, 
    HasAliasProperty {

    public DbmsProperty(Project parent) {
        super(parent);
    }
    
    public StringProperty typeNameProperty() {
        return stringPropertyOf(TYPE_NAME, () -> getAsString(TYPE_NAME).orElse(null));
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
            DatabaseUtil.findDbmsType(dbmsHandlerComponent, this).getDefaultPort(),
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

    protected StringBinding defaultConnectionUrlProperty(DbmsHandlerComponent dbmsHandlerComponent) {
        return Bindings.createStringBinding(() -> 
            DatabaseUtil.findDbmsType(dbmsHandlerComponent, this).getConnectionUrlGenerator().from(this), 
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
    protected List<String> keyPathEndingWith(String key) {
        return concat(DocumentPropertyComponent.DBMSES, key);
    }

    @Override
    public StringProperty nameProperty() {
        return HasNameProperty.super.nameProperty();
    }
    
}