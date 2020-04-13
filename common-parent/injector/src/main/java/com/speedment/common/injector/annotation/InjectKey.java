/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
import java.lang.annotation.*;

/**
 * Annotates that the specified interface or class should have the
 * result of the {@code getName()} method on the given class as the
 * key when organizing injectable instances.
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectKey {
    
    /**
     * The key that this implementation should be associated with. The key is
     * typically a public API interface while the implementing class can be an
     * internal component. If multiple implementations share the same key, the
     * most recently installed will be used in the typical case (unless the
     * internal implementation is requested specifically).
     * 
     * @return  the injection key
     */
    Class<?> value();
    
    /**
     * Returns {@code true} if the class with this annotation should replace any
     * previously defined implementations with the same {@link #value()} or if
     * it should be appended as a more recent implementation without replacing
     * the existing one.
     * <p>
     * If this is {@code false}, then <em>every</em> implementation with the 
     * same {@link #value()} will be instantiated and configured by the 
     * dependency injector, but when a specific key is requested using 
     * {@link Injector#get(Class)}, then the most recent one is returned.
     * <p>
     * If this is {@code true}, then <em>only</em> the most recent 
     * implementation will be instantiated and configured.
     * <p>
     * The default value for this method is {@code true}.
     * 
     * @return  if annotated implementation overwrites existing implementations
     * 
     * @since 1.1.0
     */
    boolean overwrite() default true;
}