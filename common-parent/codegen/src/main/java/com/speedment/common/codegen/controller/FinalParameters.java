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
package com.speedment.common.codegen.controller;

import com.speedment.common.codegen.model.trait.HasMethods;

import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

/**
 * This control makes sure all method parameters in the specified model is set
 * to final.
 * 
 * @author     Emil Forslund
 * @param <T>  The extending type
 */
public final class FinalParameters<T extends HasMethods<T>> implements Consumer<T> {
    
    /**
     * Sets all method parameters in the specified model to <code>final</code>.
     * 
     * @param model  the model to operate on
     */
	@Override
	public void accept(T model) {
		requireNonNull(model).getMethods()
            .forEach(m -> m.getFields()
                .forEach(f -> f.final_())
            );
	}
}