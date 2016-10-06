/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.codegen.internal.java.view.value;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.Transform;
import com.speedment.common.codegen.model.value.ArrayValue;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.speedment.common.codegen.internal.util.NullUtil.requireNonNulls;

/**
 * Transforms from an {@link ArrayValue} to java code.
 * 
 * @author Emil Forslund
 */
public final class ArrayValueView implements Transform<ArrayValue, String> {
    
    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<String> transform(Generator gen, ArrayValue model) {
        requireNonNulls(gen, model);
        
		return Optional.of(
			gen.onEach(model.getValue()).collect(
				Collectors.joining(", ", "{", "}")
			)
		);
	}
}