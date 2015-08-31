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

import com.speedment.internal.codegen.lang.models.AnnotationUsage;
import com.speedment.internal.codegen.lang.models.Field;
import com.speedment.internal.codegen.lang.models.Generic;
import com.speedment.internal.codegen.lang.models.Javadoc;
import com.speedment.internal.codegen.lang.models.Method;
import com.speedment.internal.codegen.lang.models.Type;
import com.speedment.internal.codegen.lang.models.modifiers.Modifier;
import com.speedment.internal.codegen.util.Copier;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.Set;

/**
 * This is the default implementation of the {@link Method} interface.
 * This class should not be instantiated directly. Instead you should call the
 * {@link Method#of(java.lang.String, com.speedment.codegen.lang.models.Type)} 
 * method to get an instance. In that way, you can layer change the implementing 
 * class without modifying the using code.
 * 
 * @author Emil Forslund
 * @see    Method
 */
public final class MethodImpl implements Method {
	
	private String name;
	private Type type;
	private Javadoc javadoc;
	private final List<AnnotationUsage> annotations;
	private final List<Generic> generics;
	private final List<Field> params;
	private final List<String> code;
	private final Set<Modifier> modifiers;
    private final Set<Type> exceptions;
	
    /**
     * Initializes this method using a name and a type.
     * <p>
     * <b>Warning!</b> This class should not be instantiated directly but using 
     * the {@link Method#of(java.lang.String, com.speedment.codegen.lang.models.Type)} 
     * method!
     * 
     * @param name  the name
     * @param type  the type
     */
	public MethodImpl(String name, Type type) {
		this.name			= requireNonNull(name);
		this.type			= requireNonNull(type);
		this.javadoc		= null;
		this.annotations	= new ArrayList<>();
		this.generics		= new ArrayList<>();
		this.params			= new ArrayList<>();
		this.code			= new ArrayList<>();
		this.modifiers		= EnumSet.noneOf(Modifier.class);
        this.exceptions     = new HashSet<>();
	}
	
    /**
     * Copy constructor.
     * 
     * @param prototype  the prototype
     */
	protected MethodImpl(final Method prototype) {
		name		= requireNonNull(prototype).getName();
		type		= Copier.copy(prototype.getType());
		javadoc		= prototype.getJavadoc().map(Copier::copy).orElse(null);
		annotations	= Copier.copy(prototype.getAnnotations());
		generics	= Copier.copy(prototype.getGenerics());
		params		= Copier.copy(prototype.getFields());
		code		= Copier.copy(prototype.getCode(), s -> s);
		modifiers	= Copier.copy(prototype.getModifiers(), c -> c.copy(), EnumSet.noneOf(Modifier.class));
        exceptions  = Copier.copy(prototype.getExceptions());
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
	public Method setName(String name) {
		this.name = requireNonNull(name);
		return this;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Type getType() {
		return type;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Method set(Type type) {
		this.type = requireNonNull(type);
		return this;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public List<Field> getFields() {
		return params;
	}

    /**
     * {@inheritDoc}
     */
    @Override
	public List<String> getCode() {
		return code;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Set<Modifier> getModifiers() {
		return modifiers;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Method set(Javadoc doc) {
		javadoc = doc;
		return this;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<Javadoc> getJavadoc() {
		return Optional.ofNullable(javadoc);
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
	public List<Generic> getGenerics() {
		return generics;
	}
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Type> getExceptions() {
        return exceptions;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
	public MethodImpl copy() {
		return new MethodImpl(this);
	}

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.name);
        hash = 19 * hash + Objects.hashCode(this.type);
        hash = 19 * hash + Objects.hashCode(this.javadoc);
        hash = 19 * hash + Objects.hashCode(this.annotations);
        hash = 19 * hash + Objects.hashCode(this.generics);
        hash = 19 * hash + Objects.hashCode(this.params);
        hash = 19 * hash + Objects.hashCode(this.code);
        hash = 19 * hash + Objects.hashCode(this.modifiers);
        hash = 19 * hash + Objects.hashCode(this.exceptions);
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        return Optional.ofNullable(obj)
            .filter(o -> Method.class.isAssignableFrom(o.getClass()))
            .map(o -> (Method) o)
            .filter(o -> Objects.equals(getName(), o.getName()))
            .filter(o -> Objects.equals(getType(), o.getType()))
            .filter(o -> Objects.equals(getJavadoc(), o.getJavadoc()))
            .filter(o -> Objects.equals(getAnnotations(), o.getAnnotations()))
            .filter(o -> Objects.equals(getGenerics(), o.getGenerics()))
            .filter(o -> Objects.equals(getFields(), o.getFields()))
            .filter(o -> Objects.equals(getCode(), o.getCode()))
            .filter(o -> Objects.equals(getModifiers(), o.getModifiers()))
            .filter(o -> Objects.equals(getExceptions(), o.getExceptions()))
            .isPresent();
    }
}