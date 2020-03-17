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

import com.speedment.runtime.config.ForeignKey;
import com.speedment.runtime.config.ForeignKeyColumn;
import com.speedment.runtime.config.ForeignKeyUtil;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.internal.ForeignKeyImpl;

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
public final class ImmutableForeignKey extends ImmutableDocument implements ForeignKey {

    private final String id;
    private final String name;
    private final boolean enabled;
    private final List<ForeignKeyColumn> foreignKeyColumns;

    ImmutableForeignKey(ImmutableTable parent, Map<String, Object> data) {
        super(parent, data);
        final ForeignKey prototype = new ForeignKeyImpl(parent, data);
        this.id      = prototype.getId();
        this.name    = prototype.getName();
        this.enabled = prototype.isEnabled();
        this.foreignKeyColumns = unmodifiableList(super.children(ForeignKeyUtil.FOREIGN_KEY_COLUMNS, ImmutableForeignKeyColumn::new).collect(toList()));
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
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public Stream<ForeignKeyColumn> foreignKeyColumns() {
        return foreignKeyColumns.stream();
    }
    
    @Override
    public Optional<Table> getParent() {
        return super.getParent().map(Table.class::cast);
    }
}