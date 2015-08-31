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
package com.speedment.internal.util;

/**
 * Support class for classes that only contains static methods and fields.
 * This interface can for example be used for various "Util" classes.
 *
 * @author pemi
 */
public final class StaticClassUtil {

    /**
     * Support method that can be used in constructors to throw an
     * {@code UnsupportedOperationException} if someone is trying to create an
     * instance of the class.
     * 
     * @param caller the class of the instance that called this method
     */
    public static void instanceNotAllowed(Class<?> caller) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(
            "It is not allowed to create instances of the " + 
            caller.getName() + 
            " class."
        );
    }
    
    /**
     * Utility classes should not be instantiated.
     */
    private StaticClassUtil() { instanceNotAllowed(getClass()); }
}