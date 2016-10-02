/*
 * Copyright (c) Emil Forslund, 2016.
 * All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Emil Forslund and his suppliers, if any. 
 * The intellectual and technical concepts contained herein 
 * are proprietary to Emil Forslund and his suppliers and may 
 * be covered by U.S. and Foreign Patents, patents in process, 
 * and are protected by trade secret or copyright law. 
 * Dissemination of this information or reproduction of this 
 * material is strictly forbidden unless prior written 
 * permission is obtained from Emil Forslund himself.
 */
package com.speedment.generator.standard.internal;

import static com.speedment.common.injector.State.INITIALIZED;
import static com.speedment.common.injector.State.RESOLVED;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.WithState;
import static com.speedment.generator.standard.StandardTranslatorKey.*;
import com.speedment.generator.standard.entity.EntityImplTranslator;
import com.speedment.generator.standard.entity.EntityTranslator;
import com.speedment.generator.standard.entity.GeneratedEntityImplTranslator;
import com.speedment.generator.standard.entity.GeneratedEntityTranslator;
import com.speedment.generator.standard.lifecycle.ApplicationBuilderTranslator;
import com.speedment.generator.standard.lifecycle.ApplicationImplTranslator;
import com.speedment.generator.standard.lifecycle.ApplicationTranslator;
import com.speedment.generator.standard.lifecycle.GeneratedApplicationBuilderTranslator;
import com.speedment.generator.standard.lifecycle.GeneratedApplicationImplTranslator;
import com.speedment.generator.standard.lifecycle.GeneratedApplicationTranslator;
import com.speedment.generator.standard.lifecycle.GeneratedMetadataTranslator;
import com.speedment.generator.standard.manager.GeneratedManagerImplTranslator;
import com.speedment.generator.standard.manager.GeneratedManagerTranslator;
import com.speedment.generator.standard.manager.ManagerImplTranslator;
import com.speedment.generator.standard.manager.ManagerTranslator;
import com.speedment.generator.translator.component.CodeGenerationComponent;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Table;

/**
 *
 * @author  Emil Forslund
 * @since   3.0.1
 */
public final class StandardTranslatorComponent {

    @ExecuteBefore(RESOLVED)
    void installTranslators(@WithState(INITIALIZED) CodeGenerationComponent codeGen) {
        codeGen.put(Table.class, ENTITY, EntityTranslator::new);
        codeGen.put(Table.class, ENTITY_IMPL, EntityImplTranslator::new);
        codeGen.put(Table.class, MANAGER, ManagerTranslator::new);
        codeGen.put(Table.class, MANAGER_IMPL, ManagerImplTranslator::new);
        codeGen.put(Table.class, GENERATED_ENTITY, GeneratedEntityTranslator::new);
        codeGen.put(Table.class, GENERATED_ENTITY_IMPL, GeneratedEntityImplTranslator::new);
        codeGen.put(Table.class, GENERATED_MANAGER, GeneratedManagerTranslator::new);
        codeGen.put(Table.class, GENERATED_MANAGER_IMPL, GeneratedManagerImplTranslator::new);
        codeGen.put(Project.class, APPLICATION, ApplicationTranslator::new);
        codeGen.put(Project.class, APPLICATION_IMPL, ApplicationImplTranslator::new);
        codeGen.put(Project.class, APPLICATION_BUILDER, ApplicationBuilderTranslator::new);
        codeGen.put(Project.class, GENERATED_APPLICATION, GeneratedApplicationTranslator::new);
        codeGen.put(Project.class, GENERATED_APPLICATION_IMPL, GeneratedApplicationImplTranslator::new);
        codeGen.put(Project.class, GENERATED_APPLICATION_BUILDER, GeneratedApplicationBuilderTranslator::new);
        codeGen.put(Project.class, GENERATED_METADATA, GeneratedMetadataTranslator::new);
    }
}