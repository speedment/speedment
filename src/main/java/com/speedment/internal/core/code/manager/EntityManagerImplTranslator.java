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
package com.speedment.internal.core.code.manager;

import com.speedment.Speedment;
import com.speedment.internal.core.code.EntityAndManagerTranslator;
import com.speedment.internal.codegen.base.Generator;
import com.speedment.internal.codegen.lang.models.File;
import com.speedment.internal.codegen.lang.models.Class;
import com.speedment.config.db.Table;
import com.speedment.internal.codegen.lang.models.Constructor;
import com.speedment.internal.codegen.lang.models.Field;
import com.speedment.internal.codegen.lang.models.Type;

/**
 *
 * @author Emil Forslund
 * @author Per-Åke Minborg
 */
public final class EntityManagerImplTranslator extends EntityAndManagerTranslator<Class> {

    public EntityManagerImplTranslator(Speedment speedment, Generator gen, Table table) {
        super(speedment, gen, table, Class::of);
    }

    @Override
    protected Class makeCodeGenModel(File file) {
        return newBuilder(file, manager.getImplName()).build()
            .public_().final_()
            .setSupertype(manager.getGeneratedImplType())
            .add(manager.getType())
            .add(Constructor.of().public_()
                .add(Field.of("speedment", Type.of(Speedment.class)))
                .add("super(speedment);")
            );
    }
    
    @Override
    protected String getClassOrInterfaceName() {
        return manager.getImplName();
    }

    @Override
    protected String getJavadocRepresentText() {
        return "A manager implementation";
    }
    
    public Type getImplType() {
        return manager.getImplType();
    }
}