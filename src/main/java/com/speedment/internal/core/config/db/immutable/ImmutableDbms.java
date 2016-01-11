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

import com.speedment.config.db.Dbms;
import com.speedment.config.db.Project;
import com.speedment.internal.core.stream.OptionalUtil;
import static java.util.Collections.unmodifiableSet;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.BiFunction;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public final class ImmutableDbms extends ImmutableDocument implements Dbms {

    private final boolean enabled;
    private final String name;
    private final Optional<String> alias;
    private final String typeName;
    private final Optional<String> ipAddress;
    private final OptionalInt port;
    private final Optional<String> username;
    
    private final Set<ImmutableSchema> schemas;

    ImmutableDbms(ImmutableProject parent, Map<String, Object> dbms) {
        super(parent, dbms);

        this.enabled   = (boolean) dbms.get(ENABLED);
        this.name      = (String) dbms.get(NAME);
        this.alias     = Optional.ofNullable((String) dbms.get(ALIAS));
        this.typeName  = (String) dbms.get(TYPE_NAME);
        this.ipAddress = Optional.ofNullable((String) dbms.get(IP_ADDRESS));
        this.port      = OptionalUtil.ofNullable((Integer) dbms.get(PORT));
        this.username  = Optional.ofNullable((String) dbms.get(USERNAME));
        
        this.schemas   = unmodifiableSet(Dbms.super.schemas().map(ImmutableSchema.class::cast).collect(toSet()));
    }

    @Override
    public boolean isEnabled() {
        return enabled;
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
    public BiFunction<Dbms, Map<String, Object>, ImmutableSchema> schemaConstructor() {
        return (parent, map) -> new ImmutableSchema((ImmutableDbms) parent, map);
    }

    @Override
    public Stream<ImmutableSchema> schemas() {
        return schemas.stream();
    }

    @Override
    public Optional<Project> getParent() {
        return super.getParent().map(Project.class::cast);
    }
}
