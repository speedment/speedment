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
package com.speedment.codegen.lang.models;

import com.speedment.codegen.lang.models.implementation.InterfaceImpl;
import com.speedment.codegen.lang.models.modifiers.InterfaceModifier;
import java.util.function.Supplier;

/**
 *
 * @author Emil Forslund
 */
public interface Interface extends ClassOrInterface<Interface>, InterfaceModifier<Interface> {
    
    enum Factory { INST;
        private Supplier<Interface> supplier = () -> new InterfaceImpl(null);
    }

    static Interface of(String name) {
        return Factory.INST.supplier.get().setName(name);
    }
    
    static void setSupplier(Supplier<Interface> a) {
        Factory.INST.supplier = a;
    }
}