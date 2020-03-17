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
package com.speedment.generator.standard.manager;

import static com.speedment.common.codegen.constant.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.common.codegen.constant.DefaultType.isPrimitive;
import static com.speedment.common.codegen.constant.DefaultType.wrapperFor;
import static com.speedment.common.codegen.util.Formatting.shortName;
import static com.speedment.generator.standard.internal.util.GenerateMethodBodyUtil.generateApplyResultSetBody;
import static com.speedment.runtime.core.util.DatabaseUtil.dbmsTypeOf;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.constant.SimpleType;
import com.speedment.common.codegen.model.AnnotationUsage;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Constructor;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.Method;
import com.speedment.common.codegen.model.Value;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.generator.translator.AbstractEntityAndManagerTranslator;
import com.speedment.generator.translator.TranslatorSupport;
import com.speedment.generator.translator.exception.SpeedmentTranslatorException;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.component.SqlAdapter;
import com.speedment.runtime.core.component.resultset.ResultSetMapperComponent;
import com.speedment.runtime.core.component.resultset.ResultSetMapping;
import com.speedment.runtime.core.component.sql.SqlTypeMapperHelper;
import com.speedment.runtime.core.db.SqlFunction;
import com.speedment.runtime.core.util.ResultSetUtil;
import com.speedment.runtime.typemapper.TypeMapper;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 * @since 3.0.1
 */
public final class GeneratedSqlAdapterTranslator extends AbstractEntityAndManagerTranslator<Class> {

    public static final String CREATE_HELPERS_METHOD_NAME = "createHelpers";
    public static final String INSTALL_METHOD_NAME = "installMethodName";
    public static final String OFFSET_PARAMETER_NAME = "offset";

    private final ResultSetMapperComponent resultSetMapperComponent;
    private final DbmsHandlerComponent dbmsHandlerComponent;

    public GeneratedSqlAdapterTranslator(Injector injector, Table table) {
        super(injector, table, Class::of);
        this.resultSetMapperComponent = injector.getOrThrow(ResultSetMapperComponent.class);
        this.dbmsHandlerComponent     = injector.getOrThrow(DbmsHandlerComponent.class);
    }

    @Override
    protected Class makeCodeGenModel(File file) {
        final Type tableIdentifierType = SimpleParameterizedType
            .create(TableIdentifier.class, getSupport().entityType());

        return newBuilder(file, getClassOrInterfaceName())
            .forEveryTable((clazz, table) -> {
                final Method createHelpers = Method.of(CREATE_HELPERS_METHOD_NAME, void.class)
                    .public_()
                    .add(withExecuteBefore(file))
                    .add(Field.of("projectComponent", ProjectComponent.class))
                    .add("final Project project = projectComponent.getProject();");

                clazz.public_().abstract_()
                    .add(
                        SimpleParameterizedType.create(
                            SqlAdapter.class,
                            getSupport().entityType()
                        )
                    )
                    ////////////////////////////////////////////////////////////
                    // Generate constructor                                   //
                    ////////////////////////////////////////////////////////////
                    .add(Constructor.of().protected_()
                        .add("this.tableIdentifier = "
                            + TableIdentifier.class.getSimpleName() + ".of("
                            + Stream.of(
                                getSupport().dbmsOrThrow().getId(),
                                getSupport().schemaOrThrow().getId(),
                                getSupport().tableOrThrow().getId()
                            ).map(s -> "\"" + s + "\"").collect(joining(", "))
                            + ");")
                    )
                    ////////////////////////////////////////////////////////////
                    // Generate members fields                                //
                    ////////////////////////////////////////////////////////////
                    .add(Field.of("tableIdentifier", tableIdentifierType).private_().final_())
                    ////////////////////////////////////////////////////////////
                    // Generate methods                                       //
                    ////////////////////////////////////////////////////////////
                    //                    .add(Method.of(INSTALL_METHOD_NAME, void.class).add(withExecuteBefore(file))
                    //                        .add(Field.of("streamSupplierComponent", SqlStreamSupplierComponent.class)
                    //                            .add(AnnotationUsage.of(WithState.class).set(Value.ofReference("RESOLVED")))
                    //                        )
                    //                        .add(Field.of("persistenceComponent", SqlPersistenceComponent.class)
                    //                            .add(AnnotationUsage.of(WithState.class).set(Value.ofReference("RESOLVED")))
                    //                        )
                    //                        .add("streamSupplierComponent.install(tableIdentifier, this::apply);")
                    //                        .add("persistenceComponent.install(tableIdentifier);")
                    //                    )
                    .add(generateApplyResultSet(getSupport(), file, table::columns))
                    .add(generateCreateEntity(file))
                    .add(
                        Method.of("identifier", tableIdentifierType)
                            .public_()
                            .add(OVERRIDE)
                            .add("return tableIdentifier;")
                    )
                    .add(
                        Method.of("entityMapper", SimpleParameterizedType.create(SqlFunction.class, ResultSet.class, getSupport().entityType()))
                            .public_()
                            .add(OVERRIDE)
                            .add("return entityMapper(0);")
                    )
                    .add(
                        Method.of("entityMapper", SimpleParameterizedType.create(SqlFunction.class, ResultSet.class, getSupport().entityType()))
                            .public_()
                            .add(OVERRIDE)
                            .add(Field.of(OFFSET_PARAMETER_NAME, int.class))
                            .add("return rs -> apply(rs, offset);")
                    )
                    .call(() ->
                        // Operate on enabled columns that has a type mapper
                        // that is not either empty, an identity mapper or a
                        // primitive mapper.
                        table.columns()
                            .filter(HasEnabled::test)
                            .filter(c -> c.getTypeMapper()
                            .filter(tm -> !"".equals(tm))
                            .filter(tm -> !tm.equals(TypeMapper.identity().getClass().getName()))
                            .filter(tm -> !tm.equals(TypeMapper.primitive().getClass().getName()))
                            .isPresent()
                            ).forEachOrdered(col -> {
                                // If the method has not yet been added, add it
                                if (clazz.getMethods().stream()
                                    .map(Method::getName)
                                    .noneMatch(CREATE_HELPERS_METHOD_NAME::equals)) {
                                    file.add(Import.of(Project.class));
                                    clazz.add(createHelpers);
                                }

                                // Append the line for this helper to the method
                                final TypeMapper<?, ?> tm = typeMappers().get(col);
                                final Type javaType = tm.getJavaType(col);

                                final String tmsName = helperName(col);
                                final Type tmsType = SimpleParameterizedType.create(
                                    SqlTypeMapperHelper.class,
                                    typeMappers().findDatabaseTypeOf(tm)
                                        .orElseThrow(() -> new SpeedmentTranslatorException(
                                        "Could not find appropriate "
                                        + "database type for column '" + col
                                        + "'. Available TypeMappers: " + availableTypeMappers()
                                    )),
                                    isPrimitive(javaType)
                                    ? wrapperFor(javaType)
                                    : javaType
                                );

                                clazz.add(Field.of(tmsName, tmsType).private_());

                                createHelpers
                                    .add(tmsName + " = " + SqlTypeMapperHelper.class.getSimpleName()
                                        + ".create(project, " + getSupport().entityName()
                                        + "." + getSupport().namer().javaStaticFieldName(col.getJavaName())
                                        + ", " + getSupport().entityName() + ".class);"
                                    );
                            })
                    );
            })
            .build();
    }

    private Method generateCreateEntity(File file) {
        final Type entityImplType = getSupport().entityImplType();
        file.add(Import.of(entityImplType));
        return Method.of("createEntity", entityImplType).protected_()
            .add("return new " + getSupport().entityImplName() + "();");
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

    private Method generateApplyResultSet(
        TranslatorSupport<Table> support,
        File file,
        Supplier<Stream<? extends Column>> columnsSupplier) {

        return Method.of("apply", support.entityType())
            .protected_()
            .add(SQLException.class)
            .add(Field.of("resultSet", ResultSet.class))
            .add(Field.of(OFFSET_PARAMETER_NAME, int.class))
            .add(generateApplyResultSetBody(
                this::readFromResultSet, support, file, columnsSupplier
            ));
    }

    private static final Set<java.lang.Class<?>> NULL_AWARE_GETTERS = Stream.of(
        String.class,
        BigDecimal.class,
        java.sql.Time.class,
        java.sql.Date.class,
        java.sql.Timestamp.class,
        Blob.class,
        Clob.class,
        Object.class
    ).collect(collectingAndThen(toSet(), Collections::unmodifiableSet));

    private String readFromResultSet(File file, Column c, AtomicInteger position) {
        final Dbms dbms = c.getParentOrThrow().getParentOrThrow().getParentOrThrow();

        final ResultSetMapping<?> mapping = resultSetMapperComponent.apply(
            dbmsTypeOf(dbmsHandlerComponent, c.getParentOrThrow().getParentOrThrow().getParentOrThrow()),
            c.findDatabaseType()
        );

        final java.lang.Class<?> typeMapperClass = typeMappers().get(c).getClass();
        final boolean isCustomTypeMapper = c.getTypeMapper().isPresent()
            && !TypeMapper.identity().getClass().isAssignableFrom(typeMapperClass)
            && !TypeMapper.primitive().getClass().isAssignableFrom(typeMapperClass);

        final StringBuilder sb = new StringBuilder();
        if (isCustomTypeMapper) {
            sb.append(helperName(c)).append(".apply(");
        }

        final String getterName = "get" + mapping.getResultSetMethodName(dbms);

        // We do not need to wrap-get some classes X since getX() returns null for null X:es.
        // UPDATE: Some objects, such as BigInteger, would get generated as rs.getBigInteger()
        // if they were non-nullable. This issue occurred when the Snowflake connector was
        // being implemented
        if (!NULL_AWARE_GETTERS.contains(mapping.getJavaClass())) {
            file.add(Import.of(ResultSetUtil.class).static_().setStaticMember("*"));

            if (isCastingRequired(c, getterName)) {
                file.add(Import.of(SimpleType.create(c.getDatabaseType())));
                sb.append("(").append(shortName(c.getDatabaseType())).append(") ");
            }

            sb.append(getterName).append("(resultSet, ")
                .append(position.getAndIncrement()).append(" + ").append(OFFSET_PARAMETER_NAME).append(")");
        } else {
            if (isCastingRequired(c, getterName)) {
                file.add(Import.of(SimpleType.create(c.getDatabaseType())));
                sb.append("(").append(shortName(c.getDatabaseType())).append(") ");
            }

            sb.append("resultSet.").append(getterName)
                .append("(").append(position.getAndIncrement()).append(" + ").append(OFFSET_PARAMETER_NAME)
                .append(")");
        }

        if (isCustomTypeMapper) {
            sb.append(")");
        }

        return sb.toString();
    }

    private boolean isCastingRequired(Column column, String getterName) {
        return ("getObject".equals(getterName)
            && !Object.class.getName().equals(column.getDatabaseType()));
    }

    private AnnotationUsage withExecuteBefore(File file) {
        file.add(Import.of(State.class).static_().setStaticMember("RESOLVED"));
        return AnnotationUsage.of(ExecuteBefore.class).set(Value.ofReference("RESOLVED"));
    }

    private String helperName(Column column) {
        return getSupport().namer()
            .javaVariableName(column.getJavaName()) + "Helper";
    }

    private String availableTypeMappers() {
        return typeMappers().stream()
            .map(Object::getClass)
            .map(java.lang.Class::getSimpleName)
            .distinct()
            .sorted()
            .collect(Collectors.joining(", "));
    }
}
