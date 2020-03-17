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
import com.speedment.common.codegen.model.*;
import com.speedment.common.codegen.model.Enum;
import com.speedment.common.codegen.model.trait.HasCopy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * This is the default implementation of the {@link EnumConstant} interface.
 * This class should not be instantiated directly. Instead you should call the
 * {@link EnumConstant#of(java.lang.String)} method to get an instance. In that way, 
 * you can later change the implementing class without modifying the using code.
 * 
 * @author Emil Forslund
 * @see    EnumConstant
 */
public final class EnumConstantImpl implements EnumConstant {

    private Enum parent;
	private String name;
    private Javadoc javadoc;
    private final List<Import> imports;
    private final List<ClassOrInterface<?>> classes;
    private final List<Initializer> initializers;
    private final List<Method> methods;
    private final List<Field> fields;
	private final List<Value<?>> values;
	private final List<AnnotationUsage> annotations;

    /**
     * Initializes this enum constant using a name.
     * <p>
     * <b>Warning!</b> This class should not be instantiated directly but using 
     * the {@link EnumConstant#of(java.lang.String)} method!
     * 
     * @param name  the name
     */
	public EnumConstantImpl(String name) {
		this.name	      = requireNonNull(name);
		this.imports      = new ArrayList<>();
		this.classes      = new ArrayList<>();
		this.initializers = new ArrayList<>();
		this.methods      = new ArrayList<>();
		this.fields       = new ArrayList<>();
		this.values       = new ArrayList<>();
		this.annotations  = new ArrayList<>();
	}
	
    /**
     * Copy constructor.
     * 
     * @param prototype  the prototype 
     */
	protected EnumConstantImpl(EnumConstant prototype) {
	    name	     = requireNonNull(prototype).getName();
        javadoc      = prototype.getJavadoc().orElse(null);
		imports	     = Copier.copy(prototype.getImports());
		classes	     = Copier.copy(prototype.getClasses(), ClassOrInterface::copy);
		initializers = Copier.copy(prototype.getInitializers(), HasCopy::copy);
		methods	     = Copier.copy(prototype.getMethods(), HasCopy::copy);
		fields	     = Copier.copy(prototype.getFields(), HasCopy::copy);
		values	     = Copier.copy(prototype.getValues(), HasCopy::copy);
        annotations  = Copier.copy(prototype.getAnnotations(), HasCopy::copy);
	}

    @Override
    public EnumConstant setParent(Enum parent) {
        this.parent = parent;
        return this;
    }

    @Override
    public Optional<Enum> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    public List<Import> getImports() {
        return imports;
    }

    @Override
	public EnumConstant setName(String name) {
		this.name = requireNonNull(name);
		return this;
	}

	@Override
	public String getName() {
		return name;
	}

    @Override
	public List<Value<?>> getValues() {
		return values;
	}
    
    @Override
    public EnumConstant set(Javadoc doc) {
        this.javadoc = doc.setParent(this);
        return this;
    }

    @Override
    public Optional<Javadoc> getJavadoc() {
        return Optional.ofNullable(javadoc);
    }

    @Override
    public List<ClassOrInterface<?>> getClasses() {
        return classes;
    }

    @Override
    public List<Initializer> getInitializers() {
        return initializers;
    }

    @Override
    public List<Method> getMethods() {
        return methods;
    }

    @Override
    public List<Field> getFields() {
        return fields;
    }

    @Override
    public List<AnnotationUsage> getAnnotations() {
        return annotations;
    }

    @Override
	public EnumConstantImpl copy() {
		return new EnumConstantImpl(this);
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final EnumConstantImpl that = (EnumConstantImpl) o;
        return Objects.equals(parent, that.parent) &&
                Objects.equals(name, that.name) &&
                Objects.equals(javadoc, that.javadoc) &&
                Objects.equals(imports, that.imports) &&
                Objects.equals(classes, that.classes) &&
                Objects.equals(initializers, that.initializers) &&
                Objects.equals(methods, that.methods) &&
                Objects.equals(fields, that.fields) &&
                Objects.equals(values, that.values) &&
                Objects.equals(annotations, that.annotations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, javadoc, imports, classes, initializers, methods, fields, values, annotations)
                + HashUtil.identityHashForParent(this);
    }
}