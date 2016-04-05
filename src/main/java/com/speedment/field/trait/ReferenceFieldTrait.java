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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.field.trait;

import com.speedment.annotation.Api;
import com.speedment.config.db.mapper.TypeMapper;
import com.speedment.field.FieldIdentifier;
import com.speedment.field.methods.FieldSetter;
import com.speedment.field.methods.Getter;
import com.speedment.field.methods.Setter;
import com.speedment.field.predicate.SpeedmentPredicate;

/**
 * A representation of an Entity field that is a reference type (eg 
 * {@code Integer} and not {@code int}).
 *
 * @param <ENTITY>  the entity type
 * @param <D>       the database value type
 * @param <V>       the field value type
 *
 * @author  Per Minborg
 * @author  Emil Forslund
 */
@Api(version = "2.3")
public interface ReferenceFieldTrait<ENTITY, D, V> extends FieldTrait {

    @Override
    FieldIdentifier<ENTITY> getIdentifier();

    /**
     * Returns a reference to the setter for this field.
     *
     * @return the setter
     */
    Setter<ENTITY, V> setter();

    /**
     * Returns a reference to the getter of this field.
     *
     * @return the getter
     */
    Getter<ENTITY, V> getter();

    /**
     * Returns the type mapper of this field.
     *
     * @return type mapper
     */
    TypeMapper<D, V> typeMapper();

    /**
     * Gets the value form the Entity field.
     *
     * @param e entity
     * @return the field value
     */
    default V get(ENTITY e) {
        return getter().apply(e);
    }

    /**
     * Sets the value in the given Entity
     *
     * @param e entity
     * @param value to set
     * @return the entity itself
     */
    default ENTITY set(ENTITY e, V value) {
        return setter().apply(e, value);
    }

    /**
     * Creates and returns a FieldSetter with a given value.
     *
     * @param value to set
     * @return a FieldSetter with a given value
     */
    FieldSetter<ENTITY, V> setTo(V value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is {@code null}.
     *
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is {@code null}
     */
    SpeedmentPredicate<ENTITY, D, V> isNull();

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>not</em> {@code null}.
     *
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>not</em> {@code null}
     */
    SpeedmentPredicate<ENTITY, D, V> isNotNull();

}
