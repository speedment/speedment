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

import java.util.function.BiConsumer;

/**
 * A short-cut functional reference to the {@code setXXX(value)} method for a
 * particular field in an entity. The referenced method should return a 
 * reference to itself.
 * <p>
 * A {@code ReferenceSetter<ENTITY, V>} has the following signature:
 * {@code
 *      interface ENTITY {
 *          void setXXX(V value);
 *      }
 * }
 * 
 * @param <ENTITY>  the entity
 * @param <V>       the type of the value to be set
 * 
 * @author  Emil Forslund
 * @since   2.2.0
 */

@FunctionalInterface
public interface ReferenceSetter<ENTITY, V> 
extends Setter<ENTITY>, BiConsumer<ENTITY, V> {

    @Override
    default void set(ENTITY entity, Object value) {
        @SuppressWarnings("unchecked")
        final V casted = (V) value;
        accept(entity, casted);
    }
    
}