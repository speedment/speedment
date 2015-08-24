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
import com.speedment.core.config.model.aspects.Enableable;
import com.speedment.core.config.model.aspects.Node;
import com.speedment.core.config.model.impl.TableImpl;
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
@Api(version = "2.0")
public interface Table extends Node, Enableable, Child<Schema>, Parent<Child<Table>>,
    FieldStorageTypeable,
    ColumnCompressionTypeable,
    StorageEngineTypeable {

    /**
     * Factory holder.
     */
    enum Holder {
        HOLDER;
        private Supplier<Table> provider = TableImpl::new;
    }

    /**
     * Sets the instantiation method used to create new instances of this
     * interface.
     *
     * @param provider the new constructor
     */
    static void setSupplier(Supplier<Table> provider) {
        Holder.HOLDER.provider = provider;
    }

    /**
     * Creates a new instance implementing this interface by using the class
     * supplied by the default factory. To change implementation, please use the
     * {@link #setSupplier(java.util.function.Supplier) setSupplier} method.
     *
     * @return the new instance
     */
    static Table newTable() {
        return Holder.HOLDER.provider.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Class<Table> getInterfaceMainClass() {
        return Table.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Class<Schema> getParentInterfaceMainClass() {
        return Schema.class;
    }

    /**
     * Creates and adds a new {@link Column} as a child to this node in the
     * configuration tree.
     *
     * @return the newly added child
     */
    default Column addNewColumn() {
        final Column e = Column.newColumn();
        add(e);
        return e;
    }

    /**
     * Creates and adds a new {@link Index} as a child to this node in the
     * configuration tree.
     *
     * @return the newly added child
     */
    default Index addNewIndex() {
        final Index e = Index.newIndex();
        add(e);
        return e;
    }

    /**
     * Creates and adds a new {@link PrimaryKeyColumn} as a child to this node
     * in the configuration tree.
     *
     * @return the newly added child
     */
    default PrimaryKeyColumn addNewPrimaryKeyColumn() {
        final PrimaryKeyColumn e = PrimaryKeyColumn.newPrimaryKeyColumn();
        add(e);
        return e;
    }

    /**
     * Creates and adds a new {@link ForeignKey} as a child to this node in the
     * configuration tree.
     *
     * @return the newly added child
     */
    default ForeignKey addNewForeignKey() {
        final ForeignKey e = ForeignKey.newForeignKey();
        add(e);
        return e;
    }

    /**
     * Returns the name of this table as specified in the database.
     * <p>
     * This property is editable in the GUI through reflection.
     *
     * @return the table name in the database
     */
    @External(type = String.class)
    Optional<String> getTableName();

    /**
     * Sets the name of this table as specified in the database.
     * <p>
     * This property is editable in the GUI through reflection.
     *
     * @param tableName the table name in the database
     */
    @External(type = String.class)
    void setTableName(String tableName);

    /**
     * Creates and returns a new Column.
     * <p>
     * This method is used by the Groovy parser.
     *
     * @param c Closure
     * @return the new Column
     */
    // DO NOT REMOVE, CALLED VIA REFLECTION
    default Column column(Closure<?> c) {
        return ConfigUtil.groovyDelegatorHelper(c, this::addNewColumn);
    }

    /**
     * Creates and returns a new Index.
     * <p>
     * This method is used by the Groovy parser.
     *
     * @param c Closure
     * @return the new Index
     */
    // DO NOT REMOVE, CALLED VIA REFLECTION
    default Index index(Closure<?> c) {
        return ConfigUtil.groovyDelegatorHelper(c, this::addNewIndex);
    }

    /**
     * Creates and returns a new ForeignKey.
     * <p>
     * This method is used by the Groovy parser.
     *
     * @param c Closure
     * @return the new ForeignKey
     */
    // DO NOT REMOVE, CALLED VIA REFLECTION
    default ForeignKey foreignKey(Closure<?> c) {
        return ConfigUtil.groovyDelegatorHelper(c, this::addNewForeignKey);
    }

    /**
     * Creates and returns a new PrimaryKeyColumn.
     * <p>
     * This method is used by the Groovy parser.
     *
     * @param c Closure
     * @return the new PrimaryKeyColumn
     */
    // DO NOT REMOVE, CALLED VIA REFLECTION
    default PrimaryKeyColumn primaryKeyColumn(Closure<?> c) {
        return ConfigUtil.groovyDelegatorHelper(c, this::addNewPrimaryKeyColumn);
    }

}
