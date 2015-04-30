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

import com.speedment.codegen.lang.models.Import;
import com.speedment.codegen.lang.models.Type;
import com.speedment.codegen.lang.models.modifiers.Modifier;
import com.speedment.util.Copier;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author Emil Forslund
 */
public class ImportImpl implements Import {
	
	private Type type;
	private final Set<Modifier> modifiers;

	public ImportImpl(Type type) {
		this.type = type;
		this.modifiers = EnumSet.noneOf(Modifier.class);
	}
	
	protected ImportImpl(Import prototype) {
		type = Copier.copy(prototype.getType());
		modifiers = Copier.copy(prototype.getModifiers(), c -> c.copy(), EnumSet.noneOf(Modifier.class));
	}

	@Override
	public Import set(Type type) {
		this.type = type;
		return this;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public Set<Modifier> getModifiers() {
		return this.modifiers;
	}

	@Override
	public ImportImpl copy() {
		return new ImportImpl(this);
	}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.type);
        hash = 97 * hash + Objects.hashCode(this.modifiers);
        return hash;
    }

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