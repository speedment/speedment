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

import com.speedment.internal.codegen.lang.interfaces.Copyable;
import com.speedment.internal.codegen.lang.interfaces.HasType;
import com.speedment.internal.codegen.lang.models.implementation.ImportImpl;
import com.speedment.internal.codegen.lang.models.modifiers.ImportModifier;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.function.Function;

/**
 * A model that represents the explicit import of an dependency in code.
 *
 * @author Emil Forslund
 */
public interface Import extends Copyable<Import>, HasType<Import>,
    ImportModifier<Import> {

    /**
     * Returns any static member referenced in this import. For non-static
     * imports, this value will be empty.
     *
     * @return the static member referenced
     */
    Optional<String> getStaticMember();

    /**
     * Sets the static member referenced by this import. Remember to also set
     * the modifier to static using the {@link #static_()} method!
     *
     * @param member the new static member to reference
     * @return a reference to this model
     */
    Import setStaticMember(String member);

    /**
     * Factory holder.
     */
    enum Factory {
        INST;
        private Function<Type, Import> mapper = ImportImpl::new;
    }

    /**
     * Creates a new instance implementing this interface by using the class
     * supplied by the default factory. To change implementation, please use the
     * {@link #setSupplier(java.util.function.Supplier) setSupplier} method.
     *
     * @param type the type
     * @return the new instance
     */
    static Import of(Type type) {
        return Factory.INST.mapper.apply(type);
    }

    /**
     * Sets the instantiation method used to create new instances of this
     * interface.
     *
     * @param mapper the new constructor
     */
    static void setSupplier(Function<Type, Import> mapper) {
        Factory.INST.mapper = requireNonNull(mapper);
    }
}
