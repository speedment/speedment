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
package com.speedment.field.predicate;

import java.util.Optional;

/**
 *
 * @author pemi
 */
public enum PredicateType {

    // Constants
    ALWAYS_TRUE,
    ALWAYS_FALSE,
    // Reference
    IS_NULL,
    IS_NOT_NULL,
    // Comparable
    EQUAL,
    NOT_EQUAL,
    GREATER_THAN,
    GREATER_OR_EQUAL,
    LESS_THAN,
    LESS_OR_EQUAL,
    BETWEEN,
    //NOT_BETWEEN, // TO BE IMPLEMENTED
    IN,
    // String
    EQUAL_IGNORE_CASE,
    NOT_EQUAL_IGNORE_CASE,
    STARTS_WITH,
    ENDS_WITH,
    CONTAINS,
    IS_EMPTY,
    IS_NOT_EMPTY;

    private Optional<PredicateType> complementType;

    static {
        associateComplimentTypes(ALWAYS_TRUE, ALWAYS_FALSE);
        associateComplimentTypes(IS_NULL, IS_NOT_NULL);
        associateComplimentTypes(EQUAL, NOT_EQUAL);
        associateComplimentTypes(GREATER_THAN, LESS_OR_EQUAL);
        associateComplimentTypes(GREATER_OR_EQUAL, LESS_THAN);
        //associateComplimentTypes(BETWEEN, NOT_BETWEEN);
        associateComplimentTypes(EQUAL_IGNORE_CASE, NOT_EQUAL_IGNORE_CASE);
        associateComplimentTypes(IS_EMPTY, IS_NOT_EMPTY);
    }

    public Optional<PredicateType> getComplementType() {
        return complementType;
    }

    private static void associateComplimentTypes(PredicateType a, PredicateType b) {
        a.complementType = Optional.of(b);
        b.complementType = Optional.of(a);
    }

}
