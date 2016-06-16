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

import com.speedment.common.codegen.internal.model.value.ArrayValue;
import com.speedment.common.codegen.internal.model.value.ReferenceValue;
import static com.speedment.common.codegen.internal.util.Formatting.shortName;
import com.speedment.common.codegen.model.AnnotationUsage;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.Type;
import com.speedment.common.codegen.model.Value;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.mapstream.MapStream;
import com.speedment.generator.TranslatorSupport;
import com.speedment.runtime.config.Project;
import com.speedment.generator.internal.DefaultJavaClassTranslator;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.internal.runtime.AbstractSpeedment;
import com.speedment.runtime.internal.runtime.AbstractApplicationBuilder;
import static com.speedment.runtime.internal.util.document.DocumentDbUtil.traverseOver;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toSet;
import com.speedment.common.injector.annotation.IncludeInjectable;
import static com.speedment.runtime.internal.util.document.DocumentDbUtil.traverseOver;

/**
 *
 * @author Emil Forslund
 * @since  2.4.0
 */
public final class GeneratedApplicationImplTranslator extends DefaultJavaClassTranslator<Project, Class> {

    private @Inject Injector injector;
    
    public GeneratedApplicationImplTranslator(Project project) {
        super(project, Class::of);
    }

    @Override
    protected String getClassOrInterfaceName() {
        return "Generated" + getSupport().typeName(getSupport().projectOrThrow()) + "ApplicationImpl";
    }

    @Override
    public boolean isInGeneratedPackage() {
        return true;
    }

    @Override
    protected Class makeCodeGenModel(File file) {
        return newBuilder(file, getClassOrInterfaceName())
            .forEveryProject((clazz, project) -> {
                clazz.public_()
                    .setSupertype(Type.of(AbstractSpeedment.class))
                    .add(generatedType());
                
//                final Map<String, List<Table>> nameMap = traverseOver(project, Table.class)
//                    .filter(HasEnabled::test)
//                    .collect(Collectors.groupingBy(Table::getName));
//                
//                final Set<String> ambigousNames = MapStream.of(nameMap)
//                    .filterValue(l -> l.size() > 1)
//                    .keys()
//                    .collect(toSet());
//                
//                final AnnotationUsage requireInjectable = AnnotationUsage.of(Type.of(IncludeInjectable.class));
//                final List<Value<?>> requirements = new LinkedList<>();
//                
//                traverseOver(project, Table.class)
//                    .filter(HasEnabled::test)
//                    .forEachOrdered(t -> {
//                        final TranslatorSupport<Table> support = injector.inject(new TranslatorSupport<>(t));
//                        final Type managerType = support.managerImplType();
//                        
//                        if (ambigousNames.contains(t.getName())) {
//                            requirements.add(new ReferenceValue(managerType.getName() + ".class"));
//                        } else {
//                            file.add(Import.of(managerType));
//                            requirements.add(new ReferenceValue(shortName(managerType.getName()) + ".class"));
//                        }
//                    });
//                
//                requireInjectable.set(new ArrayValue(requirements));
//                clazz.add(requireInjectable);
            }).build();
    }
    
    @Override
    protected String getJavadocRepresentText() {
        return "The generated {@link " + AbstractApplicationBuilder.class.getName() + 
            "} implementation class for the {@link " + Project.class.getName() + 
            "} named " + getSupport().projectOrThrow().getName() + ".";
    }
    
    private Type generatedType() {
        return Type.of(
            getSupport().basePackageName() + ".generated.Generated" + 
            getSupport().typeName(getSupport().projectOrThrow()) + "Application"
        );
    }
}