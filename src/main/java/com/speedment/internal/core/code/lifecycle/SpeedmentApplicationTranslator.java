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
import com.speedment.config.db.Project;
import com.speedment.codegen.base.Generator;
import com.speedment.codegen.lang.models.Class;
import com.speedment.codegen.lang.models.File;
import com.speedment.codegen.lang.models.Type;
import com.speedment.internal.core.code.DefaultJavaClassTranslator;
import com.speedment.internal.core.runtime.SpeedmentApplicationLifecycle;

/**
 *
 * @author Emil Forslund
 */
public final class SpeedmentApplicationTranslator extends DefaultJavaClassTranslator<Project, Class> {

    private final String className = typeName(projectOrThrow()) + "Application";
    
    public SpeedmentApplicationTranslator(
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
    protected Class makeCodeGenModel(File file) {
        return newBuilder(file, className).build()
            .public_().final_()
            .setSupertype(generatedType());
    }

    @Override
    protected String getJavadocRepresentText() {
        return "A {@link " + SpeedmentApplicationLifecycle.class.getName() + 
            "} class for the {@link " + Project.class.getName() + 
            "} named " + projectOrThrow().getName() + ".";
    }
    
    private Type generatedType() {
        return Type.of(
            basePackageName() + ".generated.Generated" + 
            typeName(projectOrThrow()) + "Application"
        );
    }
}