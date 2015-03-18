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

import com.speedment.codegen.lang.interfaces.Annotable;
import com.speedment.codegen.lang.interfaces.Callable;
import com.speedment.codegen.lang.interfaces.Codeable;
import com.speedment.codegen.lang.interfaces.Copyable;
import com.speedment.codegen.lang.interfaces.Documentable;
import com.speedment.codegen.lang.interfaces.Fieldable;
import com.speedment.codegen.lang.interfaces.Generable;
import com.speedment.codegen.lang.interfaces.Nameable;
import com.speedment.codegen.lang.interfaces.Typeable;
import com.speedment.codegen.lang.models.implementation.MethodImpl;
import com.speedment.codegen.lang.models.modifiers.MethodModifier;
import java.util.function.Supplier;

/**
 *
 * @author Emil Forslund
 */
public interface Method extends Nameable<Method>, Typeable<Method>, 
    Generable<Method>, Fieldable<Method>, Documentable<Method>, Annotable<Method>, 
    Codeable<Method>, Callable<Method>, MethodModifier<Method>, Copyable<Method> {

    enum Factory { INST;
        private Supplier<Method> supplier = () -> new MethodImpl(null, null);
    }

    static Method of(String name, Type type) {
        return Factory.INST.supplier.get().setName(name).set(type);
    }
    
    static void setSupplier(Supplier<Method> a) {
        Factory.INST.supplier = a;
    }
}