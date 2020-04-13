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
package com.speedment.generator.core;

import com.speedment.common.injector.InjectBundle;
import com.speedment.generator.core.provider.StandardTranslatorManager;
import com.speedment.generator.core.provider.StandardEventComponent;
import com.speedment.generator.core.provider.StandardPathComponent;
import com.speedment.generator.standard.StandardTranslatorBundle;
import com.speedment.generator.translator.TranslatorBundle;

import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 * @since  3.0.0
 */
public class GeneratorBundle implements InjectBundle {

    @Override
    public Stream<Class<?>> injectables() {
        return InjectBundle.of(
                StandardEventComponent.class,
                StandardTranslatorManager.class,
                StandardPathComponent.class
            ).withBundle(new TranslatorBundle())
            .withBundle(new StandardTranslatorBundle())
            .injectables();
    }
}