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
package com.speedment.internal.core.code.entity;

import com.speedment.internal.core.code.EntityAndManagerTranslator;
import com.speedment.codegen.base.Generator;
import com.speedment.codegen.lang.models.Class;
import com.speedment.codegen.lang.models.File;
import com.speedment.config.db.Table;
import com.speedment.Speedment;

/**
 *
 * @author Emil Forslund
 * @author Per-Åke Minborg
 */
public final class EntityImplTranslator extends EntityAndManagerTranslator<Class> {

    public EntityImplTranslator(Speedment speedment, Generator cg, Table configEntity) {
        super(speedment, cg, configEntity, Class::of);
    }

    @Override
    protected Class makeCodeGenModel(File file) {
        return newBuilder(file, entity.getImplName()).build()
            .public_().abstract_()
            .add(entity.getType()).setSupertype(entity.getGeneratedImplType());
    }
    
    @Override
    protected String getClassOrInterfaceName() {
        return entity.getImplName();
    }

    @Override
    protected String getJavadocRepresentText() {
        return "An implementation";
    }
}