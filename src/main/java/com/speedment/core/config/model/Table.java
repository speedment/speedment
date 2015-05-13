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
import com.speedment.core.config.model.impl.TableImpl;
import com.speedment.core.config.model.parameters.ColumnCompressionTypeable;
import com.speedment.core.config.model.parameters.FieldStorageTypeable;
import com.speedment.core.config.model.parameters.StorageEngineTypeable;
import com.speedment.core.platform.Platform;
import groovy.lang.Closure;
import java.util.Optional;
import java.util.function.Supplier;

/**
 *
 * @author pemi
 */
@Api(version = 0)
public interface Table extends ConfigEntity, Child<Schema>, Parent<Child<Table>>,
        FieldStorageTypeable,
        ColumnCompressionTypeable,
        StorageEngineTypeable {

    enum Holder {

        HOLDER;
        private Supplier<Table> provider = () -> new TableImpl();
    }

    static void setSupplier(Supplier<Table> provider) {
        Holder.HOLDER.provider = provider;
    }

    static Table newTable() {
        return Holder.HOLDER.provider.get();
    }

    @Override
    default Class<Table> getInterfaceMainClass() {
        return Table.class;
    }

    @Override
    default Class<Schema> getParentInterfaceMainClass() {
        return Schema.class;
    }

    default Column addNewColumn() {
        final Column e = Column.newColumn();
        add(e);
        return e;
    }

    default Index addNewIndex() {
        final Index e = Index.newIndex();
        add(e);
        return e;
    }

    default PrimaryKeyColumn addNewPrimaryKeyColumn() {
        final PrimaryKeyColumn e = PrimaryKeyColumn.newPrimaryKeyColumn();
        add(e);
        return e;
    }

    default ForeignKey addNewForeignKey() {
        final ForeignKey e = ForeignKey.newForeignKey();
        add(e);
        return e;
    }

    @External(type = String.class)
    Optional<String> getTableName();

    @External(type = String.class)
    void setTableName(String tableName);

    // Groovy
    default Column column(Closure<?> c) {
        return ConfigEntityUtil.groovyDelegatorHelper(c, this::addNewColumn);
    }

    default Index index(Closure<?> c) {
        return ConfigEntityUtil.groovyDelegatorHelper(c, this::addNewIndex);
    }

    default ForeignKey foreignKey(Closure<?> c) {
        return ConfigEntityUtil.groovyDelegatorHelper(c, this::addNewForeignKey);
    }

    default PrimaryKeyColumn primaryKeyColumn(Closure<?> c) {
        return ConfigEntityUtil.groovyDelegatorHelper(c, this::addNewPrimaryKeyColumn);
    }

}
