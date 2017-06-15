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

import com.speedment.runtime.config.mutator.ColumnMutator;
import com.speedment.runtime.config.mutator.DocumentMutator;
import com.speedment.runtime.config.trait.*;

/**
 * A typed {@link Document} that represents a column in the database. A
 * {@code Column} is located inside a {@link Table}.
 *
 * @author  Emil Forslund
 * @since   2.0.0
 */
public interface Column extends
        Document,
        HasParent<Table>,
        HasEnabled,
        HasId,
        HasName,
        HasAlias,
        HasNullable,
        HasOrdinalPosition,
        HasTypeMapper,
        HasDecimalDigits,
        HasColumnSize,
        HasMainInterface,
        HasEnumConstants,
        HasMutator<ColumnMutator<? extends Column>> {

    String AUTO_INCREMENT = "autoIncrement";

    /**
     * Returns whether or not this column will auto increment when new values
     * are added to the table.
     *
     * @return  {@code true} if the column auto increments, else {@code false}
     */
    default boolean isAutoIncrement() {
        return getAsBoolean(AUTO_INCREMENT).orElse(false);
    }

    @Override
    default Class<Column> mainInterface() {
        return Column.class;
    }

    @Override
    default ColumnMutator<? extends Column> mutator() {
        return DocumentMutator.of(this);
    }
}