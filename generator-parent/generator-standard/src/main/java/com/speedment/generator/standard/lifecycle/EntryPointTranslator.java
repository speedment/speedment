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

import static com.speedment.common.codegen.util.Formatting.indent;

import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Method;
import com.speedment.common.injector.Injector;
import com.speedment.generator.translator.AbstractJavaClassTranslator;
import com.speedment.runtime.config.Project;

/**
 * @author Mislav Milicevic
 * @since 3.2.9
 */
public final class EntryPointTranslator extends AbstractJavaClassTranslator<Project, Class> {

    public EntryPointTranslator(Injector injector, Project project) {
        super(injector, project, Class::of);
    }

    @Override
    protected String getClassOrInterfaceName() {
        return getSupport().typeName(getSupport().projectOrThrow()) + "EntryPoint";
    }

    @Override
    protected Class makeCodeGenModel(File file) {
        return newBuilder(file, getClassOrInterfaceName())
            .forEveryProject((clazz, project) -> {
                clazz.public_().final_();
                clazz.add(mainMethod());
            }).build();
    }

    private Method mainMethod() {
        final Method method = Method.of("main", void.class)
            .public_()
            .static_()
            .add(Field.of("args", String[].class));

        final String projectName = getSupport().typeName(getSupport().projectOrThrow());

        method.add("final " + projectName + "Application application = new " + projectName + "ApplicationBuilder()");
        method.add(indent("// Add bundles, auth information, etc."));
        method.add(indent(".build();"));
        method.add("");
        method.add("// Application logic goes here");
        method.add("");
        method.add("application.stop();");
        return method;
    }

    @Override
    protected String getJavadocRepresentText() {
        return "The entry point for the {@link " + Project.class.getName() + "} "
                + "named " + getSupport().projectOrThrow().getId();
    }
}
