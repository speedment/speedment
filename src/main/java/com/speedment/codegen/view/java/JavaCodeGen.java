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
import com.speedment.codegen.model.CodeModel;

/**
 *
 * @author Duncan
 */
public class JavaCodeGen extends CodeGenerator {
	public JavaCodeGen() {
		super ((CodeModel.Type type) -> {
			switch (type) {
				case ANNOTATION			: return new AnnotationView();
				case DEPENDENCY			: return new DependencyView();
				case BLOCK				: return new BlockView();
				case CLASS				: return new ClassView();
				case CONSTRUCTOR		: return new ConstructorView();
				case EXPRESSION			: return new ExpressionView();
				case FIELD				: return new FieldView();
				case GENERIC_PARAMETER	: return new GenericView();
				case INTERFACE			: return new InterfaceView();
				case METHOD				: return new MethodView();
				case OPERATOR			: return new OperatorView();
				case PACKAGE			: return new PackageView();
				case PARAMETER			: return new ParameterView();
				case STATEMENT			: return new StatementView();
				case TYPE				: return new TypeView();
				case MODIFIER			: return new ModifierView();
				default : throw new UnsupportedOperationException("Missing implementation for type " + type + ".");
			}
		});
	}
}