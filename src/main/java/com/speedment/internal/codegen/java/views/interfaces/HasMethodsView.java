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
package com.speedment.internal.codegen.java.views.interfaces;

import static com.speedment.internal.codegen.util.Formatting.dnl;
import com.speedment.internal.codegen.base.Generator;
import com.speedment.internal.codegen.base.Transform;
import com.speedment.internal.codegen.lang.interfaces.HasMethods;
import com.speedment.internal.codegen.lang.models.Method;
import java.util.stream.Collectors;

/**
 * A trait with the functionality to render models with the trait 
 * {@link HasMethods}.
 * 
 * @author     Emil Forslund
 * @param <M>  The model type
 * @see        Transform
 */
public interface HasMethodsView<M extends HasMethods<M>> extends Transform<M, String> {
    
    /**
     * Render the methods-part of the model separated with two new-line 
     * characters. The {@link #wrapMethod(com.speedment.codegen.lang.models.Method)}-method
     * can be overridden to change the implementation type of the methods before
     * rendering.
     * 
     * @param gen    the generator
     * @param model  the model
     * @return       the generated code
     */
    default String renderMethods(Generator gen, M model) {
        return gen.onEach(
            model.getMethods().stream()
                .map(this::wrapMethod)
                .collect(Collectors.toList())
        ).collect(Collectors.joining(dnl()));
    }
    
    /**
     * This method is called for every method being generated to give the
     * implementing class a chance to change the implementation before rendering.
     * There must be a {@link Transform} installed in the generator that can
     * handle the output of this method.
     * <p>
     * The default behaviour of this method is to return the input without any
     * modifications.
     * 
     * @param method  the method to wrap
     * @return        a model derived from the method
     */
    default Object wrapMethod(Method method) {
        return method;
    }
}