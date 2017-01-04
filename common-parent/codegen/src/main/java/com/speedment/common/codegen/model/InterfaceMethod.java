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

import com.speedment.common.codegen.internal.model.InterfaceMethodImpl;
import com.speedment.common.codegen.model.modifier.InterfaceMethodModifier;
import com.speedment.common.codegen.model.trait.*;

/**
 * A model that represents a method of an interface in code.
 * 
 * @author  Emil Forslund
 * @see     Interface
 * @since  2.0
 */
public interface InterfaceMethod extends HasName<InterfaceMethod>, 
    HasThrows<InterfaceMethod>,
    HasType<InterfaceMethod>, HasGenerics<InterfaceMethod>, 
    HasFields<InterfaceMethod>, HasJavadoc<InterfaceMethod>, 
    HasAnnotationUsage<InterfaceMethod>, HasCode<InterfaceMethod>, 
    InterfaceMethodModifier<InterfaceMethod>, HasCopy<InterfaceMethod> {

    /**
     * Creates a new instance implementing this interface by wrapping an existing
     * {@link Method} in an {@link InterfaceMethodImpl}.
     * 
     * @param wrapped  the wrapped method
     * @return         the new instance
     */
    static InterfaceMethod of(Method wrapped) {
        return new InterfaceMethodImpl(wrapped);
    }
}