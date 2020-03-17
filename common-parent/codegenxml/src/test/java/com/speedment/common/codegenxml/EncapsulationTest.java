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
package com.speedment.common.codegenxml;

import com.speedment.common.codegen.internal.model.FieldImpl;
import com.speedment.common.codegen.model.Field;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Per Minborg
 */
@Disabled("This test does not work for Jenkins, See #815")
final class EncapsulationTest {


    @Test
    void internalNotAccessible() {
        assertThrows(IllegalAccessError.class, () -> new FieldImpl("s", String.class), "The module com.speedment.common.codegen is not encapsulated");
    }

    @Test
    void deepReflection() {
        final Field f = Field.of("s", String.class);

        try {
            f.getClass().getDeclaredField("name").setAccessible(true);
        } catch (Exception e) {
            if ("InaccessibleObjectException".equals(e.getClass().getSimpleName())) {
                return;
            }
        }
        fail("The module com.speedment.common.codegen is not encapsulated for deep reflection");
    }
}


