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
package com.speedment.generator.standard.entity;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.model.File;
import com.speedment.common.injector.Injector;
import com.speedment.generator.standard.Projects;
import com.speedment.generator.standard.StandardTranslatorBundle;
import com.speedment.generator.translator.TranslatorBundle;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.util.DocumentDbUtil;
import com.speedment.runtime.core.provider.DelegateInfoComponent;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

final class EntityTranslatorTest {

    @Test
    void makeCodeGenModel() throws InstantiationException {
        final Injector injector = Injector.builder()
            .withComponent(DelegateInfoComponent.class)
            .withBundle(TranslatorBundle.class)
            .withBundle(StandardTranslatorBundle.class)
            .build();

        final Table table = DocumentDbUtil.traverseOver(Projects.SPEEDMENT_JSON.project(), Table.class).findFirst().orElseThrow(NoSuchElementException::new);
        final EntityTranslator translator = new EntityTranslator(injector,table);
        final File file = translator.get();
        Generator generator = injector.get(Generator.class).orElseThrow(NoSuchElementException::new);
        final String code = generator.on(file).orElseThrow(NoSuchElementException::new);
        assertNotNull(code);
    }

}