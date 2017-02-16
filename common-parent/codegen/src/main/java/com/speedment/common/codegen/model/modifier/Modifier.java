/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.codegen.model.modifier;

import com.speedment.common.codegen.model.trait.HasCopy;

/**
 * A modifier is a keyword that the programmer can put before a declaration
 * to configure things like accessibility and lifespan.
 * 
 * @author Emil Forslund
 * @since  2.0
 */
public enum Modifier implements HasCopy<Modifier> {
    
	PUBLIC ("public"),
	PROTECTED ("protected"),
	PRIVATE ("private"),
	ABSTRACT ("abstract"),
	FINAL ("final"),
    STATIC ("static"),
    STRICTFP ("strictfp"),
	TRANSIENT ("transient"),
	VOLATILE ("volatile"),
	SYNCHRONIZED ("synchronized"),
	NATIVE ("native"),
	DEFAULT ("default");
	
	private final String name;
	
    /**
     * Modifier constructor.
     * 
     * @param name the name in lowercase
     */
	Modifier(String name) {
		this.name = name;
	}
	
    /**
     * Returns the name of the modifier.
     * 
     * @return the name
     */
	public String getName() {
		return name;
	}

	@Override
	public Modifier copy() {
		return this;
	}
}