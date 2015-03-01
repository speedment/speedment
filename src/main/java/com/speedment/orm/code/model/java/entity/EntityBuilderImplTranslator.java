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

import com.speedment.codegen.lang.models.Method;
import com.speedment.codegen.lang.models.Type;
import com.speedment.codegen.lang.models.Class;
import com.speedment.codegen.lang.models.constants.Default;
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
    protected Class make() {
        return with(new BaseClass(BUILDER.getImplType().getName(), (cl, c) -> {
            cl
                    .add(cl.fieldFor(c).private_())
                    .add(Method.of("get" + typeName(c), Type.of(c.getMappedClass()))
                            .public_()
                            .add(Default.OVERRIDE)
                            .add("return " + variableName(c) + ";"))
                    .add(Method.of(BUILDER_METHOD_PREFIX + typeName(c), BUILDER.getType())
                            .public_()
                            .add(Default.OVERRIDE)
                            .add(cl.fieldFor(c))
                            .add("this." + variableName(c) + " = " + variableName(c) + ";")
                            .add("return this;")
                    );
        }), cl -> {
            cl
                    .add(BUILDER.getType())
                    .add(cl.emptyConstructor())
                    .add(cl.copyConstructor())
                    .add(Method.of("build", INTERFACE.getType())
                            .add(Default.OVERRIDE)
                            .public_()
                            .add("return new " + INTERFACE.getImplType().getName() + "(this);"));
        });
    }

    @Override
    protected String getJavadocRepresentText() {
        return "A builder implementation ";
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
