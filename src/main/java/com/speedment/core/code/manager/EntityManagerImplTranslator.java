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
package com.speedment.core.code.manager;

import com.speedment.api.Speedment;
import com.speedment.core.code.EntityAndManagerTranslator;
import com.speedment.codegen.base.Generator;
import com.speedment.codegen.lang.models.Field;
import com.speedment.codegen.lang.models.File;
import com.speedment.codegen.lang.models.Import;
import com.speedment.codegen.lang.models.Class;
import com.speedment.codegen.lang.models.Constructor;
import com.speedment.codegen.lang.models.Generic;
import com.speedment.codegen.lang.models.Method;
import com.speedment.codegen.lang.models.Type;
import static com.speedment.codegen.lang.models.constants.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.codegen.lang.models.constants.DefaultType.OBJECT;
import static com.speedment.codegen.lang.models.constants.DefaultType.VOID;
import com.speedment.codegen.util.Formatting;
import static com.speedment.core.code.DefaultJavaClassTranslator.GETTER_METHOD_PREFIX;
import static com.speedment.core.code.DefaultJavaClassTranslator.SETTER_METHOD_PREFIX;
import com.speedment.api.config.Column;
import com.speedment.api.config.Dbms;
import com.speedment.api.config.Table;
import com.speedment.core.manager.sql.AbstractSqlManager;
import com.speedment.api.exception.SpeedmentException;
import com.speedment.core.platform.SpeedmentImpl;
import com.speedment.core.platform.component.JavaTypeMapperComponent;
import com.speedment.core.platform.component.ProjectComponent;
import com.speedment.core.runtime.typemapping.JavaTypeMapping;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static com.speedment.codegen.util.Formatting.block;
import static com.speedment.codegen.util.Formatting.nl;
import static com.speedment.codegen.util.Formatting.block;
import static com.speedment.codegen.util.Formatting.nl;

/**
 *
 * @author pemi
 */
public class EntityManagerImplTranslator extends EntityAndManagerTranslator<Class> {

    private static final Speedment SPEEDMENT = new SpeedmentImpl(); // default values from here.. Todo: Make pluggable later
    
    private static final String SPEEDMENT_VARIABLE_NAME = "speedment";

    public EntityManagerImplTranslator(Speedment speedment, Generator cg, Table configEntity) {
        super(speedment, cg, configEntity);
    }

    @Override
    protected Class make(File file) {

        return new ClassBuilder(MANAGER.getImplName()).build()
            .public_()
            .setSupertype(Type.of(AbstractSqlManager.class)
                .add(Generic.of().add(ENTITY.getType()))
            )
            // .add(MANAGER.getType())
            //            .call(i -> file.add(Import.of(Type.of(Platform.class))))
            //            .call(i -> file.add(Import.of(Type.of(ProjectComponent.class))))
            //            .add(Method.of("getTable", Type.of(Table.class)).public_().add(OVERRIDE)
            //                .add("return " + Platform.class.getSimpleName() + 
            //                    ".get().get(" + ProjectComponent.class.getSimpleName() + 
            //                    ".class).getProject().findTableByName(getTableName());"))

            .call(i -> file.add(Import.of(ENTITY.getImplType())))
//            .add(Field.of("speedment_", Type.of(Speedment.class)).private_().final_())
            .add(Constructor.of()
                .public_()
                .add(Field.of(SPEEDMENT_VARIABLE_NAME, Type.of(Speedment.class)))
                .add("super(" + SPEEDMENT_VARIABLE_NAME + ");")
                .add("setSqlEntityMapper(this::defaultReadEntity);"))
            //            .add(Method.of("builder", BUILDER.getType()).public_().add(OVERRIDE)
            //                .add("return new " + ENTITY.getImplName() + "();"))
            //            .add(Method.of("toBuilder", BUILDER.getType()).public_().add(OVERRIDE)
            //                .add(Field.of("prototype", ENTITY.getType()))
            //                .add("return new " + ENTITY.getImplName() + "(prototype);"))

            .add(Method.of("getEntityClass", Type.of(java.lang.Class.class).add(GENERIC_OF_ENTITY)).public_().add(OVERRIDE)
                .add("return " + ENTITY.getName() + ".class;"))
            .add(generateGet(file))
            .add(generateSet(file))
            .add(Method.of("getTable", Type.of(Table.class)).public_().add(OVERRIDE)
                .add("return " + SPEEDMENT_VARIABLE_NAME
                    + ".get(" + ProjectComponent.class.getSimpleName()
                    + ".class).getProject().findTableByName(\"" + table().getRelativeName(Dbms.class) + "\");"))
            .call($ -> file.add(Import.of(Type.of(ProjectComponent.class))))
            //.call(i -> file.add(Import.of(Type.of(Stream.class))))
            //                .add(Method.of("stream", Type.of(Stream.class).add(GENERIC_OF_ENTITY)).public_().add(OVERRIDE)
            //                        .add("return Stream.empty();")) //TODO MUST BE FIXED!

            //                .add(Method.of("persist", ENTITY.getType()).public_().add(OVERRIDE)
            //                        .add(Field.of("entity", ENTITY.getType()))
            //                        .add("return entity;")) //TODO MUST BE FIXED!

            //                .add(Method.of("remove", ENTITY.getType()).public_().add(OVERRIDE)
            //                        .add(Field.of("entity", ENTITY.getType()))
            //                        .add("return entity;")) //TODO MUST BE FIXED!
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

        final JavaTypeMapperComponent mapperComponent = SPEEDMENT.get(JavaTypeMapperComponent.class);
        final Stream.Builder<String> streamBuilder = Stream.builder();

        columns().forEachOrdered(c -> {

            final JavaTypeMapping<?> mapping = mapperComponent.apply(dbms().getType(), c.getMapping());
            final StringBuilder sb = new StringBuilder()
                .append("entity.set")
                .append(typeName(c))
                .append("(");

            final String getterName = "get" + mapping.getResultSetMethodName(dbms());

            if (Stream.of(ResultSet.class.getMethods())
                .map(java.lang.reflect.Method::getName)
                .anyMatch(getterName::equals)
                && !c.isNullable()) {
                sb
                    .append("resultSet.")
                    .append("get")
                    .append(mapping.getResultSetMethodName(dbms()))
                    .append("(\"").append(c.getName()).append("\")");
            } else {
                sb
                    .append("get")
                    .append(mapping.getResultSetMethodName(dbms()))
                    .append("(resultSet, ")
                    .append("\"").append(c.getName()).append("\")");
            }
            sb.append(");");
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
                .peek(c -> file.add(Import.of(Type.of(c.getMapping()))))
                .map(c -> "case \"" + c.getName() + "\" : entity." + SETTER_METHOD_PREFIX + typeName(c) + "((" + c.getMapping().getSimpleName() + ") value); break;").collect(Collectors.joining(nl()))
                + nl() + "default : throw new IllegalArgumentException(\"Unknown column '\" + column.getName() + \"'.\");"
            ));
    }

    protected Method generatePrimaryKeyFor(File file) {
        final Method method = Method.of("primaryKeyFor", typeOfPK()).public_().add(OVERRIDE)
            .add(Field.of("entity", ENTITY.getType()));

        if (primaryKeyColumns().count() == 1) {
            method.add("return entity.get" + typeName(primaryKeyColumns().findAny().get().getColumn()) + "();");
        } else {
            file.add(Import.of(Type.of(Arrays.class)));
            method.add(primaryKeyColumns()
                .map(pkc -> "entity.get" + typeName(pkc.getColumn()) + "()")
                .collect(Collectors.joining(", ", "return Arrays.asList(", ");"))
            );
        }

        return method;
    }

}
