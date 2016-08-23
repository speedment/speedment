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
package com.speedment.plugins.enums;


import com.speedment.plugins.enums.internal.GeneratedEntityDecorator;
import com.speedment.generator.StandardTranslatorKey;
import com.speedment.generator.component.CodeGenerationComponent;
import com.speedment.generator.component.EventComponent;
import com.speedment.generator.component.TypeMapperComponent;
import com.speedment.internal.common.injector.Injector;
import static com.speedment.internal.common.injector.State.INITIALIZED;
import static com.speedment.internal.common.injector.State.RESOLVED;
import com.speedment.internal.common.injector.annotation.ExecuteBefore;
import com.speedment.internal.common.injector.annotation.Inject;
import com.speedment.internal.common.injector.annotation.InjectorKey;
import com.speedment.internal.common.injector.annotation.WithState;
import com.speedment.plugins.enums.internal.ui.CommaSeparatedStringEditor;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.internal.component.AbstractComponent;
import com.speedment.runtime.internal.license.AbstractSoftware;
import static com.speedment.runtime.internal.license.OpenSourceLicense.APACHE_2;
import com.speedment.runtime.license.Software;
import com.speedment.tool.component.PropertyEditorComponent;
import com.speedment.tool.config.ColumnProperty;

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
 * @since 1.0.0
 */
@InjectorKey(EnumGeneratorComponent.class)
public final class EnumGeneratorComponent extends AbstractComponent {

    private @Inject
    EventComponent events;

    @ExecuteBefore(RESOLVED)
    void installDecorators(Injector injector,
        @WithState(INITIALIZED) TypeMapperComponent typeMappers,
        @WithState(INITIALIZED) CodeGenerationComponent codeGen,
        @WithState(RESOLVED) PropertyEditorComponent editors){

        typeMappers.install(String.class, StringToEnumTypeMapper::new);
        codeGen.add(Table.class, StandardTranslatorKey.GENERATED_ENTITY, new GeneratedEntityDecorator(injector));

        editors.install(ColumnProperty.class, Column.ENUM_CONSTANTS, CommaSeparatedStringEditor::new);
        
    }

    @Override
    public Class<EnumGeneratorComponent> getComponentClass() {
        return EnumGeneratorComponent.class;
    }

    @Override
    public Software asSoftware() {
        return AbstractSoftware.with(
            "Enum Generator",
            "1.0.0",
            "Generate enum implementations for columns marked as ENUM in the database.",
            APACHE_2
        );
    }
}
