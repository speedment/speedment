/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.generator.translator;

import com.speedment.common.codegen.model.ClassOrInterface;
import com.speedment.common.codegen.model.File;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.runtime.core.exception.SpeedmentException;

import java.util.function.Supplier;

/**
 * A more specific {@link Translator} that results in a CodeGen {@link File}.
 * The class contains many helper-functions to make the generation process
 * easier.
 *
 * @author       Per Minborg
 * @param <DOC>  the document type
 * @param <T>    the codegen model type
 * @since        2.0.0
 */

public interface JavaClassTranslator<DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>> 
    extends Translator<DOC, T> {

    TranslatorSupport<DOC> getSupport();
    
    static Supplier<SpeedmentException> foundNoProjectException() {
        return () -> new SpeedmentException(
            "Could not find any project node in document"
        );
    }
    
}
