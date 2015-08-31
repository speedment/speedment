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
import com.speedment.internal.codegen.lang.interfaces.Copyable;
import com.speedment.internal.codegen.lang.interfaces.HasJavadocTags;
import com.speedment.internal.codegen.lang.models.implementation.JavadocImpl;
import static java.util.Objects.requireNonNull;
import java.util.function.Supplier;

/**
 * A model that represents a block of documentation in code. 
 * 
 * @author  Emil Forslund
 * @see     JavadocTag
 */
public interface Javadoc extends Copyable<Javadoc>, Callable<Javadoc>, 
    HasJavadocTags<Javadoc> {
    
    /**
     * Sets the body text shown in the javadoc.
     * 
     * @param text  the text
     * @return      a reference to this model
     */
    Javadoc setText(String text);

    /**
     * Returns the body text shown in the javadoc.
     * 
     * @return  the body documentation text
     */
    String getText();
    
    /**
     * Factory holder.
     */
    enum Factory { INST;
        private Supplier<Javadoc> prototype = JavadocImpl::new;
    }

    /**
     * Creates a new instance implementing this interface by using the class
     * supplied by the default factory. To change implementation, please use
     * the {@link #setSupplier(java.util.function.Supplier) setSupplier} method.

     * @return      the new instance
     */
    static Javadoc of() {
        return Factory.INST.prototype.get();
    }
    
    /**
     * Creates a new instance implementing this interface by using the class
     * supplied by the default factory. To change implementation, please use
     * the {@link #setSupplier(java.util.function.Supplier) setSupplier} method.
     * 
     * @param text  the documentation
     * @return      the new instance
     */
    static Javadoc of(String text) {
        return Factory.INST.prototype.get().setText(text);
    }
        
    /**
     * Sets the instantiation method used to create new instances of this
     * interface.
     * 
     * @param supplier  the new constructor 
     */
    static void setSupplier(Supplier<Javadoc> supplier) {
        Factory.INST.prototype = requireNonNull(supplier);
    }
}