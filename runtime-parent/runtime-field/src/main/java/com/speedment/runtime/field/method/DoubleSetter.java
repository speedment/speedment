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
package com.speedment.runtime.field.method;

import com.speedment.common.annotation.GeneratedCode;
import static java.util.Objects.requireNonNull;

/**
 * A short-cut functional reference to the {@code setXXX(value)} method for a
 * particular field in an entity. The referenced method should return a
 * reference to itself.
 * <p>
 * A {@code DoubleSetter<ENTITY>} has the following signature:
 * {@code
 *     interface ENTITY {
 *         void setXXX(double value);
 *     }
 * }
 * 
 * @param <ENTITY> the entity
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@GeneratedCode(value = "Speedment")
@FunctionalInterface
public interface DoubleSetter<ENTITY> extends Setter<ENTITY> {
    
    /**
     * Sets the member represented by this setter in the specified instance to
     * the specified value, returning a reference to the same instance as
     * result.
     * 
     * @param instance the instance to set it in
     * @param value    the new value
     */
    void setAsDouble(ENTITY instance, double value);
    
    @Override
    default void set(ENTITY instance, Object value) {
        requireNonNull(value, "Attempting to set primitive double field to null.");
        @SuppressWarnings("unchecked")
        final Double casted = (Double) value;
        setAsDouble(instance, casted);
    }
}