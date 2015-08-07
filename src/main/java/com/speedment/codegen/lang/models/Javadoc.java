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

import com.speedment.codegen.lang.interfaces.Callable;
import com.speedment.codegen.lang.interfaces.Copyable;
import com.speedment.codegen.lang.interfaces.HasJavadocTags;
import com.speedment.codegen.lang.models.implementation.JavadocImpl;
import java.util.Collections;
import java.util.List;
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
     * Adds the specified row of documentation to this block.
     * 
     * @param row  the new row
     * @return     a reference to this model
     */
    default Javadoc add(String row) {
		getRows().add(requireNonNull(row));
		return this;
	}
	
    /**
     * Adds multiple rows of documentation to this block.
     * 
     * @param first  the first row
     * @param rows   the following rows
     * @return       a reference to this model
     */
	default Javadoc add(String first, String... rows) {
		getRows().add(requireNonNull(first));
		Collections.addAll(getRows(), requireNonNull(rows));
		return this;
	}

    /**
     * Returns a modifiable list of documentation text rows.
     * 
     * @return  the rows of documentation
     */
    List<String> getRows();
    
    /**
     * Factory holder.
     */
    enum Factory { INST;
        private Supplier<Javadoc> prototype = () -> new JavadocImpl();
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
     * @param row  the documentation row
     * @return     the new instance
     */
    static Javadoc of(String row) {
        return Factory.INST.prototype.get().add(row);
    }
    
    /**
     * Creates a new instance implementing this interface by using the class
     * supplied by the default factory. To change implementation, please use
     * the {@link #setSupplier(java.util.function.Supplier) setSupplier} method.
     * 
     * @param row   the first documentation row
     * @param rows  the following documentation rows
     * @return      the new instance
     */
    static Javadoc of(String row, String... rows) {
        return Factory.INST.prototype.get().add(row, rows);
    }
        
    /**
     * Sets the instantiation method used to create new instances of this
     * interface.
     * 
     * @param supplier  the new constructor 
     */
    static void setSupplier(Supplier<Javadoc> supplier) {
        Factory.INST.prototype = supplier;
    }
}