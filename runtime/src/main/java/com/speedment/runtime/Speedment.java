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
package com.speedment.runtime;

import com.speedment.common.injector.Injector;
import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.component.Component;

/**
 * The {@code Platform} class acts as a generic holder of different system
 * {@link Component Components}. Using its pluggable architecture, one can
 * replace existing default implementations of existing Components or plug in
 * custom made implementation of any Interface.
 * <p>
 * Pluggable instances must implement the {@link Component} interface.
 *
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.0.0
 */
@Api(version = "2.4")
public interface Speedment {
    
    /**
     * Starts the Speedment instance and allocates any resources. The Speedment
     * instance must not be called before this method has been executed.
     */
    void start();

    /**
     * Stops the Speedment instance and deallocates any allocated resources.
     * After stop() has been called, the Speedment instance can not be called
     * any more.
     */
    void stop();
    
    /**
     * Returns the Injector that holds the Speedment platform.
     * 
     * @return  the injector
     */
    Injector injector();
}