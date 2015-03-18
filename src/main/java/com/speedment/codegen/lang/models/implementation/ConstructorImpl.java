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
import com.speedment.codegen.lang.models.Constructor;
import com.speedment.codegen.lang.models.Field;
import com.speedment.codegen.lang.models.Javadoc;
import com.speedment.codegen.lang.models.modifiers.Modifier;
import com.speedment.util.Copier;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author Emil Forslund
 */
public class ConstructorImpl implements Constructor {
	
	private Javadoc javadoc;
	private final List<AnnotationUsage> annotations;
	private final List<Field> params;
	private final List<String> code;
	private final Set<Modifier> modifiers;
	
	public ConstructorImpl() {
		javadoc		= null;
		annotations = new ArrayList<>();
		params		= new ArrayList<>();
		code		= new ArrayList<>();
		modifiers	= new HashSet<>();
	}
	
	protected ConstructorImpl(final Constructor prototype) {
		javadoc		= prototype.getJavadoc().map(Copier::copy).orElse(null);
		annotations	= Copier.copy(prototype.getAnnotations());
		params		= Copier.copy(prototype.getFields());
		code		= Copier.copy(prototype.getCode(), c -> c);
		modifiers	= Copier.copy(prototype.getModifiers(), c -> c.copy(), EnumSet.noneOf(Modifier.class));
	}

	@Override
	public List<Field> getFields() {
		return params;
	}

    @Override
	public List<String> getCode() {
		return code;
	}

	@Override
	public Set<Modifier> getModifiers() {
		return modifiers;
	}

	@Override
	public Constructor set(Javadoc doc) {
		javadoc = doc;
		return this;
	}

	@Override
	public Optional<Javadoc> getJavadoc() {
		return Optional.ofNullable(javadoc);
	}

	@Override
	public List<AnnotationUsage> getAnnotations() {
		return annotations;
	}
    
    @Override
	public ConstructorImpl copy() {
		return new ConstructorImpl(this);
	}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.javadoc);
        hash = 59 * hash + Objects.hashCode(this.annotations);
        hash = 59 * hash + Objects.hashCode(this.params);
        hash = 59 * hash + Objects.hashCode(this.code);
        hash = 59 * hash + Objects.hashCode(this.modifiers);
        return hash;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        return Optional.ofNullable(obj)
            .filter(o -> Constructor.class.isAssignableFrom(o.getClass()))
            .map(o -> (Constructor) o)
            .filter(o -> Objects.equals(getJavadoc(), o.getJavadoc()))
            .filter(o -> Objects.equals(getAnnotations(), o.getAnnotations()))
            .filter(o -> Objects.equals(getFields(), o.getFields()))
            .filter(o -> Objects.equals(getCode(), o.getCode()))
            .filter(o -> Objects.equals(getModifiers(), o.getModifiers()))
            .isPresent();
    }
}