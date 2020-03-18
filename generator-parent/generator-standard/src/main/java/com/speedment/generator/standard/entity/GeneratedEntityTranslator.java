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

import static com.speedment.common.codegen.constant.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.common.codegen.constant.DefaultJavadocTag.PARAM;
import static com.speedment.common.codegen.constant.DefaultJavadocTag.RETURN;
import static com.speedment.common.codegen.constant.DefaultType.isPrimitive;
import static com.speedment.common.codegen.constant.DefaultType.optional;
import static com.speedment.common.codegen.constant.DefaultType.wrapperFor;
import static com.speedment.common.codegen.util.Formatting.shortName;
import static com.speedment.generator.standard.util.ColumnUtil.usesOptional;
import static com.speedment.runtime.config.util.DocumentUtil.Name.DATABASE_NAME;
import static com.speedment.runtime.config.util.DocumentUtil.relativeName;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

import com.speedment.common.codegen.constant.DefaultJavadocTag;
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
import com.speedment.common.codegen.model.Value;
import com.speedment.common.codegen.util.Formatting;
import com.speedment.common.function.OptionalBoolean;
import com.speedment.common.injector.Injector;
import com.speedment.generator.standard.util.FkHolder;
import com.speedment.generator.standard.util.ForeignKeyUtil;
import com.speedment.generator.translator.AbstractEntityAndManagerTranslator;
import com.speedment.generator.translator.TranslatorSupport;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.TableUtil;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.config.util.DocumentDbUtil;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.core.util.OptionalUtil;
import com.speedment.runtime.typemapper.TypeMapper;
import com.speedment.runtime.typemapper.TypeMapperComponent;
import com.speedment.runtime.typemapper.primitive.PrimitiveTypeMapper;

import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 * @author Per-Åke Minborg
 */
public final class GeneratedEntityTranslator extends AbstractEntityAndManagerTranslator<Interface> {

    private static final String IDENTIFIER_NAME = "Identifier";
    private static final String OF_THIS = " of this ";

    public GeneratedEntityTranslator(Injector injector, Table table) {
        super(injector, table, Interface::of);
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
                .add("this.tableIdentifier\t = TableIdentifier.of(" + Formatting.indent(
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
            /*
             * General
             */
            .forEveryTable((intrf, table) -> intrf.public_().add(identifierEnum))

            /*
             * Additional interfaces
             */
            .forEveryTable(this::addInterfaces)

            /*
             * Getters
             */
            .forEveryColumn(this::addGetterMethod)
            
            /*
             * Setters
             */
            .forEveryColumn(this::addSetterMethod)
            
            /*
             * Finders
             */
            .forEveryColumn((inter, col) -> addIfForeignKey(file, inter, col))
            
            /*
             * Fields
             */
            .forEveryColumn((inter, col) -> addField(file, identifierEnum, inter, col))

            .build();
    }

    private void addField(File file, Enum identifierEnum, Interface intrf, Column col) {
        final ForeignKeyUtil.ReferenceFieldType ref
            = ForeignKeyUtil.getReferenceFieldType(
                file, getSupport().tableOrThrow(), col, getSupport().entityType(), injector()
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
        ForeignKeyUtil.getForeignKey(getSupport().tableOrThrow(), col)
            .ifPresent(fkc -> {
                final FkHolder fu = new FkHolder(injector(), fkc.getParentOrThrow());
                final TranslatorSupport<Table> fuSupport = fu.getForeignEmt().getSupport();

                fieldParams.add(Value.ofReference(
                    fuSupport.entityName() + "."
                    + fuSupport.namer().javaStaticFieldName(
                        fu.getForeignColumn().getJavaName()
                    )
                ));
            });

        // Add type mapper
        final Optional<String> oTypemapper = col.getTypeMapper();
        if (oTypemapper.isPresent()) {
            final String typeMapper = oTypemapper.get();

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
    }

    private void addIfForeignKey(File file, Interface intrf, Column col) {
        ForeignKeyUtil.getForeignKey(
            getSupport().tableOrThrow(), col
        ).ifPresent(fkc -> {
            final FkHolder fu = new FkHolder(injector(), fkc.getParentOrThrow());
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
        });
    }

    private Interface addSetterMethod(Interface intrf, Column col) {
        return intrf.add(Method.of(SETTER_METHOD_PREFIX + getSupport().typeName(col), getSupport().entityType())
            .add(Field.of(getSupport().variableName(col), typeMappers().get(col).getJavaType(col)))
            .set(Javadoc.of(
                "Sets the " + getSupport().variableName(col)
                + OF_THIS + getSupport().entityName()
                + ". The " + getSupport().variableName(col)
                + " field corresponds to the database column "
                + relativeName(col, Dbms.class, DATABASE_NAME) + "."
            )
                .add(PARAM.setValue(getSupport().variableName(col)).setText("to set of this " + getSupport().entityName()))
                .add(RETURN.setText("this " + getSupport().entityName() + " instance")))
        );
    }

    private void addGetterMethod(Interface intrf, Column col) {
        final Type retType = getterReturnType(typeMappers(), col);

        intrf.add(Method.of(GETTER_METHOD_PREFIX + getSupport().typeName(col), retType)
            .set(Javadoc.of(
                "Returns the " + getSupport().variableName(col)
                + OF_THIS + getSupport().entityName()
                + ". The " + getSupport().variableName(col)
                + " field corresponds to the database column "
                + relativeName(col, Dbms.class, DATABASE_NAME) + "."
            ).add(RETURN.setText(
                "the " + getSupport().variableName(col)
                + OF_THIS + getSupport().entityName()
            ))
            )
        );
    }

    private void addInterfaces(Interface intrf, Table table) {
        table.getAsString(TableUtil.IMPLEMENTS)
            .ifPresent(string -> Arrays.stream(string.split(","))
            .map(String::trim)
            .forEach(iface -> {
                final SimpleType interfaceType = SimpleType.create(iface);

                intrf.add(Import.of(interfaceType));
                intrf.add(interfaceType);
            }));
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

    private static final Map<Type, Type> OPTIONAL_MAPPING = Stream.of(
        new AbstractMap.SimpleImmutableEntry<>(int.class, OptionalInt.class),
        new AbstractMap.SimpleImmutableEntry<>(long.class, OptionalLong.class),
        new AbstractMap.SimpleImmutableEntry<>(double.class, OptionalDouble.class),
        new AbstractMap.SimpleImmutableEntry<>(boolean.class, OptionalBoolean.class),

        new AbstractMap.SimpleImmutableEntry<>(Integer.class, OptionalInt.class),
        new AbstractMap.SimpleImmutableEntry<>(Long.class, OptionalLong.class),
        new AbstractMap.SimpleImmutableEntry<>(Double.class, OptionalDouble.class),
        new AbstractMap.SimpleImmutableEntry<>(Boolean.class, OptionalBoolean.class)
    ).collect(collectingAndThen(toMap(Map.Entry::getKey, Map.Entry::getValue), Collections::unmodifiableMap));

    static Type getterReturnType(TypeMapperComponent typeMappers, Column col) {
        final Type type = typeMappers.get(col).getJavaType(col);
        if (usesOptional(col)) {
            if (isPrimitive(type)) {
                return OPTIONAL_MAPPING.getOrDefault(type, optional(wrapperFor(type)));
            } else {
                return OPTIONAL_MAPPING.getOrDefault(type, optional(type));
            }
        }
        return type;
    }
    
    private String returnString(String s) {
        requireNonNull(s);
        return "return \""+s+"\";";
    }
}
