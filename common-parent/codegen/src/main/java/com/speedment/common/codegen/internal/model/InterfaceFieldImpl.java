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

import com.speedment.common.codegen.model.*;
import com.speedment.common.codegen.model.modifier.Modifier;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * The default implementation of the wrapper for the {@link Field} interface.
 * 
 * @author Emil Forslund
 */
public final class InterfaceFieldImpl implements InterfaceField {
    
	private final Field f;
	
    /**
     * Wraps the specified field.
     * 
     * @param wrapped  the inner field
     */
	public InterfaceFieldImpl(Field wrapped) {
		f = requireNonNull(wrapped);
	}

    @Override
	public String getName() {
		return f.getName();
	}

    @Override
	public Type getType() {
		return f.getType();
	}

    @Override
	public Set<Modifier> getModifiers() {
		return f.getModifiers();
	}

    @Override
	public Optional<Javadoc> getJavadoc() {
		return f.getJavadoc();
	}

    @Override
	public Optional<Value<?>> getValue() {
		return f.getValue();
	}

    @Override
	public List<AnnotationUsage> getAnnotations() {
		return f.getAnnotations();
	}

    @Override
    public InterfaceField setName(String name) {
        f.setName(name);
        return this;
    }

    @Override
    public InterfaceField set(Type type) {
        f.set(type);
        return this;
    }

    @Override
    public InterfaceField set(Javadoc doc) {
        f.set(doc);
        return this;
    }

    @Override
    public InterfaceField set(Value<?> val) {
        f.set(val);
        return this;
    }

	@Override
	public InterfaceFieldImpl copy() {
		return new InterfaceFieldImpl(f.copy());
	}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.f);
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
        final InterfaceFieldImpl other = (InterfaceFieldImpl) obj;
        return Objects.equals(this.f, other.f);
    }


}