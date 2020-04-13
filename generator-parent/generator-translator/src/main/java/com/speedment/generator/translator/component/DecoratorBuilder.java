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
package com.speedment.generator.translator.component;

import com.speedment.common.codegen.model.ClassOrInterface;
import com.speedment.generator.translator.TranslatorDecorator;

import java.util.function.Consumer;

/**
 * Modifier that decorates a {@link CodeGenerationComponent}.
 *
 * @author Emil Forslund
 * @since  3.1.4
 */
public interface DecoratorBuilder<T extends ClassOrInterface<T>> {

    /**
     * Creates and appends a {@link TranslatorDecorator} to the
     * {@link CodeGenerationComponent} that calls the specified action before
     * the regular generation is complete.
     *
     * @param action  the action to perform
     * @return        the {@code CodeGenerationComponent}
     */
    CodeGenerationComponent preGenerate(Consumer<T> action);

    /**
     * Creates and appends a {@link TranslatorDecorator} to the
     * {@link CodeGenerationComponent} that calls the specified action after the
     * regular generation is complete.
     *
     * @param action  the action to perform
     * @return        the {@code CodeGenerationComponent}
     */
    CodeGenerationComponent postGenerate(Consumer<T> action);

}
