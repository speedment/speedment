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

import static com.speedment.internal.codegen.util.Formatting.COMMA_SPACE;
import static com.speedment.internal.codegen.util.Formatting.EMPTY;
import static com.speedment.internal.codegen.util.Formatting.PE;
import static com.speedment.internal.codegen.util.Formatting.PS;
import static com.speedment.internal.codegen.util.Formatting.SC;
import static com.speedment.internal.codegen.util.Formatting.SPACE;
import static com.speedment.internal.codegen.util.Formatting.block;
import static com.speedment.internal.codegen.util.Formatting.ifelse;
import static com.speedment.internal.codegen.util.Formatting.nl;
import com.speedment.internal.codegen.base.Generator;
import com.speedment.internal.codegen.base.Transform;
import com.speedment.internal.codegen.lang.models.InterfaceMethod;
import static com.speedment.internal.codegen.lang.models.modifiers.Modifier.*;
import static com.speedment.internal.core.stream.CollectorUtil.joinIfNotEmpty;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Transforms from an {@link InterfaceMethod} to java code.
 * 
 * @author Emil Forslund
 */
public final class InterfaceMethodView implements Transform<InterfaceMethod, String> {
    
    private final static String THROWS = " throws ";
    
    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<String> transform(Generator gen, InterfaceMethod model) {
        requireNonNull(gen);
        requireNonNull(model);
        
		return Optional.of(ifelse(gen.on(model.getJavadoc()), s -> s + nl(), EMPTY) +
            
            gen.onEach(model.getAnnotations()).collect(joinIfNotEmpty(nl(), EMPTY, nl())) +
					
			// The only modifiers allowed are default and static
			(model.getModifiers().contains(DEFAULT) ? gen.on(DEFAULT).orElse(EMPTY) + SPACE : EMPTY) +
			(model.getModifiers().contains(STATIC) ? gen.on(STATIC).orElse(EMPTY) + SPACE : EMPTY) +
			
			gen.on(model.getType()).orElse(EMPTY) + SPACE +
			model.getName() +
			gen.onEach(model.getFields()).collect(
				Collectors.joining(COMMA_SPACE, PS, PE)
			) +
            
            gen.onEach(model.getExceptions()).collect(joinIfNotEmpty(COMMA_SPACE, THROWS, EMPTY)) +
					
			// Append body only if it is either default or static.
			(model.getModifiers().contains(DEFAULT) 
			|| model.getModifiers().contains(STATIC) ?
			SPACE + block(
				model.getCode().stream().collect(
					Collectors.joining(nl())
				)
			) : SC)
		);
	}
}
