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
package com.speedment.plugins.enums;

import com.speedment.common.injector.InjectBundle;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.generator.standard.StandardTranslatorKey;
import com.speedment.generator.translator.component.CodeGenerationComponent;
import com.speedment.generator.translator.component.TypeMapperComponent;
import com.speedment.generator.translator.internal.component.CodeGenerationComponentImpl;
import com.speedment.generator.translator.internal.component.TypeMapperComponentImpl;
import com.speedment.plugins.enums.internal.GeneratedEntityDecorator;
import com.speedment.plugins.enums.internal.ui.CommaSeparatedStringEditor;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Table;
import com.speedment.tool.config.trait.HasEnumConstantsProperty;
import com.speedment.tool.propertyeditor.component.PropertyEditorComponent;
import com.speedment.tool.propertyeditor.internal.component.PropertyEditorComponentImpl;

import static com.speedment.common.injector.State.INITIALIZED;
import static com.speedment.common.injector.State.RESOLVED;

/**
 * A plugin for generating internal enums for columns marked as ENUM in the
 * database.
 * <p>
 * To use this plugin, add the following to your configuration tag in the
 * pom-file:
 * {@code <component>com.speedment.plugins.enums.EnumGeneratorComponent</component>}
 *
 * @author Emil Forslund
 * @author Simon Jonasson
 * @since  3.0.0
 */
@InjectKey(EnumGeneratorComponent.class)
public final class EnumGeneratorComponent {

    static InjectBundle include() {
        return InjectBundle.of(
            TypeMapperComponentImpl.class, 
            CodeGenerationComponentImpl.class, 
            PropertyEditorComponentImpl.class
        );
    }

    @ExecuteBefore(RESOLVED)
    void installDecorators(Injector injector,
            @WithState(INITIALIZED) TypeMapperComponent typeMappers,
            @WithState(INITIALIZED) CodeGenerationComponent codeGen,
            @WithState(RESOLVED) PropertyEditorComponent editors){

        typeMappers.install(String.class, StringToEnumTypeMapper::new);

        codeGen.add(
            Table.class,
            StandardTranslatorKey.GENERATED_ENTITY,
            new GeneratedEntityDecorator(injector)
        );

        editors.install(
            HasEnumConstantsProperty.class,
            Column.ENUM_CONSTANTS,
            CommaSeparatedStringEditor::new
        );
        
    }
}