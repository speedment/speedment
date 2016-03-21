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
package com.speedment.field.predicate;

import com.speedment.annotation.Api;

/**
 * The predicate types that exists in Speedment. A predicate type must always
 * have a negated {@code PredicateType} that is the exact negation of itself.
 *
 * @author Per Minborg
 */
@Api(version = "2.2")
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
    NOT_BETWEEN, // Currently not exposed in external predicates
    IN,
    NOT_IN, // Currently not exposed in external predicates
    
    // String
    EQUAL_IGNORE_CASE,
    NOT_EQUAL_IGNORE_CASE,
    STARTS_WITH,
    NOT_STARTS_WITH, // Currently not exposed in external predicates
    ENDS_WITH,
    NOT_ENDS_WITH, // Currently not exposed in external predicates
    CONTAINS,
    NOT_CONTAINS, // Currently not exposed in external predicates
    IS_EMPTY,
    IS_NOT_EMPTY;

    private PredicateType negatedType;

    static {
        associateNegations(ALWAYS_TRUE, ALWAYS_FALSE);
        associateNegations(IS_NULL, IS_NOT_NULL);
        associateNegations(EQUAL, NOT_EQUAL);
        associateNegations(GREATER_THAN, LESS_OR_EQUAL);
        associateNegations(GREATER_OR_EQUAL, LESS_THAN);
        associateNegations(BETWEEN, NOT_BETWEEN);
        associateNegations(IN, NOT_IN);
        associateNegations(EQUAL_IGNORE_CASE, NOT_EQUAL_IGNORE_CASE);
        associateNegations(STARTS_WITH, NOT_STARTS_WITH);
        associateNegations(ENDS_WITH, NOT_ENDS_WITH);
        associateNegations(CONTAINS, NOT_CONTAINS);
        associateNegations(IS_EMPTY, IS_NOT_EMPTY);
    }

    public PredicateType negate() {
        return negatedType;
    }

    public PredicateType effectiveType(boolean negate) {
        return negate ? negatedType : this;
    }

    private static void associateNegations(PredicateType a, PredicateType b) {
        a.negatedType = b;
        b.negatedType = a;
    }
}