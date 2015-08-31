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

import com.speedment.internal.codegen.lang.models.Class;
import com.speedment.internal.codegen.lang.models.Constructor;
import com.speedment.internal.codegen.lang.models.Type;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.superType);
        hash = 97 * hash + Objects.hashCode(this.constructors);
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        return Optional.ofNullable(obj)
            .filter(o -> super.equals(obj))
            .filter(o -> Class.class.isAssignableFrom(obj.getClass()))
            .map(o -> (Class) o)
            .filter(o -> Objects.equals(getSupertype(), o.getSupertype()))
            .filter(o -> Objects.equals(getConstructors(), o.getConstructors()))
            .isPresent();
    }
}