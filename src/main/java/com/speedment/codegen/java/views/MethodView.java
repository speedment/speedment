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

import com.speedment.codegen.base.CodeView;
import com.speedment.codegen.lang.models.Method;
import com.speedment.util.CodeCombiner;
import static com.speedment.codegen.Formatting.*;
import com.speedment.codegen.base.CodeGenerator;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author Emil Forslund
 */
public class MethodView implements CodeView<Method> {

	@Override
	public Optional<String> render(CodeGenerator cg, Method model) {
		return Optional.of(
			ifelse(cg.on(model.getJavadoc()), s -> s + nl(), EMPTY) +
			cg.onEach(model.getAnnotations()).collect(CodeCombiner.joinIfNotEmpty(nl(), EMPTY, nl())) +
			cg.onEach(model.getModifiers()).collect(CodeCombiner.joinIfNotEmpty(SPACE, EMPTY, SPACE)) +
			cg.onEach(model.getGenerics()).collect(CodeCombiner.joinIfNotEmpty(COMMA_SPACE, SS, SE + SPACE)) +
			ifelse(cg.on(model.getType()), s -> s + SPACE, EMPTY) +
			model.getName() +
			cg.onEach(model.getFields()).collect(
				Collectors.joining(COMMA_SPACE, PS, PE)
			) + SPACE + block(
				model.getCode().stream().collect(
					Collectors.joining(nl())
				)
			)
		);
	}
}