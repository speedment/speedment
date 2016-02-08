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

import com.speedment.config.db.Schema;
import com.speedment.config.db.Table;
import com.speedment.internal.core.config.db.TableImpl;
import static java.util.Collections.unmodifiableList;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public final class ImmutableTable extends ImmutableDocument implements Table {

    private final transient boolean enabled;
    private final transient String name;
    private final transient Optional<String> alias;
    
    private final transient List<ImmutableColumn> columns;
    private final transient List<ImmutableIndex> indexes;
    private final transient List<ImmutableForeignKey> foreignKeys;
    private final transient List<ImmutablePrimaryKeyColumn> primaryKeyColumns;

    ImmutableTable(ImmutableSchema parent, Map<String, Object> table) {
        super(parent, table);
        
        final Table prototype = new TableImpl(parent, table);
        
        this.enabled = prototype.isEnabled();
        this.name    = prototype.getName();
        this.alias   = prototype.getAlias();
        
        this.columns           = unmodifiableList(super.children(COLUMNS, ImmutableColumn::new).collect(toList()));
        this.indexes           = unmodifiableList(super.children(INDEXES, ImmutableIndex::new).collect(toList()));
        this.foreignKeys       = unmodifiableList(super.children(FOREIGN_KEYS, ImmutableForeignKey::new).collect(toList()));
        this.primaryKeyColumns = unmodifiableList(super.children(PRIMARY_KEY_COLUMNS, ImmutablePrimaryKeyColumn::new).collect(toList()));
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