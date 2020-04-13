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
package com.speedment.common.codegen.internal.model.value;

import com.speedment.common.codegen.model.value.EnumValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class EnumValueImplTest extends AbstractValueTest<Enum<?>, EnumValue> {

    private enum Foo{BAR, BAZ}
    private enum Foo2{BAR2, BAZ2}

    public EnumValueImplTest() {
        super(() -> new EnumValueImpl(Foo.class, "BAR"),
                a -> a.setValue("BAZ")
        );
    }

    @Test
    void set() {
        instance().set(Foo2.class);
        assertEquals(Foo2.class, instance().getType());
    }

    @Test
    void getType() {
        assertEquals(Foo.class, instance().getType());
    }
}