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
package com.speedment.runtime.util;

import com.speedment.runtime.config.typetoken.TypeToken;
import com.speedment.runtime.internal.config.typetoken.InternalTypeTokenFactory;
import static com.speedment.runtime.util.StaticClassUtil.instanceNotAllowed;

/**
 * A factory that can create type tokens using the most fitting implementation.
 * 
 * @author  Emil Forslund
 * @author  Simon Jonasson
 * @since   3.0.0
 */
public final class TypeTokenFactory {

    public static TypeToken createStringToken() { return TypeTokenFactory.create(String.class); }
    public static TypeToken createObjectToken() { return TypeTokenFactory.create(Object.class); }
    public static TypeToken createIntegerToken() { return TypeTokenFactory.create(Integer.class); }
    public static TypeToken createDoubleToken() { return TypeTokenFactory.create(Double.class); }
    public static TypeToken createLongToken() { return TypeTokenFactory.create(Long.class); }
    public static TypeToken createBooleanToken() { return TypeTokenFactory.create(Boolean.class); }
    
    /**
     * Produces an instance of {@link TypeToken} for any non-generic type.
     * 
     * @param type  a non-generic type
     * @return      a type token representing that type
     */
    public static TypeToken create(Class<?> type) {
        return InternalTypeTokenFactory.create(type);
    }
    
    /**
     * Utility classes should not be instantiated.
     */
    private TypeTokenFactory() {
        instanceNotAllowed(getClass());
    }
}
