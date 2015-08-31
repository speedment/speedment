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

import com.speedment.internal.codegen.lang.interfaces.Callable;
import com.speedment.internal.codegen.lang.interfaces.HasCode;
import com.speedment.internal.codegen.lang.interfaces.Copyable;
import com.speedment.internal.codegen.lang.models.implementation.InitalizerImpl;
import com.speedment.internal.codegen.lang.models.modifiers.InitalizerModifier;
import static java.util.Objects.requireNonNull;
import java.util.function.Supplier;

/**
 * A model that represents an initializer in code.
 * 
 * @author Emil Forslund
 */
public interface Initalizer extends Copyable<Initalizer>, Callable<Initalizer>, 
HasCode<Initalizer>, InitalizerModifier<Initalizer> {
    
    /**
     * Factory holder.
     */
    enum Factory { INST;
        private Supplier<Initalizer> supplier = InitalizerImpl::new;
    }

    /**
     * Creates a new instance implementing this interface by using the class
     * supplied by the default factory. To change implementation, please use
     * the {@link #setSupplier(java.util.function.Supplier) setSupplier} method.

     * @return      the new instance
     */
    static Initalizer of() {
        return Factory.INST.supplier.get();
    }
        
    /**
     * Sets the instantiation method used to create new instances of this
     * interface.
     * 
     * @param supplier  the new constructor 
     */
    static void setSupplier(Supplier<Initalizer> supplier) {
        Factory.INST.supplier = requireNonNull(supplier);
    }
}