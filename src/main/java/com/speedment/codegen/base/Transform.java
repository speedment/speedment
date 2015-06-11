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
package com.speedment.codegen.base;

import java.util.Optional;

/**
 * Transforms must have a public constructor with no parameters so that it can
 * be instantiated dynamically.
 * 
 * @author Emil Forslund
 * @param <F> The model to generate from.
 * @param <T> The resulting model.
 */
public interface Transform<F, T> {
    Optional<T> transform(Generator gen, F model);
    
    default boolean is(Class<? extends Transform<?, ?>> transformer) {
        return transformer.isAssignableFrom(getClass());
    }
}