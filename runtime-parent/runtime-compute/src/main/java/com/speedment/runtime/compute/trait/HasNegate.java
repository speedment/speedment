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
 * Trait for expressions that has a {@link #negate()} method for getting the
 * negative value of the result from the current expression. For an example, a
 * positive value will become negative and a negative value will become
 * positive.
 *
 * @param <E>  the expression type returned by the {@link #negate()} method
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface HasNegate<E extends HasNegate<E>> {

    /**
     * Returns a new expression that returns the absolute value of the result
     * of the current expression. For an example, a positive value will become
     * negative and a negative value will become positive.
     *
     * @return  the new expression
     */
    E negate();

}
