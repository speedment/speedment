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
import com.speedment.common.codegen.model.modifier.Modifier;
import com.speedment.common.codegen.model.trait.HasMethods;

import java.lang.reflect.Type;
import java.util.*;

import static java.util.Objects.requireNonNull;

/**
 * This is the default implementation of the {@link Method} interface. This
 * class should not be instantiated directly. Instead you should call the
 * {@link Method#of(String, Type)} method to get an instance. In that way, you
 * can later change the implementing class without modifying the using code.
 *
 * @author Emil Forslund
 * @see Method
 */
public final class MethodImpl implements Method {

    private HasMethods<?> parent;
    private String name;
    private Type type;
    private Javadoc javadoc;
    private final List<AnnotationUsage> annotations;
    private final List<Import> imports;
    private final List<Generic> generics;
    private final List<Field> params;
    private final List<String> code;
    private final Set<Modifier> modifiers;
    private final Set<Type> exceptions;

    /**
     * Initializes this method using a name and a type.
     * <p>
     * <b>Warning!</b> This class should not be instantiated directly but using
     * the {@link Method#of(String, Type)} method!
     *
     * @param name the name
     * @param type the type
     */
    public MethodImpl(String name, Type type) {
        this.name        = requireNonNull(name);
        this.type        = requireNonNull(type);
        this.javadoc     = null;
        this.annotations = new ArrayList<>();
        this.imports     = new ArrayList<>();
        this.generics    = new ArrayList<>();
        this.params      = new ArrayList<>();
        this.code        = new ArrayList<>();
        this.modifiers   = EnumSet.noneOf(Modifier.class);
        this.exceptions  = new HashSet<>();
    }

    /**
     * Copy constructor.
     *
     * @param prototype the prototype
     */
    protected MethodImpl(final Method prototype) {
        parent      = prototype.getParent().orElse(null);
        name        = requireNonNull(prototype).getName();
        type        = requireNonNull(prototype.getType());
        javadoc     = prototype.getJavadoc().map(Copier::copy).orElse(null);
        annotations = Copier.copy(prototype.getAnnotations());
        imports     = Copier.copy(prototype.getImports());
        generics    = Copier.copy(prototype.getGenerics());
        params      = Copier.copy(prototype.getFields());
        code        = Copier.copy(prototype.getCode(), s -> s);
        modifiers   = Copier.copy(prototype.getModifiers(), Modifier::copy, EnumSet.noneOf(Modifier.class));
        exceptions  = new HashSet<>(prototype.getExceptions());
    }

    @Override
    public Method setParent(HasMethods<?> parent) {
        this.parent = parent;
        return this;
    }

    @Override
    public Optional<HasMethods<?>> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Method setName(String name) {
        this.name = requireNonNull(name);
        return this;
    }

    @Override
    public List<Import> getImports() {
        return imports;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public Method set(Type type) {
        this.type = requireNonNull(type);
        return this;
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
    public Method set(Javadoc doc) {
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
    public MethodImpl copy() {
        return new MethodImpl(this);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + HashUtil.identityHashForParent(this);
        hash = 71 * hash + Objects.hashCode(this.name);
        hash = 71 * hash + Objects.hashCode(this.type);
        hash = 71 * hash + Objects.hashCode(this.javadoc);
        hash = 71 * hash + Objects.hashCode(this.annotations);
        hash = 71 * hash + Objects.hashCode(this.imports);
        hash = 71 * hash + Objects.hashCode(this.generics);
        hash = 71 * hash + Objects.hashCode(this.params);
        hash = 71 * hash + Objects.hashCode(this.code);
        hash = 71 * hash + Objects.hashCode(this.modifiers);
        hash = 71 * hash + Objects.hashCode(this.exceptions);
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
        final MethodImpl other = (MethodImpl) obj;
        if (!Objects.equals(this.parent, other.parent)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.javadoc, other.javadoc)) {
            return false;
        }
        if (!Objects.equals(this.annotations, other.annotations)) {
            return false;
        }
        if (!Objects.equals(this.imports, other.imports)) {
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
