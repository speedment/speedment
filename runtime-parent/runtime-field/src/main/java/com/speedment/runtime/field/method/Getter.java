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
package com.speedment.runtime.field.method;

import java.util.function.Function;

/**
 *
 * @param  <ENTITY>  the entity type
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public interface Getter<ENTITY> {
    
    /**
     * A generic (untyped) get method.
     * 
     * @param entity  the entity to get from
     * @return        the value
     */
    Object apply(ENTITY entity);
    
    /**
     * Returns this object, typed as a {@code Function} method.
     * 
     * @return  this object as a function
     */
    default Function<ENTITY, ? extends Object> asFunction() {
        return this::apply;
    }
}