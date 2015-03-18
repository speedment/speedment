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

import com.speedment.codegen.Formatting;
import com.speedment.codegen.base.CodeGenerator;
import com.speedment.codegen.lang.models.Field;
import com.speedment.codegen.lang.models.Interface;
import com.speedment.codegen.lang.models.Method;
import com.speedment.codegen.lang.models.implementation.InterfaceFieldImpl;
import com.speedment.codegen.lang.models.implementation.InterfaceMethodImpl;

/**
 *
 * @author Emil Forslund
 */
public class InterfaceView extends ClassOrInterfaceView<Interface> {
	@Override
	protected String renderDeclarationType() {
		return INTERFACE_STRING;
	}

	@Override
	public String extendsOrImplementsInterfaces() {
		return EXTENDS_STRING;
	}

	@Override
	protected String renderSuperType(CodeGenerator cg, Interface model) {
		return Formatting.EMPTY;
	}

	@Override
	protected Object wrapField(Field field) {
		return new InterfaceFieldImpl(field);
	}

    @Override
    public Object wrapMethod(Method method) {
        return new InterfaceMethodImpl(method);
    }
}