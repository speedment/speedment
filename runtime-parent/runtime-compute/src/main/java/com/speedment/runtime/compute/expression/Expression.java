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
package com.speedment.runtime.compute.expression;

/**
 * The base interface for all expressions. Some implementations of this
 * interface will be lambdas or anonymous classes and in those cases, the
 * equality is unspecified. However, there are implementations of this interface
 * that offer a specified equality contract.
 *
 * @param <T> the input entity type of this expression
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface Expression<T> {

    /**
     * Returns the expression type of this expression. It should be safe to cast
     * this instance into the corresponding interface.
     *
     * @return  the expression type
     */
    ExpressionType expressionType();

}
