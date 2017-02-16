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
import com.speedment.common.codegen.model.Initializer;
import com.speedment.common.codegen.model.modifier.Modifier;

import java.util.*;

import static java.util.Objects.requireNonNull;

/**
 * This is the default implementation of the {@link Initializer} interface.
 * This class should not be instantiated directly. Instead you should call the
 * {@link Initializer#of()} method to get an instance. In that way, 
 * you can layer change the implementing class without modifying the using code.
 * 
 * @author Emil Forslund
 * @see    Initializer
 */
public final class InitializerImpl implements Initializer {

    private final List<String> code;
    private final Set<Modifier> modifiers;
    
    /**
     * Initializes this initializer.
     * <p>
     * <b>Warning!</b> This class should not be instantiated directly but using 
     * the {@link Initializer#of()} method!
     */
    public InitializerImpl() {
        code      = new ArrayList<>();
        modifiers = EnumSet.noneOf(Modifier.class);
    }
    
    /**
     * Copy constructor.
     * 
     * @param prototype  the prototype
     */
    protected InitializerImpl(Initializer prototype) {
        requireNonNull(prototype);
        code      = Copier.copy(prototype.getCode(), c -> c);
        modifiers = Copier.copy(prototype.getModifiers(), c -> c);
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
    public InitializerImpl copy() {
        return new InitializerImpl(this);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.code);
        hash = 67 * hash + Objects.hashCode(this.modifiers);
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
        final InitializerImpl other = (InitializerImpl) obj;
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        return Objects.equals(this.modifiers, other.modifiers);
    }


}