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

import com.speedment.common.codegen.constant.DefaultAnnotationUsage;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Constructor;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.Method;
import com.speedment.generator.internal.EntityAndManagerTranslator;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.internal.entity.AbstractEntity;

import java.util.Objects;
import java.util.StringJoiner;

import static com.speedment.common.codegen.constant.DefaultAnnotationUsage.OVERRIDE;
import com.speedment.common.codegen.constant.DefaultType;
import com.speedment.runtime.util.OptionalUtil;
import java.util.Optional;
import static com.speedment.common.codegen.internal.util.Formatting.nl;
import static com.speedment.common.codegen.internal.util.Formatting.tab;
import com.speedment.common.codegen.constant.SimpleParameterizedType;
import static com.speedment.common.codegen.internal.util.Formatting.block;
import com.speedment.generator.TranslatorSupport;
import com.speedment.generator.component.TypeMapperComponent;
import static com.speedment.generator.internal.entity.GeneratedEntityTranslator.getterReturnType;
import static com.speedment.generator.internal.util.ColumnUtil.optionalGetterName;
import static com.speedment.generator.internal.util.ColumnUtil.usesOptional;
import com.speedment.generator.internal.util.EntityTranslatorSupport;
import com.speedment.generator.internal.util.FkHolder;
import com.speedment.internal.common.injector.Injector;
import com.speedment.internal.common.injector.annotation.Inject;
import com.speedment.runtime.manager.Manager;
import java.lang.reflect.Type;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author Emil Forslund
 * @author Per-Åke Minborg
 */
public final class GeneratedEntityImplTranslator extends EntityAndManagerTranslator<Class> {

    private @Inject TypeMapperComponent typeMappers;
    private @Inject Injector injector;
    
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
                final Type retType = getterReturnType(typeMappers, col);
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
             * Finders
             */
            .forEveryColumn((clazz, col) -> {
                EntityTranslatorSupport.getForeignKey(
                    getSupport().tableOrThrow(), col
                ).ifPresent(fkc -> {
                    final FkHolder fu = new FkHolder(injector, fkc.getParentOrThrow());
                    final TranslatorSupport<Table> fuSupport = fu.getForeignEmt().getSupport();
                    
                    file.add(Import.of(fuSupport.entityType()));

                    final String isPresentName = usesOptional(col) ? ".isPresent()" : " != null";
                    final String getterName = optionalGetterName(typeMappers, col).orElse("");
                    
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
                                    "return foreignManager.findAny(" + fuSupport.entityName() + 
                                    "." + fuSupport.namer().javaStaticFieldName(fu.getForeignColumn().getJavaName()) + 
                                    ", " + GETTER_METHOD_PREFIX + getSupport().namer().javaTypeName(col.getJavaName()) + 
                                    "()" + getterName + ");"
                                ) + " else " + block(
                                    "return Optional.empty();"
                                )
                                :
                                "return foreignManager.findAny(" + fuSupport.entityName() + 
                                    "." + fuSupport.namer().javaStaticFieldName(fu.getForeignColumn().getJavaName()) + 
                                    ", " + GETTER_METHOD_PREFIX + getSupport().namer().javaTypeName(col.getJavaName()) + 
                                    "()" + getterName + ").orElse(null);"
                        )
                    );
                });
            })
            
            /**
             * Class details
             */
            // We need to make it POST_MAKE because other plugins might add fields
            .forEveryTable(Phase.POST_MAKE, (clazz, table) -> {
                clazz
                    .add(copyMethod(file))
                    .add(toStringMethod(file))
                    .add(equalsMethod())
                    .add(hashCodeMethod())
                    .add(Method.of("entityClass", SimpleParameterizedType.create(java.lang.Class.class, getSupport().entityType())).public_().add(OVERRIDE)
                        .add("return " + getSupport().entityName() + ".class;")
                    );
            })
            .build()
            .public_()
            .abstract_()
            .setSupertype(SimpleParameterizedType.create(AbstractEntity.class, getSupport().entityType()))
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
            m.add("sj.add(\"" + getSupport().variableName(col) + " = \" + Objects.toString(" + getter + "));");
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
            final Type type = typeMappers.get(c).getJavaType(c);
            
            if (DefaultType.isPrimitive(type)) {
                method.add("if (this." + getter + "() != " + thatCastedName + "." + getter + "()) {return false; }");
            } else {
                method.add("if (!Objects.equals(this." + getter + "(), " + thatCastedName + "." + getter + "())) {return false; }");
            }
        });

        method.add("return true;");
        return method;
    }

    private Method hashCodeMethod() {
        final Method method = Method.of("hashCode", int.class)
            .public_()
            .add(OVERRIDE)
            .add("int hash = 7;");

        columns().forEachOrdered(c -> {

            final StringBuilder str = new StringBuilder();
            str.append("hash = 31 * hash + ");
            final Type type = typeMappers.get(c).getJavaType(c);
            
            if (DefaultType.isPrimitive(type)) {
                str.append(DefaultType.wrapperFor(type).getSimpleName());
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