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
package com.speedment.common.injector;

import com.speedment.common.injector.annotation.Execute;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.exception.NoDefaultConstructorException;
import com.speedment.common.injector.internal.InjectorImpl;

/**
 * The factory used to produce instances of classes with
 * depdendency injection. This interface is expected to be
 * implemented using a builder pattern.
 * <p>
 * To create a new instance that requires dependency injection,
 * simply call {@link #newInstance(java.lang.Class)}.
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 * 
 * @see Inject
 * @see Execute
 * @see ExecuteBefore
 */
public interface Injector {

    /**
     * Returns the instance associated with the specified class signature.
     * <p>
     * If the specified type could not be found, an
     * {@code IllegalArgumentException} is thrown.
     * 
     * @param <T>   the class signature of the injectable type
     * @param type  the expected type
     * @return      the automatically resolved instance
     * 
     * @throws IllegalArgumentException  if it could not be found
     */
    <T> T get(Class<T> type) throws IllegalArgumentException;
    
    /**
     * Create a new instance of the specified type, injecting all the
     * fields with the {@link Inject}-annotations.
     * 
     * @param <T>   the type to create
     * @param type  the type to create
     * @return      the instance created
     * 
     * @throws InstantiationException    if the class could not be instantiated
     * @throws IllegalArgumentException  if the class does not have a default constructor
     */
    <T> T newInstance(Class<T> type) throws InstantiationException, IllegalArgumentException;
    
    /**
     * Stop all installed componenets by calling their 
     * {@code @ExecuteBefore(STOPPED)}-methods in the correct order.
     */
    void stop();
    
    /**
     * Returns a new builder for the {@link Injector} interface that uses the default
     * implementation.
     * 
     * @return  the builder
     */
    static Builder builder() {
        return InjectorImpl.builder();
    }
    
    /**
     * Builder pattern for the {@link Injector} interface.
     */
    interface Builder {
        
        /**
         * Appends one or multiple classes that can be automatically dependency 
         * injected into other classes to the builder. Classes can be appended in 
         * any order. The final injection order will be determined once the 
         * {@link #build()}-method is called.
         * 
         * @param injectableTypes  the types that should be injectable
         * @return                 a reference to this builder
         * 
         * @throws NoDefaultConstructorException  if the specified type does not 
         *                                        have a default constructor.
         */
        Builder canInject(Class<?>... injectableTypes) throws NoDefaultConstructorException;
        
        /**
         * Builds the {@link Injector} instance, organizing the 
         * injectable instances based on their internal dependencies.
         * 
         * @return  the built instance
         * 
         * @throws InstantiationException  if one of the injectables can not 
         *                                 be instantiated.
         */
        Injector build() throws InstantiationException;
    }
}