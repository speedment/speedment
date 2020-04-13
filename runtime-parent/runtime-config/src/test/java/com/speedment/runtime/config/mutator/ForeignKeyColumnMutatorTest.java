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

package com.speedment.runtime.config.mutator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.speedment.runtime.config.ForeignKeyColumn;
import com.speedment.runtime.config.mutator.trait.HasIdMutatorMixin;
import com.speedment.runtime.config.mutator.trait.HasNameMutatorMixin;
import com.speedment.runtime.config.mutator.trait.HasOrdinalPositionMutatorMixin;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

final class ForeignKeyColumnMutatorTest implements
        HasIdMutatorMixin<ForeignKeyColumn, ForeignKeyColumnMutator<ForeignKeyColumn>>,
        HasNameMutatorMixin<ForeignKeyColumn, ForeignKeyColumnMutator<ForeignKeyColumn>>,
        HasOrdinalPositionMutatorMixin<ForeignKeyColumn, ForeignKeyColumnMutator<ForeignKeyColumn>> {

    @Override
    @SuppressWarnings("unchecked")
    public ForeignKeyColumnMutator<ForeignKeyColumn> getMutatorInstance() {
        return (ForeignKeyColumnMutator<ForeignKeyColumn>) ForeignKeyColumn.create(null, new HashMap<>()).mutator();
    }

    @Test
    void setForeignTableName() {
        assertDoesNotThrow(() -> getMutatorInstance().setForeignTableName("table"));
    }

    @Test
    void setForeignColumnName() {
        assertDoesNotThrow(() -> getMutatorInstance().setForeignColumnName("column"));
    }

    @Test
    void setForeignDatabaseName() {
        assertDoesNotThrow(() -> getMutatorInstance().setForeignDatabaseName("database"));
    }

    @Test
    void setForeignSchemaName() {
        assertDoesNotThrow(() -> getMutatorInstance().setForeignSchemaName("table"));
    }
}
