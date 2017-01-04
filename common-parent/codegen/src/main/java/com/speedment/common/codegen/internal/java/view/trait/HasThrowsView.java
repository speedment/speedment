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
package com.speedment.common.codegen.internal.java.view.trait;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.Transform;
import static com.speedment.common.codegen.internal.util.CollectorUtil.joinIfNotEmpty;
import com.speedment.common.codegen.model.trait.HasThrows;

/**
 * A trait with the functionality to render models with the trait 
 * {@link HasThrows}.
 * 
 * @author     Emil Forslund
 * @param <M>  The model type
 * @see        Transform
 */
public interface HasThrowsView<M extends HasThrows<M>> extends 
    Transform<M, String> {
    
    /**
     * Render the throws-part of the model.
     * 
     * @param gen    the generator
     * @param model  the model
     * @return       the generated code
     */
    default String renderThrows(Generator gen, M model) {
        return gen.onEach(model.getExceptions())
            .collect(joinIfNotEmpty(", ", "throws ", throwsSuffix(model)));
    }
    
    /**
     * Returns any suffix to a throws statement. This will only be used if a
     * throws clause was specified.
     * 
     * @param model  the model being rendered
     * @return       the throws suffix
     */
    String throwsSuffix(M model);
}