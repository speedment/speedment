/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.config.typetoken;

/**
 * 
 * @author  Emil Forslund
 * @author  Simon Jonasson
 * @since   3.0.0
 */
public interface ArrayTypeToken extends TypeToken {
    
    /**
     * Returns the dimension for the array in this type. 
     * <p>
     * Here are some examples:
     * <pre>
     *     String       → 0
     *     String[]     → 1
     *     String[][]   → 2
     *     String[][][] → 3
     * </pre> 
     * 
     * @return  the array dimension
     */
    int getArrayDimension();

    @Override
    default boolean isArray() {
        return true;
    }
}