/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.plugins.reactor.internal.translator;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Generic;
import com.speedment.common.codegen.model.Interface;
import com.speedment.common.codegen.model.Type;
import com.speedment.generator.internal.DefaultJavaClassTranslator;
import com.speedment.plugins.reactor.MaterializedView;
import static com.speedment.plugins.reactor.internal.translator.TranslatorUtil.mergingColumnType;
import com.speedment.runtime.Speedment;
import com.speedment.runtime.config.Table;

/**
 *
 * @author Emil Forslund
 * @since  1.1.0
 */
public final class GeneratedViewTranslator extends DefaultJavaClassTranslator<Table, Interface> {

    public GeneratedViewTranslator(
            Speedment speedment, 
            Generator generator, 
            Table document) {
        
        super(speedment, generator, document, Interface::of);
    }

    @Override
    protected String getClassOrInterfaceName() {
        return "Generated" + getNamer().javaTypeName(getDocument().getJavaName()) + "View";
    }

    @Override
    public boolean isInGeneratedPackage() {
        return true;
    }

    @Override
    protected Interface makeCodeGenModel(File file) {
        return newBuilder(file, getClassOrInterfaceName())
            .forEveryTable((clazz, table) -> {
                clazz.public_()
                    .add(Type.of(MaterializedView.class)
                        .add(Generic.of().add(getSupport().entityType()))
                        .add(Generic.of().add(mergingColumnType(table)))
                    );
            }).build();
    }

    @Override
    protected String getJavadocRepresentText() {
        return "A materialized object view";
    }
}