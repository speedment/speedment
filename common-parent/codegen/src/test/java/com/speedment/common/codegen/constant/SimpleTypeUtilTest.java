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
package com.speedment.common.codegen.constant;

import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.File;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class SimpleTypeUtilTest {

    @Test
    void nameOfIllegalFileName() {
        final File file = File.of("com.company.module.Foo.c++");
        final Class clazz = Class.of("Bar");
        assertThrows(RuntimeException.class, () -> SimpleTypeUtil.nameOf(file, clazz));
    }

    @Test
    void nameOfNoClass() {
        final File file = File.of("com.company.module.Foo.java");
        final Class clazz = Class.of("Bar");
        assertThrows(RuntimeException.class, () -> SimpleTypeUtil.nameOf(file, clazz));
    }

    @Test
    void nameOf() {
        final File file = File.of("com.company.module.Foo.java");
        final Class clazz = Class.of("Foo");
        file.add(clazz);
        assertDoesNotThrow(() -> SimpleTypeUtil.nameOf(file, clazz));
    }

}