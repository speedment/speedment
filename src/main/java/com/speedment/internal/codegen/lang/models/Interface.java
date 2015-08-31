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
package com.speedment.internal.codegen.lang.models;

import com.speedment.internal.codegen.lang.models.implementation.InterfaceImpl;
import com.speedment.internal.codegen.lang.models.modifiers.InterfaceModifier;
import java.util.function.Function;
import static java.util.Objects.requireNonNull;

/**
 * A model that represents an interface in code.
 * 
 * @author  Emil Forslund
 * @see     InterfaceField
 * @see     InterfaceMethod
 */
public interface Interface extends ClassOrInterface<Interface>, InterfaceModifier<Interface> {
    
    /**
     * Factory holder.
     */
    enum Factory { INST;
        private Function<String, Interface> mapper = InterfaceImpl::new;
    }

    /**
     * Creates a new instance implementing this interface by using the class
     * supplied by the default factory. To change implementation, please use
     * the {@link #setSupplier(java.util.function.Supplier) setSupplier} method.
     * 
     * @param name  the name
     * @return      the new instance
     */
    static Interface of(String name) {
        return Factory.INST.mapper.apply(name);
    }
        
    /**
     * Sets the instantiation factory method used to create new instances of this
     * interface.
     * 
     * @param mapper  the new constructor 
     */
    static void setMapper(Function<String, Interface> mapper) {
        Factory.INST.mapper = requireNonNull(mapper);
    }
}