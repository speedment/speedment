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

import com.speedment.runtime.Speedment;
import com.speedment.fika.codegen.Generator;
import com.speedment.fika.codegen.model.Class;
import com.speedment.fika.codegen.model.Constructor;
import com.speedment.fika.codegen.model.Field;
import com.speedment.fika.codegen.model.File;
import com.speedment.fika.codegen.model.Generic;
import com.speedment.fika.codegen.model.Import;
import com.speedment.fika.codegen.model.Method;
import com.speedment.fika.codegen.model.Type;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.exception.SpeedmentException;
import static com.speedment.fika.codegen.internal.model.constant.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.fika.codegen.internal.model.constant.DefaultType.BOOLEAN_PRIMITIVE;
import static com.speedment.fika.codegen.internal.model.constant.DefaultType.INT_PRIMITIVE;
import static com.speedment.fika.codegen.internal.model.constant.DefaultType.OBJECT;
import static com.speedment.fika.codegen.internal.model.constant.DefaultType.OPTIONAL;
import static com.speedment.fika.codegen.internal.model.constant.DefaultType.STRING;
import static com.speedment.fika.codegen.internal.util.Formatting.indent;
import static com.speedment.generator.internal.DefaultJavaClassTranslator.GETTER_METHOD_PREFIX;
import static com.speedment.generator.internal.DefaultJavaClassTranslator.SETTER_METHOD_PREFIX;
import com.speedment.generator.internal.EntityAndManagerTranslator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 * @author Per-Åke Minborg
 */
public final class GeneratedEntityImplTranslator extends EntityAndManagerTranslator<Class> {

    private static final String MANAGER_OF_METHOD = "managerOf_";

    public GeneratedEntityImplTranslator(Speedment speedment, Generator gen, Table table) {
        super(speedment, gen, table, Class::of);
    }

    @Override
    protected Class makeCodeGenModel(File file) {
        requireNonNull(file);

        file.add(Import.of(Type.of(Speedment.class)));

        final Map<Table, List<String>> fkStreamers = new HashMap<>();
        return newBuilder(file, getSupport().generatedEntityImplName())
            /**
             * Getters
             */
            .forEveryColumn((clazz, col) -> {
                final Type retType;
                final String getter;
                if (col.isNullable()) {
                    retType = OPTIONAL.add(Generic.of().add(Type.of(col.findTypeMapper().getJavaType())));
                    getter = "Optional.ofNullable(" + getSupport().variableName(col) + ")";
                } else {
                    retType = Type.of(col.findTypeMapper().getJavaType());
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
             * Add streamers from back pointing foreign keys
             */
            .forEveryForeignKeyReferencingThis((clazz, fk) -> {
                final FkHolder fu = new FkHolder(getSpeedment(), getCodeGenerator(), fk);
                final String methodName = EntityTranslatorSupport.FIND
                    + EntityTranslatorSupport.pluralis(fu.getTable(), getNamer())
                    + "By" + getSupport().typeName(fu.getColumn());
                fkStreamers.computeIfAbsent(fu.getTable(), t -> new ArrayList<>()).add(methodName);

                final Type returnType = Type.of(Stream.class).add(Generic.of().add(fu.getEmt().getSupport().entityType()));
                final Method method = Method.of(methodName, returnType).public_().add(OVERRIDE)
                    .add("return " + MANAGER_OF_METHOD + "(" + getSupport().typeName(fu.getTable()) + ".class)")
                    .add("        .stream().filter(" + getSupport().typeName(fu.getTable()) + "." + getNamer().javaStaticFieldName(fu.getColumn().getJavaName()) + ".equal(this." + GETTER_METHOD_PREFIX + getSupport().typeName(fu.getForeignColumn()) + "()));");
                clazz.add(method);
            })
            /**
             * Add getter for ordinary foreign keys
             */
            .forEveryForeignKey((clazz, fk) -> {
                final FkHolder fu = new FkHolder(getSpeedment(), getCodeGenerator(), fk);

                final Type returnType;
                if (fu.getColumn().isNullable()) {
                    file.add(Import.of(OPTIONAL));
                    returnType = OPTIONAL.add(Generic.of().add(fu.getForeignEmt().getSupport().entityType()));
                } else {
                    returnType = fu.getForeignEmt().getSupport().entityType();
                }

                final Method method = Method.of("find" + getSupport().typeName(fu.getColumn()), returnType).public_().add(OVERRIDE);
                if (fu.getColumn().isNullable()) {
                    final String varName = getSupport().variableName(fu.getColumn()) + "_";
                    method.add("return get" + getSupport().typeName(fu.getColumn()) + "()")
                        .add(indent(".flatMap(" + varName + " -> " + MANAGER_OF_METHOD + "(" + fu.getForeignEmt().getSupport().typeName() + ".class).findAny("
                            + getSupport().typeName(fu.getForeignTable()) + "." + getNamer().javaStaticFieldName(fu.getForeignColumn().getJavaName()) + ", " + varName + "));"
                        ));
                } else {
                    file.add(Import.of(Type.of(SpeedmentException.class)));
                    method.add("return " + MANAGER_OF_METHOD + "(" + fu.getForeignEmt().getSupport().typeName() + ".class).findAny("
                        + getSupport().typeName(fu.getForeignTable()) + "." + getNamer().javaStaticFieldName(fu.getForeignColumn().getJavaName()) + ", get" + getSupport().typeName(fu.getColumn()) + "())\n"
                        + indent(".orElseThrow(() -> new SpeedmentException(\n"
                            + indent(
                                "\"Foreign key constraint error. "
                                + getSupport().typeName(fu.getForeignTable())
                                + " is set to \" + get"
                                + getSupport().typeName(fu.getColumn()) + "()\n"
                            ) + "));\n"
                        )
                    );
                }
                clazz.add(method);
            })
            /**
             * Class details
             */
            .forEveryTable(Phase.POST_MAKE, (clazz, table) -> {
                clazz
                    .add(toString(file))
                    .add(equalsMethod())
                    .add(hashCodeMethod())
                    .add(Method.of("entityClass", Type.of(java.lang.Class.class).add(Generic.of().add(getSupport().entityType()))).public_().add(OVERRIDE)
                        .add("return " + getSupport().entityName() + ".class;")
                    );

                /**
                 * Create aggregate streaming functions, if any
                 */
                fkStreamers.keySet().stream().forEach(referencingTable -> {
                    final List<String> methodNames = fkStreamers.get(referencingTable);
                    if (!methodNames.isEmpty()) {
                        final Method method = Method.of(EntityTranslatorSupport.FIND + EntityTranslatorSupport.pluralis(referencingTable, getNamer()),
                            Type.of(Stream.class).add(Generic.of().setLowerBound(getSupport().typeName(referencingTable)))
                        ).public_().add(OVERRIDE);

                        if (methodNames.size() == 1) {
                            method.add("return " + methodNames.get(0) + "();");
                        } else {
                            file.add(Import.of(Type.of(Function.class)));
                            method.add("return Stream.of("
                                + methodNames.stream().map(n -> n + "()").collect(Collectors.joining(", "))
                                + ").flatMap(Function.identity()).distinct();");
                        }
                        clazz.add(method);
                    }
                });
            })
            .build()
            .public_()
            .abstract_()
            .setSupertype(Type.of(AbstractBaseEntity.class).add(Generic.of().add(getSupport().entityType())))
            .add(getSupport().entityType())
            .add(Constructor.of().protected_());

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
                getter = "get" + getSupport().typeName(c) + "()" + ".orElse(null)";
            } else {
                getter = "get" + getSupport().typeName(c) + "()";
            }
            m.add("sj.add(\"" + getSupport().variableName(c) + " = \"+Objects.toString(" + getter + "));");
        });

        m.add("return \"" + getSupport().entityImplName() + " \"+sj.toString();");

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
            if (c.findTypeMapper().getJavaType().isPrimitive()) {
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

            switch (c.findTypeMapper().getJavaType().getName()) {
                case "byte":
                    str.append("Byte");
                    break;
                case "short":
                    str.append("Short");
                    break;
                case "int":
                    str.append("Integer");
                    break;
                case "long":
                    str.append("Long");
                    break;
                case "float":
                    str.append("Float");
                    break;
                case "double":
                    str.append("Double");
                    break;
                case "boolean":
                    str.append("Boolean");
                    break;
                case "char":
                    str.append("Character");
                    break;
                default:
                    str.append("Objects");
                    break;
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
