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
package com.speedment.codegen.java.views;

import com.speedment.codegen.lang.models.Type;
import static com.speedment.codegen.util.Formatting.*;
import com.speedment.codegen.base.Generator;
import com.speedment.codegen.base.DependencyManager;
import com.speedment.codegen.base.Transform;
import java.util.Optional;
import com.speedment.codegen.util.CodeCombiner;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Transforms from a {@link Type} to java code.
 * 
 * @author Emil Forslund
 */
public class TypeView implements Transform<Type, String> {
    
    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<String> transform(Generator gen, Type model) {
		final DependencyManager mgr = gen.getDependencyMgr();

		if (mgr.isLoaded(model.getName())) {
			return renderName(gen, model, shortName(model.getName()));
		} else {
			return renderName(gen, model, model.getName());
		}
	}
    
    /**
     * Renders the full name of the type with generics and array dimension. 
     * 
     * @param gen    the generator
     * @param model  the type
     * @param name   the name, short or full
     * @return       the generated name
     */
	private Optional<String> renderName(Generator gen, Type model, String name) {
		return Optional.of(
			name + gen.onEach(model.getGenerics()).collect(
				CodeCombiner.joinIfNotEmpty(
					COMMA_SPACE, 
					SS, 
					SE
				)
			) + 
			(model.getArrayDimension() > 0 ?
				Collections.nCopies(
					model.getArrayDimension(), 
					(AS + AE)
				).stream().collect(Collectors.joining())
				: EMPTY
			)
		);
	}
}