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

import static com.speedment.common.codegen.internal.model.constant.DefaultAnnotationUsage.OVERRIDE;
import com.speedment.generator.TranslatorSupport;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Constructor;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Generic;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.Method;
import com.speedment.common.codegen.model.Type;
import com.speedment.runtime.component.resultset.ResultSetMapperComponent;
import com.speedment.runtime.component.resultset.ResultSetMapping;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.exception.SpeedmentException;
import static com.speedment.common.codegen.internal.model.constant.DefaultType.OBJECT;
import static com.speedment.common.codegen.internal.model.constant.DefaultType.OPTIONAL;
import static com.speedment.common.codegen.internal.model.constant.DefaultType.VOID;
import com.speedment.common.codegen.internal.model.value.ReferenceValue;
import com.speedment.common.codegen.internal.util.Formatting;
import com.speedment.generator.internal.EntityAndManagerTranslator;
import static com.speedment.runtime.internal.util.document.DocumentDbUtil.dbmsTypeOf;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static com.speedment.generator.internal.DefaultJavaClassTranslator.SETTER_METHOD_PREFIX;
import com.speedment.runtime.config.identifier.FieldIdentifier;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.field.trait.FieldTrait;
import com.speedment.runtime.internal.manager.sql.AbstractSqlManager;
import com.speedment.runtime.util.tuple.Tuples;
import static com.speedment.common.codegen.internal.util.Formatting.block;
import static com.speedment.common.codegen.internal.util.Formatting.indent;
import static com.speedment.common.codegen.internal.util.Formatting.nl;
import static com.speedment.common.codegen.internal.util.Formatting.tab;
import com.speedment.common.codegen.model.AnnotationUsage;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import static com.speedment.generator.internal.DefaultJavaClassTranslator.GETTER_METHOD_PREFIX;
import com.speedment.generator.internal.util.EntityTranslatorSupport;
import com.speedment.generator.internal.util.FkHolder;
import com.speedment.runtime.component.DbmsHandlerComponent;
import com.speedment.runtime.component.ProjectComponent;
import static com.speedment.runtime.internal.util.document.DocumentUtil.Name.DATABASE_NAME;
import com.speedment.runtime.internal.util.sql.ResultSetUtil;
import static java.util.stream.Collectors.joining;
import static com.speedment.runtime.internal.util.document.DocumentUtil.relativeName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 *
 * @author Emil Forslund
 */
public final class GeneratedEntityManagerImplTranslator extends EntityAndManagerTranslator<Class> {

    public final static String 
        NEW_ENTITY_FROM_METHOD         = "newEntityFrom",
        NEW_EMPTY_ENTITY_METHOD        = "newEmptyEntity",
        GET_PRIMARY_KEY_CLASSES_METHOD = "getPrimaryKeyClasses",
        FIELDS_METHOD                  = "fields",
        PRIMARY_KEYS_FIELDS_METHOD     = "primaryKeyFields",
        GET_METHOD                     = "get",
        SET_METHOD                     = "set",
        SPEEDMENT_VARIABLE_NAME        = "speedment",
        PRIMARY_KEY_CLASSES            = "PRIMARY_KEY_CLASSES";

    private @Inject ResultSetMapperComponent resultSetMapperComponent;
    private @Inject DbmsHandlerComponent dbmsHandlerComponent;
    private @Inject Injector injector;
    
    public GeneratedEntityManagerImplTranslator(Table table) {
        super(table, Class::of);
    }

    @Override
    protected Class makeCodeGenModel(File file) {
        
        final Map<Table, List<String>> fkStreamers = new HashMap<>();
        final Set<Type> fkManagers = new HashSet<>();
        final Set<Type> fkManagersReferencingThis = new HashSet<>();
        
        return newBuilder(file, getSupport().generatedManagerImplName())

            /**
             * Add streamers from back pointing foreign keys
             */
            .forEveryForeignKeyReferencingThis((clazz, fk) -> {
                final FkHolder fu = new FkHolder(injector, fk);
                final String methodName = EntityTranslatorSupport.FIND
                    + EntityTranslatorSupport.pluralis(fu.getTable(), getNamer())
                    + "By" + getSupport().typeName(fu.getColumn());
                fkStreamers.computeIfAbsent(fu.getTable(), t -> new ArrayList<>()).add(methodName);
                
                final Type manager = fu.getEmt().getSupport().managerType();
                final String fkManagerName = getSupport().namer().javaVariableName(Formatting.shortName(manager.getName()));
                if (fkManagersReferencingThis.add(manager)) {
                    clazz.add(Field.of(fkManagerName, manager)
                        .private_()
                        .add(AnnotationUsage.of(Type.of(Inject.class)))
                    );
                }
                
                final Type returnType = Type.of(Stream.class).add(Generic.of().add(fu.getEmt().getSupport().entityType()));
                final Method method = Method.of(methodName, returnType)
                    .public_().add(OVERRIDE)
                    .add(Field.of("entity", fu.getForeignEmt().getSupport().entityType()))
                    .add("return " + fkManagerName + ".stream()")
                    .add(tab() + ".filter(" + getSupport().typeName(fu.getTable()) + "." + getNamer().javaStaticFieldName(fu.getColumn().getJavaName()) + ".equal(entity." + GETTER_METHOD_PREFIX + getSupport().typeName(fu.getForeignColumn()) + "()));");
                clazz.add(method);
            })
            
            /**
             * Add getter for ordinary foreign keys
             */
            .forEveryForeignKey((clazz, fk) -> {
                final FkHolder fu = new FkHolder(injector, fk);
                
                final Type manager = fu.getForeignEmt().getSupport().managerType();
                final String fkManagerName = getSupport().namer().javaVariableName(Formatting.shortName(manager.getName()));
                if (fkManagers.add(manager)) {
                    clazz.add(Field.of(fkManagerName, manager)
                        .private_()
                        .add(AnnotationUsage.of(Type.of(Inject.class)))
                    );
                }

                final Type returnType;
                if (fu.getColumn().isNullable()) {
                    file.add(Import.of(OPTIONAL));
                    returnType = OPTIONAL.add(Generic.of().add(fu.getForeignEmt().getSupport().entityType()));
                } else {
                    returnType = fu.getForeignEmt().getSupport().entityType();
                }

                final Method method = Method.of("find" + getSupport().typeName(fu.getColumn()), returnType)
                    .public_().add(OVERRIDE)
                    .add(Field.of("entity", fu.getEmt().getSupport().entityType()));
                
                if (fu.getColumn().isNullable()) {
                    final String varName = getSupport().variableName(fu.getColumn()) + "_";
                    method.add("return entity.get" + getSupport().typeName(fu.getColumn()) + "()")
                        .add(indent(".flatMap(" + varName + " -> " + fkManagerName + ".findAny("
                            + getSupport().typeName(fu.getForeignTable()) + "." + getNamer().javaStaticFieldName(fu.getForeignColumn().getJavaName()) + ", " + varName + "));"
                        ));
                } else {
                    file.add(Import.of(Type.of(SpeedmentException.class)));
                    method.add("return " + fkManagerName + ".findAny("
                        + getSupport().typeName(fu.getForeignTable()) + "." + getNamer().javaStaticFieldName(fu.getForeignColumn().getJavaName()) + ", entity.get" + getSupport().typeName(fu.getColumn()) + "())\n"
                        + indent(".orElseThrow(() -> new SpeedmentException(\n"
                            + indent(
                                "\"Foreign key constraint error. "
                                + getSupport().typeName(fu.getForeignTable())
                                + " is set to \" + entity.get"
                                + getSupport().typeName(fu.getColumn()) + "()\n"
                            ) + "));\n"
                        )
                    );
                }
                clazz.add(method);
            })
            
            /**
             * The table specific methods.
             */
            .forEveryTable((clazz, table) -> {
                clazz
                    .public_()
                    .abstract_()
                    .setSupertype(Type.of(AbstractSqlManager.class)
                        .add(Generic.of().add(getSupport().entityType()))
                    )
                    .add(getSupport().generatedManagerType())
                    .add(Field.of("projectComponent", Type.of(ProjectComponent.class)).add(AnnotationUsage.of(Type.of(Inject.class))).private_())
                    .add(Constructor.of()
                        .protected_()
                        .add("setEntityMapper(this::" + NEW_ENTITY_FROM_METHOD + ");")
                    )
                    .add(Method.of("getTable", Type.of(Table.class))
                        .public_().add(OVERRIDE)
                        .add("return projectComponent.getProject().findTableByName(\"" +
                            relativeName(getSupport().tableOrThrow(), Dbms.class, DATABASE_NAME) + "\");")
                    )
                    .add(generateNewEntityFrom(getSupport(), file, table::columns))
                    .add(generateNewEmptyEntity(getSupport(), file, table::columns))
                    .add(generateGet(getSupport(), file, table::columns))
                    .add(generateSet(getSupport(), file, table::columns))
                    .add(generateFields(getSupport(), file, table::columns))
                    .add(generatePrimaryKeyFields(getSupport(), file, 
                        () -> table.columns().filter(GeneratedEntityManagerImplTranslator::isPrimaryKey))
                    )
                    .add(generateGetPrimaryKeyClassesField(file))
                    .add(generateGetPrimaryKeyClasses(file))
                    .add(generateNewCopyOf(file));
            })
            
            /**
             * Create aggregate streaming functions, if any
             */
            .forEveryTable(Phase.POST_MAKE, (clazz, table) -> {
                fkStreamers.keySet().stream().forEach(referencingTable -> {
                    final List<String> methodNames = fkStreamers.get(referencingTable);
                    final TranslatorSupport<Table> foreignSupport = injector.inject(new TranslatorSupport<>(referencingTable));
                    
                    if (!methodNames.isEmpty()) {
                        final Method method = Method.of(EntityTranslatorSupport.FIND + EntityTranslatorSupport.pluralis(referencingTable, getNamer()),
                            Type.of(Stream.class).add(Generic.of(foreignSupport.entityType()))
                        ).public_().add(OVERRIDE);

                        method.add(Field.of("entity", getSupport().entityType()));
                        
                        if (methodNames.size() == 1) {
                            method.add("return " + methodNames.get(0) + "(entity);");
                        } else {
                            file.add(Import.of(Type.of(Function.class)));
                            method.add("return Stream.of("
                                + methodNames.stream().map(n -> n + "(entity)").collect(Collectors.joining(", "))
                                + ").flatMap(Function.identity()).distinct();");
                        }
                        clazz.add(method);
                    }
                });
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

    private String readFromResultSet(File file, Column c, AtomicInteger position) {

        final TranslatorSupport<Table> support = injector.inject(new TranslatorSupport<>(c.getParentOrThrow()));
        final Dbms dbms = c.getParentOrThrow().getParentOrThrow().getParentOrThrow();
        
        final ResultSetMapping<?> mapping = resultSetMapperComponent.apply(
            dbmsTypeOf(dbmsHandlerComponent, c.getParentOrThrow().getParentOrThrow().getParentOrThrow()),
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
            file.add(Import.of(Type.of(ResultSetUtil.class)).static_().setStaticMember("*"));
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
            columnsSupplier.get()
            .filter(HasEnabled::isEnabled)
            .map(c
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
            .filter(HasEnabled::isEnabled)
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

    private static Method generateFields(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        return Method.of(FIELDS_METHOD, Type.of(Stream.class).add(Generic.of().add(Type.of(FieldTrait.class))))
            .public_().add(OVERRIDE)
            .add(generateFieldsBody(support, file, columnsSupplier));
    }

    private static Method generatePrimaryKeyFields(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        return Method.of(PRIMARY_KEYS_FIELDS_METHOD, Type.of(Stream.class).add(Generic.of().add(Type.of(FieldTrait.class))))
            .public_().add(OVERRIDE)
            .add(generateFieldsBody(support, file, columnsSupplier));
    }

    private static String[] generateFieldsBody(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        final List<String> rows = new LinkedList<>();

        rows.add("return Stream.of(");
        rows.add(indent(columnsSupplier.get()
            .filter(HasEnabled::isEnabled)
            .map(Column::getJavaName)
            .map(support.namer()::javaStaticFieldName)
            .map(field -> support.typeName() + "." + field)
            .collect(joining("," + nl()))
        ));
        rows.add(");");

        return rows.toArray(new String[rows.size()]);
    }

    private Method generateNewEntityFrom(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        return Method.of(NEW_ENTITY_FROM_METHOD, support.entityType())
            .protected_()
            .add(Type.of(SQLException.class))
            .add(Type.of(SpeedmentException.class))
            .add(Field.of("resultSet", Type.of(ResultSet.class)))
            .add(generateNewEntityFromBody(support, file, columnsSupplier));
    }

    private String[] generateNewEntityFromBody(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {

        final List<String> rows = new LinkedList<>();
        rows.add("final " + support.entityName() + " entity = " + NEW_EMPTY_ENTITY_METHOD + "();");

        final Stream.Builder<String> streamBuilder = Stream.builder();

        final AtomicInteger position = new AtomicInteger(1);
        columnsSupplier.get()
            .filter(HasEnabled::isEnabled)
            .forEachOrdered(c -> {
                streamBuilder.add("entity.set" + support.namer().javaTypeName(c.getJavaName()) + "(" + readFromResultSet(file, c, position) + ");");
            });

        rows.add("try " + block(streamBuilder.build()));
        rows.add("catch (" + SQLException.class.getSimpleName() + " sqle) " + block(
            "throw new " + SpeedmentException.class.getSimpleName() + "(sqle);"
        ));
        rows.add("return entity;");

        return rows.toArray(new String[rows.size()]);
    }

    private Method generateNewEmptyEntity(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        return Method.of(NEW_EMPTY_ENTITY_METHOD, support.entityType())
            .public_().add(OVERRIDE)
            .add(generateNewEmptyEntityBody(support, file, columnsSupplier));

    }

    private String[] generateNewEmptyEntityBody(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        file.add(Import.of(support.entityImplType()));
        return new String[] {
            "return new " + support.entityImplName() + "();"
        };
    }
    
    private Method generateNewCopyOf(File file) {
        file.add(Import.of(getSupport().entityImplType()));

        final String varName = "source";
        final String entityName = "copy";
        final Method result = Method.of("newCopyOf", getSupport().entityType()).public_().add(OVERRIDE)
            .add(Field.of(varName, getSupport().entityType()))
            .add("final " + getSupport().entityName() + " " + entityName + 
                " = new " + getSupport().entityImplName() + "();"
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

    private static boolean isPrimaryKey(Column column) {
        return column.getParentOrThrow().findPrimaryKeyColumn(column.getName()).isPresent();
    }
}