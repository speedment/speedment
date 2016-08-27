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
package com.speedment.common.codegen.internal.java.view.trait;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.Transform;
import com.speedment.common.codegen.model.trait.HasGenerics;

import static com.speedment.common.codegen.internal.util.CollectorUtil.joinIfNotEmpty;

/**
 * A trait with the functionality to render models with the trait 
 * {@link HasGenerics}.
 * 
 * @author     Emil Forslund
 * @param <M>  The model type
 * @see        Transform
 */
public interface HasGenericsView<M extends HasGenerics<M>> extends 
    Transform<M, String> {
    
    /**
     * A trailing suffix that is to be appended to the generics if it is rendered.
     * <p>
     * The default value is a single space (" ").
     * 
     * @return  the suffix
     */
    default String genericsSuffix() {
        return " ";
    }
    
    /**
     * Render the generics-part of the model followed by a space character.
     * 
     * @param gen    the generator
     * @param model  the model
     * @return       the generated code
     */
    default String renderGenerics(Generator gen, M model) {
        return gen.onEach(model.getGenerics())
            .collect(joinIfNotEmpty(", ", "<", ">" + genericsSuffix()));
    }
}