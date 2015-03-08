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
package com.speedment.orm.code.model.java;

import com.speedment.codegen.Formatting;
import com.speedment.codegen.base.CodeGenerator;
import com.speedment.codegen.java.JavaGenerator;
import com.speedment.codegen.java.JavaInstaller;
import com.speedment.codegen.lang.models.File;
import com.speedment.orm.code.model.Translator;
import com.speedment.orm.code.model.java.entity.EntityBeanImplTranslator;
import com.speedment.orm.code.model.java.entity.EntityBuilderImplTranslator;
import com.speedment.orm.code.model.java.entity.EntityImplTranslator;
import com.speedment.orm.code.model.java.entity.EntityTranslator;
import com.speedment.orm.config.model.Project;
import com.speedment.orm.config.model.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 *
 * @author pemi
 */
public class MainGenerator implements Consumer<Project> {
    
    @Override
    public void accept(Project project) {
        final List<Translator<?, File>> translators = new ArrayList<>();
       
        project.traversalOf(Table.class).forEach(table -> {
            translators.add(new EntityTranslator(table));
            translators.add(new EntityImplTranslator(table));
            translators.add(new EntityBeanImplTranslator(table));
            translators.add(new EntityBuilderImplTranslator(table));
        });
        
        final CodeGenerator cg = new JavaGenerator(
                new JavaInstaller()
        );
        
        Formatting.tab("    ");
        translators.forEach(t -> {
            File file = t.get();
            final Optional<String> code = cg.on(file);
            System.out.println("*** BEGIN File:" + file.getName());
            System.out.println(code.get());
            System.out.println("*** END   File:" + file.getName());
        });
        
    }
    
}
