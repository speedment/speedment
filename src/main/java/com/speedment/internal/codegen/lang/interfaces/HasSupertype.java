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
import java.util.Optional;

/**
 * A trait for models that has a super type.
 * 
 * @author Emil Forslund
 * @param <T> The extending type
 */
public interface HasSupertype<T extends HasSupertype<T>> {
    
    /**
     * Sets the super type of this model.
     * 
     * @param type  the super type
     * @return      a reference to this
     */
	T setSupertype(Type type);
    
    /**
     * Returns the super type if such exists, else <code>empty</code>.
     * 
     * @return  the super type or <code>empty</code>
     */
	Optional<Type> getSupertype();
}