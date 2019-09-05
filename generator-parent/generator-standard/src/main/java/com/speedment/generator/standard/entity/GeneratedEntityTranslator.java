/*
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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
import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.common.codegen.model.*;
import com.speedment.common.codegen.model.Enum;
import com.speedment.common.function.OptionalBoolean;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.generator.standard.internal.util.EntityTranslatorSupport;
import com.speedment.generator.standard.internal.util.FkHolder;
import com.speedment.generator.translator.AbstractEntityAndManagerTranslator;
import com.speedment.generator.translator.TranslatorSupport;
import com.speedment.generator.translator.component.TypeMapperComponent;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.config.util.DocumentDbUtil;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.core.util.OptionalUtil;
import com.speedment.runtime.typemapper.TypeMapper;
import com.speedment.runtime.typemapper.primitive.PrimitiveTypeMapper;

import java.lang.reflect.Type;
import java.util.*;

import static com.speedment.common.codegen.constant.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.common.codegen.constant.DefaultJavadocTag.PARAM;
import static com.speedment.common.codegen.constant.DefaultJavadocTag.RETURN;
import static com.speedment.common.codegen.constant.DefaultType.*;
import static com.speedment.common.codegen.util.Formatting.indent;
import static com.speedment.common.codegen.util.Formatting.shortName;
import static com.speedment.generator.standard.internal.util.ColumnUtil.usesOptional;
import static com.speedment.runtime.config.util.DocumentUtil.Name.DATABASE_NAME;
import static com.speedment.runtime.config.util.DocumentUtil.relativeName;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 * @author Per-Åke Minborg
 */
public final class GeneratedEntityTranslator extends AbstractEntityAndManagerTranslator<Interface> {

    public static final String IDENTIFIER_NAME = "Identifier";

    @Inject private Injector injector;
    @Inject private TypeMapperComponent typeMappers;

    public GeneratedEntityTranslator(Table table) {
        super(table, Interface::of);
    }

    @Override
    protected Interface makeCodeGenModel(File file) {

        final Type tableIdentifierType = SimpleParameterizedType.create(TableIdentifier.class, getSupport().entityType());

        final Enum identifierEnum = Enum.of(IDENTIFIER_NAME)
            .add(Field.of("columnId", String.class).private_().final_())
            .add(Field.of("tableIdentifier", tableIdentifierType).private_().final_())
            .add(SimpleParameterizedType.create(ColumnIdentifier.class, getSupport().entityType()))
            .add(Constructor.of()
                .add(Field.of("columnId", String.class))
                .add("this.columnId\t = columnId;")
                .add("this.tableIdentifier\t = TableIdentifier.of(" + indent(
                    "getDbmsId(), ",
                    "getSchemaId(), ",
                    "getTableId()"
                ) + ");")
            )
            .add(Method.of("getDbmsId", String.class).public_()
                .add(OVERRIDE)
                .add(returnString(getSupport().dbmsOrThrow().getId()))
            )
            .add(Method.of("getSchemaId", String.class).public_()
                .add(OVERRIDE)
                .add(returnString(getSupport().schemaOrThrow().getId()))
            )
            .add(Method.of("getTableId", String.class).public_()
                .add(OVERRIDE)
                .add(returnString(getSupport().tableOrThrow().getId()))
            )
            .add(Method.of("getColumnId", String.class).public_()
                .add(OVERRIDE)
                .add("return this.columnId;")
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
            .forEveryColumn((intrf, col) -> 
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
                )
            )
            
            /**
             * Finders
             */
            .forEveryColumn((intrf, col) -> 
                EntityTranslatorSupport.getForeignKey(
                    getSupport().tableOrThrow(), col
                ).ifPresent(fkc -> {
                    final FkHolder fu = new FkHolder(injector, fkc.getParentOrThrow());
                    final TranslatorSupport<Table> fuSupport = fu.getForeignEmt().getSupport();

                    file.add(Import.of(fuSupport.entityType()));

                    intrf.add(Method.of(FINDER_METHOD_PREFIX + getSupport().typeName(col),
                        col.isNullable()
                            ? optional(fuSupport.entityType())
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
                })
            )
            
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
                
                final String constant = getSupport().namer().javaStaticFieldName(col.getJavaName());
                identifierEnum.add(EnumConstant.of(constant).add(Value.ofText(col.getId())));

                // Begin building the field value parameters.
                final List<Value<?>> fieldParams = new ArrayList<>();
                fieldParams.add(Value.ofReference("Identifier." + constant));
                
                // Add getter method reference
                if (usesOptional(col)) {
                    fieldParams.add(Value.ofReference(
                        "o -> OptionalUtil.unwrap(o." +
                            GETTER_METHOD_PREFIX +
                            getSupport().typeName(col) + "())"));

                    file.add(Import.of(OptionalUtil.class));
                } else {
                    fieldParams.add(Value.ofReference(
                        shortEntityName + "::get" +
                            getSupport().typeName(col)));
                }
                
                // Add setter method reference
                fieldParams.add(Value.ofReference(
                    shortEntityName + "::" + SETTER_METHOD_PREFIX +
                        getSupport().typeName(col)));

                // Add the foreign key method reference
                EntityTranslatorSupport.getForeignKey(getSupport().tableOrThrow(), col)
                    .ifPresent(fkc -> {
                        final FkHolder fu = new FkHolder(injector, fkc.getParentOrThrow());
                        final TranslatorSupport<Table> fuSupport = fu.getForeignEmt().getSupport();

                        fieldParams.add(Value.ofReference(
                            fuSupport.entityName() + "."
                            + fuSupport.namer().javaStaticFieldName(
                                fu.getForeignColumn().getJavaName()
                            )
                        ));
                    });
                
                // Add type mapper
                if (col.getTypeMapper().isPresent()) {
                    final String typeMapper = col.getTypeMapper().get();

                    if (PrimitiveTypeMapper.class.getName().equals(typeMapper)) {
                        file.add(Import.of(TypeMapper.class));
                        fieldParams.add(Value.ofReference("TypeMapper.primitive()"));
                    } else {
                        file.add(Import.of(SimpleType.create(typeMapper)));
                        fieldParams.add(Value.ofReference("new " + shortName(typeMapper) + "()"));
                    }
                } else {
                    fieldParams.add(Value.ofReference("TypeMapper.identity()"));
                    file.add(Import.of(TypeMapper.class));
                }

                // Add the 'unique' boolean to the end
                fieldParams.add(Value.ofBoolean(DocumentDbUtil.isUnique(col)));

                intrf.add(Field.of(getSupport().namer().javaStaticFieldName(col.getJavaName()), ref.getType())
                    .final_()
                    .set(Value.ofInvocation(
                        ref.getType(),
                        "create",
                        fieldParams.toArray(new Value<?>[0])
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
            + getDocument().getId() + "}-table in the database.";
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
            if (isPrimitive(type)) {
                if (type.equals(int.class)) {
                    retType = OptionalInt.class;
                } else if (type.equals(long.class)) {
                    retType = OptionalLong.class;
                } else if (type.equals(double.class)) {
                    retType = OptionalDouble.class;
                } else if (type.equals(boolean.class)) {
                    retType = OptionalBoolean.class;
                } else {
                    retType = optional(wrapperFor(type)); // Optional<Float>
                }
            } else {
                if (type.equals(Integer.class)) {
                    retType = OptionalInt.class;
                } else if (type.equals(Long.class)) {
                    retType = OptionalLong.class;
                } else if (type.equals(Double.class)) {
                    retType = OptionalDouble.class;
                } else if (type.equals(Boolean.class)) {
                    retType = OptionalBoolean.class;
                } else {
                    retType = optional(type);
                }
            }
        } else {
            retType = type;
        }

        return retType;
    }
    
    private String returnString(String s) {
        requireNonNull(s);
        return "return \""+s+"\";";
    }
}
