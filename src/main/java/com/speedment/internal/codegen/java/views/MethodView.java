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

import com.speedment.internal.codegen.lang.models.Method;
import static com.speedment.internal.codegen.util.Formatting.*;
import com.speedment.internal.codegen.base.Generator;
import com.speedment.internal.codegen.base.Transform;
import static com.speedment.internal.core.stream.CollectorUtil.joinIfNotEmpty;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Transforms from a {@link Method} to java code.
 * 
 * @author Emil Forslund
 */
public final class MethodView implements Transform<Method, String> {
    
    private final static String THROWS = "throws ";

    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<String> transform(Generator gen, Method model) {
        requireNonNull(gen);
        requireNonNull(model);
        
		return Optional.of(
			ifelse(gen.on(model.getJavadoc()), s -> s + nl(), EMPTY) +
			gen.onEach(model.getAnnotations()).collect(joinIfNotEmpty(nl(), EMPTY, nl())) +
			gen.onEach(model.getModifiers()).collect(joinIfNotEmpty(SPACE, EMPTY, SPACE)) +
			gen.onEach(model.getGenerics()).collect(joinIfNotEmpty(COMMA_SPACE, SS, SE + SPACE)) +
			ifelse(gen.on(model.getType()), s -> s + SPACE, EMPTY) +
			model.getName() +
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
}