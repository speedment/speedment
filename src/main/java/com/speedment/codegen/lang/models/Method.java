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

import com.speedment.codegen.lang.interfaces.HasAnnotationUsage;
import com.speedment.codegen.lang.interfaces.Callable;
import com.speedment.codegen.lang.interfaces.HasCode;
import com.speedment.codegen.lang.interfaces.Copyable;
import com.speedment.codegen.lang.interfaces.HasJavadoc;
import com.speedment.codegen.lang.interfaces.HasThrows;
import com.speedment.codegen.lang.interfaces.HasFields;
import com.speedment.codegen.lang.interfaces.HasGenerics;
import com.speedment.codegen.lang.interfaces.HasName;
import com.speedment.codegen.lang.interfaces.HasType;
import com.speedment.codegen.lang.models.implementation.MethodImpl;
import com.speedment.codegen.lang.models.modifiers.MethodModifier;
import java.util.function.Supplier;

/**
 *
 * @author Emil Forslund
 */
public interface Method extends HasName<Method>, HasType<Method>, HasThrows<Method>,
    HasGenerics<Method>, HasFields<Method>, HasJavadoc<Method>, HasAnnotationUsage<Method>, 
    HasCode<Method>, Callable<Method>, MethodModifier<Method>, Copyable<Method> {

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