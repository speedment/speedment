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
import com.speedment.codegen.model.class_.Interface_;
import com.speedment.codegen.model.modifier.InterfaceModifier_;
import com.speedment.util.$;
import static com.speedment.codegen.CodeUtil.*;
import java.util.Optional;

/**
 *
 * @author Duncan
 */
public class InterfaceView extends ClassAndInterfaceView<InterfaceModifier_, Interface_> {
	private final static String INTERFACE_STRING = "interface ";

	public InterfaceView() {
		super (InterfaceModifier_.class, InterfaceModifier_.values());
	}
	
	@Override
	public Optional<CharSequence> render(CodeGenerator renderer, Interface_ interf) {
		return Optional.of(new $(
			renderPackage(renderer, interf), dnl(),
			renderJavadoc(renderer, interf),
			renderModifiers(interf, renderer, SPACE),
			INTERFACE_STRING,
			renderName(interf), SPACE,
			renderList(interf.getInterfaces(), renderer, COMMA_STRING, EXTENDS_STRING, SPACE),
			looseBracketsIndent(new $(
				renderList(interf.getFields(), renderer, nl()), dnl(),
				renderList(interf.getMethods(), renderer, dnl())
			))
		));
	}
}