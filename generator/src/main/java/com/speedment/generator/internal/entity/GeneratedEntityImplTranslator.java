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
package com.speedment.generator.internal.entity;

import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Constructor;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Generic;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.Method;
import com.speedment.common.codegen.model.Type;
import com.speedment.generator.internal.EntityAndManagerTranslator;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.internal.entity.AbstractEntity;

import java.util.Objects;
import java.util.StringJoiner;

import static com.speedment.common.codegen.internal.model.constant.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.common.codegen.internal.model.constant.DefaultType.*;
import com.speedment.generator.typetoken.TypeTokenGenerator;
import com.speedment.runtime.config.typetoken.PrimitiveTypeToken;
import com.speedment.runtime.config.typetoken.TypeToken;
import com.speedment.runtime.util.OptionalUtil;
import java.util.Optional;
import static com.speedment.common.codegen.internal.util.Formatting.nl;
import static com.speedment.common.codegen.internal.util.Formatting.tab;
import com.speedment.common.injector.annotation.Inject;
import static com.speedment.generator.internal.util.ColumnUtil.usesOptional;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author Emil Forslund
 * @author Per-Åke Minborg
 */
public final class GeneratedEntityImplTranslator extends EntityAndManagerTranslator<Class> {

    private @Inject TypeTokenGenerator typeTokenGenerator;
    
    public GeneratedEntityImplTranslator(Table table) {
        super(table, Class::of);
    }

    @Override
    protected Class makeCodeGenModel(File file) {
        requireNonNull(file);

        return newBuilder(file, getSupport().generatedEntityImplName())
            /**
             * Getters
             */
            .forEveryColumn((clazz, col) -> {
                final Type retType = GeneratedEntityTranslator.getterReturnType(typeTokenGenerator, col);
                final String getter;
                if (usesOptional(col)) {
                    final String varName = getSupport().variableName(col);
                    if (retType.getName().equals(Optional.class.getName())) {
                        getter = "Optional.ofNullable(" + varName + ")";
                    } else {
                        file.add(Import.of(Type.of(OptionalUtil.class)));
                        getter = "OptionalUtil.ofNullable(" + varName + ")";
                    }
                } else {
                    getter = getSupport().variableName(col);
                }
                clazz
                    .add(fieldFor(col).private_())
                    .add(Method.of(GETTER_METHOD_PREFIX + getSupport().typeName(col), retType)
                        .public_()
                        .add(OVERRIDE)
                        .add("return " + getter + ";"));

            })
            /**
             * Setters
             */
            .forEveryColumn((clazz, col) -> {
                clazz
                    .add(Method.of(SETTER_METHOD_PREFIX + getSupport().typeName(col), getSupport().entityType())
                        .public_().final_()
                        .add(OVERRIDE)
                        .add(fieldFor(col))
                        .add("this." + getSupport().variableName(col) + " = " + getSupport().variableName(col) + ";")
                        .add("return this;"));
            })
            /**
             * Class details
             */
            .forEveryTable(Phase.POST_MAKE, (clazz, table) -> {
                clazz
                    .add(copyMethod(file))
                    .add(toStringMethod(file))
                    .add(equalsMethod())
                    .add(hashCodeMethod())
                    .add(Method.of("entityClass", Type.of(java.lang.Class.class).add(Generic.of().add(getSupport().entityType()))).public_().add(OVERRIDE)
                        .add("return " + getSupport().entityName() + ".class;")
                    );
            })
            .build()
            .public_()
            .abstract_()
            .setSupertype(Type.of(AbstractEntity.class).add(Generic.of().add(getSupport().entityType())))
            .add(getSupport().entityType())
            .add(Constructor.of().protected_());

    }
    
    protected Method copyMethod(File file) {
        file.add(Import.of(getSupport().entityImplType()));
        final Method result = Method.of("copy", getSupport().entityType())
            .add(OVERRIDE).public_()
            .add("return new " + getSupport().entityImplName() + "()");
        
        result.add(
            getDocument().columns()
                .map(Column::getJavaName)
                .map(c -> ".set" + getSupport().namer().javaTypeName(c) + "(" + getSupport().namer().javaVariableName(c) + ")")
                .collect(joining(nl() + tab(), tab(), ";"))
                .split(nl())
        );
        
        return result;
    }

    protected Method toStringMethod(File file) {
        file.add(Import.of(Type.of(StringJoiner.class)));
        file.add(Import.of(Type.of(Objects.class)));
        
        final Method m = Method.of("toString", STRING)
            .public_()
            .add(OVERRIDE)
            .add("final StringJoiner sj = new StringJoiner(\", \", \"{ \", \" }\");");

        columns().forEachOrdered(col -> {
            final String getter;
            if (usesOptional(col)) {
                file.add(Import.of(Type.of(OptionalUtil.class)));
                getter = "OptionalUtil.unwrap(get" + getSupport().typeName(col) + "())";
            } else {
                getter = "get" + getSupport().typeName(col) + "()";
            }
            m.add("sj.add(\"" + getSupport().variableName(col) + " = \" + Objects.toString(" + getter + "));");
        });

        m.add("return \"" + getSupport().entityImplName() + " \" + sj.toString();");

        return m;

    }

    private Method equalsMethod() {

        final String thatName = "that";
        final String thatCastedName = thatName + getSupport().entityName();
        final Method method = Method.of("equals", BOOLEAN_PRIMITIVE)
            .public_()
            .add(OVERRIDE)
            .add(Field.of(thatName, OBJECT))
            .add("if (this == that) { return true; }")
            .add("if (!(" + thatName + " instanceof " + getSupport().entityName() + ")) { return false; }")
            .add("final " + getSupport().entityName() + " " + thatCastedName + " = (" + getSupport().entityName() + ")" + thatName + ";");

        columns().forEachOrdered(c -> {
            final String getter = "get" + getSupport().typeName(c);
            final TypeToken token = typeTokenGenerator.tokenOf(c);
            
            if (token.isPrimitive()) {
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

            final StringBuilder str = new StringBuilder();
            str.append("hash = 31 * hash + ");
            final TypeToken token = typeTokenGenerator.tokenOf(c);
            
            if (token.isPrimitive()) {
                @SuppressWarnings("unchecked")
                final PrimitiveTypeToken primitive = (PrimitiveTypeToken) token;
                str.append(primitive.getPrimitiveType().getWrapper().getSimpleName());
            } else {
                str.append("Objects");
            }
            
            str.append(".hashCode(get").append(getSupport().typeName(c)).append("());");
            method.add(str.toString());
        });

        method.add("return hash;");
        return method;
    }

    @Override
    protected String getJavadocRepresentText() {
        return "The generated base implementation";
    }

    @Override
    protected String getClassOrInterfaceName() {
        return getSupport().generatedEntityImplName();
    }

    @Override
    public boolean isInGeneratedPackage() {
        return true;
    }
}