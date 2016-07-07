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

import com.speedment.common.codegen.model.*;
import com.speedment.generator.JavaClassTranslator;
import com.speedment.generator.TranslatorDecorator;
import com.speedment.plugins.reactor.MaterializedView;
import com.speedment.runtime.config.Project;

import static com.speedment.common.codegen.internal.model.constant.DefaultType.WILDCARD;

/**
 *
 * @author  Emil Forslund
 * @since   1.1.0
 */
public final class GeneratedApplicationDecorator implements TranslatorDecorator<Project, Interface> {

    @Override
    public void apply(JavaClassTranslator<Project, Interface> translator) {
        translator.onMake(builder -> {
            builder.forEveryProject((intrf, project) -> {
                final Generic generic = Generic.of().setLowerBound("ENTITY");
                intrf.add(Method.of("viewOf", Type.of(MaterializedView.class)
                    .add(generic)
                    .add(Generic.of().add(WILDCARD))
                ).add(generic)
                    .add(Field.of(
                        "entityType", 
                        Type.of(Class.class).add(generic)
                    )));
            });
        });
    }
    
}