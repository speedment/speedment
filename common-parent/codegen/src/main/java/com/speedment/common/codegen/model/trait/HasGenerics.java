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

import com.speedment.common.codegen.model.Generic;

import java.lang.reflect.Type;
import java.util.List;

/**
 * A trait for models that contain {@link Generic} components.
 *
 * @param <T> the extending type
 *
 * @author Emil Forslund
 * @since  2.0
 */
public interface HasGenerics<T extends HasGenerics<T>> {
    
    /**
     * Adds the specified {@link Generic} to this model.
     * 
     * @param generic  the new child
     * @return         a reference to this
     */
    @SuppressWarnings("unchecked")
    default T add(final Generic generic) {
        getGenerics().add(generic);
        return (T) this;
    }

    /**
     * Adds the specified {@link Generic} to this model. This method is a
     * synonym of {@link #add(Generic)}.
     *
     * @param generic  the new child
     * @return         a reference to this
     *
     * @since 2.5
     */
    default T generic(final Generic generic) {
        return add(generic);
    }

    /**
     * Created a new {@link Generic} with the specified name and adds it to this
     * model.
     *
     * @param generic  the name of the new generic variable
     * @return         a reference to this
     *
     * @since 2.5
     */
    default T generic(final String generic) {
        return add(Generic.of(generic));
    }

    /**
     * Created a new {@link Generic} with the specified type and adds it to this
     * model.
     *
     * @param generic  the type of the new generic variable
     * @return         a reference to this
     *
     * @since 2.5
     */
    default T generic(final Type generic) {
        return add(Generic.of(generic));
    }

    /**
     * Created a new {@link Generic} with the specified name and type and adds
     * it to this model. The {@link Generic#getBoundType() bound type} of the
     * new {@link Generic} will be {@link Generic.BoundType#EXTENDS}.
     *
     * @param name  the name of the new generic
     * @param type  the upper bound of the new generic
     * @return      a reference to this
     *
     * @since 2.5
     */
    default T generic(final String name, final Type type) {
        return add(Generic.of(name).add(type));
    }
    
    /**
     * Returns a list of all the generics in this model.
     * <p>
     * The list returned must be mutable for changes!
     * 
     * @return  the generics 
     */
    List<Generic> getGenerics();
}