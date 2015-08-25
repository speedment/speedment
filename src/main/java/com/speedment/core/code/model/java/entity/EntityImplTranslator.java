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
import com.speedment.codegen.lang.models.Constructor;
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
import com.speedment.codegen.lang.models.implementation.GenericImpl;
import static com.speedment.codegen.util.Formatting.indent;
import com.speedment.core.config.model.Table;
import com.speedment.core.exception.SpeedmentException;
import com.speedment.core.manager.Manager;
import com.speedment.core.platform.Speedment;
import com.speedment.core.platform.component.ManagerComponent;
import com.speedment.util.java.JavaLanguage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class EntityImplTranslator extends BaseEntityAndManagerTranslator<Class> {

    private static final String SPEEDMENT_INSTANCE_NAME = "speedmentInstance_";
    private static final String MANAGER_METHOD = "manager_";
    private static final String MANAGER_OF_METHOD = "managerOf_";

    public EntityImplTranslator(Generator cg, Table configEntity) {
        super(cg, configEntity);
    }

    @Override
    protected Class make(File file) {
        final Map<Table, List<String>> fkStreamers = new HashMap<>();

        final Class newClass = new ClassBuilder(ENTITY.getImplName())
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
            // Add streamers from back pointing FK:s
            .addForeignKeyReferencesThisTableConsumer((i, fk) -> {
                final FkHolder fu = new FkHolder(getCodeGenerator(), fk);
                fu.imports().forEachOrdered(file::add);
                final String methodName = EntityTranslatorSupport.pluralis(fu.getTable()) + "By" + typeName(fu.getColumn());
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
                final FkHolder fu = new FkHolder(getCodeGenerator(), fk);
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
                    method.add("return " +  MANAGER_OF_METHOD + "(" + fu.getForeignEmt().typeName() + ".class).stream()\n" + indent(
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
            .add(ENTITY.getType())
            .add(Field.of(SPEEDMENT_INSTANCE_NAME, Type.of(Speedment.class)).private_().final_())
            .add(Constructor.of().add(Field.of(SPEEDMENT_INSTANCE_NAME, Type.of(Speedment.class)))
                .add("this." + SPEEDMENT_INSTANCE_NAME + " = " + SPEEDMENT_INSTANCE_NAME + ";")
            )
            .add(EntityTranslatorSupport.toJson().public_().add(OVERRIDE).add("return manager_().toJson(this);"))
            //.add(EntityTranslatorSupport.toJsonExtended(ENTITY.getType()).public_().add(OVERRIDE))
            //.add(stream())
            .add(manager())
            .add(managerOf(file))
            .add(persist())
            .add(update())
            .add(remove())
            .add(persistWithListener())
            .add(updateWithListener())
            .add(removeWithListener())
            .add(toString(file))
            .add(equalsMethod())
            .add(hashCodeMethod());

        // Create aggregate streaming functions, if any
        fkStreamers.keySet().stream().forEach((referencingTable) -> {
            final List<String> methodNames = fkStreamers.get(referencingTable);
            if (!methodNames.isEmpty()) {
                final Method method = Method.of(
                    EntityTranslatorSupport.pluralis(referencingTable), 
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

        return newClass;

    }

    private Method manager() {
        //file.add(Import.of(Type.of(ManagerComponent.class)));
        return Method.of(MANAGER_METHOD, Type.of(Manager.class).add(Generic.of().add(ENTITY.getType()))).private_()
            .add("return " + MANAGER_OF_METHOD + "(" + ENTITY.getName() + ".class);");
//            .add("return " + SPEEDMENT_INSTANCE_NAME + ".get(ManagerComponent.class).managerOf(" + ENTITY.getName() + ".class);");
    }

    private Method managerOf(File file) {
        file.add(Import.of(Type.of(ManagerComponent.class)));
        final Generic genericTypeT = Generic.of().add(Type.of("T"));
        final String parameterName = "entityClass";
        return Method.of(MANAGER_OF_METHOD, Type.of(Manager.class).add(genericTypeT))
            .add(genericTypeT)
            .add(Field.of(parameterName, Type.of(java.lang.Class.class).add(genericTypeT)))
            .private_()
            .add("return " + SPEEDMENT_INSTANCE_NAME + ".get(ManagerComponent.class).managerOf(" + parameterName + ");");
    }

    private Method persist() {
        return EntityTranslatorSupport.persist().public_().add(OVERRIDE)
            .add("manager_().persist(this);");

    }

    private Method update() {
        return EntityTranslatorSupport.update().public_().add(OVERRIDE)
            .add("manager_().update(this);");
    }

    private Method remove() {
        return EntityTranslatorSupport.remove().public_().add(OVERRIDE)
            .add("manager_().remove(this);");
    }

    private Method persistWithListener() {
        return EntityTranslatorSupport.persistWithListener(ENTITY.getType()).public_().add(OVERRIDE)
            .add("manager_().persist(this, " + EntityTranslatorSupport.CONSUMER_NAME + ");");
    }

    private Method updateWithListener() {
        return EntityTranslatorSupport.updateWithListener(ENTITY.getType()).public_().add(OVERRIDE)
            .add("manager_().update(this, " + EntityTranslatorSupport.CONSUMER_NAME + ");");
    }

    private Method removeWithListener() {
        return EntityTranslatorSupport.removeWithListener(ENTITY.getType()).public_().add(OVERRIDE)
            .add("manager_().remove(this, " + EntityTranslatorSupport.CONSUMER_NAME + ");");
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
