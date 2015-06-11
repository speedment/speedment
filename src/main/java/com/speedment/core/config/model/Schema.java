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

import com.speedment.core.annotations.Api;
import com.speedment.core.config.model.aspects.Parent;
import com.speedment.core.config.model.aspects.Child;
import com.speedment.core.config.model.impl.SchemaImpl;
import com.speedment.core.config.model.parameters.ColumnCompressionTypeable;
import com.speedment.core.config.model.parameters.FieldStorageTypeable;
import com.speedment.core.config.model.parameters.StorageEngineTypeable;
import groovy.lang.Closure;
import java.util.Optional;
import java.util.function.Supplier;

/**
 *
 * @author pemi
 */
@Api(version = 0)
public interface Schema extends ConfigEntity, Child<Dbms>, Parent<Table>,
        FieldStorageTypeable,
        ColumnCompressionTypeable,
        StorageEngineTypeable {

    enum Holder {

        HOLDER;
        private Supplier<Schema> provider = () -> new SchemaImpl();
    }

    static void setSupplier(Supplier<Schema> provider) {
        Holder.HOLDER.provider = provider;
    }

    static Schema newSchema() {
        return Holder.HOLDER.provider.get();
    }

    @Override
    default Class<Schema> getInterfaceMainClass() {
        return Schema.class;
    }

    @Override
    default Class<Dbms> getParentInterfaceMainClass() {
        return Dbms.class;
    }

    default Table addNewTable() {
        final Table e = Table.newTable();
        add(e);
        return e;
    }

    @External(type = Boolean.class)
    Boolean isDefaultSchema();

    @External(type = Boolean.class)
    void setDefaultSchema(Boolean defaultSchema);

    @External(type = String.class)
    Optional<String> getCatalogName();

    @External(type = String.class)
    void setCatalogName(String catalogName);

    @External(type = String.class)
    Optional<String> getSchemaName();

    @External(type = String.class)
    void setSchemaName(String schemaName);

    // Groovy
    default Table table(Closure<?> c) {
        return ConfigEntityUtil.groovyDelegatorHelper(c, this::addNewTable);
    }

}
