/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
import com.speedment.internal.codegen.base.Generator;
import com.speedment.internal.codegen.lang.models.Class;
import com.speedment.internal.codegen.lang.models.Constructor;
import com.speedment.internal.codegen.lang.models.File;
import com.speedment.internal.codegen.lang.models.Import;
import com.speedment.internal.codegen.lang.models.Javadoc;
import com.speedment.internal.codegen.lang.models.Method;
import com.speedment.internal.codegen.lang.models.Type;
import static com.speedment.internal.codegen.lang.models.constants.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.internal.codegen.lang.models.constants.DefaultJavadocTag.AUTHOR;
import static com.speedment.internal.codegen.lang.models.constants.DefaultType.VOID;
import com.speedment.internal.codegen.lang.models.implementation.GenericImpl;
import com.speedment.internal.codegen.lang.models.implementation.JavadocImpl;
import com.speedment.internal.core.code.DefaultJavaClassTranslator;
import static com.speedment.internal.core.code.DefaultJavaClassTranslator.GENERATED_JAVADOC_MESSAGE;
import static com.speedment.internal.core.code.lifecycle.SpeedmentApplicationMetadataTranslator.METADATA;
import com.speedment.internal.core.code.manager.EntityManagerImplTranslator;
import com.speedment.config.Project;
import com.speedment.config.Table;
import com.speedment.internal.core.runtime.SpeedmentApplicationLifecycle;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
public final class SpeedmentApplicationTranslator extends DefaultJavaClassTranslator<Project, Class> {

    private final String className = typeName(project()) + "Application";

    public SpeedmentApplicationTranslator(Speedment speedment, Generator cg, Project configEntity) {
        super(speedment, cg, configEntity);
    }

    @Override
    protected Class make(File file) {
        requireNonNull(file);
        final Method onInit = Method.of("onInit", VOID)
            .protected_()
            .add(OVERRIDE)
            .add("loadAndSetProject();");

        project().traverseOver(Table.class)
            .filter(Table::isEnabled)
            .forEachOrdered(t -> {
                EntityManagerImplTranslator entityManagerImplTranslator = new EntityManagerImplTranslator(getSpeedment(), getCodeGenerator(), t);
                final Type managerType = entityManagerImplTranslator.getImplType();
                file.add(Import.of(managerType));
                onInit.add("put(new " + managerType.getName() + "(speedment));");
            });

        onInit.add("super.onInit();");

        //final Path path = project().getConfigPath();
        return Class.of(className)
            .public_()
            .setSupertype(Type.of(SpeedmentApplicationLifecycle.class).add(new GenericImpl(className)))
            .add(Constructor.of()
                .public_()
                .add("setSpeedmentApplicationMetadata(new " + className + METADATA + "());")
            )
            .add(onInit)
            .add(generated());
    }

    @Override
    protected Javadoc getJavaDoc() {
        return new JavadocImpl(getJavadocRepresentText() + GENERATED_JAVADOC_MESSAGE).add(AUTHOR.setValue("Speedment"));
    }

    @Override
    protected String getJavadocRepresentText() {
        return "A {@link " + SpeedmentApplicationLifecycle.class.getName() + "} class for the {@link " + Project.class.getName() + "} named " + project().getName() + ".";
    }

    @Override
    protected String getFileName() {
        return className;
    }
}
