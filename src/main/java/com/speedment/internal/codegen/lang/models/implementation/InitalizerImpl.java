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

import com.speedment.internal.codegen.lang.models.Initalizer;
import com.speedment.internal.codegen.lang.models.modifiers.Modifier;
import com.speedment.internal.codegen.util.Copier;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.Set;

/**
 * This is the default implementation of the {@link Initalizer} interface.
 * This class should not be instantiated directly. Instead you should call the
 * {@link Initalizer#of()} method to get an instance. In that way, 
 * you can layer change the implementing class without modifying the using code.
 * 
 * @author Emil Forslund
 * @see    Initalizer
 */
public final class InitalizerImpl implements Initalizer {

    private final List<String> code;
    private final Set<Modifier> modifiers;
    
    /**
     * Initializes this initalizer.
     * <p>
     * <b>Warning!</b> This class should not be instantiated directly but using 
     * the {@link Initalizer#of()} method!
     */
    public InitalizerImpl() {
        code      = new ArrayList<>();
        modifiers = EnumSet.noneOf(Modifier.class);
    }
    
    /**
     * Copy constructor.
     * 
     * @param prototype  the prototype
     */
    protected InitalizerImpl(Initalizer prototype) {
        requireNonNull(prototype);
        code      = Copier.copy(prototype.getCode(), c -> c);
        modifiers = Copier.copy(prototype.getModifiers(), c -> c);
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
    public InitalizerImpl copy() {
        return new InitalizerImpl(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.code);
        hash = 37 * hash + Objects.hashCode(this.modifiers);
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        return Optional.ofNullable(obj)
            .filter(o -> Initalizer.class.isAssignableFrom(obj.getClass()))
            .map(o -> (Initalizer) o)
            .filter(o -> Objects.equals(getCode(), o.getCode()))
            .filter(o -> Objects.equals(getModifiers(), o.getModifiers()))
            .isPresent();
    }
}