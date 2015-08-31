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
package com.speedment.internal.codegen.lang.models;

import com.speedment.internal.codegen.lang.interfaces.Copyable;

/**
 * A model that represents any kind of value declared in code.
 * 
 * @author Emil Forslund
 * @param <V> the value type
 */
public interface Value<V> extends Copyable<Value<V>> {
    
    /**
     * Sets the inner value of this.
     * 
     * @param value  the new value
     * @return       a reference to this model
     */
    Value<V> setValue(V value);
    
    /**
     * Returns the inner value of this model.
     * 
     * @return  the inner value 
     */
	V getValue();
}