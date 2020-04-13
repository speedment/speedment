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

import com.speedment.runtime.config.*;
import com.speedment.runtime.config.internal.TableImpl;

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
public final class ImmutableTable extends ImmutableDocument implements Table {

    private final boolean enabled;
    private final String id;
    private final String name;
    private final String alias;
    private final String packageName;
    private final boolean view;
    
    private final List<Column> columns;
    private final List<Index> indexes;
    private final List<ForeignKey> foreignKeys;
    private final List<PrimaryKeyColumn> primaryKeyColumns;

    ImmutableTable(ImmutableSchema parent, Map<String, Object> table) {
        super(parent, table);
        final Table prototype = new TableImpl(parent, table);
        this.enabled     = prototype.isEnabled();
        this.id          = prototype.getId();
        this.name        = prototype.getName();
        this.alias       = prototype.getAlias().orElse(null);
        this.packageName = prototype.getPackageName().orElse(null);
        this.view        = prototype.isView();
        this.columns           = unmodifiableList(super.children(TableUtil.COLUMNS, ImmutableColumn::new).collect(toList()));
        this.indexes           = unmodifiableList(super.children(TableUtil.INDEXES, ImmutableIndex::new).collect(toList()));
        this.foreignKeys       = unmodifiableList(super.children(TableUtil.FOREIGN_KEYS, ImmutableForeignKey::new).collect(toList()));
        this.primaryKeyColumns = unmodifiableList(super.children(TableUtil.PRIMARY_KEY_COLUMNS, ImmutablePrimaryKeyColumn::new).collect(toList()));
    }

    @Override
    public boolean isEnabled() { return enabled; }
   
    @Override
    public String getId() { return id; }

    @Override
    public String getName() { return name; }

    @Override
    public Optional<String> getAlias() { return Optional.ofNullable(alias); }

    @Override
    public Optional<String> getPackageName() { return Optional.ofNullable(packageName); }

    @Override
    public boolean isView() { return view; }

    @Override
    public Stream<Column> columns() {
        return columns.stream();
    }

    @Override
    public Stream<Index> indexes() {
        return indexes.stream();
    }

    @Override
    public Stream<ForeignKey> foreignKeys() {
        return foreignKeys.stream();
    }

    @Override
    public Stream<PrimaryKeyColumn> primaryKeyColumns() {
        return primaryKeyColumns.stream();
    }

    @Override
    public Optional<Schema> getParent() {
        return super.getParent().map(Schema.class::cast);
    }
}