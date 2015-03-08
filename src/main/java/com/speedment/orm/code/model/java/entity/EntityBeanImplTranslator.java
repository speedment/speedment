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

import com.speedment.codegen.Formatting;
import com.speedment.codegen.base.CodeGenerator;
import com.speedment.codegen.lang.models.Class;
import com.speedment.codegen.lang.models.File;
import com.speedment.codegen.lang.models.Method;
import static com.speedment.codegen.lang.models.constants.DefaultAnnotationUsage.OVERRIDE;
import com.speedment.orm.config.model.Table;

/**
 *
 * @author pemi
 */
public class EntityBeanImplTranslator extends BaseEntityTranslator<Class> {

    public EntityBeanImplTranslator(CodeGenerator cg, Table configEntity) {
        super(cg, configEntity);
    }

    @Override
    protected Class make(File file) {
        return new ClassBuilder(BEAN.getImplType().getName())
            .addColumnConsumer((cl, c) -> {
                cl
                .add(fieldFor(c).private_())
                .add(Method.of(GETTER_METHOD_PREFIX + typeName(c), fieldFor(c).getType())
                    .public_()
                    .add(OVERRIDE)
                    .add("return " + variableName(c) + ";"))
                .add(Method.of(SETTER_METHOD_PREFIX + typeName(c), BEAN.getType())
                    .public_()
                    .add(OVERRIDE)
                    .add(fieldFor(c))
                    .add("this." + variableName(c) + " = " + variableName(c) + ";")
                    .add("return this;"));
            })
            .build()
            .public_()
            .add(BEAN.getType())
            .add(emptyConstructor())
            .add(copyConstructor(INTERFACE.getType(), CopyConstructorMode.SETTER));
    }

    @Override
    protected String getJavadocRepresentText() {
        return "A bean implementation";
    }

    @Override
    protected String getFileName() {
        return Formatting.shortName(BEAN.getImplType().getName());
    }

    @Override
    protected boolean isInImplPackage() {
        return true;
    }

}
