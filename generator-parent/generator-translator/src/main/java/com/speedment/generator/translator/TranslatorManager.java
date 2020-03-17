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
package com.speedment.generator.translator;

import com.speedment.common.codegen.Meta;
import com.speedment.common.codegen.model.File;
import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.runtime.config.Project;
import java.nio.file.Path;

/**
 *
 * @author Per Minborg
 */
@InjectKey(TranslatorManager.class)
public interface TranslatorManager {
    
    void accept(Project project);

    void clearExistingFiles();
    
    void writeToFile(Project project, Meta<File, String> meta, boolean overwriteExisting);
    
    void writeToFile(Project project, String filename, String content, boolean overwriteExisting);
    
    void writeToFile(Path location, String content, boolean overwriteExisting);
    
    int getFilesCreated();
}