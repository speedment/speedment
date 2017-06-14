/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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

import com.speedment.common.codegen.internal.model.FieldImpl;
import com.speedment.common.codegen.model.modifier.FieldModifier;
import com.speedment.common.codegen.model.trait.*;

import java.lang.reflect.Type;

/**
 * A model that represents a field in code. This can be either as a method
 * parameter or as a class member.
 *
 * @author Emil Forslund
 * @since  2.0
 */
public interface Field extends 
        HasCopy<Field>, 
        HasCall<Field>, 
        HasName<Field>,
        HasType<Field>, 
        HasJavadoc<Field>, 
        HasValue<Field>, 
        HasAnnotationUsage<Field>,
        FieldModifier<Field> {

    /**
     * Creates a new instance implementing this interface by using the default
     * implementation.
     *
     * @param name the name
     * @param type the type
     * @return the new instance
     */
    static Field of(String name, Type type) {
        return new FieldImpl(name, type);
    }
}