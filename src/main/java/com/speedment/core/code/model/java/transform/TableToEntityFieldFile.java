/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.core.code.model.java.transform;

import com.speedment.codegen.base.Generator;
import com.speedment.codegen.base.Meta;
import com.speedment.codegen.base.Transform;
import com.speedment.codegen.lang.models.File;
import com.speedment.core.code.model.java.ImportDelegator;
import com.speedment.core.config.model.Table;
import java.util.Optional;

/**
 *
 * @author pemi
 */
public class TableToEntityFieldFile implements Transform<Table, File> {

    @Override
    public Optional<File> transform(Generator gen, Table model) {
        final ImportDelegator delegator = new ImportDelegator(model);
        return gen.metaOn(delegator, File.class).findAny().map(Meta::getResult);
    }
    
}