/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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

import com.speedment.common.codegen.model.Interface;
import com.speedment.generator.standard.StandardTranslatorKey;
import com.speedment.generator.translator.Translator;
import com.speedment.generator.translator.TranslatorManager;
import com.speedment.generator.translator.component.CodeGenerationComponent;
import com.speedment.generator.translator.namer.JavaLanguageNamer;
import com.speedment.runtime.config.Table;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 *
 * @author pemi
 */
public class TranslatorManagerTest extends SimpleModel {

    @Test
    public void testAccept() {
        final TranslatorManager instance = speedment.getOrThrow(TranslatorManager.class);
        
        try {
            instance.accept(project);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Test
    public void testPreview() {

        final Translator<Table, Interface> translator = speedment.getOrThrow(CodeGenerationComponent.class)
            .findTranslator(table, StandardTranslatorKey.GENERATED_ENTITY);

        final String code = translator.toCode();
        //System.out.println(code);

        final JavaLanguageNamer javaLanguageNamer = speedment.getOrThrow(JavaLanguageNamer.class);

        assertTrue(code.contains(javaLanguageNamer.javaVariableName(table.getId())));
        assertTrue(code.contains(javaLanguageNamer.javaTypeName(table.getId())));
        assertTrue(code.contains(javaLanguageNamer.javaVariableName(column.getId())));
        assertTrue(code.contains(javaLanguageNamer.javaTypeName(column.getId())));
    }
}
