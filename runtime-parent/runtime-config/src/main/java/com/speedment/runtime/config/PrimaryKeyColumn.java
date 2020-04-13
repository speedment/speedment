/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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


import com.speedment.runtime.config.internal.PrimaryKeyColumnImpl;
import com.speedment.runtime.config.mutator.DocumentMutator;
import com.speedment.runtime.config.mutator.PrimaryKeyColumnMutator;
import com.speedment.runtime.config.trait.HasColumn;
import com.speedment.runtime.config.trait.HasDeepCopy;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.config.trait.HasId;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.config.trait.HasMutator;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.runtime.config.trait.HasOrdinalPosition;
import com.speedment.runtime.config.trait.HasParent;
import com.speedment.runtime.config.util.DocumentUtil;

import java.util.Map;

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
    HasDeepCopy,
    HasId,    
    HasName,
    HasEnabled,
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


    @Override
    default PrimaryKeyColumn deepCopy() {
        return DocumentUtil.deepCopy(this, PrimaryKeyColumnImpl::new);
    }

    /**
     * Creates and returns a new standard implementation of a {@link PrimaryKeyColumn}
     * with the given {@code parent} and {@code data}
     *
     * @param parent of the config document (nullable)
     * @param data of the config document
     * @return new {@link PrimaryKeyColumn} with the given parameters
     *
     * @throws NullPointerException if the provided {@code data} is {@code null}
     */
    static PrimaryKeyColumn create(Table parent, Map<String, Object> data) {
        return new PrimaryKeyColumnImpl(parent, data);
    }
}
