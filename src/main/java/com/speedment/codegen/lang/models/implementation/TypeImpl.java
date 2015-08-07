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
package com.speedment.codegen.lang.models.implementation;

import com.speedment.codegen.lang.models.AnnotationUsage;
import com.speedment.codegen.lang.models.Generic;
import com.speedment.codegen.lang.models.Type;
import com.speedment.codegen.util.Copier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * This is the default implementation of the {@link Type} interface.
 * This class should not be instantiated directly. Instead you should call the
 * {@link Type#of(java.lang.Class)} method to get an instance. In that 
 * way, you can layer change the implementing class without modifying the using 
 * code.
 * 
 * @author Emil Forslund
 * @see    Type
 */
public class TypeImpl implements Type {

    private String name;
    private int arrayDimension;
    private final List<AnnotationUsage> annotations;
    private final List<Generic> generics;
    private java.lang.Class<?> javaImpl;

    /**
     * Initialises this type using a java class.
     * <p>
     * <b>Warning!</b> This class should not be instantiated directly but using the
     * {@link Type#of(java.lang.Class)} method!
     * 
     * @param javaImpl  the java class to use
     */
    public TypeImpl(java.lang.Class<?> javaImpl) {
        this(javaImpl.getName(), javaImpl);
    }

    /**
     * Initialises this type using an absolute type name.
     * <p>
     * <b>Warning!</b> This class should not be instantiated directly but using the
     * {@link Type#of(java.lang.String)} method!
     * 
     * @param name  the type name
     */
    public TypeImpl(String name) {
        this(name, null);
    }

    /**
     * Initialises this type using both a java class and an absolute type name.
     * <p>
     * <b>Warning!</b> This class should not be instantiated directly but using the
     * {@link Type#of(java.lang.Class)} method!
     * 
     * @param name      the type name
     * @param javaImpl  the type name
     */
    public TypeImpl(String name, java.lang.Class<?> javaImpl) {
        this.name = name;
        this.arrayDimension = 0;
        this.annotations = new ArrayList<>();
        this.generics = new ArrayList<>();
        this.javaImpl = javaImpl;
    }

    /**
     * Copy constructor.
     * 
     * @param prototype  the prototype
     */
    protected TypeImpl(Type prototype) {
        name = prototype.getName();
        arrayDimension = prototype.getArrayDimension();
        annotations = Copier.copy(prototype.getAnnotations());
        generics = Copier.copy(prototype.getGenerics());
        javaImpl = prototype.getJavaImpl().orElse(null);
	}

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Type setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<java.lang.Class<?>> getJavaImpl() {
        return Optional.ofNullable(javaImpl);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Type setJavaImpl(java.lang.Class<?> javaImpl) {
        this.javaImpl = javaImpl;
        this.name     = javaImpl.getName();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getArrayDimension() {
        return arrayDimension;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Type setArrayDimension(int arrayDimension) {
        this.arrayDimension = arrayDimension;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Generic> getGenerics() {
        return generics;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<AnnotationUsage> getAnnotations() {
        return annotations;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public TypeImpl copy() {
        return new TypeImpl(this);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		return Optional.ofNullable(obj)
            .filter(o -> Type.class.isAssignableFrom(o.getClass()))
            .map(o -> (Type) o)
            .filter(o -> Objects.equals(getName(), o.getName()))
            .filter(o -> Objects.equals(getArrayDimension(), o.getArrayDimension()))
            .filter(o -> Objects.equals(getAnnotations(), o.getAnnotations()))
            .filter(o -> Objects.equals(getGenerics(), o.getGenerics()))
            .filter(o -> Objects.equals(getJavaImpl(), o.getJavaImpl()))
            .isPresent();
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 61 * hash + Objects.hashCode(this.name);
		hash = 61 * hash + this.arrayDimension;
		hash = 61 * hash + Objects.hashCode(this.annotations);
		hash = 61 * hash + Objects.hashCode(this.generics);
		hash = 61 * hash + Objects.hashCode(this.javaImpl);
		return hash;
	}

    public static final class TypeConst extends TypeImpl {

        public TypeConst(java.lang.Class<?> javaImpl) {
            super(javaImpl);
        }

        public TypeConst(String name) {
            super(name);
        }

        public TypeConst(String name, java.lang.Class<?> javaImpl) {
            super(name, javaImpl);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Type setArrayDimension(int arrayDimension) {
            return copy().setArrayDimension(arrayDimension);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Type setJavaImpl(java.lang.Class<?> javaImpl) {
            return copy().setJavaImpl(javaImpl);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Type setName(String name) {
            return copy().setName(name);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Type add(AnnotationUsage annotation) {
            return copy().add(annotation);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Type add(Generic generic) {
            return copy().add(generic);
        }
    }
}