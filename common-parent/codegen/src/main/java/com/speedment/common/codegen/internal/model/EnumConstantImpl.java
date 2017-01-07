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
package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.internal.util.Copier;
import com.speedment.common.codegen.model.ClassOrInterface;
import com.speedment.common.codegen.model.EnumConstant;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.Initializer;
import com.speedment.common.codegen.model.Javadoc;
import com.speedment.common.codegen.model.Method;
import com.speedment.common.codegen.model.Value;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.Optional;

/**
 * This is the default implementation of the {@link EnumConstant} interface.
 * This class should not be instantiated directly. Instead you should call the
 * {@link EnumConstant#of(java.lang.String)} method to get an instance. In that way, 
 * you can layer change the implementing class without modifying the using code.
 * 
 * @author Emil Forslund
 * @see    EnumConstant
 */
public final class EnumConstantImpl implements EnumConstant {
	
	private String name;
    private Javadoc javadoc;
    private final List<ClassOrInterface<?>> classes;
    private final List<Initializer> initializers;
    private final List<Method> methods;
    private final List<Field> fields;
	private final List<Value<?>> values;

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
		this.classes      = new ArrayList<>();
		this.initializers = new ArrayList<>();
		this.methods      = new ArrayList<>();
		this.fields       = new ArrayList<>();
		this.values       = new ArrayList<>();
	}
	
    /**
     * Copy constructor.
     * 
     * @param prototype  the prototype 
     */
	protected EnumConstantImpl(EnumConstant prototype) {
		name	     = requireNonNull(prototype).getName();
        javadoc      = prototype.getJavadoc().orElse(null);
		classes	     = Copier.copy(prototype.getClasses(), v -> v.copy());
		initializers = Copier.copy(prototype.getInitializers(), v -> v.copy());
		methods	     = Copier.copy(prototype.getMethods(), v -> v.copy());
		fields	     = Copier.copy(prototype.getFields(), v -> v.copy());
		values	     = Copier.copy(prototype.getValues(), v -> v.copy());
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public EnumConstant setName(String name) {
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
	public List<Value<?>> getValues() {
		return values;
	}
    
    @Override
    public EnumConstant set(Javadoc doc) {
        this.javadoc = doc;
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

    /**
     * {@inheritDoc}
     */
	@Override
	public EnumConstantImpl copy() {
		return new EnumConstantImpl(this);
	}

    
}