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
package com.speedment.generator.standard.entity;

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
public class EntityTranslator extends AbstractEntityAndManagerTranslator<Interface> {

    public EntityTranslator(Injector injector, Table table) {
        super(injector, table, Interface::of);
    }

    @Override
    protected Interface makeCodeGenModel(File file) {
        return newBuilder(file, getSupport().entityName()).build()
            .public_().add(getSupport().generatedEntityType());
    }
    
    @Override
    protected String getClassOrInterfaceName() {
        return getSupport().entityName();
    }

    @Override
    protected String getJavadocRepresentText() {
        return "The main interface for entities of the {@code " + 
            getDocument().getId() + "}-table in the database.";
    }
}
