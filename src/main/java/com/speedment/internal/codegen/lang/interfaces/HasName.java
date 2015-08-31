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

/**
 * A trait for models that have a name.
 * 
 * @author Emil Forslund
 * @param <T> The extending type
 */
public interface HasName<T extends HasName<T>> {
    
    /**
     * Sets the name of this model.
     * <p>
     * This must not be null!
     * 
     * @param name  the new name
     * @return      a reference to this
     */
    T setName(final String name);
    
    /**
     * Returns the name of this model. 
     * <p>
     * This should never be null!
     * 
     * @return  the name
     */
    String getName();
}