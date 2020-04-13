/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.internal.util.Copier;
import com.speedment.common.codegen.model.Constructor;
import com.speedment.common.codegen.model.Enum;
import com.speedment.common.codegen.model.EnumConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This is the default implementation of the {@link Enum} interface.
 * This class should not be instantiated directly. Instead you should call the
 * {@link Enum#of(java.lang.String)} method to get an instance. In that way, 
 * you can later change the implementing class without modifying the using code.
 * 
 * @author Emil Forslund
 * @see    Enum
 */
public final class EnumImpl extends ClassOrInterfaceImpl<Enum> implements Enum {
	
	private final List<EnumConstant> constants;
	private final List<Constructor> constructors;
	
    /**
     * Initializes this enum using a name.
     * <p>
     * <b>Warning!</b> This class should not be instantiated directly but using 
     * the {@link Enum#of(java.lang.String)} method!
     * 
     * @param name  the name
     */
	public EnumImpl(String name) {
		super(name);
		constants    = new ArrayList<>();
		constructors = new ArrayList<>();
	}
	
    /**
     * Copy constructor.
     * 
     * @param prototype  the prototype
     */
	protected EnumImpl(Enum prototype) {
		super (prototype);
		constants    = Copier.copy(prototype.getConstants());
		constructors = Copier.copy(prototype.getConstructors());
	}

    @Override
	public List<EnumConstant> getConstants() {
		return constants;
	}

	@Override
	public List<Constructor> getConstructors() {
		return constructors;
	}

	@Override
	public EnumImpl copy() {
		return new EnumImpl(this);
	}

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + super.hashCode();
        hash = 13 * hash + Objects.hashCode(this.constants);
        hash = 13 * hash + Objects.hashCode(this.constructors);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        final EnumImpl other = (EnumImpl) obj;
        if (!Objects.equals(this.constants, other.constants)) {
            return false;
        }
        return Objects.equals(this.constructors, other.constructors);
    }

}