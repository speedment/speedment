/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.codegen.model.trait;

import java.util.function.Consumer;

/**
 * Trait for code generator models that can be called.
 * 
 * @param <T> The extending type
 * 
 * @author Emil Forslund
 * @since  2.0
 */
public interface HasCall<T> {
    
    /**
     * Calls the specified {@link Consumer} with this object as the parameter.
     * This method exists so that methods can operate on an object without
     * breaking the flow.
     * 
     * @param procedure  the procedure to call
     * @return           a reference to this
     */
	@SuppressWarnings("unchecked")
    default T call(Consumer<T> procedure) {
		procedure.accept((T) this);
		return (T) this;
	}
    
    /**
     * Calls the specified {@link Runnable}. This method exists so that methods 
     * can operate on an object without breaking the flow.
     * 
     * @param procedure  the procedure to call
     * @return           a reference to this
     */
	@SuppressWarnings("unchecked")
    default T call(Runnable procedure) {
		procedure.run();
		return (T) this;
	}
}