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

import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Interface;
import com.speedment.common.injector.Injector;
import com.speedment.generator.translator.AbstractEntityAndManagerTranslator;
import com.speedment.runtime.config.Table;

/**
 *
 * @author Emil Forslund
 * @author Per-Åke Minborg
 */
public final class ManagerTranslator extends AbstractEntityAndManagerTranslator<Interface> {

    public ManagerTranslator(Injector injector, Table doc) {
        super(injector, doc, Interface::of);
    }

    @Override
    protected Interface makeCodeGenModel(File file) {
        return newBuilder(file, getSupport().managerName())
            .forEveryTable((intf, table) ->
                intf.public_().add(getSupport().generatedManagerType())
            ).build();
    }
    
    @Override
    protected String getClassOrInterfaceName() {
        return getSupport().managerName();
    }

    @Override
    protected String getJavadocRepresentText() {
        return "The main interface for the manager of every {@link " + 
            getSupport().entityType().getTypeName() + "} entity.";
    }
}