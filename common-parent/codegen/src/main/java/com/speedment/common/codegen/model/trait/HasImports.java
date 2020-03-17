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

import com.speedment.common.codegen.model.Import;

import java.lang.reflect.Type;
import java.util.List;

/**
 * A trait for models that contain {@link Import} components.
 *
 * @param <T>  the extending type
 *
 * @author Emil Forslund
 * @since  2.0
 */
public interface HasImports<T extends HasImports<T>> {
    
    /**
     * Adds the specified {@link Import} to this model.
     * 
     * @param dep  the new child
     * @return     a reference to this
     */
    @SuppressWarnings("unchecked")
    default T add(final Import dep) {
        getImports().add(dep.setParent(this));
        return (T) this;
    }

    /**
     * Adds the specified {@link Import} to this model. This is a synonym for
     * {@link #add(Import)}.
     *
     * @param dep  the dependency to add
     * @return     a reference to this
     *
     * @since 2.5
     */
    default T imports(final Import dep) {
        return add(dep);
    }

    /**
     * Creates an {@link Import} using the default implementation and adds it to
     * this model.
     *
     * @param type  the dependency to add
     * @return      a reference to this
     *
     * @since 2.5
     */
    default T imports(final Type type) {
        return add(Import.of(type));
    }

    /**
     * Creates a static {@link Import} using the default implementation and adds
     * it to this model.
     *
     * @param type  the dependency to add
     * @param method name of the static member method
     * @return      a reference to this
     *
     * @since 2.5
     */
    default T imports(final Type type, String method) {
        return add(Import.of(type).static_().setStaticMember(method));
    }

    /**
     * Returns a list of all imports in this model.
     * <p>
     * The list returned must be mutable for changes!
     * 
     * @return  the imports
     */
    List<Import> getImports();
}