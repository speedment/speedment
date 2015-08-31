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
package com.speedment.internal.core.code.entity;

import com.speedment.internal.core.code.EntityAndManagerTranslator;
import com.speedment.internal.codegen.base.Generator;
import com.speedment.internal.codegen.lang.models.Method;
import com.speedment.internal.codegen.lang.models.Type;
import com.speedment.internal.codegen.lang.models.Class;
import com.speedment.internal.codegen.lang.models.Constructor;
import com.speedment.internal.codegen.lang.models.Field;
import com.speedment.internal.codegen.lang.models.File;
import com.speedment.internal.codegen.lang.models.Generic;
import com.speedment.internal.codegen.lang.models.Import;
import static com.speedment.internal.codegen.lang.models.constants.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.internal.codegen.lang.models.constants.DefaultType.BOOLEAN_PRIMITIVE;
import static com.speedment.internal.codegen.lang.models.constants.DefaultType.INT_PRIMITIVE;
import static com.speedment.internal.codegen.lang.models.constants.DefaultType.OBJECT;
import static com.speedment.internal.codegen.lang.models.constants.DefaultType.OPTIONAL;
import static com.speedment.internal.codegen.lang.models.constants.DefaultType.STRING;
import static com.speedment.internal.codegen.util.Formatting.indent;
import com.speedment.config.Table;
import com.speedment.internal.core.code.AbstractBaseEntity;
import com.speedment.exception.SpeedmentException;
import com.speedment.Speedment;
import com.speedment.internal.util.JavaLanguage;
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
 * @author pemi
 */
public final class EntityImplTranslator extends EntityAndManagerTranslator<Class> {

    public static final String SPEEDMENT_NAME = "speedment";
    private static final String MANAGER_METHOD = "manager_";
    private static final String MANAGER_OF_METHOD = "managerOf_";

    public EntityImplTranslator(Speedment speedment, Generator cg, Table configEntity) {
        super(speedment, cg, configEntity);
    }

    @Override
    protected Class make(File file) {
        requireNonNull(file);
        final Map<Table, List<String>> fkStreamers = new HashMap<>();

        final Class newClass = new ClassBuilder(ENTITY.getImplName())
            // Getters
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
                        .add("return " + getter + ";"));

            })
            // Setters
            .addColumnConsumer((cl, c) -> {
                cl
                    .add(Method.of(BUILDER_METHOD_PREFIX + typeName(c), ENTITY.getImplType())
                        .public_().final_()
                        .add(OVERRIDE)
                        .add(fieldFor(c))
                        .add("this." + variableName(c) + " = " + variableName(c) + ";")
                        .add("return this;"));
            })
            // Add streamers from back pointing FK:s
            .addForeignKeyReferencesThisTableConsumer((i, fk) -> {
                final FkHolder fu = new FkHolder(getSpeedment(), getCodeGenerator(), fk);
//999                file.add(Import.of(fu.getForeignEmt().ENTITY.getType()));
                fu.imports().forEachOrdered(file::add);
                final String methodName = EntityTranslatorSupport.FIND + EntityTranslatorSupport.pluralis(fu.getTable()) + "By" + typeName(fu.getColumn());
                // Record for later use in the construction of aggregate streamers
                fkStreamers.computeIfAbsent(fu.getTable(), t -> new ArrayList<>()).add(methodName);
                final Type returnType = Type.of(Stream.class).add(fu.getEmt().GENERIC_OF_ENTITY);
                final Method method = Method.of(methodName, returnType).public_().add(OVERRIDE)
                    //.add("return " + managerTypeName(fu.getTable()) + ".get()")
                    .add("return " + MANAGER_OF_METHOD + "(" + typeName(fu.getTable()) + ".class)")
                    //.add("        .stream().filter(" + variableName(fu.getTable()) + " -> Objects.equals(this." + GETTER_METHOD_PREFIX + typeName(fu.getForeignColumn()) + "(), " + variableName(fu.getTable()) + "." + GETTER_METHOD_PREFIX + typeName(fu.getColumn()) + "()));");
                    .add("        .stream().filter(" + typeName(fu.getTable()) + "." + JavaLanguage.javaStaticFieldName(fu.getColumn().getName()) + ".equal(this." + GETTER_METHOD_PREFIX + typeName(fu.getForeignColumn()) + "()));");
                i.add(method);
            })
            .addForeignKeyConsumer((i, fk) -> {
                final FkHolder fu = new FkHolder(getSpeedment(), getCodeGenerator(), fk);
                fu.imports().forEachOrdered(file::add);

                final Type returnType;
                if (fu.getColumn().isNullable()) {
                    file.add(Import.of(OPTIONAL));
                    returnType = OPTIONAL.add(fu.getForeignEmt().GENERIC_OF_ENTITY);

                } else {
                    returnType = fu.getForeignEmt().ENTITY.getType();
                }

                final Method method = Method.of("find" + typeName(fu.getColumn()), returnType).public_().add(OVERRIDE);
                if (fu.getColumn().isNullable()) {
                    final String varName = variableName(fu.getForeignTable());
                    method.add("return get" + typeName(fu.getColumn()) + "()")
                        .add(indent(
                            //".flatMap(" + varName + " -> " + fu.getForeignEmt().MANAGER.getName() + ".get().stream()\n" + indent(
                            ".flatMap(" + varName + " -> " + MANAGER_OF_METHOD + "(" + fu.getForeignEmt().typeName() + ".class).stream()\n" + indent(
                                ".filter(" + typeName(fu.getForeignTable()) + "." + JavaLanguage.javaStaticFieldName(fu.getForeignColumn().getName()) + ".equal(" + varName + "))\n"
                                + ".findAny()"
                            ) + "\n);"
                        ));
                } else {
                    file.add(Import.of(Type.of(SpeedmentException.class)));
                    //method.add("return " + fu.getForeignEmt().MANAGER.getName() + ".get().stream()\n" + indent(
                    method.add("return " + MANAGER_OF_METHOD + "(" + fu.getForeignEmt().typeName() + ".class).stream()\n" + indent(
                        ".filter(" + typeName(fu.getForeignTable()) + "." + JavaLanguage.javaStaticFieldName(fu.getForeignColumn().getName()) + ".equal(get" + typeName(fu.getColumn()) + "()))\n"
                        + ".findAny().orElseThrow(() -> new SpeedmentException(\n" + indent(
                            "\"Foreign key constraint error. " + typeName(fu.getForeignTable()) + " is set to \" + get" + typeName(fu.getColumn()) + "()\n"
                        ) + "));\n"
                    ));
                }
                i.add(method);
            })
            .build()
            .public_()
            .final_()
            .setSupertype(Type.of(AbstractBaseEntity.class).add(Generic.of().add(ENTITY.getType())))
            .add(ENTITY.getType())
            .add(Constructor.of().add(Field.of(SPEEDMENT_NAME, Type.of(Speedment.class)))
                .add("super(" + SPEEDMENT_NAME + ");")
            )
            .add(copyConstructor(ENTITY.getType(), CopyConstructorMode.BUILDER)) //            .add(Constructor.of().
            ;

        // Create aggregate streaming functions, if any
        fkStreamers.keySet().stream().forEach((referencingTable) -> {
            final List<String> methodNames = fkStreamers.get(referencingTable);
            if (!methodNames.isEmpty()) {
                final Method method = Method.of(
                    EntityTranslatorSupport.FIND + EntityTranslatorSupport.pluralis(referencingTable),
                    Type.of(Stream.class).add(Generic.of().setLowerBound(typeName(referencingTable)))
                ).public_().add(OVERRIDE);

                if (methodNames.size() == 1) {
                    method.add("return " + methodNames.get(0) + "();");
                } else {
                    file.add(Import.of(Type.of(Function.class)));
                    method.add("return Stream.of("
                        + methodNames.stream().map(n -> n + "()").collect(Collectors.joining(", "))
                        + ").flatMap(Function.identity()).distinct();");
                }
                newClass.add(method);
            }
        });

        newClass
            .add(copy())
            .add(toString(file))
            .add(equalsMethod())
            .add(hashCodeMethod())
            .add(Method.of("getEntityClass_", Type.of(java.lang.Class.class).add(Generic.of().add(ENTITY.getType()))).public_().add(OVERRIDE)
                .add("return " + ENTITY.getName() + ".class;")
            );

        return newClass;

    }

    private Method copy() {
        return Method.of("copy", ENTITY.getType()).public_().add(OVERRIDE)
            .add("return new " + ENTITY.getImplName() + "(getSpeedment_(), this);");

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
