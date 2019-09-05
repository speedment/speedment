/*
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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

import com.speedment.common.lazy.LazyReference;
import com.speedment.runtime.config.ForeignKey;
import com.speedment.runtime.config.ForeignKeyColumn;
import com.speedment.runtime.config.ForeignKeyColumnUtil;
import com.speedment.runtime.config.internal.ForeignKeyColumnImpl;

import java.util.Map;
import java.util.Optional;

import static com.speedment.common.invariant.NullUtil.requireKeys;
import static com.speedment.runtime.config.util.DocumentUtil.toStringHelper;

/**
 *
 * @author Emil Forslund
 */
public final class ImmutableForeignKeyColumn extends ImmutableDocument implements ForeignKeyColumn {

    private final String id;
    private final String name;
    private final int ordinalPosition;
    private final String foreignColumnName;
    private final String foreignTableName;
    
    private final LazyReference<Optional<ImmutableColumn>> foreignColumn;
    private final LazyReference<Optional<ImmutableTable>> foreignTable;
    private final LazyReference<Optional<ImmutableColumn>> column;
  
    ImmutableForeignKeyColumn(ImmutableForeignKey parent, Map<String, Object> fkc) {
        super(parent, requireKeys(fkc, ForeignKeyColumnUtil.FOREIGN_COLUMN_NAME, ForeignKeyColumnUtil.FOREIGN_TABLE_NAME));
        
        final ForeignKeyColumn prototype = new ForeignKeyColumnImpl(parent, fkc);
    
        this.id                = prototype.getId();
        this.name              = prototype.getName();
        this.ordinalPosition   = prototype.getOrdinalPosition();
        this.foreignTableName  = prototype.getForeignTableName();
        this.foreignColumnName = prototype.getForeignColumnName();
        
        this.foreignTable      = LazyReference.create();
        this.foreignColumn     = LazyReference.create();
        this.column            = LazyReference.create();
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
    public int getOrdinalPosition() {
        return ordinalPosition;
    }

    @Override
    public String getForeignColumnName() {
        return foreignColumnName;
    }

    @Override
    public String getForeignTableName() {
        return foreignTableName;
    }

    @Override
    public Optional<ImmutableColumn> findForeignColumn() {
        return foreignColumn.getOrCompute(() ->
            ForeignKeyColumn.super.findForeignColumn()
                .map(ImmutableColumn.class::cast)
        );
    }

    @Override
    public Optional<ImmutableTable> findForeignTable() {
        return foreignTable.getOrCompute(() ->
            ForeignKeyColumn.super.findForeignTable()
                .map(ImmutableTable.class::cast)
        );
    }

    @Override
    public Optional<ImmutableColumn> findColumn() {
        return column.getOrCompute(() ->
            ForeignKeyColumn.super.findColumn()
                .map(ImmutableColumn.class::cast)
        );
    }
    
    @Override
    public Optional<ForeignKey> getParent() {
        return super.getParent().map(ForeignKey.class::cast);
    }
    
    @Override
    public String toString() {
        return toStringHelper(this);
    }
}