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
 *
 * @author Emil Forslund
 */
public final class InterfaceMethodImpl implements InterfaceMethod {
	
    private final Method m;
	
    /**
     * Wraps the specified method.
     * 
     * @param wrapped  the inner method
     */
	public InterfaceMethodImpl(Method wrapped) {
		this.m = requireNonNull(wrapped);
	}

    @Override
	public String getName() {
		return m.getName();
	}

    @Override
	public Type getType() {
		return m.getType();
	}

    @Override
	public List<Field> getFields() {
		return m.getFields();
	}

    @Override
	public List<String> getCode() {
		return m.getCode();
	}

    @Override
	public Set<Modifier> getModifiers() {
		return m.getModifiers();
	}

    @Override
	public Optional<Javadoc> getJavadoc() {
		return m.getJavadoc();
	}

    @Override
	public List<AnnotationUsage> getAnnotations() {
		return m.getAnnotations();
	}

    @Override
    public InterfaceMethod setName(String name) {
        m.setName(name);
        return this;
    }

    @Override
    public InterfaceMethod set(Type type) {
        m.set(type);
        return this;
    }

    @Override
    public List<Generic> getGenerics() {
        return m.getGenerics();
    }

    @Override
    public InterfaceMethod set(Javadoc doc) {
        m.set(doc);
        return this;
    }

    @Override
    public Set<Type> getExceptions() {
        return m.getExceptions();
    }

    @Override
	public InterfaceMethodImpl copy() {
		return new InterfaceMethodImpl(m.copy());
	}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.m);
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
        final InterfaceMethodImpl other = (InterfaceMethodImpl) obj;
        return Objects.equals(this.m, other.m);
    }
    

}