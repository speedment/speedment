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
import com.speedment.common.codegen.model.AnnotationUsage;
import com.speedment.common.codegen.model.Constructor;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.Generic;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.Javadoc;
import com.speedment.common.codegen.model.modifier.Modifier;
import com.speedment.common.codegen.model.trait.HasConstructors;

import java.lang.reflect.Type;
import java.util.*;

import static java.util.Objects.requireNonNull;

/**
 * This is the default implementation of the {@link Constructor} interface. This
 * class should not be instantiated directly. Instead you should call the
 * {@link Constructor#of()} method to get an instance. In that way, you can
 * later change the implementing class without modifying the using code.
 *
 * @author Emil Forslund
 * @see Constructor
 */
public final class ConstructorImpl implements Constructor {

    private HasConstructors<?> parent;
    private Javadoc javadoc;
    private final List<Import> imports;
    private final List<AnnotationUsage> annotations;
    private final List<Generic> generics;
    private final List<Field> params;
    private final List<String> code;
    private final Set<Modifier> modifiers;
    private final Set<Type> exceptions;

    /**
     * Initializes this constructor.
     * <p>
     * <b>Warning!</b> This class should not be instantiated directly but using
     * the {@link Constructor#of()} method!
     */
    public ConstructorImpl() {
        javadoc     = null;
        imports     = new ArrayList<>();
        annotations = new ArrayList<>();
        generics    = new ArrayList<>();
        params      = new ArrayList<>();
        code        = new ArrayList<>();
        modifiers   = new HashSet<>();
        exceptions  = new HashSet<>();
    }

    /**
     * Copy constructor.
     *
     * @param prototype the prototype
     */
    private ConstructorImpl(final Constructor prototype) {
        javadoc     = requireNonNull(prototype).getJavadoc().map(Copier::copy).orElse(null);
        imports     = Copier.copy(prototype.getImports());
        annotations = Copier.copy(prototype.getAnnotations());
        generics    = Copier.copy(prototype.getGenerics());
        params      = Copier.copy(prototype.getFields());
        code        = Copier.copy(prototype.getCode(), c -> c);
        modifiers   = Copier.copy(prototype.getModifiers(), Modifier::copy, EnumSet.noneOf(Modifier.class));
        exceptions  = new LinkedHashSet<>(prototype.getExceptions());
    }

    @Override
    public Constructor setParent(HasConstructors<?> parent) {
        this.parent = parent;
        return this;
    }

    @Override
    public Optional<HasConstructors<?>> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    public List<Import> getImports() {
        return imports;
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
        javadoc = doc.setParent(this);
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
    public List<Generic> getGenerics() {
        return generics;
    }

    @Override
    public Set<Type> getExceptions() {
        return exceptions;
    }

    @Override
    public ConstructorImpl copy() {
        return new ConstructorImpl(this);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + HashUtil.identityHashForParent(this); // Avoid stack overflow
        hash = 43 * hash + Objects.hashCode(this.javadoc);
        hash = 43 * hash + Objects.hashCode(this.imports);
        hash = 43 * hash + Objects.hashCode(this.annotations);
        hash = 43 * hash + Objects.hashCode(this.generics);
        hash = 43 * hash + Objects.hashCode(this.params);
        hash = 43 * hash + Objects.hashCode(this.code);
        hash = 43 * hash + Objects.hashCode(this.modifiers);
        hash = 43 * hash + Objects.hashCode(this.exceptions);
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
        final ConstructorImpl other = (ConstructorImpl) obj;
        if (!Objects.equals(this.parent, other.parent)) {
            return false;
        }
        if (!Objects.equals(this.javadoc, other.javadoc)) {
            return false;
        }
        if (!Objects.equals(this.imports, other.imports)) {
            return false;
        }
        if (!Objects.equals(this.annotations, other.annotations)) {
            return false;
        }
        if (!Objects.equals(this.generics, other.generics)) {
            return false;
        }
        if (!Objects.equals(this.params, other.params)) {
            return false;
        }
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        if (!Objects.equals(this.modifiers, other.modifiers)) {
            return false;
        }
        return Objects.equals(this.exceptions, other.exceptions);
    }

}
