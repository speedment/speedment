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

import com.speedment.internal.codegen.lang.models.Initalizer;
import java.util.List;

/**
 * A trait for models that contain {@link Initalizer} components.
 * 
 * @author Emil Forslund
 * @param <T> The extending type
 */
public interface HasInitalizers<T extends HasInitalizers<T>> {
    
    /**
     * Adds the specified {@link Initalizer} to this model.
     * 
     * @param init  the new child
     * @return      a reference to this
     */
    @SuppressWarnings("unchecked")
    default T add(final Initalizer init) {
        getInitalizers().add(init);
        return (T) this;
    }
    
    /**
     * Returns a list of all intializers in this model.
     * <p>
     * The list returned must be mutable for changes!
     * 
     * @return  the initalizers
     */
    List<Initalizer> getInitalizers();
}