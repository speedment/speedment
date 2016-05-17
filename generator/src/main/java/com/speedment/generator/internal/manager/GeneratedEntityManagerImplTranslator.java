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
package com.speedment.generator.internal.manager;

import com.speedment.runtime.Speedment;
import com.speedment.generator.TranslatorSupport;
import com.speedment.fika.codegen.Generator;
import com.speedment.fika.codegen.model.Class;
import com.speedment.fika.codegen.model.Constructor;
import com.speedment.fika.codegen.model.Field;
import com.speedment.fika.codegen.model.File;
import com.speedment.fika.codegen.model.Generic;
import com.speedment.fika.codegen.model.Import;
import com.speedment.fika.codegen.model.Method;
import com.speedment.fika.codegen.model.Type;
import com.speedment.runtime.component.resultset.ResultSetMapperComponent;
import com.speedment.runtime.component.resultset.ResultSetMapping;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.exception.SpeedmentException;
import static com.speedment.fika.codegen.internal.model.constant.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.fika.codegen.internal.model.constant.DefaultType.OBJECT;
import static com.speedment.fika.codegen.internal.model.constant.DefaultType.VOID;
import com.speedment.fika.codegen.internal.model.value.ReferenceValue;
import static com.speedment.fika.codegen.internal.util.Formatting.block;
import static com.speedment.fika.codegen.internal.util.Formatting.indent;
import static com.speedment.fika.codegen.internal.util.Formatting.nl;
import com.speedment.generator.internal.EntityAndManagerTranslator;
import com.speedment.runtime.internal.manager.sql.AbstractSqlManager;
import static com.speedment.runtime.internal.util.document.DocumentDbUtil.dbmsTypeOf;
import com.speedment.runtime.internal.util.sql.ResultSetUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static com.speedment.generator.internal.DefaultJavaClassTranslator.GETTER_METHOD_PREFIX;
import static com.speedment.generator.internal.DefaultJavaClassTranslator.SETTER_METHOD_PREFIX;
import com.speedment.runtime.config.identifier.FieldIdentifier;
import com.speedment.runtime.field.trait.FieldTrait;
import com.speedment.runtime.util.tuple.Tuples;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author Emil Forslund
 */
public final class GeneratedEntityManagerImplTranslator extends EntityAndManagerTranslator<Class> {
    
    public final static String NEW_ENTITY_FROM_METHOD = "newEntityFrom",
        NEW_EMPTY_ENTITY_METHOD = "newEmptyEntity",
        GET_PRIMARY_KEY_CLASSES_METHOD = "getPrimaryKeyClasses",
        FIELDS_METHOD = "fields",
        PRIMARY_KEYS_FIELDS_METHOD = "primaryKeyFields",
        GET_METHOD = "get",
        SET_METHOD = "set";
    
    private static final String SPEEDMENT_VARIABLE_NAME = "speedment";
    private static final String PRIMARY_KEY_CLASSES = "PRIMARY_KEY_CLASSES";
    
    public GeneratedEntityManagerImplTranslator(Speedment speedment, Generator gen, Table table) {
        super(speedment, gen, table, Class::of);
    }
    
    @Override
    protected Class makeCodeGenModel(File file) {
        file.add(Import.of(Type.of(ResultSetUtil.class)).static_().setStaticMember("*"));
        return newBuilder(file, getSupport().generatedManagerImplName())
            .forEveryTable((clazz, table) -> {
                clazz
                    .public_()
                    .abstract_()
                    .setSupertype(Type.of(AbstractSqlManager.class)
                        .add(Generic.of().add(getSupport().entityType()))
                    )
                    .add(getSupport().generatedManagerType())
                    .add(Constructor.of()
                        .protected_()
                        .add(Field.of(SPEEDMENT_VARIABLE_NAME, Type.of(Speedment.class)))
                        .add("super(" + SPEEDMENT_VARIABLE_NAME + ");")
                        .add("setEntityMapper(this::" + NEW_ENTITY_FROM_METHOD + ");"))
                    .add(generateNewEntityFrom(getSupport(), file, table::columns))
                    .add(generateNewEmptyEntity(getSupport(), file, table::columns))
                    .add(generateGet(getSupport(), file, table::columns))
                    .add(generateSet(getSupport(), file, table::columns))
                    .add(generateFields(getSupport(), file, table::columns))
                    .add(generatePrimaryKeyFields(getSupport(), file, () -> table.columns().filter(this::isPrimaryKey)))
                    .add(generateGetPrimaryKeyClassesField(file))
                    .add(generateGetPrimaryKeyClasses(file))
                    .add(generateNewCopyOf(file));
                
            })
            .build()
            .call(i -> file.add(Import.of(getSupport().entityImplType())));
    }
    
    @Override
    protected String getJavadocRepresentText() {
        return "The generated base manager implementation";
    }
    
    @Override
    protected String getClassOrInterfaceName() {
        return getSupport().generatedManagerImplName();
    }
    
    @Override
    public boolean isInGeneratedPackage() {
        return true;
    }
    
    public Type getImplType() {
        return getSupport().managerImplType();
    }
    
    protected Field generateGetPrimaryKeyClassesField(File file) {
        file.add(Import.of(Type.of(Tuples.class)));
        final Field field = Field.of(PRIMARY_KEY_CLASSES, pkTupleType())
            .private_()
            .static_()
            .final_();
        
        final String parameters = primaryKeyColumns()
            .map(pkc -> pkc.findColumn().get().findTypeMapper().getJavaType())
            .map(c -> c.getSimpleName() + ".class")
            .collect(joining(", "));
        
        field.set(new ReferenceValue(Tuples.class.getSimpleName() + ".of(" + parameters + ")"));
        
        return field;
    }
    
    protected Method generateGetPrimaryKeyClasses(File file) {
        
        final Method method = Method.of(GET_PRIMARY_KEY_CLASSES_METHOD, pkTupleType())
            .public_()
            .add(OVERRIDE)
            .add("return " + PRIMARY_KEY_CLASSES + ";");
        return method;
    }
    
    public static String readFromResultSet(Speedment speedment, Column c, AtomicInteger position) {
        
        final TranslatorSupport<Table> support = new TranslatorSupport<>(speedment, c.getParentOrThrow());
        final Dbms dbms = c.getParentOrThrow().getParentOrThrow().getParentOrThrow();
        final ResultSetMapperComponent mapperComponent = speedment.getResultSetMapperComponent();
        
        final ResultSetMapping<?> mapping = mapperComponent
            .apply(
                dbmsTypeOf(speedment, c.getParentOrThrow().getParentOrThrow().getParentOrThrow()),
                c.findTypeMapper().getDatabaseType()
            );
        
        final boolean isIdentityMapper = c.findTypeMapper().isIdentityMapper();
        
        final StringBuilder sb = new StringBuilder();
        if (!isIdentityMapper) {
            sb
                .append(typeMapperName(support, c))
                .append(".toJavaType(");
        }
        final String getterName = "get" + mapping.getResultSetMethodName(dbms);
        
        final boolean isResultSetMethod = Stream.of(ResultSet.class.getMethods())
            .map(java.lang.reflect.Method::getName)
            .anyMatch(getterName::equals);
        
        final boolean isResultSetMethodReturnsPrimitive = Stream.of(ResultSet.class.getMethods())
            .filter(m -> m.getName().equals(getterName))
            .anyMatch(m -> m.getReturnType().isPrimitive());
        
        if (isResultSetMethod && !(c.isNullable() && isResultSetMethodReturnsPrimitive)) {
            sb
                .append("resultSet.")
                .append("get")
                .append(mapping.getResultSetMethodName(dbms))
                .append("(").append(position.getAndIncrement()).append(")");
        } else {
            sb
                .append("get")
                .append(mapping.getResultSetMethodName(dbms))
                .append("(resultSet, ")
                .append(position.getAndIncrement()).append(")");
        }
        if (!isIdentityMapper) {
            sb.append(")");
        }
        
        return sb.toString();
    }
    
    public static Method generateGet(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        return Method.of(GET_METHOD, OBJECT).public_().add(OVERRIDE)
            .add(Field.of("entity", support.entityType()))
            .add(Field.of("identifier", Type.of(FieldIdentifier.class).add(Generic.of().add(support.entityType()))))
            .add(generateGetBody(support, file, columnsSupplier));
    }
    
    public static String[] generateGetBody(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        file.add(Import.of(Type.of(IllegalArgumentException.class)));
        
        return new String[]{
            "switch ((" + support.entityName() + ".Identifier) identifier) " + block(
            columnsSupplier.get().map(c
            -> "case " + support.namer().javaStaticFieldName(c.getJavaName())
            + " : return entity." + getterCode(support, c)
            + ";"
            ).collect(Collectors.joining(nl()))
            + nl() + "default : throw new IllegalArgumentException(\"Unknown identifier '\" + identifier + \"'.\");"
            )
        };
    }
    
    public static Method generateSet(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        return Method.of(SET_METHOD, VOID).public_().add(OVERRIDE)
            .add(Field.of("entity", support.entityType()))
            .add(Field.of("identifier", Type.of(FieldIdentifier.class).add(Generic.of().add(support.entityType()))))
            .add(Field.of("value", Type.of(Object.class)))
            .add(generateSetBody(support, file, columnsSupplier));
    }
    
    public static String[] generateSetBody(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        file.add(Import.of(Type.of(IllegalArgumentException.class)));
        
        return new String[]{
            "switch ((" + support.entityName() + ".Identifier) identifier) " + block(
            columnsSupplier.get()
            .peek(c -> file.add(Import.of(Type.of(c.findTypeMapper().getJavaType()))))
            .map(c
            -> "case " + support.namer().javaStaticFieldName(c.getJavaName())
            + " : entity." + SETTER_METHOD_PREFIX + support.typeName(c)
            + "("
            + castToColumnTypeIfNotObject(c)
            + "value); break;").collect(Collectors.joining(nl()))
            + nl() + "default : throw new IllegalArgumentException(\"Unknown identifier '\" + identifier + \"'.\");"
            )
        };
    }
    
    private static String castToColumnTypeIfNotObject(Column c) {
        final java.lang.Class<?> castType = c.findTypeMapper().getJavaType();
        if (Object.class.equals(castType)) {
            return "";
        } else {
            return "(" + c.findTypeMapper().getJavaType().getSimpleName() + ") ";
        }
    }
    
    public static Method generateFields(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        return Method.of(FIELDS_METHOD, Type.of(Stream.class).add(Generic.of().add(Type.of(FieldTrait.class))))
            .public_().add(OVERRIDE)
            .add(generateFieldsBody(support, file, columnsSupplier));
    }
    
    public static Method generatePrimaryKeyFields(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        return Method.of(PRIMARY_KEYS_FIELDS_METHOD, Type.of(Stream.class).add(Generic.of().add(Type.of(FieldTrait.class))))
            .public_().add(OVERRIDE)
            .add(generateFieldsBody(support, file, columnsSupplier));
    }
    
    public static String[] generateFieldsBody(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        final List<String> rows = new LinkedList<>();
        
        rows.add("return Stream.of(");
        rows.add(indent(columnsSupplier.get()
            .map(Column::getJavaName)
            .map(support.namer()::javaStaticFieldName)
            .map(field -> support.typeName() + "." + field)
            .collect(joining("," + nl()))
        ));
        rows.add(");");
        
        return rows.toArray(new String[rows.size()]);
    }
    
    public static Method generateNewEntityFrom(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        return Method.of(NEW_ENTITY_FROM_METHOD, support.entityType())
            .protected_()
            .add(Type.of(SQLException.class))
            .add(Type.of(SpeedmentException.class))
            .add(Field.of("resultSet", Type.of(ResultSet.class)))
            .add(generateNewEntityFromBody(support, file, columnsSupplier));
    }
    
    public static String[] generateNewEntityFromBody(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        
        final List<String> rows = new LinkedList<>();
        rows.add("final " + support.entityName() + " entity = " + NEW_EMPTY_ENTITY_METHOD + "();");
        
        final Stream.Builder<String> streamBuilder = Stream.builder();
        
        final AtomicInteger position = new AtomicInteger(1);
        columnsSupplier.get().forEachOrdered(c -> {
            streamBuilder.add("entity.set" + support.namer().javaTypeName(c.getJavaName()) + "(" + readFromResultSet(support.speedment(), c, position) + ");");
        });
        
        rows.add("try " + block(streamBuilder.build()));
        rows.add("catch (" + SQLException.class.getSimpleName() + " sqle) " + block(
            "throw new " + SpeedmentException.class.getSimpleName() + "(sqle);"
        ));
        rows.add("return entity;");
        
        return rows.toArray(new String[rows.size()]);
    }
    
    public static Method generateNewEmptyEntity(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        return Method.of(NEW_EMPTY_ENTITY_METHOD, support.entityType())
            .public_().add(OVERRIDE)
            .add(generateNewEmptyEntityBody(support, file, columnsSupplier));
        
    }
    
    public static String[] generateNewEmptyEntityBody(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        file.add(Import.of(support.entityImplType()));
        file.add(Import.of(Type.of(Speedment.class)));
        
        final List<String> rows = new LinkedList<>();
        rows.add("return new " + support.entityImplName() + "() {");
        rows.add(indent(
            "@Override",
            "protected " + Speedment.class.getSimpleName() + " speedment() {", indent(
                "return " + SPEEDMENT_VARIABLE_NAME + ";"
            ), "}"
        ));
        rows.add("};");
        
        return rows.toArray(new String[rows.size()]);
    }
    
    private static enum Primitive {
        BYTE("byte"), SHORT("short"), INT("int"), LONG("long"), FLOAT("float"),
        DOUBLE("double"), BOOLEAN("boolean");
        
        private final String javaName;
        
        private Primitive(String javaName) {
            this.javaName = javaName;
        }
        
        public String getJavaName() {
            return javaName;
        }
        
        public static boolean isPrimitive(String typeName) {
            return nameStream().anyMatch(typeName::equalsIgnoreCase);
        }
        
        public static Stream<String> nameStream() {
            return Stream.of(values()).map(Primitive::getJavaName);
        }
    }
    
    private static String getterCode(TranslatorSupport<Table> support, Column c) {
        if (c.isNullable()) {
            return GETTER_METHOD_PREFIX + support.typeName(c) + "().orElse(null)";
        } else {
            return GETTER_METHOD_PREFIX + support.typeName(c) + "()";
        }
    }
    
    private static String typeMapperName(TranslatorSupport<Table> support, Column col) {
        return support.entityName() + "." + support.namer().javaStaticFieldName(col.getJavaName()) + ".typeMapper()";
    }
    
    private boolean isPrimaryKey(Column column) {
        return column.getParentOrThrow().findPrimaryKeyColumn(column.getName()).isPresent();
    }
    
    private Method generateNewCopyOf(File file) {
        file.add(Import.of(getSupport().entityImplType()));
        
        final String varName = "source";
        final String entityName = "copy";
        final Method result = Method.of("newCopyOf", getSupport().entityType()).public_().add(OVERRIDE)
            .add(Field.of(varName, getSupport().entityType()))
            .add(
                "final " + getSupport().entityName() + " " + entityName + " = new " + getSupport().entityImplName() + "() {", indent(
                    "@Override",
                    "protected final " + Speedment.class.getSimpleName() + " speedment() {", indent(
                        "return speedment;"
                    ), "}"
                ), "};",
                ""
            );
        
        columns().forEachOrdered(c -> {
            if (c.isNullable()) {
                result.add(
                    varName + "." + GETTER_METHOD_PREFIX + getSupport().typeName(c)
                    + "().ifPresent(" + entityName + "::"
                    + SETTER_METHOD_PREFIX + getSupport().typeName(c)
                    + ");"
                );
            } else {
                result.add(
                    entityName + "." + SETTER_METHOD_PREFIX
                    + getSupport().typeName(c) + "(" + varName + ".get"
                    + getSupport().typeName(c) + "());"
                );
            }
        });
        
        return result.add("", "return " + entityName + ";");
    }
    
}
