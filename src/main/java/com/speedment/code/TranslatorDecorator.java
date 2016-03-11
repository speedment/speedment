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
import com.speedment.config.db.trait.HasName;
import com.speedment.codegen.lang.models.ClassOrInterface;

/**
 * This interface describes a functional reference to something that decorates
 * a {@link Translator} before it used used. This is useful for adding 
 * additional generation logic to a translator.
 * 
 * @author       Emil Forslund
 * @param <DOC>  the document type
 * @param <T>    the codegen model type
 * @since        2.3
 */
@Api(version = "2.3")
@FunctionalInterface
public interface TranslatorDecorator<DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>> {
    
    /**
     * Decorates the specified {@link Translator}.
     * 
     * @param translator  the translator to decorate
     */
    void apply(JavaClassTranslator<DOC, T> translator);
}