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
package com.speedment.runtime.compute.expression.predicate;

/**
 * Special types of predicates that can easily be recognized and potentially
 * short-circuited.
 *
 * @author Emil Forslund
 * @since  3.1.2
 */
public enum NullPredicateType {

    /**
     * Represents a simply {@code a -> a == null} predicate.
     */
    IS_NULL,

    /**
     * Represents a simply {@code a -> a != null} predicate.
     */
    IS_NOT_NULL,

    /**
     * Represents any kind of predicate, including {@link #IS_NULL} or
     * {@link #IS_NOT_NULL}.
     */
    OTHER
}
