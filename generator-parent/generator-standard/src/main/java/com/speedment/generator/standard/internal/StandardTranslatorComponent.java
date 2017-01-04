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
package com.speedment.generator.standard.internal;

import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.generator.standard.entity.EntityImplTranslator;
import com.speedment.generator.standard.entity.EntityTranslator;
import com.speedment.generator.standard.entity.GeneratedEntityImplTranslator;
import com.speedment.generator.standard.entity.GeneratedEntityTranslator;
import com.speedment.generator.standard.lifecycle.*;
import com.speedment.generator.standard.manager.*;
import com.speedment.generator.translator.component.CodeGenerationComponent;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Table;

import static com.speedment.common.injector.State.INITIALIZED;
import static com.speedment.common.injector.State.RESOLVED;
import static com.speedment.generator.standard.StandardTranslatorKey.*;

/**
 *
 * @author Emil Forslund
 * @since 3.0.1
 */
public final class StandardTranslatorComponent {

    @ExecuteBefore(RESOLVED)
    void installTranslators(@WithState(INITIALIZED) CodeGenerationComponent codeGen) {
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
    }
}
