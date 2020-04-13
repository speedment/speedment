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
package com.speedment.runtime.field.predicate.trait;

import com.speedment.runtime.field.predicate.Inclusion;

/**
 * A common interface for predicates that test if an item is located
 * between two other items. This is useful for determining which 
 * inclusion strategy is expected from the stream.
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public interface HasInclusion {
    
    /**
     * Returns the inclusion strategy used in the predicate.
     * 
     * @return  the inclusion strategy
     */
    Inclusion getInclusion();
    
}