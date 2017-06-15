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
import com.speedment.runtime.config.mutator.PrimaryKeyColumnMutator;
import com.speedment.runtime.config.trait.*;

/**
 * A typed {@link Document} that represents the primary key column instance in
 * the database. A {@code PrimaryKeyColumn} is located inside a {@link Table}.
 *
 * @author  Emil Forslund
 * @since   2.0.0
 */

public interface PrimaryKeyColumn extends
    Document,
    HasParent<Table>,
    HasId,    
    HasName,
    HasOrdinalPosition,
    HasColumn,
    HasMainInterface,
    HasMutator<PrimaryKeyColumnMutator<? extends PrimaryKeyColumn>> {

    @Override
    default Class<PrimaryKeyColumn> mainInterface() {
        return PrimaryKeyColumn.class;
    }

    @Override
    default PrimaryKeyColumnMutator<? extends PrimaryKeyColumn> mutator() {
        return DocumentMutator.of(this);
    }

}
