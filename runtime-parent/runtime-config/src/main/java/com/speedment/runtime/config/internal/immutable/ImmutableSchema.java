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

import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.config.SchemaUtil;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.internal.SchemaImpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Emil Forslund
 */
public final class ImmutableSchema extends ImmutableDocument implements Schema {

    private final boolean enabled;
    private final String id;
    private final String name;
    private final String alias;
    private final boolean defaultSchema;
    
    private final List<Table> tables;

    ImmutableSchema(ImmutableDbms parent, Map<String, Object> schema) {
        super(parent, schema);
        final Schema prototype = new SchemaImpl(parent, schema);
        this.enabled       = prototype.isEnabled();
        this.id            = prototype.getId();
        this.name          = prototype.getName();
        this.alias         = prototype.getAlias().orElse(null);
        this.defaultSchema = prototype.isDefaultSchema();
        this.tables = unmodifiableList(super.children(SchemaUtil.TABLES, ImmutableTable::new).collect(toList()));
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
    public boolean isDefaultSchema() {
        return defaultSchema;
    }

    @Override
    public Optional<Dbms> getParent() {
        return super.getParent().map(Dbms.class::cast);
    }

    @Override
    public Stream<Table> tables() {
        return tables.stream();
    }
}