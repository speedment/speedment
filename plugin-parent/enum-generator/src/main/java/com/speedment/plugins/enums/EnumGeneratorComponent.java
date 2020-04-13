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
package com.speedment.plugins.enums;

import static com.speedment.common.injector.State.INITIALIZED;
import static com.speedment.common.injector.State.RESOLVED;

import com.speedment.common.injector.InjectBundle;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.generator.standard.StandardTranslatorKey;
import com.speedment.generator.translator.component.CodeGenerationComponent;
import com.speedment.runtime.typemapper.TypeMapperComponent;
import com.speedment.generator.translator.provider.StandardCodeGenerationComponent;
import com.speedment.generator.translator.provider.StandardTypeMapperComponent;
import com.speedment.plugins.enums.internal.GeneratedEntityDecorator;
import com.speedment.plugins.enums.internal.ui.CommaSeparatedStringEditor;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.trait.HasEnumConstantsUtil;
import com.speedment.tool.config.trait.HasEnumConstantsProperty;
import com.speedment.tool.propertyeditor.component.PropertyEditorComponent;
import com.speedment.tool.propertyeditor.provider.DelegatePropertyEditorComponent;

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
            StandardTypeMapperComponent.class,
            StandardCodeGenerationComponent.class,
            DelegatePropertyEditorComponent.class
        );
    }

    @ExecuteBefore(RESOLVED)
    public void installDecorators(
        final Injector injector,
        @WithState(INITIALIZED) final TypeMapperComponent typeMappers,
        @WithState(INITIALIZED) final CodeGenerationComponent codeGen,
        @WithState(RESOLVED) final PropertyEditorComponent editors
    ) {
        typeMappers.install(String.class, StringToEnumTypeMapper::new);
        typeMappers.install(Integer.class, IntegerToEnumTypeMapper::new);

        codeGen.add(
            Table.class,
            StandardTranslatorKey.GENERATED_ENTITY,
            new GeneratedEntityDecorator(injector)
        );

        editors.install(
            HasEnumConstantsProperty.class,
            HasEnumConstantsUtil.ENUM_CONSTANTS,
            CommaSeparatedStringEditor::new
        );
    }
}
