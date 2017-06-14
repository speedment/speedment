/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.injector.execution;

import com.speedment.common.injector.State;
import com.speedment.common.injector.dependency.Dependency;
import com.speedment.common.injector.exception.NotInjectableException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Something that should be executed as part of the configuration process.
 * 
 * @param <T> the type to execute on
 * 
 * @author  Emil Forslund
 * @since   1.2.0
 */
public interface Execution<T> {
    
    /**
     * A mapper that can resolve a particular type into an object of that type.
     */
    @FunctionalInterface
    interface ClassMapper {
        
        /**
         * Resolves the specified type into an instance, throwing an exception
         * if it could not be resolved.
         * 
         * @param <T>  the type to resolve
         * 
         * @param type  the type to resolve
         * @return      the instance resolved
         * 
         * @throws NotInjectableException  if it can't be resolved
         */
        <T> T apply(Class<T> type) throws NotInjectableException;
    }
    
    /**
     * Returns the type of the class executed on.
     * 
     * @return  the type
     */
    Class<T> getType();
    
    /**
     * The state that this action will execute in.
     * 
     * @return  the state
     */
    State getState();
    
    /**
     * Set of requirements that must be fulfilled before this execution may
     * proceed.
     * 
     * @return  set of dependencies
     */
    Set<Dependency> getDependencies();
    
    /**
     * Invokes this {@code Execution}, resolving the required dependencies by
     * passing them to the specified {@code classMapper}.
     * 
     * @param component    the component executed on
     * @param classMapper  the class mapper to use to resolve dependencies
     * 
     * @throws IllegalAccessException     passed on from {@link Method}
     * @throws IllegalArgumentException   passed on from {@link Method}
     * @throws InvocationTargetException  passed on from {@link Method}
     * @throws NotInjectableException     if the {@code ClassMapper} could not
     *                                    resolve a particular type
     */
    void invoke(T component, ClassMapper classMapper) 
    throws IllegalAccessException, 
           IllegalArgumentException, 
           InvocationTargetException,
           NotInjectableException;
    
}