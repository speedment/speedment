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
package com.speedment.codegen.java.views.interfaces;

import static com.speedment.codegen.util.Formatting.EMPTY;
import com.speedment.codegen.base.Generator;
import com.speedment.codegen.base.Transform;
import com.speedment.codegen.lang.interfaces.HasFields;
import com.speedment.codegen.util.CodeCombiner;

/**
 * A trait with the functionality to render models with the trait 
 * {@link HasFields}.
 * 
 * @author     Emil Forslund
 * @param <M>  The model type
 * @see        Transform
 */
public interface HasFieldsView<M extends HasFields<M>> extends Transform<M, String> {
    
    /**
     * Render the fields-part of the model using the {@link #fieldSeparator()}
     * method to separate the fields and the {@link #fieldPrefix()}- and
     * {@link #fieldSuffix()}-methods on each field.
     * 
     * @param gen    the generator
     * @param model  the model
     * @return       the generated code
     */
    default String renderFields(Generator gen, M model) {
        return gen.onEach(model.getFields())
            .collect(CodeCombiner.joinIfNotEmpty(
                fieldSuffix() + fieldSeparator() + fieldPrefix(), 
                fieldPrefix(), 
                fieldSuffix()
            ));
    }
    
    /**
     * The separator string used when joining fields.
     * 
     * @return  the field separator
     */
    String fieldSeparator();
    
    /**
     * A text to be inserted before each field.
     * 
     * @return  the field prefix
     */
    default String fieldPrefix() {
        return EMPTY;
    }
    
    /**
     * A text to be inserted after each field.
     * 
     * @return  the field suffix
     */
    default String fieldSuffix() {
        return EMPTY;
    }
}