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
import com.speedment.codegen.lang.models.File;
import com.speedment.orm.code.model.Translator;
import com.speedment.orm.code.model.java.entity.EntityBuilderTranslator;
import com.speedment.orm.code.model.java.entity.EntityImplTranslator;
import com.speedment.orm.code.model.java.entity.EntityManagerImplTranslator;
import com.speedment.orm.code.model.java.entity.EntityManagerTranslator;
import com.speedment.orm.code.model.java.entity.EntityTranslator;
import com.speedment.orm.config.model.Project;
import com.speedment.orm.config.model.Table;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author pemi
 */
public class MainGenerator implements Consumer<Project> {

    private final static Logger LOGGER = LogManager.getLogger(MainGenerator.class);

    @Override
    public void accept(Project project) {
        final List<Translator<?, File>> translators = new ArrayList<>();

        final CodeGenerator cg = new JavaGenerator();

        project.traversalOf(Table.class).forEach(table -> {
            translators.add(new EntityTranslator(cg, table));
            translators.add(new EntityBuilderTranslator(cg, table));
            translators.add(new EntityImplTranslator(cg, table));
            translators.add(new EntityManagerTranslator(cg, table));
            translators.add(new EntityManagerImplTranslator(cg, table));
        });

        Formatting.tab("    ");
        cg.codeOn(translators.stream()
            .map(t -> t.get())
            .collect(Collectors.toList()))
            .forEach(c -> {

                final String fname = project.getPacketLocation()
                + "/"
                + c.getModel().getName();
                final String content = c.getText();
                final Path path = Paths.get(fname);
                path.getParent().toFile().mkdirs();

                try {
                    Files.write(path, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                } catch (IOException ex) {
                    LOGGER.error("Failed to create file " + fname, ex);
                }

                LOGGER.info("done");

                System.out.println("*** BEGIN File:" + fname);
                System.out.println(content);
                System.out.println("*** END   File:" + fname);
            });
//        
//        
//        translators.forEach(t -> {
//            File file = t.get();
//            final Optional<String> code = cg.on(file);
//            System.out.println("*** BEGIN File:" + file.getName());
//            System.out.println(code.get());
//            System.out.println("*** END   File:" + file.getName());
//        });

    }

}
