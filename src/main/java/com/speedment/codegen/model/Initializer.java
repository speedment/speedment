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
package com.speedment.codegen.model;

import com.speedment.annotation.Api;
import com.speedment.codegen.model.modifier.InitalizerModifier;
import com.speedment.codegen.model.trait.HasCall;
import com.speedment.codegen.model.trait.HasCode;
import com.speedment.codegen.model.trait.HasCopy;
import com.speedment.internal.codegen.model.InitializerImpl;
import static java.util.Objects.requireNonNull;
import java.util.function.Supplier;

/**
 * A model that represents an initializer in code.
 * 
 * @author Emil Forslund
 * @since  2.1
 */
@Api(version = "2.3")
public interface Initializer extends HasCopy<Initializer>, HasCall<Initializer>, HasCode<Initializer>, InitalizerModifier<Initializer> {
    
    /**
     * Factory holder.
     */
    enum Factory { INST;
        private Supplier<Initializer> supplier = InitializerImpl::new;
    }

    /**
     * Creates a new instance implementing this interface by using the class
     * supplied by the default factory. To change implementation, please use
     * the {@link #setSupplier(java.util.function.Supplier) setSupplier} method.

     * @return      the new instance
     */
    static Initializer of() {
        return Factory.INST.supplier.get();
    }
        
    /**
     * Sets the instantiation method used to create new instances of this
     * interface.
     * 
     * @param supplier  the new constructor 
     */
    static void setSupplier(Supplier<Initializer> supplier) {
        Factory.INST.supplier = requireNonNull(supplier);
    }
}