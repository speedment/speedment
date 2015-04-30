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
package com.speedment.codegen.java.views.interfaces;

import static com.speedment.codegen.Formatting.COMMA_SPACE;
import static com.speedment.codegen.Formatting.SPACE;
import com.speedment.codegen.base.Generator;
import com.speedment.codegen.base.Transform;
import com.speedment.codegen.lang.interfaces.HasImplements;
import static com.speedment.util.CodeCombiner.joinIfNotEmpty;

/**
 *
 * @author Emil Forslund
 * @param <M>
 */
public interface HasImplementsView<M extends HasImplements<M>> extends Transform<M, String> {
    
    String extendsOrImplementsInterfaces();
    
    default String renderInterfaces(Generator cg, M model) {
        return cg.onEach(model.getInterfaces()).collect(
            joinIfNotEmpty(
                COMMA_SPACE, 
                extendsOrImplementsInterfaces(), 
                SPACE
            )
        );
    }
}