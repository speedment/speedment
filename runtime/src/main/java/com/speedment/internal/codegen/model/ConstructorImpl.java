/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.codegen.model;

import com.speedment.codegen.model.AnnotationUsage;
import com.speedment.codegen.model.Constructor;
import com.speedment.codegen.model.Field;
import com.speedment.codegen.model.Javadoc;
import com.speedment.codegen.model.Type;
import com.speedment.codegen.model.modifier.Modifier;
import com.speedment.internal.codegen.util.Copier;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.Set;

/**
 * This is the default implementation of the {@link Constructor} interface. This
 * class should not be instantiated directly. Instead you should call the
 * {@link Constructor#of()} method to get an instance. In that way, you can
 * layer change the implementing class without modifying the using code.
 *
 * @author Emil Forslund
 * @see Constructor
 */
public final class ConstructorImpl implements Constructor {

    private Javadoc javadoc;
    private final List<AnnotationUsage> annotations;
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
        javadoc = null;
        annotations = new ArrayList<>();
        params = new ArrayList<>();
        code = new ArrayList<>();
        modifiers = new HashSet<>();
        exceptions = new HashSet<>();
    }

    /**
     * Copy constructor.
     *
     * @param prototype the prototype
     */
    protected ConstructorImpl(final Constructor prototype) {
        javadoc = requireNonNull(prototype).getJavadoc().map(Copier::copy).orElse(null);
        annotations = Copier.copy(prototype.getAnnotations());
        params = Copier.copy(prototype.getFields());
        code = Copier.copy(prototype.getCode(), c -> c);
        modifiers = Copier.copy(prototype.getModifiers(), c -> c.copy(), EnumSet.noneOf(Modifier.class));
        exceptions = Copier.copy(prototype.getExceptions());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Field> getFields() {
        return params;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getCode() {
        return code;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Modifier> getModifiers() {
        return modifiers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Constructor set(Javadoc doc) {
        javadoc = doc;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Javadoc> getJavadoc() {
        return Optional.ofNullable(javadoc);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AnnotationUsage> getAnnotations() {
        return annotations;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Type> getExceptions() {
        return exceptions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConstructorImpl copy() {
        return new ConstructorImpl(this);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.javadoc);
        hash = 43 * hash + Objects.hashCode(this.annotations);
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
        if (!Objects.equals(this.javadoc, other.javadoc)) {
            return false;
        }
        if (!Objects.equals(this.annotations, other.annotations)) {
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
        if (!Objects.equals(this.exceptions, other.exceptions)) {
            return false;
        }
        return true;
    }

}
