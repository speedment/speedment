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

import com.speedment.orm.code.model.java.BaseEntityAndManagerTranslator;
import com.speedment.codegen.base.Generator;
import com.speedment.codegen.lang.models.File;
import com.speedment.codegen.lang.models.Interface;
import com.speedment.codegen.lang.models.Method;
import com.speedment.codegen.lang.models.Type;
import com.speedment.orm.config.model.Table;
import com.speedment.orm.core.Buildable;

/**
 *
 * @author pemi
 */
public class EntityBuilderTranslator extends BaseEntityAndManagerTranslator<Interface> {

    public EntityBuilderTranslator(Generator cg, Table configEntity) {
        super(cg, configEntity);
    }

    @Override
    protected Interface make(File file) {
        return new InterfaceBuilder(BUILDER.getName())
            .addColumnConsumer((i, c) -> {
                i.add(Method.of(SETTER_METHOD_PREFIX + typeName(c), BUILDER.getType().copy())
                    .add(fieldFor(c))
                );
            })
            .build()
            .add(ENTITY.getType().copy())
            .add(Type.of(Buildable.class).add(GENERIC_OF_ENTITY.copy()))
            .public_();
    }

    @Override
    protected String getJavadocRepresentText() {
        return "A builder";
    }

    @Override
    protected String getFileName() {
        return BUILDER.getName();
    }
}