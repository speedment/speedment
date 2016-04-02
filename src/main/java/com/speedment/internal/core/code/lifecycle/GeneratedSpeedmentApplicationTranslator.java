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
import com.speedment.codegen.model.Constructor;
import com.speedment.codegen.model.File;
import com.speedment.codegen.model.Import;
import com.speedment.codegen.model.Javadoc;
import com.speedment.codegen.model.Method;
import com.speedment.codegen.model.Type;
import static com.speedment.internal.codegen.model.constant.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.internal.codegen.model.constant.DefaultJavadocTag.AUTHOR;
import static com.speedment.internal.codegen.model.constant.DefaultType.VOID;
import com.speedment.internal.codegen.model.JavadocImpl;
import com.speedment.internal.core.code.DefaultJavaClassTranslator;
import static com.speedment.internal.core.code.DefaultJavaClassTranslator.GENERATED_JAVADOC_MESSAGE;
import static com.speedment.internal.core.code.lifecycle.GeneratedSpeedmentApplicationMetadataTranslator.METADATA;
import com.speedment.internal.core.code.manager.EntityManagerImplTranslator;
import com.speedment.config.db.Project;
import com.speedment.config.db.Table;
import com.speedment.config.db.trait.HasEnabled;
import com.speedment.codegen.model.Generic;
import com.speedment.internal.core.runtime.SpeedmentApplicationLifecycle;
import com.speedment.code.TranslatorSupport;
import com.speedment.stream.MapStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toSet;
import static com.speedment.internal.util.document.DocumentDbUtil.traverseOver;
import static java.util.Objects.requireNonNull;
import static com.speedment.internal.util.document.DocumentDbUtil.traverseOver;
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
        
        final Map<String, List<Table>> nameMap = traverseOver(getSupport().projectOrThrow(), Table.class)
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
        
        traverseOver(getSupport().projectOrThrow(), Table.class)
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

        return newBuilder(file, className)
            .build()
            .public_().abstract_()
            .setSupertype(Type.of(SpeedmentApplicationLifecycle.class).add(Generic.of().add(applicationType())))
            .add(Constructor.of()
                .protected_()
                .add("setSpeedmentApplicationMetadata(new " + className + METADATA + "());")
            )
            .add(onInit);
    }
    
    @Override
    protected Javadoc getJavaDoc() {
        final String owner = getSpeedment().getUserInterfaceComponent().getBrand().title();
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