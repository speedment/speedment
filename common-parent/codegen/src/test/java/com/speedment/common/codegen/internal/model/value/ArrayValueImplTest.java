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
import com.speedment.common.codegen.model.value.ArrayValue;

import java.util.Arrays;
import java.util.List;

final class ArrayValueImplTest extends AbstractValueTest<List<Value<?>>, ArrayValue> {

    private final static List<Value<?>> VALUES = Arrays.asList(Value.ofNumber(1), Value.ofNumber(2));
    private final static List<Value<?>> OTHER_VALUES = Arrays.asList(Value.ofNumber(3), Value.ofNumber(4));

    public ArrayValueImplTest() {
        super(() -> new ArrayValueImpl(VALUES),
                a -> a.setValue(OTHER_VALUES)
        );
    }
}