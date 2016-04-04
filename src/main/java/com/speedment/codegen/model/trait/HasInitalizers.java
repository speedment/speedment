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
package com.speedment.codegen.model.trait;

import com.speedment.annotation.Api;
import com.speedment.codegen.model.Initializer;
import java.util.List;

/**
 * A trait for models that contain {@link Initializer} components.
 * 
 * @author Emil Forslund
 * @param <T> The extending type
 * @since  2.0
 */
@Api(version = "2.3")
public interface HasInitalizers<T extends HasInitalizers<T>> {
    
    /**
     * Adds the specified {@link Initializer} to this model.
     * 
     * @param init  the new child
     * @return      a reference to this
     */
    @SuppressWarnings("unchecked")
    default T add(final Initializer init) {
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
    List<Initializer> getInitalizers();
}