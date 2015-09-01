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
package com.speedment.internal.core.config;

import com.speedment.config.Column;
import com.speedment.config.ForeignKey;
import com.speedment.config.ForeignKeyColumn;
import com.speedment.config.Schema;
import com.speedment.config.Table;
import com.speedment.config.aspects.Parent;
import static com.speedment.internal.core.config.utils.ConfigUtil.findColumnByName;
import static com.speedment.internal.core.config.utils.ConfigUtil.findTableByName;
import static com.speedment.internal.core.config.utils.ConfigUtil.thereIsNo;
import com.speedment.internal.core.config.aspects.ColumnableHelper;
import com.speedment.internal.util.Cast;
import java.util.Optional;

/**
 *
 * @author pemi
 */
public final class ForeignKeyColumnImpl extends AbstractOrdinalConfigEntity implements ForeignKeyColumn, ColumnableHelper {

    private ForeignKey parent;
    private String foreignColumnName;
    private String foreignTableName;

    @Override
    protected void setDefaults() {}

    @Override
    public String getForeignColumnName() {
        return foreignColumnName;
    }

    @Override
    public void setForeignColumnName(String foreignColumnName) {
        this.foreignColumnName = foreignColumnName;
    }

    @Override
    public String getForeignTableName() {
        return foreignTableName;
    }

    @Override
    public void setForeignTableName(String foreignTableName) {
        this.foreignTableName = foreignTableName;
    }

    @Override
    public void setParent(Parent<?> parent) {
        this.parent = Cast.castOrFail(parent, ForeignKey.class);
    }

    @Override
    public Optional<ForeignKey> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    public Column getForeignColumn() {
        return findColumnByName(
            getForeignTable(), 
            getForeignColumnName()
        );
    }

    @Override
    public Table getForeignTable() {
        return findTableByName(
            ancestor(Schema.class).orElseThrow(
                thereIsNo(
                    Table.class, 
                    ForeignKeyColumn.class, 
                    getForeignTableName()
                )
            ), 
            getForeignTableName()
        );
    }
}