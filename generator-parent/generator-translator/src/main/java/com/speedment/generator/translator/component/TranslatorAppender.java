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

import com.speedment.generator.translator.component.function.GenerateClass;
import com.speedment.generator.translator.component.function.GenerateEnum;
import com.speedment.generator.translator.component.function.GenerateInterface;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.config.trait.HasName;

/**
 * Appender to the {@link CodeGenerationComponent} used to configure new
 * translators.
 *
 * @author Emil Forslund
 * @since  3.1.4
 */
public interface TranslatorAppender<DOC extends HasName & HasMainInterface> {

    /**
     * Creates a new dynamic code translator and adds it to this component. The
     * created translator will not listen to any decorators.
     *
     * @param creator  the CodeGen model creator
     * @return         this component
     * @since  3.1.4
     */
    CodeGenerationComponent newClass(GenerateClass<DOC> creator);

    /**
     * Creates a new dynamic code translator and adds it to this component. The
     * created translator will not listen to any decorators.
     *
     * @param creator  the CodeGen model creator
     * @return         this component
     * @since  3.1.4
     */
    CodeGenerationComponent newEnum(GenerateEnum<DOC> creator);

    /**
     * Creates a new dynamic code translator and adds it to this component. The
     * created translator will not listen to any decorators.
     *
     * @param creator  the CodeGen model creator
     * @return         this component
     * @since  3.1.4
     */
    CodeGenerationComponent newInterface(GenerateInterface<DOC> creator);

}
