/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.config.db;

import com.speedment.annotation.Api;
import com.speedment.config.Document;
import com.speedment.config.db.trait.HasAlias;
import com.speedment.config.db.trait.HasChildren;
import com.speedment.config.db.trait.HasEnabled;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.config.db.trait.HasMutator;
import com.speedment.config.db.trait.HasName;
import com.speedment.config.db.trait.HasParent;
import com.speedment.internal.core.config.db.mutator.DocumentMutator;
import com.speedment.internal.core.config.db.mutator.TableMutator;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public interface Table extends
        Document,
        HasParent<Schema>,
        HasEnabled,
        HasName,
        HasChildren,
        HasAlias,
        HasMainInterface,
        HasMutator<TableMutator> {

    final String COLUMNS = "columns",
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

//    default Stream<? extends Column> columns() {
//        return children(COLUMNS, columnConstructor());
//    }
//
//    default Stream<? extends Index> indexes() {
//        return children(INDEXES, indexConstructor());
//    }
//
//    default Stream<? extends ForeignKey> foreignKeys() {
//        return children(FOREIGN_KEYS, foreignKeyConstructor());
//    }
//
//    default Stream<? extends PrimaryKeyColumn> primaryKeyColumns() {
//        return children(PRIMARY_KEY_COLUMNS, primaryKeyColumnConstructor());
//    }

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

//    default Column addNewColumn() {
//        return columnConstructor().apply(this, newDocument(this, COLUMNS));
//    }
//
//    default Index addNewIndex() {
//        return indexConstructor().apply(this, newDocument(this, INDEXES));
//    }
//
//    default ForeignKey addNewForeignKey() {
//        return foreignKeyConstructor().apply(this, newDocument(this, FOREIGN_KEYS));
//    }
//
//    default PrimaryKeyColumn addNewPrimaryKeyColumn() {
//        return primaryKeyColumnConstructor().apply(this, newDocument(this, PRIMARY_KEY_COLUMNS));
//    }
//
//    BiFunction<Table, Map<String, Object>, ? extends Column> columnConstructor();
//
//    BiFunction<Table, Map<String, Object>, ? extends Index> indexConstructor();
//
//    BiFunction<Table, Map<String, Object>, ? extends ForeignKey> foreignKeyConstructor();
//
//    BiFunction<Table, Map<String, Object>, ? extends PrimaryKeyColumn> primaryKeyColumnConstructor();

    @Override
    default Class<Table> mainInterface() {
        return Table.class;
    }

    @Override
    default TableMutator mutator() {
        return DocumentMutator.of(this);
    }
}