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
 * Trait for expressions that has a {@link #sqrt()} method for getting the
 * square root of the result from the current expression.
 *
 * @param <E>  the expression type returned by the {@link #sqrt()} method
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface HasSqrt<E> {

    /**
     * Creates and returns an expression that returns the square root of the
     * result from the current expression.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @return  the new expression
     */
    E sqrt();

}
