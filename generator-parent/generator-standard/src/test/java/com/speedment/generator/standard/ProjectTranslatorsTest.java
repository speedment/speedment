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
package com.speedment.generator.standard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.model.File;
import com.speedment.common.injector.Injector;
import com.speedment.generator.standard.lifecycle.ApplicationBuilderTranslator;
import com.speedment.generator.standard.lifecycle.ApplicationImplTranslator;
import com.speedment.generator.standard.lifecycle.ApplicationTranslator;
import com.speedment.generator.standard.lifecycle.EntryPointTranslator;
import com.speedment.generator.standard.lifecycle.GeneratedApplicationBuilderTranslator;
import com.speedment.generator.standard.lifecycle.GeneratedApplicationImplTranslator;
import com.speedment.generator.standard.lifecycle.GeneratedApplicationTranslator;
import com.speedment.generator.standard.lifecycle.GeneratedMetadataTranslator;
import com.speedment.generator.standard.lifecycle.InjectorProxyTranslator;
import com.speedment.generator.translator.Translator;
import com.speedment.runtime.config.Project;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.stream.Stream;

final class ProjectTranslatorsTest {

    private final Injector injector = TestUtil.injector();

    @Test
    void speedmentJson() {
        test(Projects.SPEEDMENT_JSON);
    }

    @Test
    void spring() {
        test(Projects.SPRING_PLUGIN);
    }

    @Test
    void dataStore() {
        test(Projects.DATA_STORE);
    }

    @Test
    void foreignKey() {
        test(Projects.FOREIGN_KEY);
    }

    @Test
    void typeMapper() {
        test(Projects.TYPE_MAPPER);
    }

    @Test
    void serveralDbmses() {
        test(Projects.SEVERAL_DBMSES);
    }

    void test(Projects projectHolder) {
        Stream.of(projectHolder)
            .map(Projects::project)
            .flatMap(this::translators)
            .forEach(this::test);
    }

    void test(Translator<Project, ?> translator) {
        assertNotNull(translator.getDocument());

        assertFalse(translator.column().isPresent());
        assertTrue(translator.project().isPresent());
        assertFalse(translator.dbms().isPresent());
        assertFalse(translator.schema().isPresent());

        final Generator generator = injector.get(Generator.class).orElseThrow(NoSuchElementException::new);
        final File file = translator.get();
        final String code = generator.on(file).orElseThrow(NoSuchElementException::new);

        assertTrue(code.contains("package"));
        assertTrue(code.contains("{"));
        assertTrue(code.contains("}"));
    }


    final  Stream<Translator<Project, ?>> translators(Project project) {
        return Stream.<BiFunction<Injector, Project, Translator<Project, ?>>>of(
            // entity
            ApplicationBuilderTranslator::new,
            ApplicationImplTranslator::new,
            ApplicationTranslator::new,
            GeneratedApplicationBuilderTranslator::new,
            GeneratedApplicationImplTranslator::new,
            GeneratedApplicationTranslator::new,
            GeneratedMetadataTranslator::new,
            InjectorProxyTranslator::new,
            EntryPointTranslator::new
        )
            .map(c -> c.apply(injector, project));
    }


}
