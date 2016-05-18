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
package com.speedment.internal.core.code.lifecycle;

import com.speedment.Speedment;
import com.speedment.codegen.Generator;
import com.speedment.codegen.model.Class;
import com.speedment.codegen.model.Field;
import com.speedment.codegen.model.File;
import com.speedment.codegen.model.Import;
import com.speedment.codegen.model.Javadoc;
import com.speedment.codegen.model.Method;
import com.speedment.codegen.model.Type;
import com.speedment.config.db.Project;
import com.speedment.internal.codegen.model.JavadocImpl;
import static com.speedment.internal.codegen.model.constant.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.internal.codegen.model.constant.DefaultJavadocTag.AUTHOR;
import static com.speedment.internal.codegen.model.constant.DefaultType.STRING;
import static com.speedment.internal.codegen.model.constant.DefaultType.VOID;
import com.speedment.internal.codegen.model.value.ReferenceValue;
import static com.speedment.internal.codegen.util.Formatting.indent;
import com.speedment.internal.core.code.DefaultJavaClassTranslator;
import com.speedment.internal.core.runtime.ApplicationMetadata;
import com.speedment.internal.util.document.DocumentTranscoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author pemi
 */
public final class GeneratedSpeedmentApplicationMetadataTranslator extends DefaultJavaClassTranslator<Project, Class> {

    private static final int LINES_PER_METHOD = 100;
    private static final String INIT_PART_METHOD_NAME = "initPart";
    private static final String STRING_BUILDER_NAME = "sb";
    public static final String METADATA = "Metadata";

    private final String className = "Generated" + getSupport().typeName(getSupport().projectOrThrow()) + "Application" + METADATA;

    public GeneratedSpeedmentApplicationMetadataTranslator(Speedment speedment, Generator gen, Project doc) {
        super(speedment, gen, doc, Class::of);
    }

    @Override
    public boolean isInGeneratedPackage() {
        return true;
    }

    @Override
    protected Class makeCodeGenModel(File file) {
        requireNonNull(file);
        final Method getMetadata = Method.of("getMetadata", STRING)
            .public_()
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

        for (List<String> seg : segments) {
            Method subMethod = addNewSubMethod(subInitializers);
            int lineCnt = 0;
            for (String line : seg) {
                subMethod.add(
                    indent("\"" + line.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n") + "\"" + (++lineCnt == seg.size() ? "" : ","))
                );
            }
            subMethod.add(").forEachOrdered(" + STRING_BUILDER_NAME + "::append);");
        }

        file.add(Import.of(Type.of(StringBuilder.class)));
        file.add(Import.of(Type.of(Stream.class)));
        initializer.add("final StringBuilder " + STRING_BUILDER_NAME + " = new StringBuilder();");
        subInitializers.stream().forEachOrdered(si -> {
            initializer.add(si.getName() + "(" + STRING_BUILDER_NAME + ");");
        });
        initializer.add("return " + STRING_BUILDER_NAME + ".toString();");

        metadataField.set(new ReferenceValue("init()"));
        getMetadata.add("return METADATA;");

        final Class result = newBuilder(file, className)
            .forEveryProject((clazz, project) -> {
                clazz.public_()
                    .add(Type.of(ApplicationMetadata.class))
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
        final String owner = getSpeedment().getUserInterfaceComponent().getBrand().title();
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
        return className;
    }
}
