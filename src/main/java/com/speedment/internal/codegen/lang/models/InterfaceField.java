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
import com.speedment.internal.codegen.lang.interfaces.HasJavadoc;
import com.speedment.internal.codegen.lang.interfaces.HasName;
import com.speedment.internal.codegen.lang.interfaces.HasType;
import com.speedment.internal.codegen.lang.interfaces.HasValue;
import com.speedment.internal.codegen.lang.models.implementation.InterfaceFieldImpl;
import com.speedment.internal.codegen.lang.models.modifiers.InterfaceFieldModifier;

/**
 * A model that represents a field of an interface in code.
 * 
 * @author  Emil Forslund
 * @see     Interface
 */
public interface InterfaceField extends HasName<InterfaceField>, 
    HasType<InterfaceField>, InterfaceFieldModifier<InterfaceField>, 
    HasJavadoc<InterfaceField>, HasValue<InterfaceField>, 
    HasAnnotationUsage<InterfaceField>, Copyable<InterfaceField> {

    /**
     * Creates a new instance implementing this interface by wrapping an existing
     * {@link Field} in an {@link InterfaceFieldImpl}.
     * 
     * @param wrapped  the wrapped field
     * @return         the new instance
     */
    static InterfaceField of(Field wrapped) {
        return new InterfaceFieldImpl(wrapped);
    }
}