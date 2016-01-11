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

import com.speedment.config.db.Column;
import com.speedment.config.db.ForeignKey;
import com.speedment.config.db.Index;
import com.speedment.config.db.PrimaryKeyColumn;
import com.speedment.config.db.Schema;
import com.speedment.config.db.Table;
import static java.util.Collections.unmodifiableSet;
import java.util.Optional;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public final class ImmutableTable extends ImmutableDocument implements Table {

    private final boolean enabled;
    private final String name;
    private final Optional<String> alias;
    
    private final Set<ImmutableColumn> columns;
    private final Set<ImmutableIndex> indexes;
    private final Set<ImmutableForeignKey> foreignKeys;
    private final Set<ImmutablePrimaryKeyColumn> primaryKeyColumns;

    ImmutableTable(ImmutableSchema parent, Map<String, Object> table) {
        super(parent, table);
        this.enabled = (boolean) table.get(ENABLED);
        this.name    = (String) table.get(NAME);
        this.alias   = Optional.ofNullable((String) table.get(ALIAS));
        
        this.columns           = unmodifiableSet(Table.super.columns().map(ImmutableColumn.class::cast).collect(toSet()));
        this.indexes           = unmodifiableSet(Table.super.indexes().map(ImmutableIndex.class::cast).collect(toSet()));
        this.foreignKeys       = unmodifiableSet(Table.super.foreignKeys().map(ImmutableForeignKey.class::cast).collect(toSet()));
        this.primaryKeyColumns = unmodifiableSet(Table.super.primaryKeyColumns().map(ImmutablePrimaryKeyColumn.class::cast).collect(toSet()));
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
    public Stream<ImmutableColumn> columns() {
        return columns.stream();
    }

    @Override
    public Stream<ImmutableIndex> indexes() {
        return indexes.stream();
    }

    @Override
    public Stream<ImmutableForeignKey> foreignKeys() {
        return foreignKeys.stream();
    }

    @Override
    public Stream<ImmutablePrimaryKeyColumn> primaryKeyColumns() {
        return primaryKeyColumns.stream();
    }

    @Override
    public Optional<Schema> getParent() {
        return super.getParent().map(Schema.class::cast);
    }
}