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
import com.speedment.fika.codegen.Generator;
import com.speedment.fika.codegen.model.Field;
import com.speedment.fika.codegen.model.File;
import com.speedment.fika.codegen.model.Generic;
import com.speedment.fika.codegen.model.Import;
import com.speedment.fika.codegen.model.Interface;
import com.speedment.fika.codegen.model.Method;
import com.speedment.fika.codegen.model.Type;
import com.speedment.runtime.component.ProjectComponent;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Table;
import static com.speedment.fika.codegen.internal.model.constant.DefaultAnnotationUsage.OVERRIDE;
import com.speedment.generator.internal.EntityAndManagerTranslator;
import com.speedment.runtime.internal.manager.sql.SqlManager;
import static com.speedment.runtime.internal.util.document.DocumentUtil.Name.DATABASE_NAME;
import java.util.Arrays;
import java.util.stream.Collectors;
import static com.speedment.runtime.internal.util.document.DocumentUtil.relativeName;

/**
 *
 * @author Emil Forslund
 */
public final class GeneratedEntityManagerTranslator extends EntityAndManagerTranslator<Interface> {

    public GeneratedEntityManagerTranslator(Speedment speedment, Generator gen, Table table) {
        super(speedment, gen, table, Interface::of);
    }

    @Override
    protected Interface makeCodeGenModel(File file) {
        return newBuilder(file, getSupport().generatedManagerName()).build()
            .public_()
            .add(Type.of(SqlManager.class).add(Generic.of().add(getSupport().entityType())))
            .add(generatePrimaryKeyFor(file))
            .call(i -> file.add(Import.of(Type.of(ProjectComponent.class))))
            .add(Method.of("getTable", Type.of(Table.class)).default_().add(OVERRIDE)
                .add("return speedment()"
                    + ".get(" + ProjectComponent.class.getSimpleName()
                    + ".class).getProject().findTableByName(\""
                    + relativeName(getSupport().tableOrThrow(), Dbms.class, DATABASE_NAME) + "\");"
                )
            )
            .add(Method.of("getManagerClass", Type.of(Class.class).add(Generic.of().add(getSupport().managerType()))).default_().add(OVERRIDE)
                .add("return " + getSupport().managerName() + ".class;"))
            .add(Method.of("getEntityClass", Type.of(Class.class).add(Generic.of().add(getSupport().entityType()))).default_().add(OVERRIDE)
                .add("return " + getSupport().entityName() + ".class;"))
            .add(generateGetPrimaryKeyClasses(file));
    }

    protected Method generatePrimaryKeyFor(File file) {
        final Method method = Method.of("primaryKeyFor", typeOfPK()).default_().add(OVERRIDE)
            .add(Field.of("entity", getSupport().entityType()));

        if (primaryKeyColumns().count() == 1) {
            method.add("return entity.get" + getSupport().typeName(primaryKeyColumns().findAny().get().findColumn().get()) + "();");
        } else {
            file.add(Import.of(Type.of(Arrays.class)));
            method.add(primaryKeyColumns()
                .map(pkc -> "entity.get" + getSupport().typeName(pkc.findColumn().get()) + "()")
                .collect(Collectors.joining(", ", "return Arrays.asList(", ");"))
            );
        }

        return method;
    }



    protected Method generateGetPrimaryKeyClasses(File file) {
        return Method.of("getPrimaryKeyClasses", pkTupleType()).add(OVERRIDE);
    }

    @Override
    protected String getJavadocRepresentText() {
        return "The generated base manager";
    }

    @Override
    protected String getClassOrInterfaceName() {
        return getSupport().generatedManagerName();
    }

    @Override
    public boolean isInGeneratedPackage() {
        return true;
    }
}