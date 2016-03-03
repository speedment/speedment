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
package com.speedment.code;

import com.speedment.annotation.Api;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.internal.codegen.lang.models.ClassOrInterface;

/**
 * A key associated with a specific {@link Translator}. This is used to 
 * recognise different implementations of the same translator target.
 * 
 * @author      Per Minborg
 * @param <DOC> Document type
 * @param <T>   CodeGen main model
 * @since       2.3
 */
@Api(version = "2.3")
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
}
