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
package com.speedment.internal.core.config.immutable;

import com.speedment.internal.core.config.*;
import com.speedment.Speedment;
import com.speedment.config.Dbms;
import com.speedment.config.Project;
import com.speedment.config.Schema;
import com.speedment.config.aspects.Parent;
import com.speedment.config.parameters.DbmsType;
import com.speedment.internal.core.config.aspects.DbmsTypeableHelper;
import groovy.lang.Closure;
import java.util.Optional;
import static com.speedment.internal.core.config.immutable.ImmutableUtil.throwNewUnsupportedOperationExceptionImmutable;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
public final class ImmutableDbms extends ImmutableAbstractNamedConfigEntity implements Dbms, DbmsTypeableHelper, ImmutableParentHelper<Schema> {

    private final Speedment speedment;
    private final Optional<Project> parent;
    private final ChildHolder<Schema> children;
    private final DbmsType type;
    private final Optional<String> ipAddress;
    private final Optional<Integer> port;
    private final Optional<String> username, password;

    public ImmutableDbms(Project parent, Dbms dbms) {
        super(requireNonNull(dbms).getName(), dbms.isEnabled());
        requireNonNull(parent);
        // Members
        this.speedment = parent.getSpeedment();
        this.parent = Optional.of(parent);
        this.type = dbms.getType();
        this.ipAddress = dbms.getIpAddress();
        this.port = dbms.getPort();
        this.username = dbms.getUsername();
        this.password = dbms.getPassword();
        // Children
        children = childHolderOf(Schema.class, dbms.stream().map(p -> new ImmutableSchema(this, p)));
    }

    @Override
    public DbmsType getType() {
        return type;
    }

    @Override
    public void setType(DbmsType dbmsType) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Optional<String> getIpAddress() {
        return ipAddress;
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
