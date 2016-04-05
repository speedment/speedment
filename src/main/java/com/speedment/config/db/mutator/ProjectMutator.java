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
package com.speedment.config.db.mutator;

import com.speedment.config.db.*;
import static com.speedment.config.db.Project.COMPANY_NAME;
import static com.speedment.config.db.Project.CONFIG_PATH;
import static com.speedment.config.db.Project.DBMSES;
import static com.speedment.config.db.Project.PACKAGE_LOCATION;
import static com.speedment.config.db.Project.PACKAGE_NAME;
import com.speedment.config.db.mutator.trait.HasEnabledMutator;
import com.speedment.config.db.mutator.trait.HasNameMutator;
import com.speedment.internal.core.config.db.DbmsImpl;
import static com.speedment.internal.util.document.DocumentUtil.newDocument;

/**
 *
 * @author       Per Minborg
 * @param <DOC>  document type
 */
public class ProjectMutator<DOC extends Project> extends DocumentMutatorImpl<DOC> implements 
        HasEnabledMutator<DOC>, 
        HasNameMutator<DOC> {

    public ProjectMutator(DOC project) {
        super(project);
    }
    
    public void setCompanyName(String companyName) {
        put(COMPANY_NAME, companyName);
    }
    
    public void setPackageName(String packageName) {
        put(PACKAGE_NAME, packageName);
    }

    public void setPackageLocation(String packageLocation) {
        put(PACKAGE_LOCATION, packageLocation);
    }

    public void setConfigPath(String configPath) {
        put(CONFIG_PATH, configPath);
    }
    
    public Dbms addNewDbms() {
        return new DbmsImpl(document(), newDocument(document(), DBMSES));
    }
}