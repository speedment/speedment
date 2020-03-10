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

package com.speedment.runtime.config.mutator.trait;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.mutator.DocumentMutator;
import com.speedment.runtime.config.trait.HasId;
import org.junit.jupiter.api.Test;

public interface HasIdMutatorMixin<TYPE extends Document & HasId,
        MUTATOR extends DocumentMutator<TYPE> & HasIdMutator<TYPE>>
        extends BaseTraitMixin<TYPE, MUTATOR> {

    @Test
    default void setId() {
        assertDoesNotThrow(() -> getMutatorInstance().setId("id"));

    }
}
