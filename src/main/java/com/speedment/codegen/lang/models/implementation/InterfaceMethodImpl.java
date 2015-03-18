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
import com.speedment.codegen.lang.models.Generic;
import com.speedment.codegen.lang.models.InterfaceMethod;
import com.speedment.codegen.lang.models.Javadoc;
import com.speedment.codegen.lang.models.Method;
import com.speedment.codegen.lang.models.Type;
import com.speedment.codegen.lang.models.modifiers.Modifier;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author Emil Forslund
 */
public class InterfaceMethodImpl implements InterfaceMethod {
	private final Method m;
	
	public InterfaceMethodImpl(Method wrapped) {
		this.m = wrapped;
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
	public InterfaceMethodImpl copy() {
		return new InterfaceMethodImpl(m.copy());
	}
    
    @Override
    public int hashCode() {
        return m.hashCode();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        return Optional.ofNullable(obj)
            .filter(o -> InterfaceMethodImpl.class.isAssignableFrom(o.getClass()))
            .filter(o -> Objects.equals(((InterfaceMethodImpl) o).m, m))
            .isPresent();
    }
}