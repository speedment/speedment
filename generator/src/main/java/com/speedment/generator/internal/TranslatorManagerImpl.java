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
package com.speedment.generator.internal;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.Meta;
import com.speedment.common.codegen.internal.java.JavaGenerator;
import com.speedment.common.codegen.internal.util.Formatting;
import com.speedment.common.codegen.model.File;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.generator.Translator;
import com.speedment.generator.TranslatorManager;
import com.speedment.generator.component.CodeGenerationComponent;
import com.speedment.generator.event.AfterGenerate;
import com.speedment.generator.event.BeforeGenerate;
import com.speedment.generator.event.FileGenerated;
import com.speedment.generator.component.EventComponent;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.internal.util.Statistics;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.speedment.common.codegen.internal.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.internal.util.document.DocumentDbUtil.traverseOver;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 * @author Emil Forslund
 */
public class TranslatorManagerImpl implements TranslatorManager {

    private final static Logger LOGGER = LoggerManager.getLogger(TranslatorManagerImpl.class);
    private final static boolean PRINT_CODE = false;
    
    private final AtomicInteger fileCounter = new AtomicInteger(0);

    private @Inject EventComponent eventComponent;
    private @Inject CodeGenerationComponent codeGenerationComponent;

    @Override
    public void accept(Project project) {
        requireNonNull(project);
        Statistics.onGenerate();

        final List<Translator<?, ?>> writeOnceTranslators = new ArrayList<>();
        final List<Translator<?, ?>> writeAlwaysTranslators = new ArrayList<>();
        final Generator gen = new JavaGenerator();

        fileCounter.set(0);
        Formatting.tab("    ");

        eventComponent.notify(new BeforeGenerate(project, gen, this));

        codeGenerationComponent.translators(project)
            .forEachOrdered(t -> {
                if (t.isInGeneratedPackage()) {
                    writeAlwaysTranslators.add(t);
                } else {
                    writeOnceTranslators.add(t);
                }
            });

        traverseOver(project, Table.class)
            .filter(HasEnabled::test)
            .forEach(table -> {
                codeGenerationComponent.translators(table).forEachOrdered(t -> {
                    if (t.isInGeneratedPackage()) {
                        writeAlwaysTranslators.add(t);
                    } else {
                        writeOnceTranslators.add(t);
                    }
                });
            });

        gen.metaOn(writeOnceTranslators.stream()
            .map(Translator::get)
            .collect(Collectors.toList())
        ).forEach(meta -> writeToFile(project, meta, false));

        gen.metaOn(writeAlwaysTranslators.stream()
            .map(Translator::get)
            .collect(Collectors.toList())
        ).forEach(meta -> writeToFile(project, meta, true));

        eventComponent.notify(new AfterGenerate(project, gen, this));
    }

    @Override
    public int getFilesCreated() {
        return fileCounter.get();
    }

    @Override
    public void writeToFile(Project project, Meta<File, String> meta, boolean overwriteExisting) {
        eventComponent.notify(new FileGenerated(project, meta));
        writeToFile(project, meta.getModel().getName(), meta.getResult(), overwriteExisting);
    }

    @Override
    public void writeToFile(Project project, String filename, String content, boolean overwriteExisting) {
        final String fname = project.getPackageLocation() + "/" + filename;
        writeToFile(Paths.get(fname), content, overwriteExisting);
    }

    @Override
    public void writeToFile(Path path, String content, boolean overwriteExisting) {
        requireNonNulls(path, content);

        try {
            Optional.ofNullable(path.getParent())
                .ifPresent(p -> p.toFile().mkdirs());
        } catch (SecurityException se) {
            throw new SpeedmentException("Unable to create directory " + path.toString(), se);
        }

        try {
            if (overwriteExisting || !path.toFile().exists()) {
                Files.write(path, content.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
                );
                fileCounter.incrementAndGet();
            }
        } catch (final IOException ex) {
            LOGGER.error(ex, "Failed to write file " + path);
        }

        if (PRINT_CODE) {
            System.out.println("*** BEGIN File:" + path);
            System.out.println(content);
            System.out.println("*** END   File:" + path);
        }
    }
}