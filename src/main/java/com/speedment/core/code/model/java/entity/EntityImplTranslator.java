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
import com.speedment.codegen.lang.models.Field;
import com.speedment.codegen.lang.models.File;
import com.speedment.codegen.lang.models.Generic;
import com.speedment.codegen.lang.models.Import;
import static com.speedment.codegen.lang.models.constants.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.codegen.lang.models.constants.DefaultType.BOOLEAN_PRIMITIVE;
import static com.speedment.codegen.lang.models.constants.DefaultType.INT_PRIMITIVE;
import static com.speedment.codegen.lang.models.constants.DefaultType.OBJECT;
import static com.speedment.codegen.lang.models.constants.DefaultType.OPTIONAL;
import static com.speedment.codegen.lang.models.constants.DefaultType.STRING;
import com.speedment.core.config.model.Table;
import java.util.Objects;
import java.util.Optional;
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

                final Type retType;
                final String getter;
                if (c.isNullable()) {
                    retType = OPTIONAL.add(Generic.of().add(Type.of(c.getMapping())));
                    getter = "Optional.ofNullable(" + variableName(c) + ")";
                } else {
                    retType = Type.of(c.getMapping());
                    getter = variableName(c);
                }

                cl
                .add(fieldFor(c).private_())
                .add(Method.of(GETTER_METHOD_PREFIX + typeName(c), retType)
                    .public_()
                    .add(OVERRIDE)
                    .add("return " + getter + ";"))
                .add(Method.of(BUILDER_METHOD_PREFIX + typeName(c), ENTITY.getImplType())
                    .public_().final_()
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
            .add(toString(file))
            .add(equalsMethod())
            .add(hashCodeMethod());
    }

    protected Method toString(File file) {
        file.add(Import.of(Type.of(StringJoiner.class)));
        file.add(Import.of(Type.of(Objects.class)));
        final Method m = Method.of("toString", STRING)
            .public_()
            .add(OVERRIDE)
            .add("final StringJoiner sj = new StringJoiner(\", \", \"{ \", \" }\");");

        columns().forEachOrdered(c -> {
            final String getter;
            if (c.isNullable()) {
                getter = "get" + typeName(c) + "()" + ".orElse(null)";
            } else {
                getter = "get" + typeName(c) + "()";
            }
            m.add("sj.add(\"" + variableName(c) + " = \"+Objects.toString(" + getter + "));");
        });

        m.add("return \"" + ENTITY.getImplName() + " \"+sj.toString();");

        return m;

    }

    private Method equalsMethod() {

        final String thatName = "that";
        final String thatCastedName = thatName + ENTITY.getName();
        final Method method = Method.of("equals", BOOLEAN_PRIMITIVE)
            .public_()
            .add(OVERRIDE)
            .add(Field.of(thatName, OBJECT))
            .add("if (this == that) { return true; }")
            .add("if (!(" + thatName + " instanceof " + ENTITY.getName() + ")) { return false; }")
            .add("@SuppressWarnings(\"unchecked\")")
            .add("final " + ENTITY.getName() + " " + thatCastedName + " = (" + ENTITY.getName() + ")" + thatName + ";");

        columns().forEachOrdered(c -> {
            final String getter = "get" + typeName(c);
            if (c.getMapping().isPrimitive()) {
                method.add("if (this." + getter + "() != " + thatCastedName + "." + getter + "()) {return false; }");
            } else {
                method.add("if (!Objects.equals(this." + getter + "(), " + thatCastedName + "." + getter + "())) {return false; }");
            }
        });

        method.add("return true;");
        return method;
    }

    private Method hashCodeMethod() {
        final Method method = Method.of("hashCode", INT_PRIMITIVE)
            .public_()
            .add(OVERRIDE)
            .add("int hash = 7;");

        columns().forEachOrdered(c -> {
            final String getter = "get" + typeName(c);
            if (c.getMapping().isPrimitive()) {
                //Todo: Optimize this to remove auto boxing
                method.add("hash = 31 * hash + Objects.hash(" + getter + "());");
            } else {
                method.add("hash = 31 * hash + Objects.hash(" + getter + "());");
            }
        });

        method.add("return hash;");
        return method;
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
