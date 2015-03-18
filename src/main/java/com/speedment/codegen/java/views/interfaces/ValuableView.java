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

import static com.speedment.codegen.Formatting.EMPTY;
import static com.speedment.codegen.Formatting.EQUALS;
import static com.speedment.codegen.Formatting.SPACE;
import static com.speedment.codegen.Formatting.ifelse;
import com.speedment.codegen.base.CodeGenerator;
import com.speedment.codegen.base.CodeView;
import com.speedment.codegen.lang.interfaces.Valuable;

/**
 *
 * @author Emil Forslund
 * @param <M>
 */
public interface ValuableView<M extends Valuable<M>> extends CodeView<M> {
    default String renderValue(CodeGenerator cg, M model) {
        return ifelse(model.getValue(), 
			v -> SPACE + EQUALS + SPACE + cg.on(v).orElse(EMPTY), 
            EMPTY
        );
    }
}
