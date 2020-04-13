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
package com.speedment.runtime.compute.trait;

/**
 * Trait for expressions that can be mapped to a new expression of the same type
 * by supplying a mapping function.
 *
 * @param <T>       the input entity type
 * @param <MAPPER>  the functional interface used for mapping values
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface HasMap<T, MAPPER, RESULT extends HasMap<T, MAPPER, RESULT>> {

    /**
     * Returns an expression for the value of this expression mapped using the
     * specified mapping function. The input type remains the same.
     *
     * @param mapper  the mapping function
     * @return        the mapped expression
     */
    RESULT map(MAPPER mapper);

}
