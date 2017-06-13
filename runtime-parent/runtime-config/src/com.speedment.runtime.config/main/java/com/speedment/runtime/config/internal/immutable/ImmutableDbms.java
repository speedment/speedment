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
package com.speedment.runtime.config.internal.immutable;

import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.internal.DbmsImpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Stream;

import static com.speedment.common.invariant.NullUtil.requireKeys;
import static com.speedment.runtime.config.util.DocumentUtil.toStringHelper;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Emil Forslund
 */
public final class ImmutableDbms extends ImmutableDocument implements Dbms {

    private final transient boolean enabled;
    private final transient String id;
    private final transient String name;
    private final transient Optional<String> alias;
    private final transient String typeName;
    private final transient Optional<String> ipAddress;
    private final transient OptionalInt port;
    private final transient Optional<String> username;
    
    private final transient List<ImmutableSchema> schemas;

    ImmutableDbms(ImmutableProject parent, Map<String, Object> dbms) {
        super(parent, requireKeys(dbms, Dbms.TYPE_NAME));

        final Dbms prototype = new DbmsImpl(parent, dbms);
        
        this.enabled   = prototype.isEnabled();
        this.id        = prototype.getId();
        this.name      = prototype.getName();
        this.alias     = prototype.getAlias();
        this.typeName  = prototype.getTypeName();
        this.ipAddress = prototype.getIpAddress();
        this.port      = prototype.getPort();
        this.username  = prototype.getUsername();
        
        this.schemas   = unmodifiableList(super.children(SCHEMAS, ImmutableSchema::new).collect(toList()));
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<String> getAlias() {
        return alias;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public Optional<String> getIpAddress() {
        return ipAddress;
    }

    @Override
    public OptionalInt getPort() {
        return port;
    }

    @Override
    public Optional<String> getUsername() {
        return username;
    }

    @Override
    public Stream<ImmutableSchema> schemas() {
        return schemas.stream();
    }

    @Override
    public Optional<Project> getParent() {
        return super.getParent().map(Project.class::cast);
    }
    
    @Override
    public String toString() {
        return toStringHelper(this);
    }
}