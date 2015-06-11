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
import com.speedment.codegen.lang.interfaces.Copyable;
import com.speedment.codegen.lang.interfaces.HasGenerics;
import com.speedment.codegen.lang.interfaces.HasName;
import com.speedment.codegen.lang.models.implementation.TypeImpl;
import java.util.Optional;
import java.util.function.Supplier;

/**
 *
 * @author Emil Forslund
 */
public interface Type extends Copyable<Type>, HasName<Type>, HasGenerics<Type>, HasAnnotationUsage<Type> {
    Type setJavaImpl(java.lang.Class<?> javaImpl);
    Optional<java.lang.Class<?>> getJavaImpl();
    Type setArrayDimension(int arrayDimension);
    int getArrayDimension();
    
    enum Factory { INST;
        private Supplier<Type> supplier = () -> new TypeImpl("");
    }

    static Type of(String name) {
        return Factory.INST.supplier.get().setName(name);
    }
    
    static Type of(java.lang.Class<?> clazz) {
        return Factory.INST.supplier.get().setJavaImpl(clazz);
    }
    
    static void setSupplier(Supplier<Type> a) {
        Factory.INST.supplier = a;
    }
}
