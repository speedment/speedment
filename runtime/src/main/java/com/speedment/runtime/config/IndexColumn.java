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
package com.speedment.runtime.config;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.config.mutator.DocumentMutator;
import com.speedment.runtime.config.mutator.IndexColumnMutator;
import com.speedment.runtime.config.trait.*;

/**
 * A typed {@link Document} that represents the column referenced by an index 
 * key instance in the database. An {@code IndexColumn} is located inside an
 * {@link Index}.
 * 
 * @author  Emil Forslund
 * @since   2.0.0
 */
@Api(version = "3.0")
public interface IndexColumn extends
        Document,
        HasParent<Index>,
        HasName,
        HasOrdinalPosition,
        HasOrderType,
        HasColumn,
        HasMainInterface,
        HasMutator<IndexColumnMutator<? extends IndexColumn>> {

    @Override
    default Class<IndexColumn> mainInterface() {
        return IndexColumn.class;
    }

    @Override
    default IndexColumnMutator<? extends IndexColumn> mutator() {
        return DocumentMutator.of(this);
    }

}
