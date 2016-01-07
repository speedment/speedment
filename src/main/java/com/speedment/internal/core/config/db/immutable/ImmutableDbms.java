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
package com.speedment.internal.core.config.db.immutable;

import com.speedment.Speedment;
import com.speedment.config.ImmutableDocument;
import com.speedment.config.db.Dbms;
import com.speedment.config.db.Project;
import com.speedment.config.db.Schema;
import com.speedment.config.db.parameters.DbmsType;
import java.util.Optional;
import java.util.stream.Stream;
import static com.speedment.internal.core.config.db.immutable.ImmutableUtil.throwNewUnsupportedOperationExceptionImmutable;
import java.util.OptionalInt;

/**
 *
 * @author pemi
 */
public final class ImmutableDbms extends ImmutableDocument implements Dbms {

    private final String typeName;
    private final Optional<String> ipAddress;
    private final OptionalInt port;
    private final Optional<String> username;

    public ImmutableDbms(ImmutableProject parent, Dbms dbms) {
        super(parent, dbms.getData());

        this.typeName  = dbms.getTypeName();
        this.ipAddress = dbms.getIpAddress().orElse(null);
        this.port      = dbms.getPort().isPresent() ? dbms.getPort().getAsInt() : null;
        this.username  = dbms.getUsername().orElse(null);
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public Optional<String> getIpAddress() {
        return Optional.ofNullable(ipAddress);
    }

    @Override
    public void setIpAddress(String ipAddress) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Optional<Integer> getPort() {
        return port;
    }

    @Override
    public void setPort(Integer port) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Optional<String> getUsername() {
        return username;
    }

    @Override
    public void setUsername(String name) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Optional<String> getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public void setParent(Parent<?> parent) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Optional<Project> getParent() {
        return parent;
    }

    @Override
    public ChildHolder<Schema> getChildren() {
        return children;
    }
    
    @Override
    public Stream<? extends Schema> stream() {
        return getChildren().stream().sorted(Nameable.COMPARATOR);
    }

    @Override
    public <T extends Schema> Stream<T> streamOf(Class<T> childClass) {
        if (Schema.class.isAssignableFrom(childClass)) {
            return getChildren().stream()
                .map(child -> {
                    @SuppressWarnings("unchecked")
                    final T cast = (T) child;
                    return cast;
                }).sorted(Nameable.COMPARATOR);
        } else {
            throw new IllegalArgumentException(
                getClass().getSimpleName() + 
                " does not have children of type " + 
                childClass.getSimpleName() + "."
            );
        }
    }

    @Override
    public Schema addNewSchema() {
        return throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Speedment getSpeedment() {
        return speedment;
    }

    @Override
    public Schema schema(Closure<?> c) {
        return throwNewUnsupportedOperationExceptionImmutable();
    }
}
