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
import com.speedment.common.codegen.Generator;
import static com.speedment.common.codegen.internal.model.constant.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.common.codegen.internal.model.constant.DefaultType.WILDCARD;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Generic;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.Method;
import com.speedment.common.codegen.model.Type;
import com.speedment.runtime.config.Project;
import com.speedment.generator.internal.DefaultJavaClassTranslator;
import com.speedment.runtime.internal.runtime.AbstractSpeedment;
import com.speedment.runtime.internal.runtime.AbstractApplicationBuilder;

/**
 *
 * @author Emil Forslund
 * @since  2.4.0
 */
public final class GeneratedApplicationImplTranslator extends DefaultJavaClassTranslator<Project, Class> {

    private final String className = "Generated" + getSupport().typeName(getSupport().projectOrThrow()) + "ApplicationImpl";
    
    public GeneratedApplicationImplTranslator(
            Speedment speedment, 
            Generator generator, 
            Project project) {
        
        super(speedment, generator, project, Class::of);
    }

    @Override
    protected String getClassOrInterfaceName() {
        return className;
    }

    @Override
    public boolean isInGeneratedPackage() {
        return true;
    }

    @Override
    protected Class makeCodeGenModel(File file) {
        return newBuilder(file, className)
            .forEveryProject((clazz, project) -> {
                file.add(Import.of(builderType()));
                clazz.public_()
                    .setSupertype(Type.of(AbstractSpeedment.class))
                    .add(generatedType())
                    .add(Method.of("newApplicationBuilder",
                        Type.of(AbstractApplicationBuilder.class)
                            .add(Generic.of(WILDCARD))
                            .add(Generic.of(WILDCARD))
                        )
                        .public_().add(OVERRIDE)
                        .add("return new " + getSupport().typeName(getSupport().projectOrThrow()) + "ApplicationBuilder();"));
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
    
    private Type builderType() {
        return Type.of(
            getSupport().basePackageName() + "." + 
            getSupport().typeName(getSupport().projectOrThrow()) + "ApplicationBuilder"
        );
    }
}