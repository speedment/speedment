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
package com.speedment.common.codegen.internal.java.view;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.internal.model.InterfaceMethodImpl;
import com.speedment.common.codegen.model.Interface;
import com.speedment.common.codegen.model.Method;

/**
 * Transforms from an {@link Interface} to java code.
 * 
 * @author Emil Forslund
 */
public final class InterfaceView extends ClassOrInterfaceView<Interface> {
    
    /**
     * {@inheritDoc}
     */
	@Override
	protected String renderDeclarationType() {
		return INTERFACE_STRING;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public String extendsOrImplementsInterfaces() {
		return EXTENDS_STRING;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	protected String renderSupertype(Generator gen, Interface model) {
		return "";
	}

    /**
     * {@inheritDoc}
     */
    @Override
    public Object wrapMethod(Method method) {
        return new InterfaceMethodImpl(method);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String renderConstructors(Generator gen, Interface model) {
        return "";
    }
}