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
package com.speedment.common.codegen.internal.java.view;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.Transform;
import static com.speedment.common.codegen.internal.util.CollectorUtil.joinIfNotEmpty;
import static com.speedment.common.codegen.internal.util.NullUtil.requireNonNulls;
import com.speedment.common.codegen.model.Constructor;
import com.speedment.common.codegen.model.trait.HasName;
import com.speedment.common.codegen.util.Formatting;
import static com.speedment.common.codegen.util.Formatting.*;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Transforms from a {@link Constructor} to java code.
 * 
 * @author Emil Forslund
 */
public final class ConstructorView implements Transform<Constructor, String> {

	@Override
	public Optional<String> transform(Generator gen, Constructor model) {
        requireNonNulls(gen, model);
        
		return Optional.of(
			ifelse(gen.on(model.getJavadoc()), s -> s + nl(), "") +
			gen.onEach(model.getModifiers()).collect(joinIfNotEmpty(" ", "", " ")) +
			renderName(gen, model)
                .orElseThrow(() -> new UnsupportedOperationException(
                    "Could not find a nameable parent of constructor."
                )) +
			gen.onEach(model.getFields()).collect(
				Collectors.joining(
                    fieldSeparator(model), 
                    (model.getFields().size() > 3) ? "(" + nl() + tab() + tab() : "(", 
                    ")"
                )
			) + " " + 
            gen.onEach(model.getExceptions()).collect(joinIfNotEmpty(", ", "throws ", " ")) +
            block(
				model.getCode().stream().collect(
					Collectors.joining(nl())
				)
			)
		);
	}
	
    /**
     * Renders the name of this constructor. In java, this is the name of the
     * class or enum that the constructor is in.
     * 
     * @param gen    the generator
     * @param model  the constructor
     * @return       the rendered name
     */
	private static Optional<String> renderName(Generator gen, Constructor model) {
        requireNonNulls(gen, model);

        return gen.getRenderStack()
            .fromTop(HasName.class)
            .filter(n -> model != n)
            .map(HasName<?>::getName)
            .map(Formatting::shortName)
            .findFirst();
	}
    
    private String fieldSeparator(Constructor model) {
        if (model.getFields().size() > 3) {
            return "," + nl() + tab() + tab();
        } else return ", ";
    }
}