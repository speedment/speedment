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
package com.speedment.field;

import com.speedment.annotation.Api;
import com.speedment.field.methods.Getter;
import com.speedment.field.methods.Setter;
import com.speedment.field.builders.UnaryPredicateBuilder;
import com.speedment.field.builders.SetterBuilder;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author          pemi, Emil Forslund
 * @param <ENTITY>  the entity type
 * @param <V>       the field value type
 */
@Api(version = "2.1")
public interface ReferenceField<ENTITY, V> extends Field<ENTITY> {
    
    /**
     * Returns a reference to the setter for this field.
     * 
     * @return  the setter
     */
    Setter<ENTITY, V> setter();
    
    /**
     * Returns a reference to the getter of this field.
     * 
     * @return  the getter
     */
    Getter<ENTITY, V> getter();
    
    /**
     * Returns a {@link java.util.function.Function} that will set this field
     * to a specific value for a entity and return that entity.
     * 
     * @param newValue    the value to set
     * @return            the function builder
     */
    SetterBuilder<ENTITY, V> set(V newValue);

    /**
     * Returns the value of this field for the specified entity.
     * 
     * @param entity  the entity
     * @return        the value of the field
     */
    V get(ENTITY entity);
    
    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is {@code null}.
     *
     * @return           a Predicate that will evaluate to {@code true}, if and 
     *                   only if this Field is {@code null}
     */
    UnaryPredicateBuilder<ENTITY> isNull();
    
    /**
     * Returns if this Field is {@code null} in the given entity.
     *
     * @param entity  to use
     * @return        if this Field is {@code null}
     */
    default boolean isNullIn(ENTITY entity) {
        return isNull().test(requireNonNull(entity));
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>not</em> {@code null}.
     *
     * @return           a Predicate that will evaluate to {@code true}, if and 
     *                   only if this Field is <em>not</em> {@code null}
     */
    UnaryPredicateBuilder<ENTITY> isNotNull();
    
    /**
     * Returns if this Field is <b>not</b> {@code null} in the given entity.
     *
     * @param entity  to use
     * @return        if this Field is not {@code null}
     */
    default boolean isNotNullIn(ENTITY entity) {
        return isNotNull().test(requireNonNull(entity));
    }
}