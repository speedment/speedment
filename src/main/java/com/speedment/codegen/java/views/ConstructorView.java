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

import com.speedment.util.CodeCombiner;
import static com.speedment.codegen.Formatting.*;
import com.speedment.codegen.base.Generator;
import com.speedment.codegen.base.Transform;
import com.speedment.codegen.lang.interfaces.HasName;
import com.speedment.codegen.lang.models.Constructor;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author Emil Forslund
 */
public class ConstructorView implements Transform<Constructor, String> {
    
    private final static String THROWS = "throws ";

	@Override
	public Optional<String> transform(Generator cg, Constructor model) {
		return Optional.of(
			ifelse(cg.on(model.getJavadoc()), s -> s + nl(), EMPTY) +
			cg.onEach(model.getModifiers()).collect(CodeCombiner.joinIfNotEmpty(SPACE, EMPTY, SPACE)) +
			renderName(cg, model)
                .orElseThrow(() -> new UnsupportedOperationException("Could not find a nameable parent of constructor.")) +
			cg.onEach(model.getFields()).collect(
				Collectors.joining(COMMA_SPACE, PS, PE)
			) + SPACE + 
            cg.onEach(model.getExceptions()).collect(CodeCombiner.joinIfNotEmpty(COMMA_SPACE, THROWS, SPACE)) +
            block(
				model.getCode().stream().collect(
					Collectors.joining(nl())
				)
			)
		);
	}
	
	private static Optional<String> renderName(Generator cg, Constructor model) {
		Optional<String> result = cg.getRenderStack()
            .fromTop(HasName.class)
            .filter(n -> model != n)
            .map(n -> shortName(n.getName()))
            .findFirst();
        
        return result;
	}
}