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
import com.speedment.codegen.model.class_.Class_;
import com.speedment.util.$;
import com.speedment.codegen.model.modifier.ClassModifier_;
import static com.speedment.codegen.CodeUtil.*;

/**
 *
 * @author Duncan
 */
public class ClassView extends ClassAndInterfaceView<ClassModifier_, Class_> {
	private final static String 
		CLASS_STRING = "class ",
		IMPLEMENTS_STRING = "implements ";

	public ClassView() {
		super(ClassModifier_.class, ClassModifier_.values());
	}

	private CharSequence renderParent(Class_ child) {
		return child.getSuperClass() == null ? EMPTY : 
			new $(EXTENDS_STRING, child.getSuperClass().getName(), SPACE);
	}

	@Override
	public CharSequence render(CodeGenerator renderer, Class_ model) {
		return new $(
			renderPackage(renderer, model), dnl(),
			renderModifiers(model, renderer, SPACE),
			CLASS_STRING,
			renderName(model), SPACE,
			renderParent(model),
			renderList(model.getInterfaces(), renderer, COMMA_STRING, IMPLEMENTS_STRING, SPACE),
			looseBracketsIndent(new $(
				renderList(model.getFields(), renderer, scnl(), EMPTY, scnl()),
				renderList(model.getConstructors(), renderer, nl(), EMPTY, nl()),
				renderList(model.getMethods(), renderer, nl())
			))
		);
	}
}