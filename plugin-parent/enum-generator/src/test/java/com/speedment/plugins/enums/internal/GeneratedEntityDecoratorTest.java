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
package com.speedment.plugins.enums.internal;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Interface;
import com.speedment.common.codegen.provider.StandardJavaGenerator;
import com.speedment.common.injector.Injector;
import com.speedment.generator.standard.entity.GeneratedEntityTranslator;
import com.speedment.generator.translator.JavaClassTranslator;
import com.speedment.generator.translator.provider.StandardJavaLanguageNamer;
import com.speedment.plugins.enums.EnumGeneratorBundle;
import com.speedment.plugins.enums.TestUtil;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.util.DocumentDbUtil;
import com.speedment.runtime.core.provider.DelegateInfoComponent;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertTrue;

final class GeneratedEntityDecoratorTest {

    @Test
    void apply() throws InstantiationException {
        final Injector injector = Injector.builder()
                .withComponent(StandardJavaLanguageNamer.class)
                .withComponent(StandardJavaGenerator.class)
                .withComponent(DelegateInfoComponent.class)
                .withBundle(EnumGeneratorBundle.class)
                .build();

        final Project project = TestUtil.project();
        final Column column = DocumentDbUtil.referencedColumn(project,"speedment_test","speedment_test", "user", "name");
        final Table table = column.getParentOrThrow();
        final GeneratedEntityDecorator decorator = new GeneratedEntityDecorator(injector);

        final JavaClassTranslator<Table, Interface> translator = new GeneratedEntityTranslator(injector, table);

        decorator.apply(translator);

        final File file = translator.get();

        final String code = injector.getOrThrow(Generator.class).on(file).orElseThrow(NoSuchElementException::new);
        assertTrue(code.contains("enum Name"));
        assertTrue(code.contains("fromDatabase(String"));
    }
}