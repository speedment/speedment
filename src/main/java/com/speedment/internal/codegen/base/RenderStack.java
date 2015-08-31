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
package com.speedment.internal.codegen.base;

import java.util.stream.Stream;

/**
 * Represents the stack of models currently processed by the generator. For an
 * example, if a <code>Field</code> is currently being transformed into a String,
 * the stack might look like this:
 * <pre>
 *     File → Class → Method → Field
 * </pre>
 * 
 * @author Emil Forslund
 * @see Generator
 */
public interface RenderStack {
    
    /**
     * Returns a <code>Stream</code> of all models in the stack of a particular 
     * type from bottom and up.
     * 
     * @param <T>   the type of the models to return
     * @param type  the type of the models to return
     * @return      a stream of all models of that type
     */
    <T> Stream<T> fromBottom(Class<T> type);
    
    /**
     * Returns a <code>Stream</code> of all models in the stack of a particular 
     * type from top to bottom.
     * 
     * @param <T>   the type of the models to return
     * @param type  the type of the models to return
     * @return      a stream of all models of that type
     */
    <T> Stream<T> fromTop(Class<T> type);
    
    /**
     * Returns true if there are no models in the stack.
     * 
     * @return  true if the stack is empty
     */
    boolean isEmpty();
}