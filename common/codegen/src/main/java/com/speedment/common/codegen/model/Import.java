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

import com.speedment.common.codegen.internal.model.ImportImpl;
import com.speedment.common.codegen.model.modifier.ImportModifier;
import com.speedment.common.codegen.model.trait.HasCopy;
import com.speedment.common.codegen.model.trait.HasType;

import java.lang.reflect.Type;
import java.util.Optional;

/**
 * A model that represents the explicit import of an dependency in code.
 *
 * @author Emil Forslund
 * @since  2.0
 */
public interface Import extends HasCopy<Import>, HasType<Import>,
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
     * Creates a new instance implementing this interface by using the default
     * implementation.
     *
     * @param type the type
     * @return the new instance
     */
    static Import of(Type type) {
        return new ImportImpl(type);
    }
}