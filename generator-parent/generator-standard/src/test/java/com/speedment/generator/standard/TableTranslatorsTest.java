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

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.model.File;
import com.speedment.common.injector.Injector;
import com.speedment.generator.standard.entity.EntityImplTranslator;
import com.speedment.generator.standard.entity.EntityTranslator;
import com.speedment.generator.standard.entity.GeneratedEntityImplTranslator;
import com.speedment.generator.standard.entity.GeneratedEntityTranslator;
import com.speedment.generator.standard.manager.*;
import com.speedment.generator.translator.Translator;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.util.DocumentDbUtil;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

final class TableTranslatorsTest {

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


    void test(Projects projectHolder) {
        Stream.of(projectHolder)
            .map(Projects::project)
            .flatMap(p -> DocumentDbUtil.traverseOver(p, Table.class))
            .flatMap(this::translators)
            .forEach(this::test);
    }

    void test(Translator<Table, ?> translator) {
        assertNotNull(translator.getDocument());

        assertFalse(translator.column().isPresent());
        assertTrue(translator.project().isPresent());
        assertTrue(translator.dbms().isPresent());
        assertTrue(translator.schema().isPresent());

        final Generator generator = injector.get(Generator.class).orElseThrow(NoSuchElementException::new);
        final File file = translator.get();
        final String code = generator.on(file).orElseThrow(NoSuchElementException::new);

        assertTrue(code.contains("package"));
        assertTrue(code.contains("{"));
        assertTrue(code.contains("}"));
    }

    final Stream<Translator<Table, ?>> translators(Table table) {
        return Stream.<BiFunction<Injector, Table, Translator<Table, ?>>>of(
            // entity
            EntityImplTranslator::new,
            EntityTranslator::new,
            GeneratedEntityImplTranslator::new,
            GeneratedEntityTranslator::new,
            // manager
            GeneratedManagerImplTranslator::new,
            GeneratedManagerTranslator::new,
            GeneratedSqlAdapterTranslator::new,
            ManagerImplTranslator::new,
            ManagerTranslator::new,
            SqlAdapterTranslator::new
        )
            .map(c -> c.apply(injector, table));
    }

}