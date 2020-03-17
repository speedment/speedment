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
package com.speedment.generator.core.event;

import com.speedment.common.codegen.Generator;
import com.speedment.generator.core.event.trait.ProjectEvent;
import com.speedment.generator.translator.TranslatorManager;
import com.speedment.runtime.config.Project;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author  Emil Forslund
 * @since   2.3.0
 */

public final class AfterGenerate implements ProjectEvent, GeneratorEvent {
    
    private final Project project;
    private final Generator generator;
    private final TranslatorManager translatorManager;

    public AfterGenerate(Project project, Generator generator, TranslatorManager translatorManager) {
        this.project           = requireNonNull(project);
        this.generator         = requireNonNull(generator);
        this.translatorManager = requireNonNull(translatorManager);
    }
    
    @Override
    public Project project() {
        return project;
    }
    
    @Override
    public Generator generator() {
        return generator;
    }
    
    @Override
    public TranslatorManager translatorManager() {
        return translatorManager;
    }
}