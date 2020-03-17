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
package com.speedment.generator.standard.lifecycle;

import com.speedment.common.codegen.constant.DefaultType;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.*;
import com.speedment.common.codegen.util.Formatting;
import com.speedment.common.injector.Injector;
import com.speedment.common.json.Json;
import com.speedment.generator.translator.AbstractJavaClassTranslator;
import com.speedment.runtime.application.AbstractApplicationMetadata;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.mutator.ProjectMutator;
import com.speedment.runtime.config.util.DocumentTranscoder;
import com.speedment.runtime.core.ApplicationMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.speedment.common.codegen.constant.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.common.codegen.constant.DefaultJavadocTag.AUTHOR;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Per Minborg
 */
public final class GeneratedMetadataTranslator extends AbstractJavaClassTranslator<Project, Class> {

    private static final int LINES_PER_METHOD = 100;
    private static final String INIT_PART_METHOD_NAME = "initPart";
    private static final String STRING_BUILDER_NAME = "sb";

    static final String METADATA = "Metadata";

    public GeneratedMetadataTranslator(Injector injector, Project doc) {
        super(injector, doc, Class::of);
    }

    @Override
    public boolean isInGeneratedPackage() {
        return true;
    }

    @Override
    protected Class makeCodeGenModel(File file) {
        requireNonNull(file);
        final Method getMetadata = Method.of("getMetadata", DefaultType.optional(String.class))
            .protected_()
            .add(OVERRIDE);

        final Field metadataField = Field.of("METADATA", String.class)
            .private_().final_().static_();

        final Method initializer = Method.of("init", String.class).static_().private_();
        final ProjectMutator<? extends Project> project =
            Project.deepCopy(getSupport().projectOrThrow()).mutator();

        project.setSpeedmentVersion(infoComponent().getEditionAndVersionString());

        final List<String> lines = Stream.of(
            DocumentTranscoder.save(project.document(), Json::toJson)
                .split("\\R")).collect(toList());

        final List<List<String>> segments = new ArrayList<>();
        List<String> segment = new ArrayList<>();
        segments.add(segment);
        for (final String line : lines) {
            segment.add(line);
            if (segment.size() > LINES_PER_METHOD) {
                segment = new ArrayList<>();
                segments.add(segment);
            }
        }

        final List<Method> subInitializers = new ArrayList<>();

        segments.stream().map(seg -> {
            Method subMethod = addNewSubMethod(subInitializers);
            int lineCnt = 0;
            for (String line : seg) {
                subMethod.add(
                    Formatting.indent("\"" + line.replace("\\", "\\\\")
                        .replace("\"", "\\\"")
                        .replace("\n", "\\n") + "\"" + 
                        (++lineCnt == seg.size() ? "" : ",")
                    )
                );
            }
            return subMethod;
        }).forEach(subMethod -> subMethod.add(
            ").forEachOrdered(" + STRING_BUILDER_NAME + "::append);"
        ));

        file.add(Import.of(StringBuilder.class));
        file.add(Import.of(Stream.class));
        initializer.add("final StringBuilder " + STRING_BUILDER_NAME + " = new StringBuilder();");
        subInitializers.forEach(si -> initializer.add(si.getName() + "(" + STRING_BUILDER_NAME + ");"));
        initializer.add("return " + STRING_BUILDER_NAME + ".toString();");

        metadataField.set(Value.ofReference("init()"));
        getMetadata.add("return Optional.of(METADATA);");

        return newBuilder(file, getClassOrInterfaceName())
            .forEveryProject((clazz, p) -> {
                clazz.public_()
                    .setSupertype(AbstractApplicationMetadata.class)
                    .add(metadataField)
                    .add(initializer)
                    .add(getMetadata);

                subInitializers.forEach(clazz::add);
            }).build();
    }

    private Method addNewSubMethod(List<Method> methods) {
        final Method m = Method.of(INIT_PART_METHOD_NAME + methods.size(), void.class).private_().static_()
            .add(Field.of(STRING_BUILDER_NAME, StringBuilder.class));
        methods.add(m);
        m.add("Stream.of(");
        return m;
    }

    @Override
    protected Javadoc getJavaDoc() {
        final String owner = infoComponent().getTitle();
        return Javadoc.of(getJavadocRepresentText() + getGeneratedJavadocMessage())
            .add(AUTHOR.setValue(owner));
    }

    @Override
    protected String getJavadocRepresentText() {
        return "A {@link " + ApplicationMetadata.class.getName()
            + "} class for the {@link " + Project.class.getName()
            + "} named " + getSupport().projectOrThrow().getId() + "."
            + " This class contains the meta data present at code generation time.";
    }

    @Override
    protected String getClassOrInterfaceName() {
        return "Generated" + getSupport().typeName(getSupport().projectOrThrow()) + METADATA;
    }
}