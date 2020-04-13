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
package com.speedment.common.function;

/**
 * Functional interface representing side-effect free methods with the signature
 * {@code boolean test(int, int)}.
 *
 * @author Emil Forslund
 * @since  1.0.5
 */
@FunctionalInterface
public interface BiIntPredicate {

    /**
     * Tests this predicate with the specified arguments. Implementations of
     * this interface should be without side-effects.
     *
     * @param first   the first argument
     * @param second  the second argument
     * @return        {@code true} if the test passed, else {@code false}
     */
    boolean test(int first, int second);

}
