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
package com.speedment.generator.standard.entity;

import com.speedment.common.codegen.constant.DefaultJavadocTag;
import com.speedment.common.codegen.constant.DefaultType;
import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.common.codegen.model.Constructor;
import com.speedment.common.codegen.model.Enum;
import com.speedment.common.codegen.model.EnumConstant;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.Interface;
import com.speedment.common.codegen.model.Javadoc;
import com.speedment.common.codegen.model.Method;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.generator.standard.internal.util.EntityTranslatorSupport;
import com.speedment.generator.standard.internal.util.FkHolder;
import com.speedment.generator.translator.AbstractEntityAndManagerTranslator;
import com.speedment.generator.translator.TranslatorSupport;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.typemapper.TypeMapper;
import com.speedment.runtime.typemapper.primitive.PrimitiveTypeMapper;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.common.function.OptionalBoolean;
import com.speedment.runtime.core.util.OptionalUtil;

import java.lang.reflect.Type;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

import static com.speedment.common.codegen.constant.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.common.codegen.constant.DefaultJavadocTag.PARAM;
import static com.speedment.common.codegen.constant.DefaultJavadocTag.RETURN;
import static com.speedment.common.codegen.util.Formatting.shortName;
import com.speedment.common.codegen.model.Value;
import static com.speedment.generator.standard.internal.util.ColumnUtil.usesOptional;
import com.speedment.generator.translator.component.TypeMapperComponent;
import com.speedment.runtime.config.util.DocumentDbUtil;
import static com.speedment.runtime.config.util.DocumentUtil.Name.DATABASE_NAME;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.config.identifier.TableIdentifier;
import static com.speedment.runtime.config.util.DocumentUtil.relativeName;

/**
 *
 * @author Emil Forslund
 * @author Per-Åke Minborg
 */
public final class GeneratedEntityTranslator extends AbstractEntityAndManagerTranslator<Interface> {

    public final static String IDENTIFIER_NAME = "Identifier";

    private @Inject Injector injector;
    private @Inject TypeMapperComponent typeMappers;

    public GeneratedEntityTranslator(Table table) {
        super(table, Interface::of);
    }

    @Override
    protected Interface makeCodeGenModel(File file) {

        final Type tableIdentifierType = SimpleParameterizedType.create(TableIdentifier.class, getSupport().entityType());

        final Enum identifierEnum = Enum.of(IDENTIFIER_NAME)
            .add(Field.of("columnName", String.class).private_().final_())
            .add(Field.of("tableIdentifier", tableIdentifierType).private_().final_())
            .add(SimpleParameterizedType.create(ColumnIdentifier.class, getSupport().entityType()))
            .add(Constructor.of()
                .add(Field.of("columnName", String.class))
                .add("this.columnName = columnName;")
                .add("this.tableIdentifier = TableIdentifier.of(getDbmsName(), getSchemaName(), getTableName());")
            )
            .add(Method.of("getDbmsName", String.class).public_()
                .add(OVERRIDE)
                .add("return \"" + getSupport().dbmsOrThrow().getName() + "\";")
            )
            .add(Method.of("getSchemaName", String.class).public_()
                .add(OVERRIDE)
                .add("return \"" + getSupport().schemaOrThrow().getName() + "\";")
            )
            .add(Method.of("getTableName", String.class).public_()
                .add(OVERRIDE)
                .add("return \"" + getSupport().tableOrThrow().getName() + "\";")
            )
            .add(Method.of("getColumnName", String.class).public_()
                .add(OVERRIDE)
                .add("return this.columnName;")
            )
            .add(Method.of("asTableIdentifier", tableIdentifierType).public_()
                .add(OVERRIDE)
                .add("return this.tableIdentifier;")
            );

        return newBuilder(file, getSupport().generatedEntityName())
            /**
             * General
             */
            .forEveryTable((intrf, col) -> intrf.public_().add(identifierEnum))
            
            /**
             * Getters
             */
            .forEveryColumn((intrf, col) -> {
                final Type retType = getterReturnType(typeMappers, col);

                intrf.add(Method.of(GETTER_METHOD_PREFIX + getSupport().typeName(col), retType)
                    .set(Javadoc.of(
                        "Returns the " + getSupport().variableName(col)
                        + " of this " + getSupport().entityName()
                        + ". The " + getSupport().variableName(col)
                        + " field corresponds to the database column "
                        + relativeName(col, Dbms.class, DATABASE_NAME) + "."
                    ).add(RETURN.setText(
                        "the " + getSupport().variableName(col)
                        + " of this " + getSupport().entityName()
                    ))
                    )
                );
            })
            
            /**
             * Setters
             */
            .forEveryColumn((intrf, col) -> {
                intrf.add(Method.of(SETTER_METHOD_PREFIX + getSupport().typeName(col), getSupport().entityType())
                    .add(Field.of(getSupport().variableName(col), typeMappers.get(col).getJavaType(col)))
                    .set(Javadoc.of(
                        "Sets the " + getSupport().variableName(col)
                        + " of this " + getSupport().entityName()
                        + ". The " + getSupport().variableName(col)
                        + " field corresponds to the database column "
                        + relativeName(col, Dbms.class, DATABASE_NAME) + "."
                    )
                        .add(PARAM.setValue(getSupport().variableName(col)).setText("to set of this " + getSupport().entityName()))
                        .add(RETURN.setText("this " + getSupport().entityName() + " instance")))
                );
            })
            
            /**
             * Finders
             */
            .forEveryColumn((intrf, col) -> {
                EntityTranslatorSupport.getForeignKey(
                    getSupport().tableOrThrow(), col
                ).ifPresent(fkc -> {
                    final FkHolder fu = new FkHolder(injector, fkc.getParentOrThrow());
                    final TranslatorSupport<Table> fuSupport = fu.getForeignEmt().getSupport();

                    file.add(Import.of(fuSupport.entityType()));

                    intrf.add(Method.of(FINDER_METHOD_PREFIX + getSupport().typeName(col),
                        col.isNullable()
                            ? DefaultType.optional(fuSupport.entityType())
                            : fuSupport.entityType()
                    )
                        .set(Javadoc.of(
                            "Queries the specified manager for the referenced "
                            + fuSupport.entityName() + ". If no such "
                            + fuSupport.entityName()
                            + " exists, an {@code NullPointerException} will be thrown."
                        ).add(DefaultJavadocTag.PARAM.setValue("foreignManager").setText("the manager to query for the entity"))
                            .add(DefaultJavadocTag.RETURN.setText("the foreign entity referenced"))
                        )
                        .add(Field.of("foreignManager", SimpleParameterizedType.create(
                            Manager.class, fuSupport.entityType()
                        )))
                    );
                });
            })
            
            /**
             * Fields
             */
            .forEveryColumn((intrf, col) -> {

                final EntityTranslatorSupport.ReferenceFieldType ref
                    = EntityTranslatorSupport.getReferenceFieldType(
                        file, getSupport().tableOrThrow(), col, getSupport().entityType(), injector
                    );

                final Type entityType = getSupport().entityType();
                final String shortEntityName = getSupport().entityName();

                file.add(Import.of(entityType));

                final String getter;
                if (usesOptional(col)) {
                    getter = "o -> OptionalUtil.unwrap(o." + GETTER_METHOD_PREFIX + getSupport().typeName(col) + "())";
                    file.add(Import.of(OptionalUtil.class));
                } else {
                    getter = shortEntityName + "::get" + getSupport().typeName(col);
                }

                final String referenced = EntityTranslatorSupport.getForeignKey(getSupport().tableOrThrow(), col)
                    .map(fkc -> {
                        final FkHolder fu = new FkHolder(injector, fkc.getParentOrThrow());
                        final TranslatorSupport<Table> fuSupport = fu.getForeignEmt().getSupport();

                        return ", " + fuSupport.entityName() + "."
                            + fuSupport.namer().javaStaticFieldName(
                                fu.getForeignColumn().getJavaName()
                            );
                    }).orElse("");

                final String setter = ", " + shortEntityName + "::" + SETTER_METHOD_PREFIX + getSupport().typeName(col);

                final String constant = getSupport().namer().javaStaticFieldName(col.getJavaName());
                identifierEnum.add(EnumConstant.of(constant).add(Value.ofText(col.getName())));

                final String typeMapperCode;
                if (col.getTypeMapper().isPresent()) {
                    final String typeMapper = col.getTypeMapper().get();

                    if (PrimitiveTypeMapper.class.getName().equals(typeMapper)) {
                        file.add(Import.of(TypeMapper.class));
                        typeMapperCode = "TypeMapper.primitive()";
                    } else {
                        file.add(Import.of(SimpleType.create(typeMapper)));
                        typeMapperCode = "new " + shortName(typeMapper) + "()";
                    }
                } else {
                    typeMapperCode = "TypeMapper.identity()";
                    file.add(Import.of(TypeMapper.class));
                }

                intrf.add(Field.of(getSupport().namer().javaStaticFieldName(col.getJavaName()), ref.type)
                    .final_()
                    .set(Value.ofReference(
                        shortName(ref.type.getTypeName())
                        + ".create(Identifier."
                        + constant
                        + ", "
                        + getter
                        + setter
                        + referenced
                        + ", "
                        + typeMapperCode
                        + ", "
                        + DocumentDbUtil.isUnique(col)
                        + ")"
                    ))
                    .set(Javadoc.of(
                        "This Field corresponds to the {@link " + shortEntityName + "} field that can be obtained using the "
                        + "{@link " + shortEntityName + "#get" + getSupport().typeName(col) + "()} method."
                    )));
            })
            .build();
    }

    @Override
    protected String getJavadocRepresentText() {
        return "The generated base for the {@link "
            + getSupport().entityType().getTypeName()
            + "}-interface representing entities of the {@code "
            + getDocument().getName() + "}-table in the database.";
    }

    @Override
    protected String getClassOrInterfaceName() {
        return getSupport().generatedEntityName();
    }

    @Override
    public boolean isInGeneratedPackage() {
        return true;
    }

    static Type getterReturnType(TypeMapperComponent typeMappers, Column col) {
        final Type retType;
        final Type type = typeMappers.get(col).getJavaType(col);

        if (usesOptional(col)) {
            if (type.equals(Integer.class)) {
                retType = OptionalInt.class;
            } else if (type.equals(Long.class)) {
                retType = OptionalLong.class;
            } else if (type.equals(Double.class)) {
                retType = OptionalDouble.class;
            } else if (type.equals(Boolean.class)) {
                retType = OptionalBoolean.class;
            } else {
                retType = SimpleParameterizedType.create(Optional.class, type);
            }
        } else {
            retType = type;
        }

        return retType;
    }
}
