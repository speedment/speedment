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
package com.speedment.common.codegen.model.trait;

import com.speedment.common.codegen.model.Value;

import java.util.List;

/**
 * Trait representing a type that has multiple {@link Value values}, like a
 * method or constructor invocation.
 *
 * @author Emil Forslund
 * @since  2.4.6
 */
public interface HasValues<T extends HasValues<T>> {

    /**
     * Adds the specified {@link Value} to this model.
     *
     * @param <E>      the type of the generic value
     * @param generic  the new child
     * @return         a reference to this
     */
    @SuppressWarnings("unchecked")
    default <E> T add(final Value<E> generic) {
        getValues().add(generic);
        return (T) this;
    }

    /**
     * Returns a list of all the values in this model.
     * <p>
     * The list returned must be mutable for changes!
     *
     * @return  the values
     */
    List<Value<?>> getValues();

}
