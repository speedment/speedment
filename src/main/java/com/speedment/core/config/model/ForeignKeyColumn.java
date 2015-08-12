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
package com.speedment.core.config.model;

import com.speedment.core.config.model.aspects.Ordinable;
import com.speedment.core.annotations.Api;
import static com.speedment.core.config.model.ConfigUtil.findColumnByName;
import static com.speedment.core.config.model.ConfigUtil.findTableByName;
import static com.speedment.core.config.model.ConfigUtil.thereIsNo;
import com.speedment.core.config.model.aspects.Child;
import com.speedment.core.config.model.aspects.Columnable;
import com.speedment.core.config.model.aspects.Enableable;
import com.speedment.core.config.model.aspects.Node;
import com.speedment.core.config.model.impl.ForeignKeyColumnImpl;
import java.util.function.Supplier;

/**
 *
 * @author pemi
 */
@Api(version = "2.0")
public interface ForeignKeyColumn extends Node, Enableable, Ordinable, 
    Columnable, Child<ForeignKey> {

    enum Holder { HOLDER;
        private Supplier<ForeignKeyColumn> provider = ForeignKeyColumnImpl::new;
    }

    static void setSupplier(Supplier<ForeignKeyColumn> provider) {
        Holder.HOLDER.provider = provider;
    }

    static ForeignKeyColumn newForeignKeyColumn() {
        return Holder.HOLDER.provider.get();
    }

    @Override
    default Class<ForeignKeyColumn> getInterfaceMainClass() {
        return ForeignKeyColumn.class;
    }

    @Override
    default Class<ForeignKey> getParentInterfaceMainClass() {
        return ForeignKey.class;
    }

    @External(type = String.class)
    String getForeignColumnName();

    @External(type = String.class)
    void setForeignColumnName(String foreignColumnName);

    @External(type = String.class)
    String getForeignTableName();

    @External(type = String.class)
    void setForeignTableName(String foreignTableName);

    default Column getForeignColumn() {
        return findColumnByName(
            getForeignTable(), 
            getForeignColumnName()
        );
    }

    default Table getForeignTable() {
        return findTableByName(
            ancestor(Schema.class).orElseThrow(
                thereIsNo(Table.class, ForeignKeyColumn.class, getForeignTableName())
            ), 
            getForeignTableName()
        );
    }
}
