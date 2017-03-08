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
import com.speedment.runtime.config.mutator.ForeignKeyMutator;
import com.speedment.runtime.config.trait.HasChildren;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.config.trait.HasId;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.config.trait.HasMutator;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.runtime.config.trait.HasParent;
import java.util.stream.Stream;

/**
 * A typed {@link Document} that represents a foreign key instance in the 
 * database. A {@code ForeignKey} is located inside a {@link Table} and can have 
 * multiple {@link ForeignKeyColumn ForeignKeyColumns} as children.
 * 
 * @author  Emil Forslund
 * @since   2.0.0
 */

public interface ForeignKey extends
        Document,
        HasParent<Table>,
        HasEnabled,
        HasId,        
        HasName,
        HasChildren,
        HasMainInterface,
        HasMutator<ForeignKeyMutator<? extends ForeignKey>> {

    String FOREIGN_KEY_COLUMNS = "foreignKeyColumns";
    
    /**
     * Creates a stream of foreign key columns located in this document.
     * 
     * @return  foreign key columns
     */
    Stream<? extends ForeignKeyColumn> foreignKeyColumns();

    @Override
    default Class<ForeignKey> mainInterface() {
        return ForeignKey.class;
    }

    @Override
    default ForeignKeyMutator<? extends ForeignKey> mutator() {
        return DocumentMutator.of(this);
    }
}