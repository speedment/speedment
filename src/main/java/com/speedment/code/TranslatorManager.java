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
package com.speedment.code;

import com.speedment.codegen.Meta;
import com.speedment.codegen.model.File;
import com.speedment.config.db.Project;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author pemi
 */
public interface TranslatorManager {

    void accept(Project project);

    int getFilesCreated();
    
    default void writeToFile(Project project, Meta<File, String> meta, boolean overwriteExisting) {
        writeToFile(project, meta.getModel().getName(), meta.getResult(), overwriteExisting);
    }
    
    default void writeToFile(Project project, String filename, String content, boolean overwriteExisting) {
        final String fname = project.getPackageLocation() + "/" + filename;
        writeToFile(Paths.get(fname), content, overwriteExisting);
    }
    
    void writeToFile(Path location, String content, boolean overwriteExisting);
}
