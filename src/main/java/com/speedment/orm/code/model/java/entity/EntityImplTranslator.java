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

import com.speedment.codegen.lang.models.Type;
import com.speedment.codegen.lang.models.Class;
import com.speedment.codegen.lang.models.Method;
import com.speedment.codegen.lang.models.constants.Default;
import com.speedment.orm.config.model.Table;

/**
 *
 * @author pemi
 */
public class EntityImplTranslator extends BaseEntityTranslator {

    public EntityImplTranslator(Table configEntity) {
        super(configEntity);
    }

    @Override
    protected Class make() {
        return with(new BaseClass(INTERFACE.getImplType().getName(), (cl, c) -> {
            cl
                    .add(cl.fieldFor(c).private_().final_())
                    .add(Method.of("get" + typeName(c), Type.of(c.getMappedClass()))
                            .add(Default.OVERRIDE)
                            .public_()
                            .add("return " + variableName(c) + ";"));
        }), cl -> {
            cl
                    .add(INTERFACE.getType())
                    .add(cl.emptyConstructor())
                    .add(cl.copyConstructor());
        });
//
//        final com.speedment.codegen.lang.models.Class clazz = new ClassImpl(INTERFACE.getImplType().getName())
//                .public_()
//                .add(INTERFACE.getType());
//
//        final Constructor constructor = new ConstructorImpl().public_();
//        constructor.add(new FieldImpl(variableName(), INTERFACE.getType()).final_());
//        clazz.add(constructor);
//
//        columns().forEach(c -> {
//            final Type getterType = new TypeImpl(c.getMappedClass());
//            clazz.add(new FieldImpl(variableName(c), getterType).private_().final_());
//            clazz.add(new MethodImpl("get" + typeName(c), getterType).public_().add("return " + variableName(c) + ";").add(Default.OVERRIDE));
//            constructor.add("this." + variableName(c) + " = " + variableName() + "." + "get" + typeName(c) + "();");
//        });
//        return clazz;
    }

    @Override
    protected String getJavadocRepresentText() {
        return "An immutable implementation";
    }

    @Override
    protected String getFileName() {
        return INTERFACE.getImplType().getName();
    }

    @Override
    protected boolean isInImplPackage() {
        return true;
    }

}
