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
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.modifier.Modifier;
import java.lang.reflect.Type;
import java.util.EnumSet;
import java.util.Objects;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.Set;

/**
 * This is the default implementation of the {@link Import} interface. This
 * class should not be instantiated directly. Instead you should call the
 * {@link Import#of(Type)} method to get an instance. In that way, you can layer
 * change the implementing class without modifying the using code.
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
     * <em>Warning!</em> This class should not be instantiated directly but 
     * using the {@link Import#of(Type)} method!
     *
     * @param type the type
     */
    public ImportImpl(Type type) {
        this.type         = requireNonNull(type);
        this.staticMember = null;
        this.modifiers    = EnumSet.noneOf(Modifier.class);
    }

    /**
     * Copy constructor.
     *
     * @param prototype the prototype
     */
    protected ImportImpl(Import prototype) {
        type      = prototype.getType();
        modifiers = Copier.copy(prototype.getModifiers(), c -> c.copy(), EnumSet.noneOf(Modifier.class));
    }

    @Override
    public Import set(Type type) {
        this.type = requireNonNull(type);
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
    public Optional<String> getStaticMember() {
        return Optional.ofNullable(staticMember);
    }

    @Override
    public Import setStaticMember(String member) {
        staticMember = member;
        return this;
    }

    @Override
    public ImportImpl copy() {
        return new ImportImpl(this);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.type);
        hash = 29 * hash + Objects.hashCode(this.staticMember);
        hash = 29 * hash + Objects.hashCode(this.modifiers);
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
        final ImportImpl other = (ImportImpl) obj;
        if (!Objects.equals(this.staticMember, other.staticMember)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return Objects.equals(this.modifiers, other.modifiers);
    }

}
