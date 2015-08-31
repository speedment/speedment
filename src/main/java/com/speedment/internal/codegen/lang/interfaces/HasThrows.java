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

import com.speedment.internal.codegen.lang.models.Type;
import java.util.Set;

/**
 * A trait for models that can throw exceptions.
 * 
 * @author Emil Forslund
 * @param <T> The extending type
 */
public interface HasThrows<T extends HasThrows<T>> {
    
    /**
     * Adds the specified exception reference to the <code>throws</code>-clause 
     * of this model.
     * 
     * @param exception  the new exception
     * @return           a reference to this
     */
    @SuppressWarnings("unchecked")
    default T add(final Type exception) {
        getExceptions().add(exception);
        return (T) this;
    }
    
    /**
     * Returns a <code>Set</code> with all the exceptions that can be throwed by
     * this model.
     * <p>
     * The set returned must be mutable for changes!
     * 
     * @return  all exceptions
     */
    Set<Type> getExceptions();
}