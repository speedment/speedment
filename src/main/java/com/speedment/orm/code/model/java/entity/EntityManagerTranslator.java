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
import com.speedment.codegen.lang.models.File;
import com.speedment.codegen.lang.models.Import;
import com.speedment.codegen.lang.models.Interface;
import com.speedment.codegen.lang.models.Method;
import com.speedment.codegen.lang.models.Type;
import static com.speedment.codegen.lang.models.constants.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.codegen.lang.models.constants.DefaultAnnotationUsage.SUPPRESS_WARNINGS_UNCHECKED;
import static com.speedment.codegen.lang.models.constants.DefaultType.STRING;
import com.speedment.orm.config.model.Table;
import com.speedment.orm.core.manager.Manager;
import com.speedment.orm.platform.Platform;
import com.speedment.orm.platform.component.ManagerComponent;

/**
 *
 * @author pemi
 */
public class EntityManagerTranslator extends BaseEntityTranslator<Interface> {

    public EntityManagerTranslator(CodeGenerator cg, Table configEntity) {
        super(cg, configEntity);
    }

    @Override
    protected Interface make(File file) {
        return new InterfaceBuilder(MANAGER.getName()).build()
            .public_()
            .add(Type.of(Manager.class).add(GENERIC_OF_ENTITY).add(GENERIC_OF_BUILDER))
            
            .add(Method.of("getTableName", STRING).default_().add(OVERRIDE)
                .add("return \"" + table().getRelativeName(project()) + "\";"))
            
            .add(Method.of("getManagerClass", Type.of(Class.class).add(GENERIC_OF_MANAGER)).default_().add(OVERRIDE)
                .add("return " + MANAGER.getName() + ".class;"))
            
            .add(Method.of("getEntityClass", Type.of(Class.class).add(GENERIC_OF_ENTITY)).default_().add(OVERRIDE)
                .add("return " + ENTITY.getName() + ".class;"))
            
            .add(Method.of("getBuilderClass", Type.of(Class.class).add(GENERIC_OF_BUILDER)).default_().add(OVERRIDE)
                .add("return " + BUILDER.getName() + ".class;"))
            
            .call(i -> file.add(Import.of(Type.of(Platform.class))))
            .call(i -> file.add(Import.of(Type.of(ManagerComponent.class))))
            .add(Method.of("get", MANAGER.getType()).static_().add(SUPPRESS_WARNINGS_UNCHECKED)
                .add("return (" + MANAGER.getName() + ") " + Platform.class.getSimpleName() + 
                    ".get().get(" + ManagerComponent.class.getSimpleName() + 
                    ".class).manager(" + MANAGER.getName() + ".class);"))
            ;
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
