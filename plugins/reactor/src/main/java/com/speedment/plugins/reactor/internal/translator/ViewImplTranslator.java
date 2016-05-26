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
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Type;
import com.speedment.generator.internal.DefaultJavaClassTranslator;
import com.speedment.runtime.Speedment;
import com.speedment.runtime.config.Table;

/**
 *
 * @author Emil Forslund
 * @since  1.1.0
 */
public final class ViewImplTranslator extends DefaultJavaClassTranslator<Table, Class> {

    public ViewImplTranslator(
            Speedment speedment, 
            Generator generator, 
            Table document) {
        
        super(speedment, generator, document, Class::of);
    }

    @Override
    protected String getClassOrInterfaceName() {
        return getNamer().javaTypeName(getDocument().getJavaName()) + "ViewImpl";
    }

    @Override
    protected Class makeCodeGenModel(File file) {
        return newBuilder(file, getClassOrInterfaceName())
            .forEveryTable((clazz, table) -> {
                clazz.public_()
                    .setSupertype(Type.of(
                        getSupport().basePackageName() + 
                        ".generated.Generated" + 
                        getNamer().javaTypeName(getDocument().getJavaName()) + 
                        "ViewImpl"
                    ))
                    .add(Type.of(getSupport().entityName() + "View"));
            }).build();
    }

    @Override
    protected String getJavadocRepresentText() {
        return "A materialized object view";
    }
}