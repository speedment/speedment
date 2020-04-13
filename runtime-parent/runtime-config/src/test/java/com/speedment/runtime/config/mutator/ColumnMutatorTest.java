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

import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.mutator.trait.HasAliasMutatorMixin;
import com.speedment.runtime.config.mutator.trait.HasColumnSizeMutatorMixin;
import com.speedment.runtime.config.mutator.trait.HasDecimalDigitsMutatorMixin;
import com.speedment.runtime.config.mutator.trait.HasEnabledMutatorMixin;
import com.speedment.runtime.config.mutator.trait.HasIdMutatorMixin;
import com.speedment.runtime.config.mutator.trait.HasNameMutatorMixin;
import com.speedment.runtime.config.mutator.trait.HasOrdinalPositionMutatorMixin;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

final class ColumnMutatorTest implements
        HasEnabledMutatorMixin<Column, ColumnMutator<Column>>,
        HasIdMutatorMixin<Column, ColumnMutator<Column>>,
        HasNameMutatorMixin<Column, ColumnMutator<Column>>,
        HasAliasMutatorMixin<Column, ColumnMutator<Column>>,
        HasColumnSizeMutatorMixin<Column, ColumnMutator<Column>>,
        HasDecimalDigitsMutatorMixin<Column, ColumnMutator<Column>>,
        HasOrdinalPositionMutatorMixin<Column, ColumnMutator<Column>> {

    @Override
    @SuppressWarnings("unchecked")
    public ColumnMutator<Column> getMutatorInstance() {
        return (ColumnMutator<Column>) Column.create(null, new HashMap<>()).mutator();
    }

    @Test
    void setNullable() {
        assertDoesNotThrow(() -> getMutatorInstance().setNullable(true));
    }

    @Test
    void setAutoIncrement() {
        assertDoesNotThrow(() -> getMutatorInstance().setAutoIncrement(true));
    }

    @Test
    void setTypeMapper() {
        assertDoesNotThrow(() -> getMutatorInstance().setTypeMapper(String.class));
    }

    @Test
    void setDatabaseType() {
        assertDoesNotThrow(() -> getMutatorInstance().setDatabaseType(String.class));
    }

    @Test
    void setEnumConstants() {
        assertDoesNotThrow(() -> getMutatorInstance().setEnumConstants("constant"));
    }
}
