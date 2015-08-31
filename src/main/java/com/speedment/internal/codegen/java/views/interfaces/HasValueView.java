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

import static com.speedment.internal.codegen.util.Formatting.EMPTY;
import static com.speedment.internal.codegen.util.Formatting.EQUALS;
import static com.speedment.internal.codegen.util.Formatting.SPACE;
import static com.speedment.internal.codegen.util.Formatting.ifelse;
import com.speedment.internal.codegen.base.Generator;
import com.speedment.internal.codegen.base.Transform;
import com.speedment.internal.codegen.lang.interfaces.HasValue;

/**
 * A trait with the functionality to render models with the trait 
 * {@link HasValue}.
 * 
 * @author     Emil Forslund
 * @param <M>  The model type
 * @see        Transform
 */
public interface HasValueView<M extends HasValue<M>> extends Transform<M, String> {
    
    /**
     * Render the value of the model if it exists, else an empty string.
     * 
     * @param gen    the generator
     * @param model  the model
     * @return       the generated code
     */
    default String renderValue(Generator gen, M model) {
        return ifelse(model.getValue(), 
			v -> SPACE + EQUALS + SPACE + gen.on(v).orElse(EMPTY), 
            EMPTY
        );
    }
}