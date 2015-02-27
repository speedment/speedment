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

import com.speedment.codegen.lang.models.AnnotationUsage;
import com.speedment.codegen.lang.models.ClassOrInterface;
import com.speedment.codegen.lang.models.Field;
import com.speedment.codegen.lang.models.Interface;
import com.speedment.codegen.lang.models.Method;
import com.speedment.codegen.lang.models.Type;
import com.speedment.codegen.lang.models.constants.Default;
import com.speedment.codegen.lang.models.implementation.GenericImpl;
import com.speedment.orm.annotations.Api;
import com.speedment.orm.config.model.Table;
import com.speedment.orm.core.Buildable;

/**
 *
 * @author pemi
 */
public class EntityTranslator extends BaseEntityTranslator {

    public EntityTranslator(Table configEntity) {
        super(configEntity);
    }

    @Override
    protected ClassOrInterface make() {
        return with(Interface.of(INTERFACE.getType().getName()), i -> {
            i
                    .public_()
                    .add(AnnotationUsage.of(Type.of(Api.class)))
                    .add(bean())
                    .add(builder());
            columns().forEach(c -> {
                i.add(Method.of("get" + typeName(c), Type.of(c.getMappedClass())));
            });
        });

    }

    private Interface bean() {
        return with(Interface.of("Bean"), i -> {
            i
                    .public_()
                    .add(INTERFACE.getType());

            columns().forEach(c -> {
                final Field f = Field.of(typeName(c), Type.of(c.getMappedClass()));
                final Method m = Method.of("set" + typeName(c), Default.VOID).add(f);
                i.add(m);
            });

        });
    }

    private Interface builder() {
        return with(Interface.of("Builder"), i -> {
            i
                    .public_()
                    .add(INTERFACE.getType())
                    .add(Type.of(Buildable.class).add(new GenericImpl(INTERFACE.getType())));

            columns().forEach(c -> {
                Field f = Field.of(typeName(), Type.of(c.getMappedClass()));
                Method m = Method.of("with" + typeName(c), Type.of("Builder")).add(f);
                i.add(m);
            });

        });
    }

    @Override
    protected String getJavadocRepresentText() {
        return "An interface";
    }

    @Override
    protected String getFileName() {
        return INTERFACE.getType().getName();
    }

}
