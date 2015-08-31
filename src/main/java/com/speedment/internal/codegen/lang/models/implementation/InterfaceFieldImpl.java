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
import com.speedment.internal.codegen.lang.models.InterfaceField;
import com.speedment.internal.codegen.lang.models.Javadoc;
import com.speedment.internal.codegen.lang.models.Type;
import com.speedment.internal.codegen.lang.models.Value;
import com.speedment.internal.codegen.lang.models.modifiers.Modifier;
import java.util.List;
import java.util.Objects;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.Set;

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

    /**
     * {@inheritDoc}
     */
    @Override
	public String getName() {
		return f.getName();
	}
	
    /**
     * {@inheritDoc}
     */
    @Override
	public Type getType() {
		return f.getType();
	}

    /**
     * {@inheritDoc}
     */
    @Override
	public Set<Modifier> getModifiers() {
		return f.getModifiers();
	}

    /**
     * {@inheritDoc}
     */
    @Override
	public Optional<Javadoc> getJavadoc() {
		return f.getJavadoc();
	}

    /**
     * {@inheritDoc}
     */
    @Override
	public Optional<Value<?>> getValue() {
		return f.getValue();
	}

    /**
     * {@inheritDoc}
     */
    @Override
	public List<AnnotationUsage> getAnnotations() {
		return f.getAnnotations();
	}

    /**
     * {@inheritDoc}
     */
    @Override
    public InterfaceField setName(String name) {
        f.setName(name);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterfaceField set(Type type) {
        f.set(type);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterfaceField set(Javadoc doc) {
        f.set(doc);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InterfaceField set(Value<?> val) {
        f.set(val);
        return this;
    }
    
    /**
     * {@inheritDoc}
     */
	@Override
	public InterfaceFieldImpl copy() {
		return new InterfaceFieldImpl(f.copy());
	}

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return f.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        return Optional.ofNullable(obj)
            .filter(o -> InterfaceFieldImpl.class.isAssignableFrom(o.getClass()))
            .filter(o -> Objects.equals(((InterfaceFieldImpl) o).f, f))
            .isPresent();
    }
}