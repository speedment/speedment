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
import com.speedment.core.config.model.aspects.Child;
import com.speedment.core.config.model.impl.ColumnImpl;
import com.speedment.core.config.model.parameters.ColumnCompressionTypeable;
import com.speedment.core.config.model.parameters.FieldStorageTypeable;
import java.util.Optional;
import java.util.function.Supplier;

/**
 *
 * @author pemi
 */
@Api(version = "2.0")
public interface Column extends ConfigEntity, Ordinable, Child<Table>,
    FieldStorageTypeable,
    ColumnCompressionTypeable {

    enum Holder {

        HOLDER;
        private Supplier<Column> provider = () -> new ColumnImpl();
    }

    static void setSupplier(Supplier<Column> provider) {
        Holder.HOLDER.provider = provider;
    }

    static Column newColumn() {
        return Holder.HOLDER.provider.get();
    }

    @Override
    default Class<Column> getInterfaceMainClass() {
        return Column.class;
    }

    @Override
    default Class<Table> getParentInterfaceMainClass() {
        return Table.class;
    }

    @External(type = Boolean.class)
    Boolean isNullable();

    @External(type = Boolean.class)
    void setNullable(Boolean nullable);
    
    @External(type = Boolean.class)
    Boolean isAutoincrement();

    @External(type = Boolean.class)
    void setAutoincrement(Boolean nullable);
    
    @External(type = String.class)
    Optional<String> getAlias();

    @External(type = String.class)
    void setAlias(String alias);

    @External(type = Class.class)
    Class<?> getMapping();

    @External(type = Class.class)
    void setMapping(Class<?> mappedClass);
}
