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
package com.speedment.codegen.view.java;

import com.speedment.codegen.CodeGenerator;
import com.speedment.codegen.model.method.Method_;
import com.speedment.codegen.view.CodeView;
import static com.speedment.codegen.CodeUtil.*;
import com.speedment.codegen.model.modifier.Modifier_;
import java.util.stream.Collectors;
import com.speedment.util.$;

/**
 *
 * @author Duncan
 */
public class MethodView extends CodeView<Method_> {
	private final static CharSequence 
		PUBLIC = "public ",
		PROTECTED = "protected ",
		PRIVATE = "private ",
		FINAL = "final ",
		STATIC = "static ",
		COMMA = ", ";
	
	@Override
	public CharSequence render(CodeGenerator renderer, Method_ method) {
		return new $(
//			method. ? PUBLIC :
//			method.isProtected_() ? PROTECTED :
//			method.isPrivate_() ? PRIVATE : EMPTY, 
//			method.isFinal_() ? FINAL : EMPTY,
//			method.isStatic_() ? STATIC : EMPTY,
			method.getName_(), PS,
				method.getParameters().stream()
					.map((param) -> renderer.on(param))
					.collect(Collectors.joining(COMMA)),
			PE, renderer.on(method.getBlock_())
		);
	}
}
