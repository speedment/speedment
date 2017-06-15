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
import com.speedment.runtime.config.mutator.SchemaMutator;
import com.speedment.runtime.config.trait.*;

import java.util.stream.Stream;

/**
 * A typed {@link Document} that represents a schema instance in the database. A
 * {@code Schema} is located inside a {@link Dbms} and can have multiple 
 * {@link Table Tables} as children.
 * 
 * @author  Emil Forslund
 */

public interface Schema extends
        Document,
        HasParent<Dbms>,
        HasEnabled,
        HasId,        
        HasName,
        HasChildren,
        HasAlias,
        HasMainInterface,
        HasMutator<SchemaMutator<? extends Schema>> {

    String DEFAULT_SCHEMA = "defaultSchema",
            TABLES = "tables";

    /**
     * Returns {@code true} if this schema is the default one, else
     * {@code false}. Default value is {@code true}.
     *
     * @return {@code true} if default, else {@code false}
     */
    default boolean isDefaultSchema() {
        return getAsBoolean(DEFAULT_SCHEMA).orElse(true);
    }
    
    /**
     * Creates a stream of tables located in this document.
     * 
     * @return  tables
     */
    Stream<? extends Table> tables();
//
//    default Stream<? extends Table> tables() {
//        return children(TABLES, tableConstructor());
//    }
//
//    default Table addNewTable() {
//        return tableConstructor().apply(this, newDocument(this, TABLES));
//    }
//
//    BiFunction<Schema, Map<String, Object>, ? extends Table> tableConstructor();

    @Override
    default Class<Schema> mainInterface() {
        return Schema.class;
    }

    @Override
    default SchemaMutator<? extends Schema> mutator() {
        return DocumentMutator.of(this);
    }

}
