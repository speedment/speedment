/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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

import com.speedment.runtime.config.Table;
import com.speedment.common.codegen.model.Interface;
import com.speedment.common.injector.Injector;
import com.speedment.generator.component.CodeGenerationComponent;
import com.speedment.generator.internal.TranslatorManagerImpl;
import com.speedment.generator.util.JavaLanguageNamer;
import java.nio.file.Path;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author pemi
 */
public class TranslatorManagerTest extends SimpleModel {

    @Test
    public void testAccept() {
        
        final TranslatorManager instance = speedment.getOrThrow(Injector.class).inject(
            new TranslatorManagerImpl() {
                @Override
                public void writeToFile(Path path, String content, boolean overwriteExisting) {

                }
            }
        );
        
        instance.accept(project);
    }

    @Test
    public void testPreview() {

        final Translator<Table, Interface> translator = speedment.getOrThrow(CodeGenerationComponent.class)
            .findTranslator(table, StandardTranslatorKey.GENERATED_ENTITY);

        final String code = translator.toCode();
        //System.out.println(code);

        final JavaLanguageNamer javaLanguageNamer = speedment.getOrThrow(JavaLanguageNamer.class);

        assertTrue(code.contains(javaLanguageNamer.javaVariableName(table.getName())));
        assertTrue(code.contains(javaLanguageNamer.javaTypeName(table.getName())));
        assertTrue(code.contains(javaLanguageNamer.javaVariableName(column.getName())));
        assertTrue(code.contains(javaLanguageNamer.javaTypeName(column.getName())));
    }
}
