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
import com.speedment.internal.core.config.SchemaImpl;
import com.speedment.config.aspects.ColumnCompressionTypeable;
import com.speedment.config.aspects.FieldStorageTypeable;
import com.speedment.config.aspects.StorageEngineTypeable;
import groovy.lang.Closure;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.function.Supplier;

/**
 *
 * @author pemi
 */
@Api(version = "2.1")
public interface Schema extends Node, Enableable, Child<Dbms>, Parent<Table>,
    FieldStorageTypeable,
    ColumnCompressionTypeable,
    StorageEngineTypeable {

    /**
     * Factory holder.
     */
    enum Holder {
        HOLDER;
        private Supplier<Schema> provider = SchemaImpl::new;
    }

    /**
     * Sets the instantiation method used to create new instances of this
     * interface.
     *
     * @param provider the new constructor
     */
    static void setSupplier(Supplier<Schema> provider) {
        Holder.HOLDER.provider = requireNonNull(provider);
    }

    /**
     * Creates a new instance implementing this interface by using the class
     * supplied by the default factory. To change implementation, please use the
     * {@link #setSupplier(java.util.function.Supplier) setSupplier} method.
     *
     * @return the new instance
     */
    static Schema newSchema() {
        return Holder.HOLDER.provider.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Class<Schema> getInterfaceMainClass() {
        return Schema.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Class<Dbms> getParentInterfaceMainClass() {
        return Dbms.class;
    }

    /**
     * Creates and adds a new {@link Table} as a child to this node in the
     * configuration tree.
     *
     * @return the newly added child
     */
    Table addNewTable();

    /**
     * Returns {@code true} if this schema is the default one, else
     * {@code false}.
     * <p>
     * This property is editable in the GUI through reflection.
     *
     * @return {@code true} if default, else {@code false}
     */
    @External(type = Boolean.class)
    Boolean isDefaultSchema();

    /**
     * Sets whether this schema should be the default one or not.
     * <p>
     * This property is editable in the GUI through reflection.
     *
     * @param defaultSchema {@code true} for this schema to be default
     */
    @External(type = Boolean.class)
    void setDefaultSchema(Boolean defaultSchema);

    /**
     * Returns the catalog name of this schema as specified in the database.
     * <p>
     * This property is editable in the GUI through reflection.
     *
     * @return the catalog name in the database
     */
    @External(type = String.class)
    Optional<String> getCatalogName();

    /**
     * Sets the catalog name of this schema as specified in the database.
     * <p>
     * This property is editable in the GUI through reflection.
     *
     * @param catalogName the catalog name in the database
     */
    @External(type = String.class)
    void setCatalogName(String catalogName);

    /**
     * Returns the name of this schema as specified in the database.
     * <p>
     * This property is editable in the GUI through reflection.
     *
     * @return the schema name in the database
     */
    @External(type = String.class)
    Optional<String> getSchemaName();

    /**
     * Sets the name of this schema as specified in the database.
     * <p>
     * This property is editable in the GUI through reflection.
     *
     * @param schemaName the schema name in the database
     */
    @External(type = String.class)
    void setSchemaName(String schemaName);

    /**
     * Creates and returns a new Table.
     * <p>
     * This method is used by the Groovy parser.
     *
     * @param c Closure
     * @return the new Table
     */
    Table table(Closure<?> c);
}