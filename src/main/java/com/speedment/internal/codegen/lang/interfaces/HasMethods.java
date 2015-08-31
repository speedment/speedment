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
package com.speedment.internal.codegen.lang.interfaces;

import com.speedment.internal.codegen.lang.models.Method;
import java.util.List;

/**
 * A trait for models that contain {@link Method} components.
 * 
 * @author Emil Forslund
 * @param <T> The extending type
 */
public interface HasMethods<T extends HasMethods<T>> {
    
    /**
     * Adds the specified {@link Method} to this model.
     * 
     * @param meth  the new child
     * @return      a reference to this
     */
    @SuppressWarnings("unchecked")
    default T add(final Method meth) {
        getMethods().add(meth);
        return (T) this;
    }
    
    /**
     * Returns a list of all methods in this model.
     * <p>
     * The list returned must be mutable for changes!
     * 
     * @return  the methods
     */
    List<Method> getMethods();
}