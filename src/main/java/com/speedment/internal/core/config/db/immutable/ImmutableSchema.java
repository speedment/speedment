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
import com.speedment.config.db.Schema;
import com.speedment.config.db.Table;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 *
 * @author Emil Forslund
 */
public final class ImmutableSchema extends ImmutableDocument implements Schema {

    private final boolean enabled;
    private final String name;
    private final Optional<String> alias;
    private final boolean defaultSchema;

    public ImmutableSchema(ImmutableDbms parent, Map<String, Object> schema) {
        super(parent, schema);
        this.enabled       = (boolean) schema.get(ENABLED);
        this.name          = (String) schema.get(NAME);
        this.alias         = Optional.ofNullable((String) schema.get(ALIAS));
        this.defaultSchema = (boolean) schema.get(DEFAULT_SCHEMA);
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
}