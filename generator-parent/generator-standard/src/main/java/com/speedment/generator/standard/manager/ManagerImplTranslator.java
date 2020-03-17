/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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

import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.File;
import com.speedment.common.injector.Injector;
import com.speedment.generator.translator.AbstractEntityAndManagerTranslator;
import com.speedment.runtime.config.Table;

/**
 *
 * @author Emil Forslund
 * @author Per-Åke Minborg
 */
public final class ManagerImplTranslator extends AbstractEntityAndManagerTranslator<Class> {

    public ManagerImplTranslator(Injector injector, Table table) {
        super(injector, table, Class::of);
    }

    @Override
    protected Class makeCodeGenModel(File file) {
        return newBuilder(file, getSupport().managerImplName())
            .forEveryTable((clazz, table) ->
                clazz.public_().final_()
                    .setSupertype(getSupport().generatedManagerImplType())
                    .add(getSupport().managerType())
            ).build();
    }
    
    @Override
    protected String getClassOrInterfaceName() {
        return getSupport().managerImplName();
    }

    @Override
    protected String getJavadocRepresentText() {
        return "The default implementation for the manager of every {@link " + 
            getSupport().entityType().getTypeName() + "} entity.";
    }
}