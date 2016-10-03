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
package com.speedment.generator.standard.manager;

import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.model.AnnotationUsage;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Constructor;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.Method;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.generator.translator.AbstractEntityAndManagerTranslator;
import com.speedment.generator.translator.TranslatorSupport;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.component.resultset.ResultSetMapperComponent;
import com.speedment.runtime.core.component.resultset.ResultSetMapping;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.core.internal.util.sql.ResultSetUtil;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.speedment.common.codegen.internal.model.value.ReferenceValue;
import static com.speedment.common.codegen.internal.util.Formatting.indent;
import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.runtime.config.util.DocumentDbUtil;
import com.speedment.runtime.core.exception.SpeedmentException;
import static com.speedment.runtime.core.util.DatabaseUtil.dbmsTypeOf;
import static com.speedment.generator.standard.internal.util.ColumnUtil.usesOptional;
import static com.speedment.generator.standard.internal.util.GenerateMethodBodyUtil.generateNewEntityFromBody;
import com.speedment.generator.translator.component.TypeMapperComponent;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.sql.SqlPersistenceComponent;
import com.speedment.runtime.core.component.sql.SqlStreamSupplierComponent;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author Emil Forslund
 */
public final class GeneratedSqlAdapterTranslator extends AbstractEntityAndManagerTranslator<Class> {

    public final static String ENTITY_COPY_METHOD_NAME = "entityCopy",
        ENTITY_CREATE_METHOD_NAME = "entityCreate",
        FIELDS_METHOD = "fields",
        PRIMARY_KEYS_FIELDS_METHOD = "primaryKeyFields";

    private @Inject
    ResultSetMapperComponent resultSetMapperComponent;
    private @Inject
    DbmsHandlerComponent dbmsHandlerComponent;
    private @Inject
    TypeMapperComponent typeMappers;
    private @Inject
    Injector injector;

    public GeneratedSqlAdapterTranslator(Table table) {
        super(table, Class::of);
    }

    @Override
    protected Class makeCodeGenModel(File file) {
        file.add(Import.of(Project.class));
        return newBuilder(file, getClassOrInterfaceName())
            .forEveryTable((clazz, table) -> {
                clazz
                    .public_()
                    .abstract_()
                    .add(Field.of("manager", getSupport().managerType()).add(inject()).private_())
                    .add(Method.of("createHelpers", void.class)
                        .add(Field.of("projectComponent", ProjectComponent.class).add(withStateInitialized(file)))
                        .add("final Project project = projectComponent.getProject();")
                    )
                    .add(Method.of("install", void.class)
                        .add("")
                        .add(Field.of("manager", getSupport().managerType()).add(withStateInitialized(file)))
                        .add(Field.of("streamSupplierComponent", SqlStreamSupplierComponent.class).add(withStateInitialized(file)))
                        .add(Field.of("persistenceComponent", SqlPersistenceComponent.class).add(withStateInitialized(file)))
                        .add("streamSupplierComponent.install(tableIdentifier, this::apply);")
                        .add("persistenceComponent.install(tableIdentifier);")
                    )
                    .add(Field.of("tableIdentifier", SimpleParameterizedType.create(TableIdentifier.class, getSupport().entityType())).private_().final_())
                    .add(Constructor.of().protected_()
                        //                        .add("this.tableIdentifier = "+TableIdentifier.class.getSimpleName()+".of(\\\"\" + getSupport().dbmsOrThrow().getName() + \"\\\");
                        .add("this.tableIdentifier = " + TableIdentifier.class.getSimpleName() + ".of("
                            + Stream.of(getSupport().dbmsOrThrow().getName(), getSupport().schemaOrThrow().getName(), getSupport().tableOrThrow().getName())
                            .map(s -> "\"" + s + "\"").collect(joining(", "))
                            + ");")
                    )
                    .add(generateNewEntityFrom(getSupport(), file, table::columns));
            })
            .build()
            .call(i -> file.add(Import.of(getSupport().entityImplType())));
    }

    private Method generateApplyMethod(File file, Table table) {
        return Method.of("apply", getSupport().entityType()).protected_()
            .add("final " + getSupport().entityName() + " entity = manager.entityCreate();")
            .add("try {")
            .add(indent(fieldsss(table)))
            .add("} catch (final SQLException sqle) {")
            .add(indent("throw new SpeedmentException(sqle);"))
            .add("}")
            .add("return entity;");
    }

    private String[] fieldsss(Table table) {
        throw new UnsupportedOperationException("Todo");
    }

    @Override
    protected String getJavadocRepresentText() {
        return "The generated Sql Adapter for a {@link "
            + getSupport().entityType().getTypeName() + "} entity.";
    }

    @Override
    protected String getClassOrInterfaceName() {
        return getSupport().generatedSqlAdapterName();
    }

    @Override
    public boolean isInGeneratedPackage() {
        return true;
    }

    public Type getImplType() {
        return getSupport().managerImplType();
    }

    private Method generateNewEntityFrom(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        return Method.of("apply", support.entityType())
            .protected_()
            .add(SQLException.class)
            .add(SpeedmentException.class)
            .add(Field.of("resultSet", ResultSet.class))
            .add(generateNewEntityFromBody(this::readFromResultSet, support, file, columnsSupplier));
    }

    private String readFromResultSet(File file, Column c, AtomicInteger position) {

        final TranslatorSupport<Table> support = new TranslatorSupport<>(injector, c.getParentOrThrow());
        final Dbms dbms = c.getParentOrThrow().getParentOrThrow().getParentOrThrow();

        final ResultSetMapping<?> mapping = resultSetMapperComponent.apply(
            dbmsTypeOf(dbmsHandlerComponent, c.getParentOrThrow().getParentOrThrow().getParentOrThrow()),
            c.findDatabaseType()
        );

        final boolean isIdentityMapper = !c.getTypeMapper().isPresent();

        file.add(Import.of(DocumentDbUtil.class));

        final StringBuilder sb = new StringBuilder();
        if (!isIdentityMapper) {
            sb
                .append(typeMapperName(support, c))
                .append(".toJavaType(DocumentDbUtil.referencedColumn(projectComponent.getProject(), ")
                .append(support.entityName())
                .append(".")
                .append(support.namer().javaStaticFieldName(c.getJavaName()))
                .append(".identifier()), getEntityClass(), ");
        }
        final String getterName = "get" + mapping.getResultSetMethodName(dbms);

        final boolean isResultSetMethod = Stream.of(ResultSet.class.getMethods())
            .map(java.lang.reflect.Method::getName)
            .anyMatch(getterName::equals);

        final boolean isResultSetMethodReturnsPrimitive = Stream.of(ResultSet.class.getMethods())
            .filter(m -> m.getName().equals(getterName))
            .anyMatch(m -> m.getReturnType().isPrimitive());

        if (isResultSetMethod && !(usesOptional(c) && isResultSetMethodReturnsPrimitive)) {
            sb
                .append("resultSet.")
                .append("get")
                .append(mapping.getResultSetMethodName(dbms))
                .append("(").append(position.getAndIncrement()).append(")");
        } else {
            file.add(Import.of(ResultSetUtil.class).static_().setStaticMember("*"));
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

    private static String typeMapperName(TranslatorSupport<Table> support, Column col) {
        return support.entityName() + "." + support.namer().javaStaticFieldName(col.getJavaName()) + ".typeMapper()";
    }

    private static boolean isPrimaryKey(Column column) {
        return column.getParentOrThrow().findPrimaryKeyColumn(column.getName()).isPresent();
    }

    private AnnotationUsage withStateInitialized(File file) {
        file.add(Import.of(State.class).static_().setStaticMember("INITIALIZED"));
        return AnnotationUsage.of(WithState.class).set(new ReferenceValue("INITIALIZED"));
    }

    private AnnotationUsage inject() {
        return AnnotationUsage.of(Inject.class);
    }

}
