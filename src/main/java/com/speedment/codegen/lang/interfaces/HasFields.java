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
package com.speedment.codegen.lang.interfaces;

import com.speedment.codegen.lang.models.Field;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Emil Forslund
 * @param <T>
 */
public interface HasFields<T extends HasFields<T>> {
    
    @SuppressWarnings("unchecked")
    default T add(final Field field) {
        getFields().add(field);
        return (T) this;
    }
    
    @SuppressWarnings("unchecked")
    default T addAllFields(final Collection<? extends Field> fields) {
        getFields().addAll(fields);
        return (T) this;
    }
    
    List<Field> getFields();
}