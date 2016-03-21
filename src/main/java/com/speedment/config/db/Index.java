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
import com.speedment.config.db.trait.HasChildren;
import com.speedment.config.db.trait.HasEnabled;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.config.db.trait.HasMutator;
import com.speedment.config.db.trait.HasName;
import com.speedment.config.db.trait.HasParent;
import com.speedment.config.db.mutator.DocumentMutator;
import com.speedment.config.db.mutator.IndexMutator;
import java.util.stream.Stream;

/**
 * A typed {@link Document} that represents an index instance in the database. 
 * An {@code Index} is located inside a {@link Table} and can have 
 * multiple {@link IndexColumn IndexColumns} as children.
 * 
 * @author  Emil Forslund
 * @since   2.0
 */
@Api(version = "2.3")
public interface Index extends 
        Document,
        HasParent<Table>,
        HasEnabled,
        HasName,
        HasChildren,
        HasMainInterface,
        HasMutator<IndexMutator> {

    final String UNIQUE = "unique",
        INDEX_COLUMNS = "indexColumns";

    /**
     * Returns whether or not this index is an {@code UNIQUE} index.
     * <p>
     * This property is editable in the GUI through reflection.
     *
     * @return {@code true} if this index is {@code UNIQUE}
     */
    default boolean isUnique() {
        return getAsBoolean(UNIQUE).orElse(false);
    }
    
    /**
     * Creates a stream of index columns located in this document.
     * 
     * @return  index columns
     */
    Stream<? extends IndexColumn> indexColumns();

//    default Stream<? extends IndexColumn> indexColumns() {
//        return children(INDEX_COLUMNS, indexColumnConstructor());
//    }
//
//    default IndexColumn addNewIndexColumn() {
//        return indexColumnConstructor().apply(this, newDocument(this, INDEX_COLUMNS));
//    }
//
//    BiFunction<Index, Map<String, Object>, ? extends IndexColumn> indexColumnConstructor();

    @Override
    default Class<Index> mainInterface() {
        return Index.class;
    }

    @Override
    default IndexMutator mutator() {
        return DocumentMutator.of(this);
    }

}
