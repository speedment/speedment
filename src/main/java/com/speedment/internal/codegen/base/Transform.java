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
package com.speedment.internal.codegen.base;

import java.util.Optional;

/**
 * Transforms must have a public constructor with no parameters so that it can
 * be instantiated dynamically.
 * 
 * @author Emil Forslund
 * @param <F>  the model to generate from
 * @param <T>  the resulting model
 */
public interface Transform<F, T> {
    
    /**
     * Transforms a model from one type to another. A reference to the current
     * code generator is supplied so that intermediate generation processes can
     * be initiated to resolve dependencies. The transform can choose not to
     * accept a particular input and therefore return <code>empty</code>.
     * <p>
     * This method is not meant to be called outside the code generator. If you
     * want to transform between different types, setup a {@link Generator},
     * install the <code>Transform</code> in the factory and call one of the
     * <code>on()</code>-methods in <code>Generator</code>.
     * 
     * @param gen    a reference to the generator being used
     * @param model  the model to transform
     * @return       the transformed model or empty if the transformation could
     *               not be done for that input
     */
    Optional<T> transform(Generator gen, F model);
    
    /**
     * Returns true if this transform is or contains the specified 
     * transformer. This is used internally by the code generator to avoid 
     * circular paths.
     * 
     * @param transformer  the type of the transformer to check
     * @return             true if this transform is or contains the input
     */
    default boolean is(Class<? extends Transform<?, ?>> transformer) {
        return transformer.isAssignableFrom(getClass());
    }
}