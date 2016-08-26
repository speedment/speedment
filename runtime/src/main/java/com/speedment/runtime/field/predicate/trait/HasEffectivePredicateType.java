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
package com.speedment.runtime.field.predicate.trait;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.field.predicate.PredicateType;

/**
 * A trait for predicates the implement the {@link #getEffectivePredicateType()}
 * method.
 * 
 * @author  Per Minborg
 * @since   2.2.0
 */
@Api(version = "3.0")
public interface HasEffectivePredicateType {

    /**
     * Returns the effective predicate type. The effective predicate type is the
     * type that results when the isNegated() flag is considered. For example,
     * EQUAL.negate() has an effective predicate of NOT_EQAUL.
     *
     * @return the effective predicate type
     */
    PredicateType getEffectivePredicateType();
}