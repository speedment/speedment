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

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.InjectorBuilder;
import com.speedment.generator.translator.component.CodeGenerationComponent;
import com.speedment.generator.translator.provider.StandardCodeGenerationComponent;
import com.speedment.generator.translator.provider.StandardTypeMapperComponent;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.typemapper.TypeMapperComponent;
import com.speedment.tool.propertyeditor.provider.DelegatePropertyEditorComponent;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class EnumGeneratorComponentTest {

    @Test
    void include() {
        final Set<Class<?>> classes = EnumGeneratorComponent.include().injectables().collect(toSet());
        assertTrue(classes.contains(StandardTypeMapperComponent.class));
        assertTrue(classes.contains(StandardCodeGenerationComponent.class));
        assertTrue(classes.contains(DelegatePropertyEditorComponent.class));
    }

    @Test
    void installDecorators() throws InstantiationException {
        final InjectorBuilder builder = Injector.builder();
        EnumGeneratorComponent.include().injectables().forEach(builder::withComponent);
        builder.withComponent(EnumGeneratorComponent.class);
        final Injector injector = builder.build();

        final TypeMapperComponent typeMapperComponent = injector.getOrThrow(TypeMapperComponent.class);

        final Set<Class<?>> typeMapperComponentClasses = typeMapperComponent.stream().map(Object::getClass).collect(toSet());
        assertTrue(typeMapperComponentClasses.contains(StringToEnumTypeMapper.class));
        assertTrue(typeMapperComponentClasses.contains(IntegerToEnumTypeMapper.class));
    }
}