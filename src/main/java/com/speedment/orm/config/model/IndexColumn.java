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
package com.speedment.orm.config.model;

import com.speedment.orm.config.model.aspects.Ordinable;
import com.speedment.orm.annotations.Api;
import com.speedment.orm.config.model.aspects.Child;
import com.speedment.orm.config.model.impl.IndexColumnImpl;
import com.speedment.orm.config.model.parameters.OrderTypeable;
import java.util.function.Supplier;

/**
 *
 * @author pemi
 */
@Api(version = 0)
public interface IndexColumn extends ConfigEntity, Ordinable, Child<Index>,
    OrderTypeable {

    enum Holder {

        HOLDER;
        private Supplier<IndexColumn> provider = () -> new IndexColumnImpl();
    }

    static void setSupplier(Supplier<IndexColumn> provider) {
        Holder.HOLDER.provider = provider;
    }

    static IndexColumn newIndexColumn() {
        return Holder.HOLDER.provider.get();
    }

    @Override
    default Class<IndexColumn> getInterfaceMainClass() {
        return IndexColumn.class;
    }

    @Override
    default Class<Index> getParentInterfaceMainClass() {
        return Index.class;
    }

    default Column getColumn() {
        return ConfigEntityUtil.findColumnByName(this, ancestor(Table.class), getName());
    }
}
