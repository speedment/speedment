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
import com.speedment.config.db.trait.HasColumn;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.config.db.trait.HasMutator;
import com.speedment.config.db.trait.HasName;
import com.speedment.config.db.trait.HasOrdinalPosition;
import com.speedment.config.db.trait.HasParent;
import com.speedment.internal.core.config.db.mutator.DocumentMutator;
import com.speedment.internal.core.config.db.mutator.PrimaryKeyColumnMutator;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public interface PrimaryKeyColumn extends 
        Document,
        HasParent<Table>,
        HasName,
        HasOrdinalPosition,
        HasColumn,
        HasMainInterface,
        HasMutator<PrimaryKeyColumnMutator> {

    @Override
    default Class<PrimaryKeyColumn> mainInterface() {
        return PrimaryKeyColumn.class;
    }

    @Override
    default PrimaryKeyColumnMutator mutator() {
        return DocumentMutator.of(this);
    }

}
