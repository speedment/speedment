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
package com.speedment.generator.translator.component.function;

import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.ClassOrInterface;
import com.speedment.common.codegen.model.Enum;
import com.speedment.common.codegen.model.Interface;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.config.trait.HasName;

/**
 * Specialized function that takes a Speedment configuration document (like
 * {@link Project}, {@link Table}, etc) and returns a CodeGen model (like
 * {@link Class}, {@link Interface} or {@link Enum}).
 *
 * @author Emil Forslund
 * @since  3.1.4
 */
@FunctionalInterface
public interface GeneratorFunction<DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>> {

    /**
     * Generates a CodeGen model from the specified document.
     *
     * @param document  the document to generate from
     * @return          the created codegen model
     */
    T generate(DOC document);

    /**
     * Returns {@code true} if classes created by this generator should be
     * placed in the {@code .generated}-folder that is regenerated each time
     * Speedment is invoked.
     * <p>
     * The default value of this method is {@code false}.
     *
     * @return  {@code true} if results should be put in the generated package
     */
    default boolean inGeneratedPackage() {
        return false;
    }
}
