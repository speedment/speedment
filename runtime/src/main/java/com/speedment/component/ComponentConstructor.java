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
package com.speedment.component;

import com.speedment.Speedment;
import com.speedment.annotation.Api;

/**
 * This functional interface represents a constructor for a class implementing
 * the {@link Component} interface.
 * 
 * @author     Emil Forslund
 * @param <C>  the component type to build
 * @since      2.3
 */
@Api(version="2.3")
@FunctionalInterface
public interface ComponentConstructor<C extends Component> {
    
    /**
     * Instantiates a new {@link Component} with the specified 
     * {@link Speedment} handle.
     * <p>
     * This method is meant to be implemented using a functional reference to the
     * implementing classes constructor method.
     * 
     * @param speedment  the speedment instance
     * @return           the created instance
     */
    C create(Speedment speedment);
}