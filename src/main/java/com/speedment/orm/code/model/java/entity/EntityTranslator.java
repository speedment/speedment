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

import com.speedment.codegen.lang.models.ClassOrInterface;
import com.speedment.codegen.lang.models.Field;
import com.speedment.codegen.lang.models.Interface;
import com.speedment.codegen.lang.models.Method;
import com.speedment.codegen.lang.models.constants.Default;
import com.speedment.codegen.lang.models.implementation.AnnotationUsageImpl;
import com.speedment.codegen.lang.models.implementation.FieldImpl;
import com.speedment.codegen.lang.models.implementation.GenericImpl;
import com.speedment.codegen.lang.models.implementation.InterfaceImpl;
import com.speedment.codegen.lang.models.implementation.MethodImpl;
import com.speedment.codegen.lang.models.implementation.TypeImpl;
import com.speedment.orm.annotations.Api;
import com.speedment.orm.config.model.Table;
import com.speedment.orm.core.Builder;

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
        final Interface iface = new InterfaceImpl(INTERFACE.getType().getName())
                .public_()
                .add(new AnnotationUsageImpl(new TypeImpl(Api.class)));

        columns().forEach(c -> {
            iface.add(new MethodImpl("get" + typeName(c), new TypeImpl(c.getMappedClass())));
        });
        iface.add(bean());
        iface.add(builder());
        return iface;
    }

    private Interface bean() {
        final Interface beanInterface = new InterfaceImpl("Bean")
                .public_()
                .add(INTERFACE.getType());

        columns().forEach(c -> {
            Field f = new FieldImpl(typeName(), new TypeImpl(c.getMappedClass()));
            Method m = new MethodImpl("set" + typeName(c), Default.VOID);
            m.add(f);
            beanInterface.add(m);
        });
        return beanInterface;
    }

    private Interface builder() {
        final Interface builderInterface = new InterfaceImpl("Builder")
                .public_()
                .add(INTERFACE.getType())
                .add(new TypeImpl(Builder.class))
                .add(new GenericImpl(INTERFACE.getType()));

        columns().forEach(c -> {
            Field f = new FieldImpl(typeName(), new TypeImpl(c.getMappedClass()));
            Method m = new MethodImpl("with" + typeName(c), new TypeImpl("Builder"));
            m.add(f);
            builderInterface.add(m);
        });
        return builderInterface;
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
