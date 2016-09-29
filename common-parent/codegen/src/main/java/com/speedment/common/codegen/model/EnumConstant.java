/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.codegen.model;

import com.speedment.common.codegen.internal.model.EnumConstantImpl;
import com.speedment.common.codegen.model.trait.HasCopy;
import com.speedment.common.codegen.model.trait.HasName;

import java.util.List;

/**
 * A model that represents a constant in an enumeration.
 *
 * @author Emil Forslund
 * @see Enum
 * @since  2.0
 */
public interface EnumConstant extends HasCopy<EnumConstant>, HasName<EnumConstant> {

    /**
     * Adds the specified construction parameter to this constant.
     *
     * @param value the construction parameter
     * @return a reference to this model
     */
    default EnumConstant add(Value<?> value) {
        getValues().add(value);
        return this;
    }

    /**
     * Returns a modifiable list of all the construction parameters used when
     * instantiating this enum constant.
     *
     * @return all construction parameters
     */
    List<Value<?>> getValues();

    /**
     * Creates a new instance implementing this interface by using the default
     * implementation.
     *
     * @param name the name
     * @return the new instance
     */
    static EnumConstant of(String name) {
        return new EnumConstantImpl(name);
    }
}