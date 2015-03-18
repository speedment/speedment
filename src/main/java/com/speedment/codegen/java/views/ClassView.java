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

import static com.speedment.codegen.Formatting.*;
import com.speedment.codegen.base.CodeGenerator;
import com.speedment.codegen.lang.models.Class;

/**
 *
 * @author Emil Forslund
 */
public class ClassView extends ClassOrInterfaceView<Class> {
	@Override
	protected String renderDeclarationType() {
		return CLASS_STRING;
	}

	@Override
	public String extendsOrImplementsInterfaces() {
		return IMPLEMENTS_STRING;
	}

	@Override
	protected String renderSuperType(CodeGenerator cg, Class model) {
		if (model.getSupertype().isPresent()) {
			return EXTENDS_STRING + cg.on(model.getSupertype().get()).orElse(EMPTY) + SPACE;
		} else {
			return EMPTY;
		}
	}
}