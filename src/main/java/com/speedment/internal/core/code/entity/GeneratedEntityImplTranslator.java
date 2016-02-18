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
package com.speedment.internal.core.code.entity;

import com.speedment.Speedment;
import com.speedment.config.db.Table;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.codegen.base.Generator;
import com.speedment.internal.codegen.lang.models.Class;
import com.speedment.internal.codegen.lang.models.Constructor;
import com.speedment.internal.codegen.lang.models.Field;
import com.speedment.internal.codegen.lang.models.File;
import com.speedment.internal.codegen.lang.models.Generic;
import com.speedment.internal.codegen.lang.models.Import;
import com.speedment.internal.codegen.lang.models.Method;
import com.speedment.internal.codegen.lang.models.Type;
import static com.speedment.internal.codegen.lang.models.constants.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.internal.codegen.lang.models.constants.DefaultType.BOOLEAN_PRIMITIVE;
import static com.speedment.internal.codegen.lang.models.constants.DefaultType.INT_PRIMITIVE;
import static com.speedment.internal.codegen.lang.models.constants.DefaultType.OBJECT;
import static com.speedment.internal.codegen.lang.models.constants.DefaultType.OPTIONAL;
import static com.speedment.internal.codegen.lang.models.constants.DefaultType.STRING;
import com.speedment.internal.core.code.AbstractBaseEntity;
import static com.speedment.internal.core.code.DefaultJavaClassTranslator.SETTER_METHOD_PREFIX;
import static com.speedment.internal.core.code.DefaultJavaClassTranslator.GETTER_METHOD_PREFIX;
import com.speedment.internal.core.code.EntityAndManagerTranslator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static com.speedment.internal.codegen.util.Formatting.indent;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 * @author Per-Åke Minborg
 */
public final class GeneratedEntityImplTranslator extends EntityAndManagerTranslator<Class> {
    
    private static final String 
        MANAGER_OF_METHOD = "managerOf_";

    public GeneratedEntityImplTranslator(Speedment speedment, Generator gen, Table table) {
        super(speedment, gen, table, Class::of);
    }
    
    @Override
    protected Class make(File file) {
        requireNonNull(file);
        
        final Map<Table, List<String>> fkStreamers = new HashMap<>();
        final Class newClass = newBuilder(file, entity.getGeneratedImplName())
            /*** Getters ***/
            .forEveryColumn((clazz, col) -> {
                final Type retType;
                final String getter;
                if (col.isNullable()) {
                    retType = OPTIONAL.add(Generic.of().add(Type.of(col.findTypeMapper().getJavaType())));
                    getter = "Optional.ofNullable(" + variableName(col) + ")";
                } else {
                    retType = Type.of(col.findTypeMapper().getJavaType());
                    getter = variableName(col);
                }
                clazz
                    .add(fieldFor(col).private_())
                    .add(Method.of(GETTER_METHOD_PREFIX + typeName(col), retType)
                        .public_()
                        .add(OVERRIDE)
                        .add("return " + getter + ";"));

            })
            /*** Setters ***/
            .forEveryColumn((clazz, col) -> {
                clazz
                    .add(Method.of(SETTER_METHOD_PREFIX + typeName(col), entity.getType())
                        .public_().final_()
                        .add(OVERRIDE)
                        .add(fieldFor(col))
                        .add("this." + variableName(col) + " = " + variableName(col) + ";")
                        .add("return this;"));
            })
            
            /*** Add streamers from back pointing foreign keys ***/
            .forEveryForeignKeyReferencingThis((clazz, fk) -> {
                final FkHolder fu = new FkHolder(getSpeedment(), getCodeGenerator(), fk);
                fu.imports().forEachOrdered(file::add);
                final String methodName = EntityTranslatorSupport.FIND
                    + EntityTranslatorSupport.pluralis(fu.getTable(), javaLanguageNamer())
                    + "By" + typeName(fu.getColumn());
                fkStreamers.computeIfAbsent(fu.getTable(), t -> new ArrayList<>()).add(methodName);
                final Type returnType = Type.of(Stream.class).add(fu.getEmt().genericOfEntity());
                final Method method = Method.of(methodName, returnType).public_().add(OVERRIDE)
                    .add("return " + MANAGER_OF_METHOD + "(" + typeName(fu.getTable()) + ".class)")
                    .add("        .stream().filter(" + typeName(fu.getTable()) + "." + javaLanguageNamer().javaStaticFieldName(fu.getColumn().getJavaName()) + ".equal(this." + GETTER_METHOD_PREFIX + typeName(fu.getForeignColumn()) + "()));");
                clazz.add(method);
            })
            
            /*** Add getter for ordinary foreign keys ***/
            .forEveryForeignKey((clazz, fk) -> {
                final FkHolder fu = new FkHolder(getSpeedment(), getCodeGenerator(), fk);
                fu.imports().forEachOrdered(file::add);

                final Type returnType;
                if (fu.getColumn().isNullable()) {
                    file.add(Import.of(OPTIONAL));
                    returnType = OPTIONAL.add(fu.getForeignEmt().genericOfEntity());

                } else {
                    returnType = fu.getForeignEmt().entity().getType();
                }

                final Method method = Method.of("find" + typeName(fu.getColumn()), returnType).public_().add(OVERRIDE);
                if (fu.getColumn().isNullable()) {
                    final String varName = variableName(fu.getColumn()) + "_";
                    method.add("return get" + typeName(fu.getColumn()) + "()")
                        .add(indent(
                            ".flatMap(" + varName + " -> " + MANAGER_OF_METHOD + "(" + fu.getForeignEmt().typeName() + ".class).findAny("
                            + typeName(fu.getForeignTable()) + "." + javaLanguageNamer().javaStaticFieldName(fu.getForeignColumn().getJavaName()) + ", " + varName + "));"
                        ));
                } else {
                    file.add(Import.of(Type.of(SpeedmentException.class)));
                    method.add("return " + MANAGER_OF_METHOD + "(" + fu.getForeignEmt().typeName() + ".class).findAny("
                        + typeName(fu.getForeignTable()) + "." + javaLanguageNamer().javaStaticFieldName(fu.getForeignColumn().getJavaName()) + ", get" + typeName(fu.getColumn()) + "())\n"
                        + indent(".orElseThrow(() -> new SpeedmentException(\n" + 
                            indent(
                                "\"Foreign key constraint error. " + 
                                typeName(fu.getForeignTable()) + 
                                " is set to \" + get" + 
                                typeName(fu.getColumn()) + "()\n"
                            ) + "));\n"
                        )
                    );
                }
                clazz.add(method);
            })
            
            /*** Class details **/
            .build()
            .public_()
            .abstract_()
            .setSupertype(Type.of(AbstractBaseEntity.class).add(Generic.of().add(entity.getType())))
            .add(entity.getType())
            .add(Constructor.of().protected_())
            .add(copyConstructor(entity.getType(), CopyConstructorMode.SETTER));

        /*** Create aggregate streaming functions, if any ***/
        fkStreamers.keySet().stream().forEach((referencingTable) -> {
            final List<String> methodNames = fkStreamers.get(referencingTable);
            if (!methodNames.isEmpty()) {
                final Method method = Method.of(
                    EntityTranslatorSupport.FIND + EntityTranslatorSupport.pluralis(referencingTable, javaLanguageNamer()),
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
        
        file.add(Import.of(Type.of(Speedment.class)));

        newClass
            .add(copy())
            .add(toString(file))
            .add(equalsMethod())
            .add(hashCodeMethod())
            .add(Method.of("entityClass", Type.of(java.lang.Class.class).add(Generic.of().add(entity.getType()))).public_().add(OVERRIDE)
                .add("return " + entity.getName() + ".class;")
            );

        return newClass;

    }

    private Method copy() {
        return Method.of("copy", entity.getType()).public_().add(OVERRIDE)
            .add(
                "final " + Speedment.class.getSimpleName() + " speedment = speedment();",
                "return new " + entity.getGeneratedImplName() + "(this) {", indent(
                    "@Override",
                    "protected final " + Speedment.class.getSimpleName() + " speedment() {", indent(
                        "return speedment;"
                    ), "}"
                ), "};"
            );

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

        m.add("return \"" + entity.getImplName() + " \"+sj.toString();");

        return m;

    }

    private Method equalsMethod() {

        final String thatName = "that";
        final String thatCastedName = thatName + entity.getName();
        final Method method = Method.of("equals", BOOLEAN_PRIMITIVE)
            .public_()
            .add(OVERRIDE)
            .add(Field.of(thatName, OBJECT))
            .add("if (this == that) { return true; }")
            .add("if (!(" + thatName + " instanceof " + entity.getName() + ")) { return false; }")
            .add("final " + entity.getName() + " " + thatCastedName + " = (" + entity.getName() + ")" + thatName + ";");

        columns().forEachOrdered(c -> {
            final String getter = "get" + typeName(c);
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
                case "byte":    str.append("Byte");      break;
                case "short":   str.append("Short");     break;
                case "int":     str.append("Integer");   break;
                case "long":    str.append("Long");      break;
                case "float":   str.append("Float");     break;
                case "double":  str.append("Double");    break;
                case "boolean": str.append("Boolean");   break;
                case "char":    str.append("Character"); break;
                default:        str.append("Objects");   break;
            }

            str.append(".hashCode(get").append(typeName(c)).append("());");
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
    protected String getFileName() {
        return entity.getGeneratedImplName();
    }

    @Override
    protected boolean isInGeneratedPackage() {
        return true;
    }
}