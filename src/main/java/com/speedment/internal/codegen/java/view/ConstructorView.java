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
package com.speedment.internal.codegen.java.view;

import com.speedment.codegen.Generator;
import com.speedment.codegen.Transform;
import com.speedment.codegen.model.Constructor;
import com.speedment.codegen.model.trait.HasName;
import com.speedment.internal.codegen.util.Formatting;
import static com.speedment.internal.codegen.util.Formatting.*;
import static com.speedment.util.CollectorUtil.joinIfNotEmpty;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Transforms from a {@link Constructor} to java code.
 * 
 * @author Emil Forslund
 */
public final class ConstructorView implements Transform<Constructor, String> {
    
    private final static String THROWS = "throws ";

    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<String> transform(Generator gen, Constructor model) {
        requireNonNull(gen);
        requireNonNull(model);
        
		return Optional.of(
			ifelse(gen.on(model.getJavadoc()), s -> s + nl(), EMPTY) +
			gen.onEach(model.getModifiers()).collect(joinIfNotEmpty(SPACE, EMPTY, SPACE)) +
			renderName(gen, model)
                .orElseThrow(() -> new UnsupportedOperationException(
                    "Could not find a nameable parent of constructor."
                )) +
			gen.onEach(model.getFields()).collect(
				Collectors.joining(COMMA_SPACE, PS, PE)
			) + SPACE + 
            gen.onEach(model.getExceptions()).collect(joinIfNotEmpty(COMMA_SPACE, THROWS, SPACE)) +
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
        requireNonNull(gen);
        requireNonNull(model);

        return gen.getRenderStack()
            .fromTop(HasName.class)
            .filter(n -> model != n)
            .map(HasName<?>::getName)
            .map(Formatting::shortName)
            .findFirst();
	}
}