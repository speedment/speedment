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
package com.speedment.internal.core.config.db.immutable;

import com.speedment.config.db.Dbms;
import com.speedment.config.db.Schema;
import com.speedment.config.db.Table;
import com.speedment.internal.core.config.db.SchemaImpl;
import static java.util.Collections.unmodifiableList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public final class ImmutableSchema extends ImmutableDocument implements Schema {

    private final boolean enabled;
    private final String name;
    private final Optional<String> alias;
    private final boolean defaultSchema;
    
    private final List<ImmutableTable> tables;

    ImmutableSchema(ImmutableDbms parent, Map<String, Object> schema) {
        super(parent, schema);
        
        final Schema prototype = new SchemaImpl(parent, schema);
        
        this.enabled       = prototype.isEnabled();
        this.name          = prototype.getName();
        this.alias         = prototype.getAlias();
        this.defaultSchema = prototype.isDefaultSchema();
        
        this.tables = unmodifiableList(Schema.super.tables().map(ImmutableTable.class::cast).collect(toList()));
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
    public boolean isDefaultSchema() {
        return defaultSchema;
    }

    @Override
    public Optional<Dbms> getParent() {
        return super.getParent().map(Dbms.class::cast);
    }

    @Override
    public BiFunction<Schema, Map<String, Object>, ? extends Table> tableConstructor() {
        return (parent, map) -> new ImmutableTable((ImmutableSchema) parent, map);
    }

    @Override
    public Stream<ImmutableTable> tables() {
        return tables.stream();
    }
}