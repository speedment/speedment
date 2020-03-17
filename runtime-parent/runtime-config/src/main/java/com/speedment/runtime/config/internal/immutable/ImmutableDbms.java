/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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

import static com.speedment.common.invariant.NullUtil.requireKeys;
import static com.speedment.runtime.config.util.DocumentUtil.toStringHelper;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.DbmsUtil;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Schema;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public final class ImmutableDbms extends ImmutableDocument implements Dbms {

    private final boolean enabled;
    private final String id;
    private final String name;
    private final String alias;
    private final String typeName;
    private final String ipAddress;
    private final OptionalInt port;
    private final String username;
    private final String serverName;
    private final List<Schema> schemas;

    ImmutableDbms(Project parent, Map<String, Object> dbms) {
        super(parent, requireKeys(dbms, DbmsUtil.TYPE_NAME));
        final Dbms prototype = Dbms.create(parent, dbms);
        this.enabled    = prototype.isEnabled();
        this.id         = prototype.getId();
        this.name       = prototype.getName();
        this.alias      = prototype.getAlias().orElse(null);
        this.typeName   = prototype.getTypeName();
        this.ipAddress  = prototype.getIpAddress().orElse(null);
        this.port       = prototype.getPort();
        this.username   = prototype.getUsername().orElse(null);
        this.serverName = prototype.getServerName().orElse(null);
        this.schemas    = unmodifiableList(super.children(DbmsUtil.SCHEMAS, ImmutableSchema::new).collect(toList()));
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<String> getAlias() {
        return Optional.ofNullable(alias);
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
    public OptionalInt getPort() {
        return port;
    }

    @Override
    public Optional<String> getUsername() {
        return Optional.ofNullable(username);
    }

    @Override
    public Optional<String> getServerName() {
        return Optional.ofNullable(serverName);
    }

    @Override
    public Stream<Schema> schemas() {
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
