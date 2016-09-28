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



/**
 * A trait for predicates the implement the {@link #isNegated()} method.
 * 
 * @author  Per Minborg
 * @since   2.2.0
 */

public interface HasNegated {

    /**
     * Returns {@code true} if this predicate has been negated and should be
     * read as its opposite meaning, else {@code false}.
     * 
     * @return  {@code true} if negated, else {@code false}
     */
    public boolean isNegated();
}