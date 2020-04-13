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

import com.speedment.common.codegen.provider.StandardJavaGenerator;
import com.speedment.common.injector.InjectBundle;
import com.speedment.generator.translator.provider.StandardCodeGenerationComponent;
import com.speedment.generator.translator.provider.StandardJavaLanguageNamer;
import com.speedment.generator.translator.provider.StandardTypeMapperComponent;

import java.util.stream.Stream;

/**
 *
 * @author  Emil Forslund
 * @since   3.0.1
 */
public final class TranslatorBundle implements InjectBundle {

    @Override
    public Stream<Class<?>> injectables() {
        return Stream.of(
            StandardCodeGenerationComponent.class,
            StandardTypeMapperComponent.class,
            StandardJavaLanguageNamer.class,
            StandardJavaGenerator.class
        );
    }
}
