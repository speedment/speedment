/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.gui.config;

import com.speedment.Speedment;
import com.speedment.config.Dbms;
import com.speedment.config.Project;
import com.speedment.config.Schema;
import com.speedment.config.aspects.Parent;
import com.speedment.config.parameters.DbmsType;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.config.utils.ConfigUtil;
import com.speedment.stream.MapStream;
import groovy.lang.Closure;
import static java.util.Collections.newSetFromMap;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Stream;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import static javafx.collections.FXCollections.observableSet;
import javafx.collections.ObservableSet;

/**
 *
 * @author Emil Forslund
 */
public final class DbmsProperty extends AbstractParentProperty<Dbms, Schema> implements Dbms, ChildHelper<Dbms, Project> {
    
    private final ObservableSet<Schema> schemaChildren;
    private final StringProperty ipAddress;
    private final IntegerProperty port;
    private final StringProperty username;
    private final StringProperty password;
    private final Property<DbmsType> dbmsType;
    private final StringProperty typeName;
    
    private Project parent;
    
    public DbmsProperty(Speedment speedment) {
        super(speedment);
        schemaChildren = observableSet(newSetFromMap(new ConcurrentSkipListMap<>()));
        ipAddress      = new SimpleStringProperty();
        port           = new SimpleIntegerProperty();
        username       = new SimpleStringProperty();
        password       = new SimpleStringProperty();
        dbmsType       = new SimpleObjectProperty<>();
        typeName       = new SimpleStringProperty();
    }
    
    public DbmsProperty(Speedment speedment, Dbms prototype) {
        super(speedment, prototype);
        schemaChildren = copyChildrenFrom(prototype, Schema.class, SchemaProperty::new);
        ipAddress      = new SimpleStringProperty(prototype.getIpAddress().orElse(null));
        port           = new SimpleIntegerProperty(prototype.getPort().orElse(0));
        username       = new SimpleStringProperty(prototype.getUsername().orElse(null));
        password       = new SimpleStringProperty(prototype.getPassword().orElse(null));
        dbmsType       = new SimpleObjectProperty<>(prototype.getType());
        typeName       = new SimpleStringProperty(prototype.getTypeName());
    }
    
    @Override
    public Optional<Project> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setParent(Parent<?> parent) {
        if (parent instanceof Project) {
            this.parent = (Project) parent;
        } else {
            throw wrongParentClass(parent.getClass());
        }
    }
    
    @Override
    public Optional<String> getIpAddress() {
        return Optional.ofNullable(ipAddress.get());
    }

    @Override
    public void setIpAddress(String ipAddress) {
        this.ipAddress.setValue(ipAddress);
    }
    
    public StringProperty ipAddressProperty() {
        return ipAddress;
    }

    @Override
    public Optional<Integer> getPort() {
        return Optional.ofNullable(port.getValue());
    }

    @Override
    public void setPort(Integer port) {
        this.port.setValue(port);
    }
    
    public IntegerProperty portProperty() {
        return port;
    }

    @Override
    public Optional<String> getUsername() {
        return Optional.ofNullable(username.get());
    }

    @Override
    public void setUsername(String username) {
        this.username.setValue(username);
    }
    
    public StringProperty usernameProperty() {
        return username;
    }

    @Override
    public Optional<String> getPassword() {
        return Optional.ofNullable(password.get());
    }

    @Override
    public void setPassword(String password) {
        this.password.setValue(password);
    }
    
    public StringProperty passwordProperty() {
        return password;
    }

    @Override
    public DbmsType getType() {
        return dbmsType.getValue();
    }

    @Override
    public void setType(DbmsType dbmsType) {
        this.dbmsType.setValue(dbmsType);
    }
    
    public Property<DbmsType> typeProperty() {
        return dbmsType;
    }

    @Override
    public String getTypeName() {
        return typeName.getValue();
    }

    @Override
    public void setTypeName(String name) {
        this.typeName.setValue(name);
    }
    
    public StringProperty typeNameProperty() {
        return typeName;
    }
    
    @Override
    public Schema addNewSchema() {
        final Schema schema = new SchemaProperty(getSpeedment());
        add(schema);
        return schema;
    }
    
    @Override
    public Schema schema(Closure<?> c) {
        return ConfigUtil.groovyDelegatorHelper(c, () -> addNewSchema());
    }
    
    @Override
    public Optional<Schema> add(Schema child) throws IllegalStateException {
        return schemaChildren.add(child) ? Optional.empty() : Optional.of(child);
    }

    @Override
    public Stream<Schema> stream() {
        return schemaChildren.stream();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Schema> Stream<T> streamOf(Class<T> childType) {
        requireNonNull(childType);
        
        if (Schema.class.isAssignableFrom(childType)) {
            return (Stream<T>) schemaChildren.stream();
        } else {
            throw wrongChildTypeException(childType);
        }
    }
    
    @Override
    public int count() {
        return schemaChildren.size();
    }

    @Override
    public int countOf(Class<? extends Schema> childType) {
        if (Schema.class.isAssignableFrom(childType)) {
            return schemaChildren.size();
        } else {
            throw wrongChildTypeException(childType);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Schema> T find(Class<T> childType, String name) throws SpeedmentException {
        requireNonNull(childType);
        requireNonNull(name);
        
        if (Schema.class.isAssignableFrom(childType)) {
            return (T) schemaChildren.stream().filter(child -> name.equals(child.getName()))
                .findAny().orElseThrow(() -> noChildWithNameException(childType, name));
        } else {
            throw wrongChildTypeException(childType);
        }
    }
}