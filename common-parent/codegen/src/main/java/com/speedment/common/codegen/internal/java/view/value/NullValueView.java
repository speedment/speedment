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
import com.speedment.common.codegen.model.value.NullValue;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * Transforms from an {@link NullValue} to java code.
 * 
 * @author Emil Forslund
 */
public final class NullValueView implements Transform<NullValue, String> {
    
	private final static String NULL = "null";
	
    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<String> transform(Generator gen, NullValue model) {
        requireNonNull(gen);
        requireNonNull(model);
        
		return Optional.of(NULL);
	}
}