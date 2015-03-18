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
import com.speedment.codegen.lang.models.Field;
import com.speedment.codegen.lang.models.Javadoc;
import com.speedment.codegen.lang.models.Type;
import com.speedment.codegen.lang.models.Value;
import com.speedment.codegen.lang.models.modifiers.Modifier;
import com.speedment.util.Copier;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author Emil Forslund
 */
public class FieldImpl implements Field {
	
	private String name;
	private Type type;
	private Value<?> value;
	private Javadoc javadoc;
	private final List<AnnotationUsage> annotations;
	private final Set<Modifier> modifiers;
	
	public FieldImpl(String name, Type type) {
		this.name			= name;
		this.type			= type;
		this.value			= null;
		this.javadoc		= null;
		this.annotations	= new ArrayList<>();
		this.modifiers		= EnumSet.noneOf(Modifier.class);
	}
	
	protected FieldImpl(Field prototype) {
		name		= prototype.getName();
		type		= Copier.copy(prototype.getType());
		value		= prototype.getValue().map(Copier::copy).orElse(null);
		javadoc		= prototype.getJavadoc().map(Copier::copy).orElse(null);
		annotations	= Copier.copy(prototype.getAnnotations());
		modifiers	= Copier.copy(prototype.getModifiers(), c -> c.copy(), EnumSet.noneOf(Modifier.class));
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Field setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public Field set(Type type) {
		this.type = type;
		return this;
	}
	
	@Override
	public Set<Modifier> getModifiers() {
		return modifiers;
	}

	@Override
	public Field set(Javadoc doc) {
		javadoc = doc;
		return this;
	}

	@Override
	public Optional<Javadoc> getJavadoc() {
		return Optional.ofNullable(javadoc);
	}

	@Override
	public Field set(Value<?> val) {
		this.value = val;
		return this;
	}

	@Override
	public Optional<Value<?>> getValue() {
		return Optional.ofNullable(value);
	}

	@Override
	public List<AnnotationUsage> getAnnotations() {
		return annotations;
	}
    
    @Override
	public FieldImpl copy() {
		return new FieldImpl(this);
	}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.name);
        hash = 11 * hash + Objects.hashCode(this.type);
        hash = 11 * hash + Objects.hashCode(this.value);
        hash = 11 * hash + Objects.hashCode(this.javadoc);
        hash = 11 * hash + Objects.hashCode(this.annotations);
        hash = 11 * hash + Objects.hashCode(this.modifiers);
        return hash;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        return Optional.ofNullable(obj)
            .filter(o -> Field.class.isAssignableFrom(o.getClass()))
            .map(o -> (Field) o)
            .filter(o -> Objects.equals(getName(), o.getName()))
            .filter(o -> Objects.equals(getType(), o.getType()))
            .filter(o -> Objects.equals(getValue(), o.getValue()))
            .filter(o -> Objects.equals(getJavadoc(), o.getJavadoc()))
            .filter(o -> Objects.equals(getAnnotations(), o.getAnnotations()))
            .filter(o -> Objects.equals(getModifiers(), o.getModifiers()))
            .isPresent();
    }
}