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

import com.speedment.config.db.Column;
import com.speedment.config.db.ForeignKey;
import com.speedment.config.db.Index;
import com.speedment.config.db.PrimaryKeyColumn;
import com.speedment.config.db.Schema;
import com.speedment.config.db.Table;
import java.util.Optional;
import java.util.Map;
import java.util.function.BiFunction;

/**
 *
 * @author Emil Forslund
 */
public final class ImmutableTable extends ImmutableDocument implements Table {

    private final boolean enabled;
    private final String name;
    private final Optional<String> alias;

    public ImmutableTable(ImmutableSchema parent, Map<String, Object> table) {
        super(parent, table);
        this.enabled = (boolean) table.get(ENABLED);
        this.name    = (String) table.get(NAME);
        this.alias   = Optional.ofNullable((String) table.get(ALIAS));
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
    public BiFunction<Table, Map<String, Object>, ? extends Column> columnConstructor() {
        return (parent, map) -> new ImmutableColumn((ImmutableTable) parent, map);
    }

    @Override
    public BiFunction<Table, Map<String, Object>, ? extends Index> indexConstructor() {
        return (parent, map) -> new ImmutableIndex((ImmutableTable) parent, map);
    }

    @Override
    public BiFunction<Table, Map<String, Object>, ? extends ForeignKey> foreignKeyConstructor() {
        return (parent, map) -> new ImmutableForeignKey((ImmutableTable) parent, map);
    }

    @Override
    public BiFunction<Table, Map<String, Object>, ? extends PrimaryKeyColumn> primaryKeyColumnConstructor() {
        return (parent, map) -> new ImmutablePrimaryKeyColumn((ImmutableTable) parent, map);
    }

    @Override
    public Optional<Schema> getParent() {
        return super.getParent().map(Schema.class::cast);
    }
}