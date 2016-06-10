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

import com.speedment.generator.TranslatorSupport;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Constructor;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Generic;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.Javadoc;
import com.speedment.common.codegen.model.Method;
import com.speedment.common.codegen.model.Type;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.common.codegen.internal.model.JavadocImpl;
import static com.speedment.common.codegen.internal.model.constant.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.common.codegen.internal.model.constant.DefaultJavadocTag.AUTHOR;
import static com.speedment.common.codegen.internal.model.constant.DefaultType.VOID;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.generator.internal.DefaultJavaClassTranslator;
import static com.speedment.generator.internal.lifecycle.GeneratedMetadataTranslator.METADATA;
import com.speedment.runtime.internal.runtime.AbstractApplicationBuilder;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toSet;
import com.speedment.common.mapstream.MapStream;
import com.speedment.runtime.component.InfoComponent;
import static com.speedment.runtime.internal.util.document.DocumentDbUtil.traverseOver;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 * @since  2.4.0
 */
public final class GeneratedApplicationBuilderTranslator extends DefaultJavaClassTranslator<Project, Class> {
    
    private @Inject InfoComponent infoComponent;
    private @Inject Injector injector;
    
    public GeneratedApplicationBuilderTranslator(Project doc) {
        super(doc, Class::of);
    }

    @Override
    public boolean isInGeneratedPackage() {
        return true;
    }
    
    @Override
    protected Class makeCodeGenModel(File file) {
        requireNonNull(file);
        
        return newBuilder(file, getClassOrInterfaceName())
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
                        final TranslatorSupport<Table> support = injector.inject(new TranslatorSupport<>(t));
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
                
                final Method onStart = Method.of("onStart", VOID)
                    .public_()
                    .add(OVERRIDE)
                    .add("super.onStart();")
                    .add("application.start();");
                
                file.add(Import.of(applicationType()));
                file.add(Import.of(applicationImplType()));
                
                clazz.public_().abstract_()
                    .setSupertype(Type.of(AbstractApplicationBuilder.class)
                        .add(Generic.of().add(applicationType()))
                        .add(Generic.of().add(builderType()))
                    )
                    .add(Constructor.of()
                        .protected_()
                        .add("super(new " + getSupport().typeName(getSupport().projectOrThrow()) + "ApplicationImpl());")
                        .add("setSpeedmentApplicationMetadata(new Generated" + getSupport().typeName(getSupport().projectOrThrow()) + METADATA + "());")
                    )
                    .add(onInit)
                    .add(onStart);
            }).build();
    }
    
    @Override
    protected Javadoc getJavaDoc() {
        final String owner = infoComponent.title();
        return new JavadocImpl(getJavadocRepresentText() + getGeneratedJavadocMessage())
            .add(AUTHOR.setValue(owner));
    }
    
    @Override
    protected String getJavadocRepresentText() {
        return "A generated base {@link " + AbstractApplicationBuilder.class.getName() + 
            "} class for the {@link " + Project.class.getName() + 
            "} named " + getSupport().projectOrThrow().getName() + ".";
    }
    
    @Override
    protected String getClassOrInterfaceName() {
        return "Generated" + getSupport().typeName(getSupport().projectOrThrow()) + "ApplicationBuilder";
    }
    
    private Type builderType() {
        return Type.of(
            getSupport().basePackageName() + "." + 
            getSupport().typeName(getSupport().projectOrThrow()) + "ApplicationBuilder"
        );
    }
    
    private Type applicationType() {
        return Type.of(
            getSupport().basePackageName() + "." + 
            getSupport().typeName(getSupport().projectOrThrow()) + "Application"
        );
    }
    
    private Type applicationImplType() {
        return Type.of(
            getSupport().basePackageName() + "." + 
            getSupport().typeName(getSupport().projectOrThrow()) + "ApplicationImpl"
        );
    }
}
