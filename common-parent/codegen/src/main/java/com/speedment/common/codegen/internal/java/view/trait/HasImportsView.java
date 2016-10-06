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
import com.speedment.common.codegen.model.trait.HasImports;

import static com.speedment.common.codegen.internal.util.CollectorUtil.joinIfNotEmpty;
import static com.speedment.common.codegen.util.Formatting.dnl;
import static com.speedment.common.codegen.util.Formatting.nl;

/**
 * A trait with the functionality to render models with the trait 
 * {@link HasImports}.
 * 
 * @author     Emil Forslund
 * @param <M>  The model type
 * @see        Transform
 */
public interface HasImportsView<M extends HasImports<M>> extends 
    Transform<M, String> {
    
    /**
     * Render the imports-part of the model separated by new-line characters and
     * appended by two new-line characters.
     * 
     * @param gen    the generator
     * @param model  the model
     * @return       the generated code
     */
    default String renderImports(Generator gen, M model) {
        return gen.onEach(model.getImports())
            .distinct().sorted()
            .collect(joinIfNotEmpty(nl(), "", dnl()));
    }
}