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
import com.speedment.config.db.ForeignKeyColumn;
import com.speedment.config.db.Table;
import com.speedment.internal.core.config.db.ForeignKeyColumnImpl;
import com.speedment.internal.util.Lazy;
import static com.speedment.internal.util.document.DocumentUtil.toStringHelper;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Emil Forslund
 */
public final class ImmutableForeignKeyColumn extends ImmutableDocument implements ForeignKeyColumn {

    private final transient String name;
    private final transient int ordinalPosition;
    private final transient String foreignColumnName;
    private final transient String foreignTableName;
    
    private final transient Lazy<Column> foreignColumn;
    private final transient Lazy<Table> foreignTable;
    private final transient Lazy<Column> column;
  
    ImmutableForeignKeyColumn(ImmutableForeignKey parent, Map<String, Object> fkc) {
        super(parent, fkc);
        
        final ForeignKeyColumn prototype = new ForeignKeyColumnImpl(parent, fkc);
        
        this.name              = prototype.getName();
        this.ordinalPosition   = prototype.getOrdinalPosition();
        this.foreignTableName  = prototype.getForeignTableName();
        this.foreignColumnName = prototype.getForeignColumnName();
        
        this.foreignTable      = Lazy.create();
        this.foreignColumn     = Lazy.create();
        this.column            = Lazy.create();
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
    public Column findForeignColumn() {
        return foreignColumn.getOrCompute(ForeignKeyColumn.super::findForeignColumn);
    }

    @Override
    public Table findForeignTable() {
        return foreignTable.getOrCompute(ForeignKeyColumn.super::findForeignTable);
    }

    @Override
    public Column findColumn() {
        return column.getOrCompute(ForeignKeyColumn.super::findColumn);
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