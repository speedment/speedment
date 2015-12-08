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
package com.speedment.internal.core.code.manager;

import com.speedment.Speedment;
import com.speedment.internal.core.code.EntityAndManagerTranslator;
import com.speedment.internal.codegen.base.Generator;
import com.speedment.internal.codegen.lang.models.Field;
import com.speedment.internal.codegen.lang.models.File;
import com.speedment.internal.codegen.lang.models.Import;
import com.speedment.internal.codegen.lang.models.Class;
import com.speedment.internal.codegen.lang.models.Constructor;
import com.speedment.internal.codegen.lang.models.Generic;
import com.speedment.internal.codegen.lang.models.Method;
import com.speedment.internal.codegen.lang.models.Type;
import static com.speedment.internal.codegen.lang.models.constants.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.internal.codegen.lang.models.constants.DefaultType.OBJECT;
import static com.speedment.internal.codegen.lang.models.constants.DefaultType.VOID;
import com.speedment.internal.codegen.util.Formatting;
import static com.speedment.internal.core.code.DefaultJavaClassTranslator.GETTER_METHOD_PREFIX;
import static com.speedment.internal.core.code.DefaultJavaClassTranslator.SETTER_METHOD_PREFIX;
import com.speedment.config.Column;
import com.speedment.config.Dbms;
import com.speedment.config.Table;
import com.speedment.internal.core.manager.sql.AbstractSqlManager;
import com.speedment.exception.SpeedmentException;
import com.speedment.component.JavaTypeMapperComponent;
import com.speedment.config.mapper.TypeMapper;
import com.speedment.internal.codegen.lang.models.values.ReferenceValue;
import static com.speedment.internal.codegen.util.Formatting.block;
import static com.speedment.internal.codegen.util.Formatting.nl;
import com.speedment.internal.core.platform.SpeedmentFactory;
import com.speedment.internal.core.runtime.typemapping.JavaTypeMapping;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public final class EntityManagerImplTranslator extends EntityAndManagerTranslator<Class> {

    private final Speedment speedment;

    private static final String SPEEDMENT_VARIABLE_NAME = "speedment";

    public EntityManagerImplTranslator(Speedment speedment, Generator cg, Table configEntity) {
        super(speedment, cg, configEntity);
        this.speedment = SpeedmentFactory.newSpeedmentInstance(); // default values from here.. Todo: Make pluggable later
    }

    @Override
    protected Class make(File file) {

        return new ClassBuilder(MANAGER.getImplName())
            .addColumnConsumer((i, c) -> {

                final TypeMapper<?, ?> mapper = c.getTypeMapper();
                final java.lang.Class<?> javaType = mapper.getJavaType();
                final java.lang.Class<?> dbType = mapper.getDatabaseType();
                final Type mapperType = Type.of(TypeMapper.class).add(Generic.of().add(Type.of(dbType))).add(Generic.of().add(Type.of(javaType)));

                file.add(Import.of(Type.of(mapper.getClass())));

                i.add(Field.of(typeMapperName(c), mapperType)
                    .private_().final_()
                    .set(new ReferenceValue("new " + mapper.getClass().getSimpleName() + "()"))
                );
            })
            .build()
            .public_()
            .setSupertype(Type.of(AbstractSqlManager.class)
                .add(Generic.of().add(ENTITY.getType()))
            )
            .call(i -> file.add(Import.of(ENTITY.getImplType())))
            .add(Constructor.of()
                .public_()
                .add(Field.of(SPEEDMENT_VARIABLE_NAME, Type.of(Speedment.class)))
                .add("super(" + SPEEDMENT_VARIABLE_NAME + ");")
                .add("setSqlEntityMapper(this::defaultReadEntity);"))
            .add(Method.of("getEntityClass", Type.of(java.lang.Class.class).add(GENERIC_OF_ENTITY)).public_().add(OVERRIDE)
                .add("return " + ENTITY.getName() + ".class;"))
            .add(generateGet(file))
            .add(generateSet(file))
            .add(Method.of("getTable", Type.of(Table.class)).public_().add(OVERRIDE)
                .add("return " + SPEEDMENT_VARIABLE_NAME
                    + ".getProjectComponent()"
                    + ".getProject().findTableByName(\"" + table().getRelativeName(Dbms.class) + "\");"))
            .
            add(defaultReadEntity(file))
            .add(Method.of("newInstance", ENTITY.getType())
                .public_().add(OVERRIDE)
                .add("return new " + Formatting.shortName(ENTITY.getImplType().getName()) + "(" + SPEEDMENT_VARIABLE_NAME + ");")
                .call($ -> file.add(Import.of(ENTITY.getImplType())))
            )
            .add(generatePrimaryKeyFor(file));
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

    private Method defaultReadEntity(File file) {

        file.add(Import.of(Type.of(SQLException.class)));
        file.add(Import.of(Type.of(SpeedmentException.class)));

        final Method method = Method.of("defaultReadEntity", ENTITY.getType())
            .protected_()
            .add(Field.of("resultSet", Type.of(ResultSet.class)))
            .add("final " + ENTITY.getName() + " entity = newInstance();");

        final JavaTypeMapperComponent mapperComponent = speedment.getJavaTypeMapperComponent();
        final Stream.Builder<String> streamBuilder = Stream.builder();

        final AtomicInteger position = new AtomicInteger(1);
        columns().forEachOrdered(c -> {

            final JavaTypeMapping<?> mapping = mapperComponent.apply(dbms().getType(), c.getTypeMapper().getDatabaseType());
            final StringBuilder sb = new StringBuilder()
                .append("entity.set")
                .append(typeName(c))
                .append("(")
                .append(typeMapperName(c))
                .append(".toJavaType(");

            final String getterName = "get" + mapping.getResultSetMethodName(dbms());

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
                    .append(mapping.getResultSetMethodName(dbms()))
                    .append("(").append(position.getAndIncrement()).append(")");
            } else {
                sb
                    .append("get")
                    .append(mapping.getResultSetMethodName(dbms()))
                    .append("(resultSet, ")
                    .append(position.getAndIncrement()).append(")");
            }
            sb.append("));");
            streamBuilder.add(sb.toString());
        });

        method
            .add("try " + block(streamBuilder.build()))
            .add("catch (" + SQLException.class.getSimpleName() + " sqle) " + block(
                "throw new " + SpeedmentException.class.getSimpleName() + "(sqle);"
            ))
            .add("return entity;");

        return method;
    }

    @Override
    protected String getJavadocRepresentText() {
        return "A manager implementation";
    }

    @Override
    protected String getFileName() {
        return MANAGER.getImplName();
    }

    @Override
    protected boolean isInImplPackage() {
        return true;
    }

    public Type getImplType() {
        return MANAGER.getImplType();
    }

    protected Method generateGet(File file) {
        file.add(Import.of(Type.of(IllegalArgumentException.class)));
        return Method.of("get", OBJECT).public_().add(OVERRIDE)
            .add(Field.of("entity", ENTITY.getType()))
            .add(Field.of("column", Type.of(Column.class)))
            .add("switch (column.getName()) " + block(
                columns().map(c -> "case \"" + c.getName() + "\" : return entity." + GETTER_METHOD_PREFIX + typeName(c) + "();").collect(Collectors.joining(nl()))
                + nl() + "default : throw new IllegalArgumentException(\"Unknown column '\" + column.getName() + \"'.\");"
            ));
    }

    protected Method generateSet(File file) {
        file.add(Import.of(Type.of(IllegalArgumentException.class)));
        return Method.of("set", VOID).public_().add(OVERRIDE)
            .add(Field.of("entity", ENTITY.getType()))
            .add(Field.of("column", Type.of(Column.class)))
            .add(Field.of("value", Type.of(Object.class)))
            .add("switch (column.getName()) " + block(
                columns()
                .peek(c -> file.add(Import.of(Type.of(c.getTypeMapper().getJavaType()))))
                .map(c -> "case \"" + c.getName() + "\" : entity." + SETTER_METHOD_PREFIX + typeName(c) + "((" + c.getTypeMapper().getJavaType().getSimpleName() + ") value); break;").collect(Collectors.joining(nl()))
                + nl() + "default : throw new IllegalArgumentException(\"Unknown column '\" + column.getName() + \"'.\");"
            ));
    }

    protected Method generatePrimaryKeyFor(File file) {
        final Method method = Method.of("primaryKeyFor", typeOfPK()).public_().add(OVERRIDE)
            .add(Field.of("entity", ENTITY.getType()));

        final int count = (int) primaryKeyColumns().count();
        switch (count) {
            case 0: {
                file.add(Import.of(Type.of(Collections.class)));
                method.add("return Collections.emptyList();");
                break;
            }
            case 1: {
                method.add("return entity.get" + typeName(primaryKeyColumns().findFirst().get().getColumn()) + "();");
                break;
            }
            default: {
                file.add(Import.of(Type.of(Arrays.class)));
                method.add(primaryKeyColumns()
                    .map(pkc -> "entity.get" + typeName(pkc.getColumn()) + "()")
                    .collect(Collectors.joining(", ", "return Arrays.asList(", ");"))
                );
                break;
            }
        }

        return method;
    }

    private String typeMapperName(Column c) {
        return variableName(c) + "TypeMapper";
    }
}
