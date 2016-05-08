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
package com.speedment.generator;

import static com.speedment.generator.SimpleModel.TABLE_NAME;
import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.Table;
import com.speedment.fika.codegen.Generator;
import com.speedment.fika.codegen.controller.AutoImports;
import com.speedment.fika.codegen.internal.java.JavaGenerator;
import com.speedment.fika.codegen.model.File;
import com.speedment.generator.internal.entity.EntityTranslator;
import static com.speedment.runtime.internal.util.document.DocumentDbUtil.traverseOver;
import java.util.Optional;
import org.junit.Test;

/**
 *
 * @author pemi
 */
public class EntityTranslatorTest extends SimpleModel {

    @Test
    public void testApply() {
        final Generator cg = new JavaGenerator();

        final Table t = traverseOver(project, Table.class)
                .peek(this::print)
                .filter(e -> TABLE_NAME.equals(e.getName()))
                .findAny().get();

        final EntityTranslator instance = new EntityTranslator(speedment, cg, t);
        final File file = instance.get();

        file.call(new AutoImports(cg.getDependencyMgr()));

        final Optional<String> code = cg.on(file);

        //System.out.println(code.get());
    }
    
    private void print(Document d) {
        //System.out.println(d);
    }
    
    
}
