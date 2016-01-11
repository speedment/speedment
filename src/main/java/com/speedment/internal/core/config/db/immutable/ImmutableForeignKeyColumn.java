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
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Emil Forslund
 */
public final class ImmutableForeignKeyColumn extends ImmutableDocument implements ForeignKeyColumn {

    private final String name;
    private final int ordinalPosition;
    private final String foreignColumnName;
    private final String foreignTableName;
    private final Column foreignColumn;
    private final Table foreignTable;
    private final Column column;
  
    ImmutableForeignKeyColumn(ImmutableForeignKey parent, Map<String, Object> fkc) {
        super(parent, fkc);
        
        this.name              = (String) fkc.get(NAME);
        this.ordinalPosition   = (int) fkc.get(ORDINAL_POSITION);
        this.foreignColumnName = (String) fkc.get(FOREIGN_COLUMN_NAME);
        this.foreignTableName  = (String) fkc.get(FOREIGN_TABLE_NAME);
        this.foreignColumn     = ForeignKeyColumn.super.findForeignColumn();
        this.foreignTable      = ForeignKeyColumn.super.findForeignTable();
        this.column            = ForeignKeyColumn.super.findColumn();
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
        return foreignColumn;
    }

    @Override
    public Table findForeignTable() {
        return foreignTable;
    }

    @Override
    public Column findColumn() {
        return column;
    }
    
    @Override
    public Optional<ForeignKey> getParent() {
        return super.getParent().map(ForeignKey.class::cast);
    }
}