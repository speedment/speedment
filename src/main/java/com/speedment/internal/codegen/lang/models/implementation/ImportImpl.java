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

import com.speedment.internal.codegen.lang.models.Import;
import com.speedment.internal.codegen.lang.models.Type;
import com.speedment.internal.codegen.lang.models.modifiers.Modifier;
import com.speedment.internal.codegen.util.Copier;
import java.util.EnumSet;
import java.util.Objects;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.Set;

/**
 * This is the default implementation of the {@link Import} interface.
 * This class should not be instantiated directly. Instead you should call the
 * {@link Import#of(com.speedment.codegen.lang.models.Type)} method to get an 
 * instance. In that way, you can layer change the implementing class without 
 * modifying the using code.
 * 
 * @author Emil Forslund
 * @see    Import
 */
public final class ImportImpl implements Import {
	
	private Type type;
    private String staticMember;
	private final Set<Modifier> modifiers;

    /**
     * Initializes this import using a type.
     * <p>
     * <b>Warning!</b> This class should not be instantiated directly but using 
     * the {@link Import#of(com.speedment.codegen.lang.models.Type)} method!

     * @param type  the type
     */
	public ImportImpl(Type type) {
		this.type = requireNonNull(type);
        this.staticMember = null;
		this.modifiers = EnumSet.noneOf(Modifier.class);
	}
	
    /**
     * Copy constructor.
     * 
     * @param prototype  the prototype
     */
	protected ImportImpl(Import prototype) {
		type = Copier.copy(requireNonNull(prototype).getType());
		modifiers = Copier.copy(prototype.getModifiers(), c -> c.copy(), EnumSet.noneOf(Modifier.class));
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Import set(Type type) {
		this.type = requireNonNull(type);
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
	public Set<Modifier> getModifiers() {
		return this.modifiers;
	}
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<String> getStaticMember() {
        return Optional.ofNullable(staticMember);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Import setStaticMember(String member) {
        staticMember = member;
        return this;
    }

    /**
     * {@inheritDoc}
     */
	@Override
	public ImportImpl copy() {
		return new ImportImpl(this);
	}

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.type);
        hash = 97 * hash + Objects.hashCode(this.modifiers);
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        return Optional.ofNullable(obj)
            .filter(o -> Import.class.isAssignableFrom(o.getClass()))
            .map(o -> (Import) o)
            .filter(o -> Objects.equals(getType(), o.getType()))
            .filter(o -> Objects.equals(getModifiers(), o.getModifiers()))
            .isPresent();
    }
}