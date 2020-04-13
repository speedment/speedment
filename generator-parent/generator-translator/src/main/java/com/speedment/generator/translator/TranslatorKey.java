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
import com.speedment.generator.translator.internal.TranslatorKeyImpl;
import com.speedment.runtime.config.trait.HasMainInterface;

/**
 * A key associated with a specific {@link Translator}. This is used to 
 * recognize different implementations of the same translator target.
 * 
 * @author      Per Minborg
 * @param <DOC> Document type
 * @param <T>   CodeGen main model
 * @since       2.3.0
 */

public interface TranslatorKey<DOC extends HasMainInterface, T extends ClassOrInterface<T>> {

    /**
     * Returns the key that is used to identify this category of translators.
     * 
     * @return  the key
     */
    String getKey();
    
    /**
     * Returns the CodeGen type that the translator will operate on.
     * 
     * @return  codegen main model type
     */
    Class<T> getTranslatedType();
    
    static <DOC extends HasMainInterface, T extends ClassOrInterface<T>> TranslatorKey<DOC, T> of(String name , Class<T> clazz) {
        return new TranslatorKeyImpl<>(name, clazz);
    }
    
}
