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

import java.util.function.Function;
import java.util.function.ToDoubleFunction;

/**
 * A short-cut functional reference to the {@code getXXX(value)} method for a
 * particular field in an entity.
 * <p>
 * A {@code DoubleGetter<ENTITY>} has the following signature:
 * {@code
 *     interface ENTITY {
 *         double getXXX();
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
public interface DoubleGetter<ENTITY> extends Getter<ENTITY>, ToDoubleFunction<ENTITY> {
    
    /**
     * Returns the member represented by this getter in the specified instance.
     * 
     * @param instance the instance to get from
     * @return         the value
     */
    @Override
    double applyAsDouble(ENTITY instance);
    
    @Override
    default Double apply(ENTITY instance) {
        return applyAsDouble(instance);
    }
    
    @Override
    default Function<ENTITY, Double> asFunction() {
        return this::apply;
    }
}