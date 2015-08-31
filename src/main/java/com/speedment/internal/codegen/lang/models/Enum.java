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

import com.speedment.internal.codegen.lang.interfaces.HasConstructors;
import com.speedment.internal.codegen.lang.models.implementation.EnumImpl;
import com.speedment.internal.codegen.lang.models.modifiers.EnumModifier;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.function.Function;

/**
 * A model that represents an enumeration in code.
 *
 * @author Emil Forslund
 * @see EnumConstant
 */
public interface Enum extends ClassOrInterface<Enum>, EnumModifier<Enum>,
    HasConstructors<Enum> {

    /**
     * Adds the specified constant to this enum. The constant must not be null.
     *
     * @param constant the constant
     * @return a reference to this model
     */
    default Enum add(EnumConstant constant) {
        getConstants().add(requireNonNull(constant));
        return this;
    }

    /**
     * Returns a modifiable list of all the constants in this enum.
     *
     * @return a list of constants
     */
    List<EnumConstant> getConstants();

    /**
     * Factory holder.
     */
    enum Factory {
        INST;
        private Function<String, Enum> mapper = EnumImpl::new;
    }

    /**
     * Creates a new instance implementing this interface by using the class
     * supplied by the default factory. To change implementation, please use the
     * {@link #setSupplier(java.util.function.Supplier) setSupplier} method.
     *
     * @param name the name
     * @return the new instance
     */
    static Enum of(String name) {
        return Factory.INST.mapper.apply(name);
    }

    /**
     * Sets the instantiation factory method used to create new instances of
     * this interface.
     *
     * @param mapper the new constructor
     */
    static void setSupplier(Function<String, Enum> mapper) {
        Factory.INST.mapper = requireNonNull(mapper);
    }
}
