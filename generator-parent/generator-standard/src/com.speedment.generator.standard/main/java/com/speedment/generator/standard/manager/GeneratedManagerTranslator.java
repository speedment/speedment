/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.generator.standard.manager;

import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Interface;
import com.speedment.common.codegen.model.Method;
import com.speedment.generator.translator.AbstractEntityAndManagerTranslator;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.core.manager.Manager;

import static com.speedment.common.codegen.constant.DefaultAnnotationUsage.OVERRIDE;
import static com.speedment.common.codegen.constant.DefaultType.classOf;

/**
 *
 * @author Emil Forslund
 * @since  2.3.0
 */
public final class GeneratedManagerTranslator 
extends AbstractEntityAndManagerTranslator<Interface> {

    public GeneratedManagerTranslator(Table table) {
        super(table, Interface::of);
    }

    @Override
    protected Interface makeCodeGenModel(File file) {
        return newBuilder(file, getSupport().generatedManagerName())
            .forEveryTable((intf, table) ->
                intf.public_()
                    .add(SimpleParameterizedType.create(Manager.class, getSupport().entityType()))
                    .add(Method.of("getEntityClass", classOf(getSupport().entityType()))
                        .default_().add(OVERRIDE)
                        .add("return " + getSupport().entityName() + ".class;")
                    )
            ).build();
    }

    @Override
    protected String getJavadocRepresentText() {
        return "The generated base interface for the manager of every {@link " + 
            getSupport().entityType().getTypeName() + "} entity.";
    }

    @Override
    protected String getClassOrInterfaceName() {
        return getSupport().generatedManagerName();
    }

    @Override
    public boolean isInGeneratedPackage() {
        return true;
    }
}