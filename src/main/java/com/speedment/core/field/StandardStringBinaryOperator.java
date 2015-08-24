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
package com.speedment.core.field;

import java.util.Objects;
import java.util.function.BiPredicate;

/**
 *
 * @author pemi
 */
public enum StandardStringBinaryOperator implements BinaryOperator {

    STARTS_WITH(String::startsWith),
    ENDS_WITH(String::endsWith),
    CONTAINS(String::contains),
    EQUAL_IGNORE_CASE(String::equalsIgnoreCase),
    NOT_EQUAL_IGNORE_CASE((s0, s1) -> !s0.equalsIgnoreCase(s1));

    private final BiPredicate<String, String> biPredicate;

    StandardStringBinaryOperator(BiPredicate<String, String> biPredicate) {
        this.biPredicate = Objects.requireNonNull(biPredicate);
    }

    public BiPredicate<String, String> getComparator() {
        return biPredicate;
    }
}
