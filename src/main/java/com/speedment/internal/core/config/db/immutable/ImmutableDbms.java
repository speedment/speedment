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
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.BiFunction;

/**
 *
 * @author Emil Forslund
 */
public final class ImmutableDbms extends ImmutableDocument implements Dbms {

    private final boolean enabled;
    private final String name;
    private final String typeName;
    private final Optional<String> ipAddress;
    private final OptionalInt port;
    private final Optional<String> username;

    public ImmutableDbms(ImmutableProject parent, Map<String, Object> dbms) {
        super(parent, dbms);

        this.enabled   = (boolean) dbms.get(ENABLED);
        this.name      = (String) dbms.get(NAME);
        this.typeName  = (String) dbms.get(TYPE_NAME);
        this.ipAddress = Optional.ofNullable((String) dbms.get(IP_ADDRESS));
        this.port      = OptionalUtil.ofNullable((Integer) dbms.get(PORT));
        this.username  = Optional.ofNullable((String) dbms.get(USERNAME));
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
    public Optional<Project> getParent() {
        return super.getParent().map(Project.class::cast);
    }
}
