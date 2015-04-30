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
package com.speedment.orm.code.model.java.transform;

import static com.speedment.codegen.Formatting.DOT;
import static com.speedment.codegen.Formatting.EMPTY;
import com.speedment.codegen.base.Generator;
import com.speedment.codegen.base.Transform;
import com.speedment.codegen.lang.models.Type;
import com.speedment.orm.config.model.Project;
import com.speedment.orm.config.model.Table;
import static com.speedment.util.java.JavaLanguage.javaTypeName;
import java.util.Optional;

/**
 *
 * @author pemi
 */
public abstract class TableToType implements Transform<Table, Type> {
    
    protected String namePrefix() {return EMPTY;}
    protected String nameSuffix() {return EMPTY;}

    @Override
    public Optional<Type> transform(Generator gen, Table table){
        final Project project = table.ancestor(Project.class).get();
        
        return Optional.of(
            Type.of(
                project.getPacketName() + DOT + 
                table.getRelativeName(Project.class) + DOT +
                namePrefix() + 
                javaTypeName(table.getName()) + 
                nameSuffix()
            )
        );
    }
}