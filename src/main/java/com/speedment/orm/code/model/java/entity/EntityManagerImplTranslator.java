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
package com.speedment.orm.code.model.java.entity;

import com.speedment.codegen.base.CodeGenerator;
import com.speedment.codegen.lang.models.Field;
import com.speedment.codegen.lang.models.File;
import com.speedment.codegen.lang.models.Import;
import com.speedment.codegen.lang.models.Class;
import com.speedment.codegen.lang.models.Generic;
import com.speedment.codegen.lang.models.Method;
import com.speedment.codegen.lang.models.Type;
import static com.speedment.codegen.lang.models.constants.DefaultAnnotationUsage.OVERRIDE;
import com.speedment.orm.config.model.Table;
import com.speedment.orm.core.manager.AbstractManager;
import com.speedment.orm.platform.Platform;
import com.speedment.orm.platform.component.ProjectComponent;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class EntityManagerImplTranslator extends BaseEntityTranslator<Class> {

    public EntityManagerImplTranslator(CodeGenerator cg, Table configEntity) {
        super(cg, configEntity);
    }

    @Override
    protected Class make(File file) {
        return new ClassBuilder(MANAGER.getImplName()).build()
            .public_()
            .setSupertype(Type.of(AbstractManager.class)
                .add(Generic.of().add(typeOfPK()))
                .add(Generic.of().add(ENTITY.getType()))
                .add(Generic.of().add(BUILDER.getType()))
            )
            .add(MANAGER.getType())
            
            .call(i -> file.add(Import.of(Type.of(Platform.class))))
            .call(i -> file.add(Import.of(Type.of(ProjectComponent.class))))
            .add(Method.of("getTable", Type.of(Table.class)).public_().add(OVERRIDE)
                .add("return " + Platform.class.getSimpleName() + 
                    ".get().get(" + ProjectComponent.class.getSimpleName() + 
                    ".class).getProject().findTableByName(getTableName());"))
            
            .call(i -> file.add(Import.of(ENTITY.getImplType())))
            .add(Method.of("builder", BUILDER.getType()).public_().add(OVERRIDE)
                .add("return new " + ENTITY.getImplName() + "();"))
            
            .add(Method.of("toBuilder", BUILDER.getType()).public_().add(OVERRIDE)
                .add(Field.of("prototype", ENTITY.getType()))
                .add("return new " + ENTITY.getImplName() + "(prototype);"))
            
            .call(i -> file.add(Import.of(Type.of(Stream.class))))
            .add(Method.of("stream", Type.of(Stream.class).add(GENERIC_OF_ENTITY)).public_().add(OVERRIDE)
                .add("return Stream.empty();")) //TODO MUST BE FIXED!
            
            .add(Method.of("persist", ENTITY.getType()).public_().add(OVERRIDE)
                .add(Field.of("entity", ENTITY.getType()))
                .add("return entity;")) //TODO MUST BE FIXED!
            
            .add(Method.of("remove", ENTITY.getType()).public_().add(OVERRIDE)
                .add(Field.of("entity", ENTITY.getType()))
                .add("return entity;")) //TODO MUST BE FIXED!
            ;
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
}