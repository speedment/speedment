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
package com.speedment.generator.core.provider;

import com.speedment.common.codegen.Meta;
import com.speedment.common.codegen.model.File;
import com.speedment.common.injector.annotation.Config;
import com.speedment.generator.core.component.EventComponent;
import com.speedment.generator.core.component.PathComponent;
import com.speedment.generator.core.internal.translator.TranslatorManagerImpl;
import com.speedment.generator.core.translator.AbstractTranslatorManager;
import com.speedment.generator.translator.TranslatorManager;
import com.speedment.generator.translator.component.CodeGenerationComponent;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.core.component.InfoComponent;
import com.speedment.runtime.core.component.ProjectComponent;

import java.nio.file.Path;

/**
 * Default implementation of the {@link TranslatorManager}-interface. This is
 * the implementation used by the injector system by default. The internal logic
 * is stored in the class {@link TranslatorManagerImpl}. To override it,
 * use the public class {@link AbstractTranslatorManager}.
 * 
 * @author Per Minborg
 * @author Emil Forslund
 * @since  3.0.2
 */
public final class StandardTranslatorManager implements TranslatorManager {
    
    private final TranslatorManagerImpl helper;
    
    public StandardTranslatorManager(
        final InfoComponent info,
        final PathComponent paths,
        final EventComponent events,
        final ProjectComponent projects,
        final CodeGenerationComponent codeGenerationComponent,
        final @Config(name="skipClear", value="false") boolean skipClear
    ) {
        helper = new TranslatorManagerImpl(info, paths, events, projects, codeGenerationComponent, skipClear);
    }
    
    @Override
    public void accept(Project project) {
        helper.accept(this, project);
    }

    @Override
    public void clearExistingFiles() {
        helper.clearExistingFiles();
    }

    @Override
    public void writeToFile(Project project, Meta<File, String> meta, boolean overwriteExisting) {
        helper.writeToFile(this, project, meta, overwriteExisting);
    }

    @Override
    public void writeToFile(Project project, String filename, String content, boolean overwriteExisting) {
        helper.writeToFile(this, filename, content, overwriteExisting);
    }

    @Override
    public void writeToFile(Path location, String content, boolean overwriteExisting) {
        helper.writeToFile(location, content, overwriteExisting);
    }

    @Override
    public int getFilesCreated() {
        return helper.getFilesCreated();
    }
}
