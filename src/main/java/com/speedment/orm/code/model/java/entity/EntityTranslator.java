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
import com.speedment.codegen.lang.models.Field;
import com.speedment.codegen.lang.models.File;
import com.speedment.codegen.lang.models.Generic;
import com.speedment.codegen.lang.models.Interface;
import com.speedment.codegen.lang.models.Javadoc;
import com.speedment.codegen.lang.models.Method;
import com.speedment.codegen.lang.models.Type;
import com.speedment.codegen.lang.models.constants.Default;
import com.speedment.orm.annotations.Api;
import com.speedment.orm.code.model.java.DefaultJavaClassTranslator;
import com.speedment.orm.config.model.Table;
import com.speedment.orm.core.Builder;

/**
 *
 * @author pemi
 */
public class EntityTranslator extends DefaultJavaClassTranslator<Table> {

    private final Type type;

    public EntityTranslator(Table configEntity) {
        super(configEntity);
        type = new Type(packagePath() + "." + javaTypeName());
    }

    @Override
    public File get() {
        final File file = new File(project().getPacketLocation() + "/" + javaTypeName());
        file.add(iface());
        return file;
    }

    private Interface iface() {
        final Interface iface = new Interface(type.getName())
                .public_()
                .setJavadoc(new Javadoc("An interface representing an entity (for example, a row) in the " + getConfigEntity().toString() + "." + GENERATED_JAVADOC_MESSAGE)
                        .add(Default.AUTHOR.copy().setValue("Speedment")))
                .add(new AnnotationUsage(new Type(Api.class)));

        columns().forEach(c -> {
            iface.add(new Method("get" + javaTypeName(c), new Type(c.getMappedClass())));
        });
        iface.add(bean());
        iface.add(builder());
        return iface;
    }

    private Interface bean() {
        final Interface beanInterface = new Interface("Bean")
                .public_()
                .add(type);

        columns().forEach(c -> {
            beanInterface.add(new Method("set" + javaTypeName(c), Default.VOID).add(new Field(javaTypeName(), new Type(c.getMappedClass()))));
        });
        return beanInterface;
    }

    private Interface builder() {
        final Interface beanInterface = new Interface("Bean")
                .public_()
                .add(type)
                .add(new Type(Builder.class))
                .add(new Generic(type));

        columns().forEach(c -> {
            beanInterface.add(new Method("with" + javaTypeName(c), new Type("Bean")).add(new Field(javaTypeName(), new Type(c.getMappedClass()))));
        });
        return beanInterface;
    }

}
