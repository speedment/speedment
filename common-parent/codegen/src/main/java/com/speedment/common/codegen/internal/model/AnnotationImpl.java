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
package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.internal.util.Copier;
import com.speedment.common.codegen.model.*;
import com.speedment.common.codegen.model.modifier.Modifier;

import java.util.*;

import static java.util.Objects.requireNonNull;

/**
 * This is the default implementation of the {@link Annotation} interface.
 * This class should not be instantiated directly. Instead you should call the
 * {@link Annotation#of(java.lang.String)} method to get an instance. In that
 * way, you can layer change the implementing class without modifying the using
 * code.
 * 
 * @author Emil Forslund
 * @see    Annotation
 */
public final class AnnotationImpl implements Annotation {
	
	private String name;
	private Javadoc javadoc;
	private final List<AnnotationUsage> annotations;
	private final List<Field> fields;
	private final List<Import> imports;
	private final Set<Modifier> modifiers;

    /**
     * Initializes this annotation using a name.
     * <p>
     * <b>Warning!</b> This class should not be instantiated directly but using 
     * the {@link Annotation#of(java.lang.String)} method!
     * 
     * @param name  the name
     */
	public AnnotationImpl(String name) {
		this.name        = requireNonNull(name);
		this.javadoc     = null;
		this.annotations = new ArrayList<>();
		this.fields      = new ArrayList<>();
		this.imports	 = new ArrayList<>();
		this.modifiers   = EnumSet.noneOf(Modifier.class);
	}
	
    /**
     * Copy constructor.
     * 
     * @param prototype  the prototype
     */
	protected AnnotationImpl(Annotation prototype) {
        requireNonNull(prototype);
		name        = prototype.getName();
		javadoc     = prototype.getJavadoc().map(Copier::copy).orElse(null);
		annotations = Copier.copy(prototype.getAnnotations());
		fields      = Copier.copy(prototype.getFields());
		imports     = Copier.copy(prototype.getImports());
		modifiers   = Copier.copy(prototype.getModifiers(), c -> c.copy(), EnumSet.noneOf(Modifier.class));
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Annotation setName(String name) {
		this.name = requireNonNull(name);
		return this;
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
	public List<Field> getFields() {
		return fields;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Annotation set(Javadoc doc) {
		this.javadoc = doc;
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
	public List<Import> getImports() {
		return imports;
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
	public List<AnnotationUsage> getAnnotations() {
		return annotations;
	}
    
    /**
     * {@inheritDoc}
     */
    @Override
	public AnnotationImpl copy() {
		return new AnnotationImpl(this);
	}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.name);
        hash = 41 * hash + Objects.hashCode(this.javadoc);
        hash = 41 * hash + Objects.hashCode(this.annotations);
        hash = 41 * hash + Objects.hashCode(this.fields);
        hash = 41 * hash + Objects.hashCode(this.imports);
        hash = 41 * hash + Objects.hashCode(this.modifiers);
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
        final AnnotationImpl other = (AnnotationImpl) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.javadoc, other.javadoc)) {
            return false;
        }
        if (!Objects.equals(this.annotations, other.annotations)) {
            return false;
        }
        if (!Objects.equals(this.fields, other.fields)) {
            return false;
        }
        if (!Objects.equals(this.imports, other.imports)) {
            return false;
        }
		return Objects.equals(this.modifiers, other.modifiers);
	}


}