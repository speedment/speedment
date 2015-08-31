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

import com.speedment.internal.codegen.lang.interfaces.HasAnnotationUsage;
import com.speedment.internal.codegen.lang.interfaces.Copyable;
import com.speedment.internal.codegen.lang.interfaces.HasGenerics;
import com.speedment.internal.codegen.lang.interfaces.HasName;
import com.speedment.internal.codegen.lang.models.implementation.TypeImpl;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * A model that represents a type in code. When classes, enumerations and
 * interfaces are referenced, it is often a <code>Type</code> that is used.
 * 
 * @author  Emil Forslund
 */
public interface Type extends Copyable<Type>, HasName<Type>, HasGenerics<Type>, 
    HasAnnotationUsage<Type> {
    
    /**
     * Sets the java class to reference with this type. The name will also be
     * set using the {@link java.lang.Class#getSimpleName() Class#getSimpleName()}
     * method.
     * 
     * @param javaImpl  the new implementation
     * @return          a reference to this model
     */
    Type setJavaImpl(java.lang.Class<?> javaImpl);
    
    /**
     * Returns the implementation used in this type, or an <code>empty</code>
     * if no such is defined for this type.
     * 
     * @return  the java class or <code>empty</code> 
     */
    Optional<java.lang.Class<?>> getJavaImpl();
    
    /**
     * Sets the dimension for the array in this type. 
     * <p>
     * Here are some examples:
     * <pre>
     *     String       → 0
     *     String[]     → 1
     *     String[][]   → 2
     *     String[][][] → 3
     * </pre>
     * 
     * @param arrayDimension  the new array dimension
     * @return                a reference to this model
     */
    Type setArrayDimension(int arrayDimension);
    
    /**
     * Returns the array dimension of this type.
     * 
     * @return  the array dimension
     */
    int getArrayDimension();
    
    /**
     * Factory holder.
     */
    enum Factory { INST;
        private Supplier<Type> supplier = () -> new TypeImpl("");
    }

    /**
     * Creates a new instance implementing this interface by using the class
     * supplied by the default factory. To change implementation, please use
     * the {@link #setSupplier(java.util.function.Supplier) setSupplier} method.
     * 
     * @param name  the type name
     * @return      the new instance
     */
    static Type of(String name) {
        return Factory.INST.supplier.get().setName(name);
    }

    /**
     * Creates a new instance implementing this interface by using the class
     * supplied by the default factory. To change implementation, please use
     * the {@link #setSupplier(java.util.function.Supplier) setSupplier} method.
     * 
     * @param clazz  the java implementation
     * @return       the new instance
     */
    static Type of(java.lang.Class<?> clazz) {
        return Factory.INST.supplier.get().setJavaImpl(clazz);
    }
        
    /**
     * Sets the instantiation method used to create new instances of this
     * interface.
     * 
     * @param supplier  the new constructor 
     */
    static void setSupplier(Supplier<Type> supplier) {
        Factory.INST.supplier = requireNonNull(supplier);
    }
}
