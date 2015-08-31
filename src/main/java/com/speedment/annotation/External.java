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
 * Marks a Method as exposed to external configuration.
 * <p>
 * A Method that is marked as exposed will be usable in GUI, configuration files
 * etc.
 *
 * @author pemi
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface External {

    /**
     * Class that holds the basic type.
     *
     * @return the basic type
     */
    Class<?> type();

    /**
     * Returns if this @External shall be visible in the GUI.
     *
     * @return if this @External shall be visible in the GUI
     */
    boolean isVisibleInGui() default true;

    /**
     * Returns if this @External is secrete (eg a password). Externals with this
     * property set will be stored in a separate file.
     *
     * @return if this @External is secrete (eg a password)
     */
    boolean isSecret() default false;
}
