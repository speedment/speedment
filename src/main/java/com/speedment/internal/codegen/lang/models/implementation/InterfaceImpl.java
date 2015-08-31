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
package com.speedment.internal.codegen.lang.models.implementation;

import com.speedment.internal.codegen.lang.models.Interface;
import com.speedment.internal.codegen.lang.models.Method;

/**
 * The default implementation of the wrapper for the {@link Method} interface.
 * 
 * @author Emil Forslund
 */
public final class InterfaceImpl extends ClassOrInterfaceImpl<Interface> implements Interface {
    
    /**
     * Initializes this interface using a name.
     * <p>
     * <b>Warning!</b> This class should not be instantiated directly but using 
     * the {@link Interface#of(java.lang.String)} method!
     * 
     * @param name  the name
     */
    public InterfaceImpl(String name) {
		super (name);
    }
	
    /**
     * Copy constructor
     * 
     * @param prototype  the prototype
     */
	protected InterfaceImpl(Interface prototype) {
		super (prototype);
    }

    /**
     * {@inheritDoc}
     */
	@Override
	public InterfaceImpl copy() {
		return new InterfaceImpl(this);
	}
}