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
import com.speedment.codegen.lang.models.Constructor;
import com.speedment.codegen.lang.models.Method;
import com.speedment.codegen.lang.models.Type;
import com.speedment.codegen.lang.models.constants.Default;
import com.speedment.codegen.lang.models.implementation.ClassImpl;
import com.speedment.codegen.lang.models.implementation.ConstructorImpl;
import com.speedment.codegen.lang.models.implementation.FieldImpl;
import com.speedment.codegen.lang.models.implementation.MethodImpl;
import com.speedment.codegen.lang.models.implementation.TypeImpl;
import com.speedment.orm.config.model.Table;

/**
 *
 * @author pemi
 */
public class EntityBuilderImplTranslator extends BaseEntityTranslator {

    private static final String BUILDER_METHOD_PREFIX = "with";

    public EntityBuilderImplTranslator(Table configEntity) {
        super(configEntity);
    }

    @Override
    protected ClassOrInterface make() {
        final com.speedment.codegen.lang.models.Class clazz = new ClassImpl(BUILDER.getImplType().getName())
                .public_()
                .add(BUILDER.getType());

        clazz.add(new ConstructorImpl().public_());

        final Constructor copyConstructor = new ConstructorImpl().public_();
        copyConstructor.add(new FieldImpl(variableName(), INTERFACE.getType()).final_());
        clazz.add(copyConstructor);

        columns().forEach(c -> {
            final Type columnType = new TypeImpl(c.getMappedClass());
            clazz.add(new FieldImpl(variableName(c), columnType).private_());

            clazz.add(new MethodImpl("get" + typeName(c), columnType)
                    .public_()
                    .add(Default.OVERRIDE)
                    .add("return " + variableName(c) + ";"));
            clazz.add(new MethodImpl(BUILDER_METHOD_PREFIX + typeName(c), BUILDER.getType())
                    .public_()
                    .add(Default.OVERRIDE)
                    .add(new FieldImpl(variableName(c), columnType))
                    .add("this." + variableName(c) + " = " + variableName(c) + ";")
                    .add("return this;")
            );
            copyConstructor.add(BUILDER_METHOD_PREFIX + typeName(c) + "(" + variableName() + ".get" + typeName(c) + "());");
        });

        final Method build = new MethodImpl("build", INTERFACE.getType())
                .add(Default.OVERRIDE)
                .public_()
                .add("return new " + INTERFACE.getImplType().getName() + "(this);");
        clazz.add(build);

        // clazz.call(new SetGetAdd());
        return clazz;
    }

    @Override
    protected String getJavadocRepresentText() {
        return "An builder";
    }

    @Override
    protected String getFileName() {
        return BUILDER.getImplType().getName();
    }

    @Override
    protected boolean isInImplPackage() {
        return true;
    }

}
