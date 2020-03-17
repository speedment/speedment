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

import com.speedment.common.codegen.model.Value;
import com.speedment.common.codegen.model.value.InvocationValue;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.*;

final class InvocationValueImplTest extends AbstractValueTest<String, InvocationValue> {

    public InvocationValueImplTest() {
        super(InvocationValueImpl::new,
                a -> a.setValue("A"),
                a -> a.set(int.class),
                a -> a.add(Value.ofNumber(1))
        );
    }

    @Test
    void getValue() {
        assertNull(instance().getValue());
    }

    @Test
    void setValue() {
        final String value = "B";
        instance().setValue(value);
        assertEquals(value, instance().getValue());
    }

    @Test
    void getType() {
        assertNull(instance().getValue());
    }

    @Test
    void set() {
        final Type value = int.class;
        instance().set(value);
        assertEquals(value, instance().getType());
    }

    @Test
    void getValues() {
        assertTrue(instance().getValues().isEmpty());
    }
}