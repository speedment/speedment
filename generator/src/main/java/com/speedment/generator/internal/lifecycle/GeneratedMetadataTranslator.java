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
package com.speedment.generator.internal.lifecycle;

import com.speedment.common.codegen.internal.model.JavadocImpl;
import com.speedment.common.codegen.internal.model.constant.DefaultType;
import com.speedment.common.codegen.internal.model.value.ReferenceValue;
import com.speedment.common.codegen.model.*;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.generator.internal.DefaultJavaClassTranslator;
import com.speedment.runtime.ApplicationMetadata;
import com.speedment.runtime.component.InfoComponent;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.internal.runtime.AbstractApplicationMetadata;
import com.speedment.runtime.internal.util.document.DocumentTranscoder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.speedment.common.codegen.internal.model.constant.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.common.codegen.internal.model.constant.DefaultJavadocTag.AUTHOR;
import static com.speedment.common.codegen.internal.model.constant.DefaultType.STRING;
import static com.speedment.common.codegen.internal.model.constant.DefaultType.VOID;
import static com.speedment.common.codegen.internal.util.Formatting.indent;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Per Minborg
 */
public final class GeneratedMetadataTranslator extends DefaultJavaClassTranslator<Project, Class> {

    private static final int LINES_PER_METHOD = 100;
    private static final String INIT_PART_METHOD_NAME = "initPart";
    private static final String STRING_BUILDER_NAME = "sb";
    public static final String METADATA = "Metadata";

    private @Inject InfoComponent infoComponent;

    public GeneratedMetadataTranslator(Project doc) {
        super(doc, Class::of);
    }

    @Override
    public boolean isInGeneratedPackage() {
        return true;
    }

    @Override
    protected Class makeCodeGenModel(File file) {
        requireNonNull(file);
        final Method getMetadata = Method.of("getMetadata", DefaultType.optional(STRING))
            .protected_()
            .add(OVERRIDE);

        final Field metadataField = Field.of("METADATA", Type.of(String.class))
            .private_().final_().static_();

        final Method initializer = Method.of("init", STRING).static_().private_();

        final List<String> lines = Stream.of(DocumentTranscoder.save(getSupport().projectOrThrow()).split("\\R")).collect(toList());
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
                    indent("\"" + line.replace("\\", "\\\\")
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

        file.add(Import.of(Type.of(StringBuilder.class)));
        file.add(Import.of(Type.of(Stream.class)));
        initializer.add("final StringBuilder " + STRING_BUILDER_NAME + " = new StringBuilder();");
        subInitializers.stream().forEachOrdered(si -> {
            initializer.add(si.getName() + "(" + STRING_BUILDER_NAME + ");");
        });
        initializer.add("return " + STRING_BUILDER_NAME + ".toString();");

        metadataField.set(new ReferenceValue("init()"));
        getMetadata.add("return Optional.of(METADATA);");

        final Class result = newBuilder(file, getClassOrInterfaceName())
            .forEveryProject((clazz, project) -> {
                clazz.public_()
                    .setSupertype(Type.of(AbstractApplicationMetadata.class))
                    .add(metadataField)
                    .add(initializer)
                    .add(getMetadata);
                
                subInitializers.forEach(clazz::add);
            }).build();
        
        return result;
    }

    private Method addNewSubMethod(List<Method> methods) {
        final Method m = Method.of(INIT_PART_METHOD_NAME + methods.size(), VOID).private_().static_()
            .add(Field.of(STRING_BUILDER_NAME, Type.of(StringBuilder.class)));
        methods.add(m);
        m.add("Stream.of(");
        return m;
    }

    @Override
    protected Javadoc getJavaDoc() {
        final String owner = infoComponent.title();
        return new JavadocImpl(getJavadocRepresentText() + getGeneratedJavadocMessage())
            .add(AUTHOR.setValue(owner));
    }

    @Override
    protected String getJavadocRepresentText() {
        return "A {@link " + ApplicationMetadata.class.getName()
            + "} class for the {@link " + Project.class.getName()
            + "} named " + getSupport().projectOrThrow().getName() + "."
            + " This class contains the meta data present at code generation time.";
    }

    @Override
    protected String getClassOrInterfaceName() {
        return "Generated" + getSupport().typeName(getSupport().projectOrThrow()) + METADATA;
    }
}