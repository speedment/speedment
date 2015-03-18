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
import com.speedment.codegen.lang.interfaces.Copyable;
import com.speedment.codegen.lang.interfaces.Documentable;
import com.speedment.codegen.lang.interfaces.Nameable;
import com.speedment.codegen.lang.interfaces.Typeable;
import com.speedment.codegen.lang.interfaces.Valuable;
import com.speedment.codegen.lang.models.implementation.FieldImpl;
import com.speedment.codegen.lang.models.modifiers.FieldModifier;
import java.util.function.Supplier;

/**
 *
 * @author Emil Forslund
 */
public interface Field extends Copyable<Field>, Callable<Field>, Nameable<Field>, 
    Typeable<Field>, Documentable<Field>, Valuable<Field>, Annotable<Field>, 
    FieldModifier<Field> {

    enum Factory { INST;
        private Supplier<Field> supplier = () -> new FieldImpl(null, null);
    }

    static Field of(String name, Type type) {
        return Factory.INST.supplier.get().setName(name).set(type);
    }
    
    static void setSupplier(Supplier<Field> a) {
        Factory.INST.supplier = a;
    }
}