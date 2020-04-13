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
package com.speedment.generator.standard.internal;

import static com.speedment.common.injector.State.INITIALIZED;
import static com.speedment.generator.standard.StandardTranslatorKey.APPLICATION;
import static com.speedment.generator.standard.StandardTranslatorKey.APPLICATION_BUILDER;
import static com.speedment.generator.standard.StandardTranslatorKey.APPLICATION_IMPL;
import static com.speedment.generator.standard.StandardTranslatorKey.ENTITY;
import static com.speedment.generator.standard.StandardTranslatorKey.ENTITY_IMPL;
import static com.speedment.generator.standard.StandardTranslatorKey.ENTRY_POINT;
import static com.speedment.generator.standard.StandardTranslatorKey.GENERATED_APPLICATION;
import static com.speedment.generator.standard.StandardTranslatorKey.GENERATED_APPLICATION_BUILDER;
import static com.speedment.generator.standard.StandardTranslatorKey.GENERATED_APPLICATION_IMPL;
import static com.speedment.generator.standard.StandardTranslatorKey.GENERATED_ENTITY;
import static com.speedment.generator.standard.StandardTranslatorKey.GENERATED_ENTITY_IMPL;
import static com.speedment.generator.standard.StandardTranslatorKey.GENERATED_MANAGER;
import static com.speedment.generator.standard.StandardTranslatorKey.GENERATED_MANAGER_IMPL;
import static com.speedment.generator.standard.StandardTranslatorKey.GENERATED_METADATA;
import static com.speedment.generator.standard.StandardTranslatorKey.GENERATED_SQL_ADAPTER;
import static com.speedment.generator.standard.StandardTranslatorKey.INJECTOR_PROXY;
import static com.speedment.generator.standard.StandardTranslatorKey.MANAGER;
import static com.speedment.generator.standard.StandardTranslatorKey.MANAGER_IMPL;
import static com.speedment.generator.standard.StandardTranslatorKey.SQL_ADAPTER;

import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.generator.standard.entity.EntityImplTranslator;
import com.speedment.generator.standard.entity.EntityTranslator;
import com.speedment.generator.standard.entity.GeneratedEntityImplTranslator;
import com.speedment.generator.standard.entity.GeneratedEntityTranslator;
import com.speedment.generator.standard.lifecycle.ApplicationBuilderTranslator;
import com.speedment.generator.standard.lifecycle.ApplicationImplTranslator;
import com.speedment.generator.standard.lifecycle.ApplicationTranslator;
import com.speedment.generator.standard.lifecycle.EntryPointTranslator;
import com.speedment.generator.standard.lifecycle.GeneratedApplicationBuilderTranslator;
import com.speedment.generator.standard.lifecycle.GeneratedApplicationImplTranslator;
import com.speedment.generator.standard.lifecycle.GeneratedApplicationTranslator;
import com.speedment.generator.standard.lifecycle.GeneratedMetadataTranslator;
import com.speedment.generator.standard.lifecycle.InjectorProxyTranslator;
import com.speedment.generator.standard.manager.GeneratedManagerImplTranslator;
import com.speedment.generator.standard.manager.GeneratedManagerTranslator;
import com.speedment.generator.standard.manager.GeneratedSqlAdapterTranslator;
import com.speedment.generator.standard.manager.ManagerImplTranslator;
import com.speedment.generator.standard.manager.ManagerTranslator;
import com.speedment.generator.standard.manager.SqlAdapterTranslator;
import com.speedment.generator.translator.component.CodeGenerationComponent;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Table;

/**
 *
 * @author Emil Forslund
 * @since 3.0.1
 */
public final class StandardTranslatorComponent {

    @ExecuteBefore(INITIALIZED)
    public void installTranslators(@WithState(INITIALIZED) CodeGenerationComponent codeGen) {
        codeGen.put(Table.class, ENTITY, EntityTranslator::new);
        codeGen.put(Table.class, ENTITY_IMPL, EntityImplTranslator::new);
        codeGen.put(Table.class, MANAGER, ManagerTranslator::new);
        codeGen.put(Table.class, MANAGER_IMPL, ManagerImplTranslator::new);
        codeGen.put(Table.class, SQL_ADAPTER, SqlAdapterTranslator::new);
        codeGen.put(Table.class, GENERATED_ENTITY, GeneratedEntityTranslator::new);
        codeGen.put(Table.class, GENERATED_ENTITY_IMPL, GeneratedEntityImplTranslator::new);
        codeGen.put(Table.class, GENERATED_MANAGER, GeneratedManagerTranslator::new);
        codeGen.put(Table.class, GENERATED_MANAGER_IMPL, GeneratedManagerImplTranslator::new);
        codeGen.put(Table.class, GENERATED_SQL_ADAPTER, GeneratedSqlAdapterTranslator::new);
        codeGen.put(Project.class, APPLICATION, ApplicationTranslator::new);
        codeGen.put(Project.class, APPLICATION_IMPL, ApplicationImplTranslator::new);
        codeGen.put(Project.class, APPLICATION_BUILDER, ApplicationBuilderTranslator::new);
        codeGen.put(Project.class, GENERATED_APPLICATION, GeneratedApplicationTranslator::new);
        codeGen.put(Project.class, GENERATED_APPLICATION_IMPL, GeneratedApplicationImplTranslator::new);
        codeGen.put(Project.class, GENERATED_APPLICATION_BUILDER, GeneratedApplicationBuilderTranslator::new);
        codeGen.put(Project.class, GENERATED_METADATA, GeneratedMetadataTranslator::new);
        codeGen.put(Project.class, INJECTOR_PROXY, InjectorProxyTranslator::new);
        codeGen.put(Project.class, ENTRY_POINT, EntryPointTranslator::new);
    }
}
