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

import com.speedment.config.ImmutableDocument;
import com.speedment.config.db.Column;
import com.speedment.config.db.ForeignKey;
import com.speedment.config.db.ForeignKeyColumn;
import com.speedment.config.db.Schema;
import com.speedment.config.db.Table;
import static com.speedment.internal.core.config.db.immutable.ImmutableUtil.throwNewUnsupportedOperationExceptionImmutable;
import static java.util.Collections.unmodifiableMap;
import java.util.Optional;

/**
 *
 * @author pemi
 */
public final class ImmutableForeignKeyColumn extends ImmutableDocument implements ForeignKeyColumn {

    private final Column column;
    private final String foreignColumnName;
    private final String foreignTableName;
    private final Column foreignColumn;
    private final Table foreignTable;
    

    public ImmutableForeignKeyColumn(ImmutableForeignKey parent, ForeignKeyColumn fkc) {
        super(parent, unmodifiableMap(fkc.getData()));
        
        this.foreignColumnName = fkc.getForeignColumnName();
        this.foreignTableName  = fkc.getForeignTableName();
        this.foreignColumn     = fkc.findForeignColumn();
    }

    @Override
    public String getForeignColumnName() {
        return foreignColumnName;
    }

    @Override
    public void setForeignColumnName(String foreignColumnName) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public String getForeignTableName() {
        return foreignTableName;
    }

    @Override
    public void setForeignTableName(String foreignTableName) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public void setParent(Parent<?> parent) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Optional<ForeignKey> getParent() {
        return parent;
    }

    @Override
    public Column getForeignColumn() {
        return foreignColumn;
    }

    @Override
    public Table getForeignTable() {
        return foreignTable;
    }

    @Override
    public void resolve() {
        foreignTable = ancestor(Schema.class).orElseThrow(
                thereIsNo(
                        Table.class,
                        Schema.class,
                        getForeignTableName()
                )
        ).find(Table.class, getForeignTableName());
        foreignColumn = foreignTable.findColumn(foreignColumnName);
    }

}
