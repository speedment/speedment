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
package com.speedment.generator;

import com.speedment.runtime.Speedment;
import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.model.ClassOrInterface;

/**
 * A functional interface that describes a constructor for a class that 
 * implements the {@link Translator} interface. This is a more modern approach
 * to the factory pattern.
 * 
 * @author       Emil Forslund
 * @param <DOC>  document type
 * @param <T>    codegen model type
 * @since        2.3
 */
@Api(version = "2.3")
@FunctionalInterface
public interface TranslatorConstructor<DOC extends HasMainInterface, T extends ClassOrInterface<T>> {
    
    /**
     * Constructs the {@link Translator} instance.
     * <p>
     * This method is ment to be implemented using a functional reference to the
     * implementing classes constructor method.
     * 
     * @param speedment  the speedment instance
     * @param gen        the code generator to use
     * @param document   the document to generate from
     * @return           the created translator
     */
    Translator<DOC, T> apply(Speedment speedment, Generator gen, DOC document);
}