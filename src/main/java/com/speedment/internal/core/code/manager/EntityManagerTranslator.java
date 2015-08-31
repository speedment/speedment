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
import static com.speedment.internal.codegen.util.Formatting.*;
import com.speedment.internal.codegen.base.Generator;
import com.speedment.internal.codegen.lang.models.Field;
import com.speedment.internal.codegen.lang.models.File;
import com.speedment.internal.codegen.lang.models.Import;
import com.speedment.internal.codegen.lang.models.Interface;
import com.speedment.internal.codegen.lang.models.Method;
import com.speedment.internal.codegen.lang.models.Type;
import static com.speedment.internal.codegen.lang.models.constants.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.internal.codegen.lang.models.constants.DefaultAnnotationUsage.SUPPRESS_WARNINGS_UNCHECKED;
import static com.speedment.internal.codegen.lang.models.constants.DefaultType.OBJECT;
import static com.speedment.internal.codegen.lang.models.constants.DefaultType.VOID;
import com.speedment.config.Column;
import com.speedment.config.Dbms;
import com.speedment.config.Table;
import com.speedment.internal.core.manager.sql.SqlManager;
import com.speedment.internal.core.platform.component.ManagerComponent;
import com.speedment.internal.core.platform.component.ProjectComponent;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 *
 * @author pemi
 */
public final class EntityManagerTranslator extends EntityAndManagerTranslator<Interface> {

    public EntityManagerTranslator(Speedment speedment, Generator cg, Table configEntity) {
        super(speedment, cg, configEntity);
    }

    @Override
    protected Interface make(File file) {
        return new InterfaceBuilder(MANAGER.getName()).build()
            .public_()
            .add(Type.of(SqlManager.class).add(GENERIC_OF_ENTITY))
            .add(generatePrimaryKeyFor(file))
            .call(i -> file.add(Import.of(Type.of(Speedment.class))))
            .call(i -> file.add(Import.of(Type.of(ProjectComponent.class))))
            .add(Method.of("getTable", Type.of(Table.class)).default_().add(OVERRIDE)
                .add("return " + Speedment.class.getSimpleName()
                    + ".get().get(" + ProjectComponent.class.getSimpleName()
                    + ".class).getProject().findTableByName(\"" + table().getRelativeName(Dbms.class) + "\");"))
            //            .add(Method.of("getTableName", STRING).default_().add(OVERRIDE)
            //                .add("return \"" + table().getRelativeName(project()) + "\";"))

            .add(Method.of("getManagerClass", Type.of(Class.class).add(GENERIC_OF_MANAGER)).default_().add(OVERRIDE)
                .add("return " + MANAGER.getName() + ".class;"))
            .add(Method.of("getEntityClass", Type.of(Class.class).add(GENERIC_OF_ENTITY)).default_().add(OVERRIDE)
                .add("return " + ENTITY.getName() + ".class;"))
//            .add(Method.of("getBuilderClass", Type.of(Class.class).add(GENERIC_OF_BUILDER)).default_().add(OVERRIDE)
//                .add("return " + BUILDER.getName() + ".class;"))
            .add(generateGet(file))
            .add(generateSet(file))
            .call(i -> file.add(Import.of(Type.of(Speedment.class))))
            .call(i -> file.add(Import.of(Type.of(ManagerComponent.class))))
            .add(Method.of("get", MANAGER.getType()).static_().add(SUPPRESS_WARNINGS_UNCHECKED)
                .add("return " + Speedment.class.getSimpleName()
                    + ".get().get(" + ManagerComponent.class.getSimpleName()
                    + ".class).manager(" + MANAGER.getName() + ".class);"));
    }

    protected Method generatePrimaryKeyFor(File file) {
        final Method method = Method.of("primaryKeyFor", typeOfPK()).default_().add(OVERRIDE)
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

    protected Method generateGet(File file) {
        file.add(Import.of(Type.of(IllegalArgumentException.class)));
        return Method.of("get", OBJECT).default_().add(OVERRIDE)
            .add(Field.of("entity", ENTITY.getType()))
            .add(Field.of("column", Type.of(Column.class)))
            .add("switch (column.getName()) " + block(
                columns().map(c -> "case \"" + c.getName() + "\" : return entity." + GETTER_METHOD_PREFIX + typeName(c) + "();").collect(Collectors.joining(nl()))
                + nl() + "default : throw new IllegalArgumentException(\"Unknown column '\" + column.getName() + \"'.\");"
            ));
    }

    protected Method generateSet(File file) {
        file.add(Import.of(Type.of(IllegalArgumentException.class)));
        return Method.of("set", VOID).default_().add(OVERRIDE)
            .add(Field.of("entity", BUILDER.getType()))
            .add(Field.of("column", Type.of(Column.class)))
            .add(Field.of("value", Type.of(Object.class)))
            .add("switch (column.getName()) " + block(
                columns()
                .peek(c -> file.add(Import.of(Type.of(c.getMapping()))))
                .map(c -> "case \"" + c.getName() + "\" : entity." + SETTER_METHOD_PREFIX + typeName(c) + "((" + c.getMapping().getSimpleName() + ") value); break;").collect(Collectors.joining(nl()))
                + nl() + "default : throw new IllegalArgumentException(\"Unknown column '\" + column.getName() + \"'.\");"
            ));
    }

    @Override
    protected String getJavadocRepresentText() {
        return "A manager";
    }

    @Override
    protected String getFileName() {
        return MANAGER.getName();
    }
}
