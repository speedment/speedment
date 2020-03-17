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

/**
 *
 * @param  <ENTITY>  the entity type
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public interface Setter<ENTITY> {
    
    /**
     * A generic (untyped) set-method. Will throw a {@code ClassCastException}
     * if the wrong type is given.
     * 
     * @param entity  the entity to set in
     * @param value   the value to set to
     * 
     * @throws ClassCastException  if the specified value is of the wrong type
     */
    void set(ENTITY entity, Object value);
    
}