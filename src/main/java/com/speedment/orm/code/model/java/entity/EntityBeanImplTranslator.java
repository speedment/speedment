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

import com.speedment.codegen.lang.models.Class;
import com.speedment.codegen.lang.models.Method;
import com.speedment.codegen.lang.models.constants.Default;
import com.speedment.orm.config.model.Table;

/**
 *
 * @author pemi
 */
public class EntityBeanImplTranslator extends BaseEntityTranslator {

    public EntityBeanImplTranslator(Table configEntity) {
        super(configEntity);
    }

    @Override
    protected Class make() {
        return with(new BaseClass(BEAN.getImplType().getName(), (cl, c) -> {
            cl
                    .add(cl.fieldFor(c).private_())
                    .add(Method.of("get" + typeName(c), cl.fieldFor(c).getType())
                            .public_()
                            .add(Default.OVERRIDE)
                            .add("return " + variableName(c) + ";"))
                    .add(Method.of("set" + typeName(c), BEAN.getType())
                            .public_()
                            .add(Default.OVERRIDE)
                            .add(cl.fieldFor(c))
                            .add("this." + variableName(c) + " = " + variableName(c) + ";")
                            .add("return this;")
                    );
        }), cl -> {
            cl
                    .add(BEAN.getType())
                    .add(cl.emptyConstructor())
                    .add(cl.copyConstructor());
        });
    }

    @Override
    protected String getJavadocRepresentText() {
        return "A bean implementation";
    }

    @Override
    protected String getFileName() {
        return BEAN.getImplType().getName();
    }

    @Override
    protected boolean isInImplPackage() {
        return true;
    }

}
