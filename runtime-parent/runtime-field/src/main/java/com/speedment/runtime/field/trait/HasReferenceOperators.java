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
package com.speedment.runtime.field.trait;

import com.speedment.runtime.field.predicate.SpeedmentPredicate;

/**
 *
 * @param <ENTITY>  the entity type
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public interface HasReferenceOperators<ENTITY> {
    
    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is {@code null}.
     *
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is {@code null}
     */
    SpeedmentPredicate<ENTITY> isNull();

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>not</em> {@code null}.
     *
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>not</em> {@code null}
     */
    default SpeedmentPredicate<ENTITY> isNotNull() {
        return isNull().negate();
    }
}
