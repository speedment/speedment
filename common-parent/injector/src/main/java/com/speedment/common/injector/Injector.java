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
package com.speedment.common.injector;

import com.speedment.common.injector.annotation.Execute;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.internal.InjectorImpl;
import com.speedment.common.logger.Logger;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * The factory used to produce instances of classes with
 * dependency injection. This interface is expected to be
 * implemented using a builder pattern.
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
    <T> T getOrThrow(Class<T> type) throws IllegalArgumentException;
    
    /**
     * Looks for an dependency injectable instance of the specified class
     * and if it exists, returns it. If it does not exist, an empty Optional
     * is returned.
     * 
     * @param <T>   the type to look for
     * @param type  type or super type of that to be returned
     * @return      the found instance
     */
    <T> Optional<T> get(Class<T> type);
    
    /**
     * Returns a stream of all the instances associated with the specified class
     * signature.
     * 
     * @param <T>   the class signature of the injectable type
     * @param type  the expected type
     * @return      the automatically resolved instances
     */
    <T> Stream<T> stream(Class<T> type);

    /**
     * Returns a stream of all the injectable types that are accessible from
     * this Injector.
     * 
     * @return  stream of injectable types
     */
    Stream<Class<?>> injectables();
    
    /**
     * Sets all the {@link Inject}-annotated fields in the specified instance.
     * This method does <b>not</b> invoke any {@link Execute}-annotated methods
     * since it is typically called after the platform has been constructed.
     * 
     * @param <T>       the type to configure
     * @param instance  the instance to configure (must not be null)
     * @return          the instance created
     */
    <T> T inject(T instance) throws IllegalArgumentException;
    
    /**
     * Stop all installed components by calling their 
     * {@code @ExecuteBefore(STOPPED)}-methods in the correct order.
     */
    void stop();
    
    /**
     * Returns the {@code ClassLoader} used by the injector.
     * 
     * @return  the class loader in use
     */
    ClassLoader classLoader();
    
    /**
     * Returns a new Injector builder based on the classes installed into
     * this one.
     * 
     * @return  the new injector builder
     */
    InjectorBuilder newBuilder();
    
    /**
     * Returns a new builder for the {@link Injector} interface that uses the
     * default implementation.
     * 
     * @return  the builder
     */
    static InjectorBuilder builder() {
        return InjectorImpl.builder();
    }
    
    /**
     * Returns a new builder for the {@link Injector} interface that uses the 
     * default implementation.
     * 
     * @param classLoader  the class loader to use
     * @return             the builder
     */
    static InjectorBuilder builder(ClassLoader classLoader) {
        return InjectorImpl.builder(classLoader);
    }
    
    /**
     * Returns the {@link logger} object used by the default implementation of 
     * the {@code Injector}.
     * 
     * @return  the default logger
     */
    static Logger logger() {
        return InjectorImpl.LOGGER;
    }
}