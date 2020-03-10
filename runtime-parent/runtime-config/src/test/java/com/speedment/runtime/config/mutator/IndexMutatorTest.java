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
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.speedment.runtime.config.Index;
import com.speedment.runtime.config.mutator.trait.HasEnabledMutatorMixin;
import com.speedment.runtime.config.mutator.trait.HasIdMutatorMixin;
import com.speedment.runtime.config.mutator.trait.HasNameMutatorMixin;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

final class IndexMutatorTest implements
        HasEnabledMutatorMixin<Index, IndexMutator<Index>>,
        HasIdMutatorMixin<Index, IndexMutator<Index>>,
        HasNameMutatorMixin<Index, IndexMutator<Index>> {

    @Override
    @SuppressWarnings("unchecked")
    public IndexMutator<Index> getMutatorInstance() {
        return (IndexMutator<Index>) Index.create(null, new HashMap<>()).mutator();
    }

    @Test
    void setUnique() {
        assertDoesNotThrow(() -> getMutatorInstance().setUnique(true));
    }

    @Test
    void addNewIndexColumn() {
        assertNotNull(getMutatorInstance().addNewIndexColumn());
    }
}
