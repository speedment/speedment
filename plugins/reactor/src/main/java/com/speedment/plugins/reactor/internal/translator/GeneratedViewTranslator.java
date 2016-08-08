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

import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Interface;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.generator.internal.DefaultJavaClassTranslator;
import com.speedment.plugins.reactor.MaterializedView;
import com.speedment.runtime.config.Table;
import com.speedment.plugins.reactor.util.MergingSupport;

/**
 *
 * @author Emil Forslund
 * @since  1.1.0
 */
public final class GeneratedViewTranslator extends DefaultJavaClassTranslator<Table, Interface> {

    private @Inject MergingSupport merger;
    
    public GeneratedViewTranslator(Table document) {
        super(document, Interface::of);
    }

    @Override
    protected String getClassOrInterfaceName() {
        return "Generated" + getSupport().namer().javaTypeName(getDocument().getJavaName()) + "View";
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
                    .add(SimpleParameterizedType.create(
                        MaterializedView.class,
                        getSupport().entityType(),
                        merger.mergingColumnType(table)
                    ));
            }).build();
    }

    @Override
    protected String getJavadocRepresentText() {
        return "A materialized object view";
    }
}