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
import com.speedment.runtime.config.mutator.IndexMutator;
import com.speedment.runtime.config.trait.HasChildren;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.config.trait.HasId;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.config.trait.HasMutator;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.runtime.config.trait.HasParent;
import java.util.stream.Stream;

/**
 * A typed {@link Document} that represents an index instance in the database. 
 * An {@code Index} is located inside a {@link Table} and can have 
 * multiple {@link IndexColumn IndexColumns} as children.
 * 
 * @author  Emil Forslund
 * @since   2.0.0
 */

public interface Index extends 
        Document,
        HasParent<Table>,
        HasEnabled,
        HasId,        
        HasName,
        HasChildren,
        HasMainInterface,
        HasMutator<IndexMutator<? extends Index>> {

    String UNIQUE = "unique",
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

    @Override
    default Class<Index> mainInterface() {
        return Index.class;
    }

    @Override
    default IndexMutator<? extends Index> mutator() {
        return DocumentMutator.of(this);
    }

}
