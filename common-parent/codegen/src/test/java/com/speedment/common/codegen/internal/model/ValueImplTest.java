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
package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.model.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

final class ValueImplTest extends AbstractTest<Value<String>> {

    private final static String VALUE = "A";

    public ValueImplTest() {
        super(() -> new TestValue(VALUE),
                a -> a.setValue("C")
        );
    }

    @Test
    void setValue() {
        final String value = "V";
        instance().setValue(value);
        assertEquals(value, instance().getValue());
    }

    @Test
    void getValue() {
        assertEquals(VALUE, instance().getValue());
    }

    private static final class TestValue extends ValueImpl<String> {

        public TestValue(String val) {
            super(val);
        }

        @Override
        public ValueImpl<String> copy() {
            return new TestValue(getValue());
        }
    }

}