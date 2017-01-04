/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.config;


import com.speedment.runtime.config.mutator.DocumentMutator;
import com.speedment.runtime.config.mutator.TableMutator;
import com.speedment.runtime.config.trait.*;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * A typed {@link Document} that represents a table instance in the database. A
 * {@code Table} is located inside a {@link Schema} and can have multiple 
 * {@link Column Columns} as children.
 * 
 * @author  Emil Forslund
 * @since   2.0.0
 */

public interface Table extends
        Document,
        HasParent<Schema>,
        HasEnabled,
        HasName,
        HasChildren,
        HasAlias,
        HasPackageName,
        HasMainInterface,
        HasMutator<TableMutator<? extends Table>> {

    String COLUMNS = "columns",
            INDEXES = "indexes",
            FOREIGN_KEYS = "foreignKeys",
            PRIMARY_KEY_COLUMNS = "primaryKeyColumns";
    
    /**
     * Creates a stream of columns located in this document.
     * 
     * @return  columns
     */
    Stream<? extends Column> columns();
    
    /**
     * Creates a stream of indexes located in this document.
     * 
     * @return  indexes
     */
    Stream<? extends Index> indexes();
    
    /**
     * Creates a stream of foreign keys located in this document.
     * 
     * @return  foreign keys
     */
    Stream<? extends ForeignKey> foreignKeys();
    
    /**
     * Creates a stream of primary key columns located in this document.
     * 
     * @return  primary key columns
     */
    Stream<? extends PrimaryKeyColumn> primaryKeyColumns();

    default Optional<? extends Column> findColumn(String name) {
        return columns().filter(child -> child.getName().equals(name)).findAny();
    }

    default Optional<? extends Index> findIndex(String name) {
        return indexes().filter(child -> child.getName().equals(name)).findAny();
    }

    default Optional<? extends ForeignKey> findForeignKey(String name) {
        return foreignKeys().filter(child -> child.getName().equals(name)).findAny();
    }

    default Optional<? extends PrimaryKeyColumn> findPrimaryKeyColumn(String name) {
        return primaryKeyColumns().filter(child -> child.getName().equals(name)).findAny();
    }

    @Override
    default Class<Table> mainInterface() {
        return Table.class;
    }

    @Override
    default TableMutator<? extends Table> mutator() {
        return DocumentMutator.of(this);
    }
}