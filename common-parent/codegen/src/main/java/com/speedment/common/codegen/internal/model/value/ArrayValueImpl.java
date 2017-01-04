/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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

import com.speedment.common.codegen.internal.model.ValueImpl;
import com.speedment.common.codegen.internal.util.Copier;
import com.speedment.common.codegen.model.Value;
import com.speedment.common.codegen.model.value.ArrayValue;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Emil Forslund
 */
public final class ArrayValueImpl extends ValueImpl<List<Value<?>>> implements ArrayValue {

    public ArrayValueImpl() {
        super(new ArrayList<>());
    }

    public ArrayValueImpl(List<Value<?>> val) {
        super(val);
    }

    @Override
    public ArrayValueImpl copy() {
        return new ArrayValueImpl(Copier.copy(getValue(), s -> s.copy()));
    }
}
