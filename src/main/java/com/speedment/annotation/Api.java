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
package com.speedment.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a Method, Type or Constructor as an API. An API marked entity with a
 * certain API version will remain "Compatible" across new software releases
 * indefinitely , for that particular API version.
 * <p>
 * "Compatible" is defined as follows:
 * <p>
 * Method: Its signature (i.e. name and parameters), and return type will
 * remain.
 * <p>
 * Interface: Its name and all its declared methods with its return type and
 * signature and static fields will remain. New methods and static field might
 * be added.
 * <p>
 * Enum: Its elements will remain and their individual order will remain. New
 * elements might be added.
 *
 * @author pemi
 */
@Api(version = "2.0", snapshot = false)
@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.CONSTRUCTOR})
public @interface Api {

    /**
     * Returns the current API version.
     *
     * @return the current API version.
     */
    String version();

    /**
     * Returns if this API version is a snapshot, i.e. it is not yet "frozen"
     * and may change within the given API version.
     *
     * @return true if this API version is a snapshot, false otherwise.
     */
    boolean snapshot() default false;
}
