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
import com.speedment.codegen.lang.models.Field;
import com.speedment.codegen.base.CodeGenerator;
import com.speedment.codegen.java.views.interfaces.AnnotableView;
import com.speedment.codegen.java.views.interfaces.DocumentableView;
import com.speedment.codegen.java.views.interfaces.ModifiableView;
import com.speedment.codegen.java.views.interfaces.NameableView;
import com.speedment.codegen.java.views.interfaces.TypeableView;
import com.speedment.codegen.java.views.interfaces.ValuableView;
import java.util.Optional;

/**
 *
 * @author Emil Forslund
 */
public class FieldView implements CodeView<Field>, NameableView<Field>, 
    DocumentableView<Field>, ModifiableView<Field>, TypeableView<Field>,
    ValuableView<Field>, AnnotableView<Field> {

	@Override
	public Optional<String> render(CodeGenerator cg, Field model) {
		return Optional.of(
			renderJavadoc(cg, model) +
            renderAnnotations(cg, model) +
			renderModifiers(cg, model) +
			renderType(cg, model) +
			renderName(cg, model) +
			renderValue(cg, model)
		);
	}
	
}