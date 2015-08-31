/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.codegen.lang.models.values;

import com.speedment.internal.codegen.lang.models.Value;
import com.speedment.internal.codegen.lang.models.implementation.ValueImpl;
import com.speedment.internal.codegen.util.Copier;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Emil Forslund
 */
public final class ArrayValue extends ValueImpl<List<Value<?>>> {
    
    public ArrayValue() {
		super(new ArrayList<>());
	}

	public ArrayValue(List<Value<?>> val) {
		super(val);
	}

	@Override
	public ArrayValue copy() {
		return new ArrayValue(Copier.copy(getValue(), s -> s.copy()));
	}
}