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
package com.speedment.common.injector.annotation;

import com.speedment.common.injector.Injector;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotes that this type requires a number of classes to be
 * automatically injectable. All the specified types must be
 * concrete classes with a default constructor.
 * <p>
 * This annotatation will only be parsed if the class that it
 * is located on is installed in the {@link Injector}.
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface IncludeInjectable {
    
    /**
     * Implementation types that can be automatically dependency
     * injected.
     * 
     * @return  implementation types that are injectable
     */
    Class<?>[] value();
    
}
