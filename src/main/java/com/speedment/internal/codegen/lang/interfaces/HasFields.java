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

import com.speedment.internal.codegen.lang.models.Field;
import java.util.Collection;
import java.util.List;

/**
 * A trait for models that contain {@link Field} components.
 * 
 * @author Emil Forslund
 * @param <T> The extending type
 */
public interface HasFields<T extends HasFields<T>> {
    
    /**
     * Adds the specified {@link Field} to this model.
     * 
     * @param field  the new child
     * @return       a reference to this
     */
    @SuppressWarnings("unchecked")
    default T add(final Field field) {
        getFields().add(field);
        return (T) this;
    }
    
    /**
     * Adds all the specified {@link Field} members to this model.
     * 
     * @param fields  the new children
     * @return        a reference to this
     */
    @SuppressWarnings("unchecked")
    default T addAllFields(final Collection<? extends Field> fields) {
        getFields().addAll(fields);
        return (T) this;
    }
    
    /**
     * Returns a list of all the fields in this model.
     * <p>
     * The list returned must be mutable for changes!
     * 
     * @return  all the fields 
     */
    List<Field> getFields();
}