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

import com.speedment.runtime.Speedment;
import com.speedment.generator.TranslatorSupport;
import com.speedment.fika.codegen.Generator;
import com.speedment.fika.codegen.model.Class;
import com.speedment.fika.codegen.model.Constructor;
import com.speedment.fika.codegen.model.File;
import com.speedment.fika.codegen.model.Generic;
import com.speedment.fika.codegen.model.Import;
import com.speedment.fika.codegen.model.Javadoc;
import com.speedment.fika.codegen.model.Method;
import com.speedment.fika.codegen.model.Type;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.fika.codegen.internal.model.JavadocImpl;
import static com.speedment.fika.codegen.internal.model.constant.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.fika.codegen.internal.model.constant.DefaultJavadocTag.AUTHOR;
import static com.speedment.fika.codegen.internal.model.constant.DefaultType.VOID;
import com.speedment.generator.internal.DefaultJavaClassTranslator;
import static com.speedment.generator.internal.DefaultJavaClassTranslator.GENERATED_JAVADOC_MESSAGE;
import static com.speedment.generator.internal.lifecycle.GeneratedSpeedmentApplicationMetadataTranslator.METADATA;
import com.speedment.runtime.internal.runtime.SpeedmentApplicationLifecycle;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toSet;
import static com.speedment.runtime.internal.util.document.DocumentDbUtil.traverseOver;
import com.speedment.fika.mapstream.MapStream;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
public final class GeneratedSpeedmentApplicationTranslator extends DefaultJavaClassTranslator<Project, Class> {
    
    private final String className = "Generated" + getSupport().typeName(getSupport().projectOrThrow()) + "Application";
    
    public GeneratedSpeedmentApplicationTranslator(Speedment speedment, Generator gen, Project doc) {
        super(speedment, gen, doc, Class::of);
    }
    
    @Override
    public boolean isInGeneratedPackage() {
        return true;
    }
    
    @Override
    protected Class makeCodeGenModel(File file) {
        requireNonNull(file);
        
        return newBuilder(file, className)
            .forEveryProject((clazz, project) -> {
                
                final Map<String, List<Table>> nameMap = traverseOver(project, Table.class)
                    .filter(HasEnabled::test)
                    .collect(Collectors.groupingBy(Table::getName));

                final Set<String> ambigousNames = MapStream.of(nameMap)
                    .filterValue(l -> l.size() > 1)
                    .keys()
                    .collect(toSet());

                final Method onInit = Method.of("onLoad", VOID)
                    .public_()
                    .add(OVERRIDE)
                    .add("super.onLoad();")
                    .add("loadAndSetProject();");

                final String methodName = "applyAndPut";

                traverseOver(project, Table.class)
                    .filter(HasEnabled::test)
                    .forEachOrdered(t -> {
                        final TranslatorSupport<Table> support = new TranslatorSupport<>(getSpeedment(), t);
                        final Type managerType = support.managerImplType();
                        final String managerName = support.managerImplName();
                        if (ambigousNames.contains(t.getName())) {
                            onInit.add(methodName+"("+managerType.getName() + "::new);");
                        } else {
                            file.add(Import.of(managerType));
                            onInit.add(methodName+"(" + managerName + "::new);");
                        }

                    });

                onInit.add("loadCustomManagers();");
                
                clazz.public_().abstract_()
                    .setSupertype(Type.of(SpeedmentApplicationLifecycle.class)
                        .add(Generic.of().add(applicationType()))
                    )
                    .add(Constructor.of()
                        .protected_()
                        .add("setSpeedmentApplicationMetadata(new " + className + METADATA + "());")
                    )
                    .add(onInit);
            }).build();
    }
    
    @Override
    protected Javadoc getJavaDoc() {
        final String owner = getSpeedment().getInfoComponent().title();
        return new JavadocImpl(getJavadocRepresentText() + GENERATED_JAVADOC_MESSAGE)
            .add(AUTHOR.setValue(owner));
    }
    
    @Override
    protected String getJavadocRepresentText() {
        return "A generated base {@link " + SpeedmentApplicationLifecycle.class.getName() + 
            "} class for the {@link " + Project.class.getName() + 
            "} named " + getSupport().projectOrThrow().getName() + ".";
    }
    
    @Override
    protected String getClassOrInterfaceName() {
        return className;
    }
    
    protected Type applicationType() {
        return Type.of(
            getSupport().basePackageName() + "." + 
            getSupport().typeName(getSupport().projectOrThrow()) + "Application"
        );
    }
}