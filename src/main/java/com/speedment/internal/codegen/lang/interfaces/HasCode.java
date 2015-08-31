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

import static com.speedment.internal.util.NullUtil.requireNonNulls;
import java.util.Collections;
import java.util.List;

/**
 * A trait for models that contains code.
 * 
 * @author Emil Forslund
 * @param <T> The extending type
 */
public interface HasCode<T extends HasCode<T>> {
    
    /**
     * Adds the specified row of code to this model.
     * 
     * @param row  the row
     * @return     a reference to this
     */
    @SuppressWarnings("unchecked")
    default T add(String row) {
        getCode().add(row);
        return (T) this;
    }
    
    /**
     * Adds all the specified rows to this model.
     * 
     * @param rows  the rows
     * @return      a reference to this
     */
    @SuppressWarnings("unchecked")
    default T add(String... rows) {
        requireNonNulls(rows);
        Collections.addAll(getCode(), rows);
        return (T) this;
    }
    
    /**
     * Returns a list of the code rows of this model.
     * <p>
     * The list returned must be mutable for changes!
     * 
     * @return  the code rows
     */
    List<String> getCode();
}