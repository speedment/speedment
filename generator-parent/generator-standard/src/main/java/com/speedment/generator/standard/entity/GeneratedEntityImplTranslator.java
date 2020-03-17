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
package com.speedment.generator.standard.entity;

import com.speedment.common.codegen.constant.DefaultAnnotationUsage;
import com.speedment.common.codegen.constant.DefaultType;
import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.*;
import com.speedment.common.injector.Injector;
import com.speedment.generator.standard.util.FkHolder;
import com.speedment.generator.standard.util.ForeignKeyUtil;
import com.speedment.generator.translator.AbstractEntityAndManagerTranslator;
import com.speedment.generator.translator.TranslatorSupport;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.core.util.OptionalUtil;

import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;

import static com.speedment.common.codegen.constant.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.common.codegen.util.Formatting.block;
import static com.speedment.generator.standard.entity.GeneratedEntityTranslator.getterReturnType;
import static com.speedment.generator.standard.util.ColumnUtil.optionalGetterName;
import static com.speedment.generator.standard.util.ColumnUtil.usesOptional;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 * @author Per-Åke Minborg
 */
public final class GeneratedEntityImplTranslator extends AbstractEntityAndManagerTranslator<Class> {

    public GeneratedEntityImplTranslator(Injector injector, Table table) {
        super(injector, table, Class::of);
    }

    @Override
    protected Class makeCodeGenModel(File file) {
        requireNonNull(file);

        return newBuilder(file, getSupport().generatedEntityImplName())

            /*
             * Class details
             */
            .forEveryTable((clazz, table) ->
                clazz.public_()
                    .abstract_()
                    .add(getSupport().entityType())
                    .add(Constructor.of().protected_())
            )

            /*
             * Getters, setters and findersGeneratedEntityTranslator.java
             */
            .forEveryColumn((clazz, col) -> addGetterTo(file, clazz, col))
            .forEveryColumn(this::addSetterTo)
            .forEveryColumn((clazz, col) -> addFindersTo(file, clazz, col))
            
            // We need to make it POST_MAKE because other plugins might add fields
            .forEveryTable(Phase.POST_MAKE, (clazz, table) -> 
                clazz
                    .add(toStringMethod(file))
                    .add(equalsMethod())
                    .add(hashCodeMethod(file))
            )
            .build();

    }

    private void addFindersTo(File file, Class clazz, Column col) {
        ForeignKeyUtil.getForeignKey(
            getSupport().tableOrThrow(), col
        ).ifPresent(fkc -> {
            final FkHolder fu = new FkHolder(injector(), fkc.getParentOrThrow());
            final TranslatorSupport<Table> fuSupport = fu.getForeignEmt().getSupport();

            file.add(Import.of(fuSupport.entityType()));

            final String isPresentName = usesOptional(col) ? ".isPresent()" : " != null";
            final String getterName = optionalGetterName(typeMappers(), col).orElse("");

            clazz.add(Method.of(FINDER_METHOD_PREFIX + getSupport().typeName(col),
                col.isNullable()
                    ? DefaultType.optional(fuSupport.entityType())
                    : fuSupport.entityType())
                .public_()
                .add(DefaultAnnotationUsage.OVERRIDE)
                .add(Field.of("foreignManager", SimpleParameterizedType.create(
                    Manager.class, fuSupport.entityType()
                )))
                .add(
                    col.isNullable() ?
                        "if (" + GETTER_METHOD_PREFIX + getSupport().namer().javaTypeName(col.getJavaName()) + "()" + isPresentName + ") " + block(
                            "return foreignManager.stream().filter(" + fuSupport.entityName() +
                            "." + fuSupport.namer().javaStaticFieldName(fu.getForeignColumn().getJavaName()) +
                            ".equal(" + GETTER_METHOD_PREFIX + getSupport().namer().javaTypeName(col.getJavaName()) +
                            "()" + getterName + ")).findAny();"
                        ) + " else " + block(
                            "return Optional.empty();"
                        )
                        :
                        "return foreignManager.stream().filter(" + fuSupport.entityName() +
                            "." + fuSupport.namer().javaStaticFieldName(fu.getForeignColumn().getJavaName()) +
                            ".equal(" + GETTER_METHOD_PREFIX + getSupport().namer().javaTypeName(col.getJavaName()) +
                            "()" + getterName + ")).findAny().orElse(null);"
                )
            );
        }
    );
    }

    private Class addSetterTo(Class clazz, Column col) {
        requireNonNull(clazz);
        requireNonNull(col);

        return clazz
            .add(Method.of(SETTER_METHOD_PREFIX + getSupport().typeName(col), getSupport().entityType())
                .public_()
                .add(OVERRIDE)
                .add(fieldFor(col))
                .add("this." + getSupport().variableName(col) + " = " + getSupport().variableName(col) + ";")
                .add("return this;"));
    }

    private void addGetterTo(File file, Class clazz, Column col) {
        requireNonNull(file);
        requireNonNull(clazz);
        requireNonNull(col);

        final Type retType = getterReturnType(typeMappers(), col);
        final String getter;
        if (usesOptional(col)) {
            final String varName = getSupport().variableName(col);
            if (retType.getTypeName().equals(Optional.class.getName())) {
                getter = "Optional.ofNullable(" + varName + ")";
            } else {
                file.add(Import.of(OptionalUtil.class));
                getter = "OptionalUtil.ofNullable(" + varName + ")";
            }
        } else {
            getter = getSupport().variableName(col);
        }
        clazz
            .add(fieldFor(col).private_())
            .add(Method.of(GETTER_METHOD_PREFIX + getSupport().typeName(col), retType)
                .public_().add(OVERRIDE)
                .add("return " + getter + ";"));
    }

    private Method toStringMethod(File file) {
        file.add(Import.of(StringJoiner.class));
        file.add(Import.of(Objects.class));
        
        final Method m = Method.of("toString", String.class)
            .public_()
            .add(OVERRIDE)
            .add("final StringJoiner sj = new StringJoiner(\", \", \"{ \", \" }\");");

        columns().forEachOrdered(col -> {
            final String getter;
            if (usesOptional(col)) {
                file.add(Import.of(OptionalUtil.class));
                getter = "OptionalUtil.unwrap(get" + getSupport().typeName(col) + "())";
            } else {
                getter = "get" + getSupport().typeName(col) + "()";
            }
            m.add("sj.add(\"" + getSupport().variableName(col) + " = \" \t+ Objects.toString(" + getter + "));");
        });

        m.add("return \"" + getSupport().entityImplName() + " \" + sj.toString();");

        return m;

    }

    private Method equalsMethod() {

        final String thatName = "that";
        final String thatCastedName = thatName + getSupport().entityName();
        final Method method = Method.of("equals", boolean.class)
            .public_()
            .add(OVERRIDE)
            .add(Field.of(thatName, Object.class))
            .add("if (this == that) { return true; }")
            .add("if (!(" + thatName + " instanceof " + getSupport().entityName() + ")) { return false; }")
            .add("final " + getSupport().entityName() + " " + thatCastedName + " = (" + getSupport().entityName() + ")" + thatName + ";");

        columns().forEachOrdered(c -> {
            final String getter = "get" + getSupport().typeName(c);
            final Type type = typeMappers().get(c).getJavaType(c);
            if (usesOptional(c) || !DefaultType.isPrimitive(type)) {
                method.add("if (!Objects.equals(this." + getter + "(), " + thatCastedName + "." + getter + "())) { return false; }");
            } else {
                method.add("if (this." + getter + "() != " + thatCastedName + "." + getter + "()) { return false; }");
            }
        });

        method.add("return true;");
        return method;
    }

    private Method hashCodeMethod(File file) {
        final Method method = Method.of("hashCode", int.class)
            .public_()
            .add(OVERRIDE)
            .add("int hash = 7;");

        columns().forEachOrdered(c -> {

            final StringBuilder str = new StringBuilder();
            str.append("hash = 31 * hash + ");
            final Type type = typeMappers().get(c).getJavaType(c);

            if (!usesOptional(c) && DefaultType.isPrimitive(type)) {
                str.append(DefaultType.wrapperFor(type).getSimpleName());
            } else {
                str.append("Objects");
            }
            
            str.append(".hashCode(");

            if (usesOptional(c)) {
                file.add(Import.of(OptionalUtil.class));
                str.append("OptionalUtil.unwrap(");
            }

            str.append("get").append(getSupport().typeName(c)).append("()");

            if (usesOptional(c)) str.append(')');
            str.append(");");

            method.add(str.toString());
        });

        method.add("return hash;");
        return method;
    }

    @Override
    protected String getJavadocRepresentText() {
        return "The generated base implementation of the {@link " + 
            getSupport().entityType().getTypeName() + "}-interface.";
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