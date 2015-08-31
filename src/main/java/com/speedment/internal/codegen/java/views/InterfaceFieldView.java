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
package com.speedment.internal.codegen.java.views;

import static com.speedment.internal.codegen.util.Formatting.*;
import com.speedment.internal.codegen.base.Generator;
import com.speedment.internal.codegen.base.Transform;
import com.speedment.internal.codegen.lang.models.InterfaceField;
import static com.speedment.internal.codegen.lang.models.modifiers.Modifier.FINAL;
import static java.util.Objects.requireNonNull;
import java.util.Optional;

/**
 * Transforms from an {@link InterfaceField} to java code.
 * 
 * @author Emil Forslund
 */
public final class InterfaceFieldView implements Transform<InterfaceField, String> {
    
    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<String> transform(Generator gen, InterfaceField model) {
        requireNonNull(gen);
        requireNonNull(model);
        
		return Optional.of(
			gen.on(model.getJavadoc()).orElse(EMPTY) +	
			(model.getModifiers().contains(FINAL) ?
				gen.on(FINAL).get() + SPACE : EMPTY
			) +		
			gen.on(model.getType()).orElse(EMPTY) + SPACE +
			model.getName()
		);
	}
}