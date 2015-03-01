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
    protected Interface make() {
        return with(new BaseInterface(INTERFACE.getType().getName(), (i, c) -> {
            i.add(Method.of("get" + typeName(c), Type.of(c.getMappedClass())));
        }), i -> {
            i
                    .add(AnnotationUsage.of(Type.of(Api.class)))
                    .add(bean())
                    .add(builder());
        });
    }

    private Interface bean() {
        return with(new BaseInterface("Bean", (i, c) -> {
            i.add(Method.of("set" + typeName(c), Default.VOID).add(i.fieldFor(c)));
        }), i -> {
            i.add(INTERFACE.getType());
        });
    }

    private Interface builder() {
        return with(new BaseInterface("Builder", (cl, c) -> {
            cl.add(Method.of("with" + typeName(c), Type.of("Builder")).add(cl.fieldFor(c)));
        }), cl -> {
            cl.add(INTERFACE.getType())
                    .add(Type.of(Buildable.class).add(new GenericImpl(INTERFACE.getType())));
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
