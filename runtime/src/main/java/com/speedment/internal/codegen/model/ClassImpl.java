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
package com.speedment.internal.codegen.model;

import com.speedment.codegen.model.Class;
import com.speedment.codegen.model.Constructor;
import com.speedment.codegen.model.Type;
import com.speedment.internal.codegen.util.Copier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * This is the default implementation of the {@link Class} interface.
 * This class should not be instantiated directly. Instead you should call the
 * {@link Class#of(java.lang.String)} method to get an instance. In that way, 
 * you can layer change the implementing class without modifying the using code.
 * 
 * @author Emil Forslund
 * @see    Class
 */
public final class ClassImpl extends ClassOrInterfaceImpl<Class> implements Class {

	private Type superType;
	private final List<Constructor> constructors;

    /**
     * Initializes this class using a name.
     * <p>
     * <b>Warning!</b> This class should not be instantiated directly but using 
     * the {@link Class#of(java.lang.String)} method!
     * 
     * @param name  the name
     */
	public ClassImpl(String name) {
		super(name);
		superType = null;
		constructors = new ArrayList<>();
	}
	
    /**
     * Copy constructor.
     * 
     * @param prototype  the prototype
     */
	protected ClassImpl(Class prototype) {
		super (prototype);
		this.superType = prototype.getSupertype().map(Copier::copy).orElse(null);
		this.constructors = Copier.copy(prototype.getConstructors());
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Class setSupertype(Type superType) {
		this.superType = superType;
		return this;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<Type> getSupertype() {
		return Optional.ofNullable(superType);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public List<Constructor> getConstructors() {
		return constructors;
	}
    
    /**
     * {@inheritDoc}
     */
    @Override
	public ClassImpl copy() {
		return new ClassImpl(this);
	}

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.superType);
        hash = 11 * hash + Objects.hashCode(this.constructors);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ClassImpl other = (ClassImpl) obj;
        if (!Objects.equals(this.superType, other.superType)) {
            return false;
        }
        if (!Objects.equals(this.constructors, other.constructors)) {
            return false;
        }
        return true;
    }


}