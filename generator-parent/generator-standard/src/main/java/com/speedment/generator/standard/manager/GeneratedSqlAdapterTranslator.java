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
import com.speedment.common.codegen.model.Value;
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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;


import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.runtime.core.exception.SpeedmentException;
import static com.speedment.runtime.core.util.DatabaseUtil.dbmsTypeOf;
import com.speedment.generator.translator.component.TypeMapperComponent;
import com.speedment.generator.translator.exception.SpeedmentTranslatorException;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.core.component.sql.SqlPersistenceComponent;
import com.speedment.runtime.core.component.sql.SqlStreamSupplierComponent;
import com.speedment.runtime.core.component.sql.SqlTypeMapperHelper;
import com.speedment.runtime.typemapper.TypeMapper;
import static java.util.stream.Collectors.joining;
import static com.speedment.generator.standard.internal.util.GenerateMethodBodyUtil.generateApplyResultSetBody;

/**
 *
 * @author  Emil Forslund
 * @since   3.0.1
 */
public final class GeneratedSqlAdapterTranslator extends AbstractEntityAndManagerTranslator<Class> {

    public final static String 
        CREATE_HELPERS_METHOD_NAME = "createHelpers",
        INSTALL_METHOD_NAME        = "installMethodName",
        ENTITY_COPY_METHOD_NAME    = "entityCopy",
        ENTITY_CREATE_METHOD_NAME  = "entityCreate",
        FIELDS_METHOD              = "fields",
        PRIMARY_KEYS_FIELDS_METHOD = "primaryKeyFields";

    private @Inject ResultSetMapperComponent resultSetMapperComponent;
    private @Inject DbmsHandlerComponent dbmsHandlerComponent;
    private @Inject TypeMapperComponent typeMapperComponent;

    public GeneratedSqlAdapterTranslator(Table table) {
        super(table, Class::of);
    }

    @Override
    protected Class makeCodeGenModel(File file) {
        final Type tableIdentifierType = SimpleParameterizedType
            .create(TableIdentifier.class, getSupport().entityType());
        
        return newBuilder(file, getClassOrInterfaceName())
            .forEveryTable((clazz, table) -> {
                final Method createHelpers = Method.of(CREATE_HELPERS_METHOD_NAME, void.class)
                    .add(withExecuteBefore(file))
                    .add(Field.of("projectComponent", ProjectComponent.class))
                    .add("final Project project = projectComponent.getProject();");
                
                clazz.public_().abstract_()
                    
                    // Generate conxtructor
                    .add(Constructor.of().protected_()
                        .add("this.tableIdentifier = "
                            + TableIdentifier.class.getSimpleName() + ".of("
                            + Stream.of(
                                getSupport().dbmsOrThrow().getName(), 
                                getSupport().schemaOrThrow().getName(), 
                                getSupport().tableOrThrow().getName()
                            ).map(s -> "\"" + s + "\"").collect(joining(", "))
                            + ");")
                    )
                    
                    // Generate member fields
                    .add(Field.of("tableIdentifier", tableIdentifierType).private_().final_())
//                    .add(Field.of("manager", getSupport().managerType()).add(inject()).private_())
                    
                    // Generate methods
                    .add(Method.of(INSTALL_METHOD_NAME, void.class).add(withExecuteBefore(file))
                        .add(Field.of("streamSupplierComponent", SqlStreamSupplierComponent.class))
                        .add(Field.of("persistenceComponent", SqlPersistenceComponent.class))
                        .add("streamSupplierComponent.install(tableIdentifier, this::apply);")
                        .add("persistenceComponent.install(tableIdentifier);")
                    )
                    
                    .add(generateApplyResultSet(getSupport(), file, table::columns))
                    .add(generateCreateEntity(file))
                    
                    .call(() -> {
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
                                final String tmName = col.getTypeMapper().get();
                                final TypeMapper<?, ?> tm = typeMapperComponent.get(col);
                                
                                final String tmsName = helperName(col);
                                final Type tmsType = SimpleParameterizedType.create(
                                    SqlTypeMapperHelper.class,
                                    typeMapperComponent.findDatabaseTypeOf(tm)
                                        .orElseThrow(() -> new SpeedmentTranslatorException(
                                            "Could not find appropriate " + 
                                            "database type for column '" + col + 
                                            "'."
                                        )),
                                    tm.getJavaType(col)
                                );
                                
                                clazz.add(Field.of(tmsName, tmsType).private_());
                                
                                createHelpers
                                    .add(tmsName + " = " + SqlTypeMapperHelper.class.getSimpleName() + 
                                        ".create(project, " + getSupport().entityName() + 
                                        "." + getSupport().namer().javaStaticFieldName(col.getJavaName()) + 
                                        ", " + getSupport().entityName() + ".class);"
                                    );
                            });
                    })
                    ;
            })
            .build();
    }
    
    private Method generateCreateEntity(File file) {
        final Type entityImplType = getSupport().entityImplType();
        file.add(Import.of(entityImplType));
        return Method.of("createEntity", entityImplType).protected_()
            .add("return new "+getSupport().entityImplName()+"();");
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

    private Method generateApplyResultSet(TranslatorSupport<Table> support, File file, Supplier<Stream<? extends Column>> columnsSupplier) {
        return Method.of("apply", support.entityType())
            .protected_()
            .add(SpeedmentException.class)
            .add(Field.of("resultSet", ResultSet.class))
            .add(generateApplyResultSetBody(this::readFromResultSet, support, file, columnsSupplier));
    }

    private String readFromResultSet(File file, Column c, AtomicInteger position) {
        final Dbms dbms = c.getParentOrThrow().getParentOrThrow().getParentOrThrow();

        final ResultSetMapping<?> mapping = resultSetMapperComponent.apply(
            dbmsTypeOf(dbmsHandlerComponent, c.getParentOrThrow().getParentOrThrow().getParentOrThrow()),
            c.findDatabaseType()
        );

        final java.lang.Class<?> typeMapperClass = typeMapperComponent.get(c).getClass();
        final boolean isCustomTypeMapper = c.getTypeMapper().isPresent()
            && !TypeMapper.identity().getClass().isAssignableFrom(typeMapperClass)
            && !TypeMapper.primitive().getClass().isAssignableFrom(typeMapperClass);

        final StringBuilder sb = new StringBuilder();
        if (isCustomTypeMapper) {
            sb.append(helperName(c)).append(".apply(");
        }
        
        final String getterName = "get" + mapping.getResultSetMethodName(dbms);
        if (c.isNullable()) {
            
            file.add(Import.of(ResultSetUtil.class).static_().setStaticMember("*"));
            sb.append(getterName).append("(resultSet, ")
                .append(position.getAndIncrement()).append(")");
        } else {
            sb.append("resultSet.").append(getterName)
                .append("(").append(position.getAndIncrement()).append(")");
        }
        
        if (isCustomTypeMapper) {
            sb.append(")");
        }

        return sb.toString();
    }

    private AnnotationUsage withExecuteBefore(File file) {
        file.add(Import.of(State.class).static_().setStaticMember("RESOLVED"));
        return AnnotationUsage.of(ExecuteBefore.class).set(Value.ofReference("RESOLVED"));
    }

    private AnnotationUsage inject() {
        return AnnotationUsage.of(Inject.class);
    }
    
    private String helperName(Column column) {
        return getSupport().namer()
            .javaVariableName(column.getJavaName()) + "Helper";
    }
    
    private enum TypeMapperType {
        IDENTITY, PRIMITIVE, OTHER;
        
        static TypeMapperType of(TypeMapperComponent mappers, Column col) {
            
            if (!col.getTypeMapper().isPresent()) {
                return IDENTITY;
            }
            
            final TypeMapper<?, ?> mapper = mappers.get(col);
            
            if (TypeMapper.identity().getClass().isAssignableFrom(mapper.getClass())) {
                return IDENTITY;
            } else if (TypeMapper.primitive().getClass().isAssignableFrom(mapper.getClass())) {
                return PRIMITIVE;
            } else {
                return OTHER;
            }
        }
    }
}