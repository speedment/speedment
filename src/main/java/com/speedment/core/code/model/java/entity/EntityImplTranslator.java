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
package com.speedment.core.code.model.java.entity;

import com.speedment.core.code.model.java.BaseEntityAndManagerTranslator;
import com.speedment.codegen.base.Generator;
import com.speedment.codegen.lang.models.Method;
import com.speedment.codegen.lang.models.Type;
import com.speedment.codegen.lang.models.Class;
import com.speedment.codegen.lang.models.File;
import com.speedment.codegen.lang.models.Import;
import static com.speedment.codegen.lang.models.constants.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.codegen.lang.models.constants.DefaultType.STRING;
import com.speedment.core.config.model.Table;
import java.util.Objects;
import java.util.StringJoiner;

/**
 *
 * @author pemi
 */
public class EntityImplTranslator extends BaseEntityAndManagerTranslator<Class> {

    public EntityImplTranslator(Generator cg, Table configEntity) {
        super(cg, configEntity);
    }

    @Override
    protected Class make(File file) {

        return new ClassBuilder(ENTITY.getImplName())
                .addColumnConsumer((cl, c) -> {
                    cl
                    .add(fieldFor(c).private_())
                    .add(Method.of(GETTER_METHOD_PREFIX + typeName(c), Type.of(c.getMapping()))
                            .public_()
                            .add(OVERRIDE)
                            .add("return " + variableName(c) + ";"))
                    .add(Method.of(BUILDER_METHOD_PREFIX + typeName(c), ENTITY.getImplType())
                            .public_()
                            .add(OVERRIDE)
                            .add(fieldFor(c))
                            .add("this." + variableName(c) + " = " + variableName(c) + ";")
                            .add("return this;"));
                })
                .build()
                .public_()
                .add(BUILDER.getType())
                .add(emptyConstructor())
                .add(copyConstructor(ENTITY.getType(), CopyConstructorMode.BUILDER))
                .add(Method.of("build", ENTITY.getType())
                        .add(OVERRIDE)
                        .public_()
                        .add("return new " + ENTITY.getImplName() + "(this);"))
                .add(toString(file));
    }

    protected Method toString(File file) {
        file.add(Import.of(Type.of(StringJoiner.class)));
        file.add(Import.of(Type.of(Objects.class)));
        final Method m = Method.of("toString", STRING)
                .public_()
                .add(OVERRIDE)
                .add("final StringJoiner sj = new StringJoiner(\", \", \"{ \", \" }\");");

        columns().forEachOrdered(c -> {
            m.add("sj.add(\"" + variableName(c) + " = \"+Objects.toString(get" + typeName(c) + "()));");
        });

        m.add("return \"" + ENTITY.getImplName() + " \"+sj.toString();");

        return m;

    }

    @Override
    protected String getJavadocRepresentText() {
        return "An implementation ";
    }

    @Override
    protected String getFileName() {
        return ENTITY.getImplName();
    }

    @Override
    protected boolean isInImplPackage() {
        return true;
    }
}
