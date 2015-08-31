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
package com.speedment.config;

import com.speedment.annotation.Api;
import com.speedment.annotation.External;
import com.speedment.config.aspects.Parent;
import com.speedment.config.aspects.Child;
import com.speedment.config.aspects.Enableable;
import com.speedment.config.aspects.ColumnCompressionTypeable;
import com.speedment.config.aspects.FieldStorageTypeable;
import com.speedment.config.aspects.StorageEngineTypeable;
import com.speedment.internal.core.config.TableImpl;
import groovy.lang.Closure;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.function.Supplier;

/**
 *
 * @author pemi
 */
@Api(version = "2.1")
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
        Holder.HOLDER.provider = requireNonNull(provider);
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
    Column addNewColumn();

    /**
     * Creates and adds a new {@link Index} as a child to this node in the
     * configuration tree.
     *
     * @return the newly added child
     */
    Index addNewIndex();

    /**
     * Creates and adds a new {@link PrimaryKeyColumn} as a child to this node
     * in the configuration tree.
     *
     * @return the newly added child
     */
    PrimaryKeyColumn addNewPrimaryKeyColumn();

    /**
     * Creates and adds a new {@link ForeignKey} as a child to this node in the
     * configuration tree.
     *
     * @return the newly added child
     */
    ForeignKey addNewForeignKey();

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
    Column column(Closure<?> c);

    /**
     * Creates and returns a new Index.
     * <p>
     * This method is used by the Groovy parser.
     *
     * @param c Closure
     * @return the new Index
     */
    Index index(Closure<?> c);

    /**
     * Creates and returns a new ForeignKey.
     * <p>
     * This method is used by the Groovy parser.
     *
     * @param c Closure
     * @return the new ForeignKey
     */
    ForeignKey foreignKey(Closure<?> c);

    /**
     * Creates and returns a new PrimaryKeyColumn.
     * <p>
     * This method is used by the Groovy parser.
     *
     * @param c Closure
     * @return the new PrimaryKeyColumn
     */
    PrimaryKeyColumn primaryKeyColumn(Closure<?> c);
}