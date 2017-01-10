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
import java.nio.file.Path;
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
     * Returns a stream of all the instances associated with the specified class
     * signature.
     * 
     * @param <T>   the class signature of the injectable type
     * @param type  the expected type
     * @return      the automatically resolved instances
     */
    <T> Stream<T> stream(Class<T> type);

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
     * Returns a new Injector builder based on the classes installed into
     * this one.
     * 
     * @return  the new injector builder
     */
    Builder newBuilder();
    
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
         * Appends a class that can be automatically dependency 
         * injected into other classes to the builder. Classes can be appended in 
         * any order. The final injection order will be determined once the 
         * {@link #build()}-method is called.
         * <p>
         * If a class has already been passed as injectable with the same InjectorKey,
         * the previous one will be replaced by this new one. The old one will
         * never be instantiated.
         * <p>
         * This method will not replace any previous injectables.
         * 
         * @param injectableType  the type that should be injectable
         * @return                a reference to this builder
         * 
         * @throws NoDefaultConstructorException  if the specified type does not 
         *                                        have a default constructor.
         */
        Builder put(Class<?> injectableType) throws NoDefaultConstructorException;

        /**
         * Appends a class that can be automatically dependency 
         * injected into other classes to the builder. Classes can be appended in 
         * any order. The final injection order will be determined once the 
         * {@link #build()}-method is called.
         * <p>
         * If a class has already been passed as injectable with the same key,
         * the previous one will be replaced by this new one. The old one will
         * never be instantiated.
         * 
         * @param key             the key to check uniqueness with
         * @param injectableType  the type that should be injectable
         * @return                a reference to this builder
         * 
         * @throws NoDefaultConstructorException  if the specified type does not 
         *                                        have a default constructor.
         */
        Builder put(String key, Class<?> injectableType) throws NoDefaultConstructorException;
        
        
         /**
         * Puts one or multiple classes contained in an InjectBundle that can 
         * be automatically dependency injected into other classes to the builder. 
         * Classes can be appended in any order. The final injection order will 
         * be determined once the {@link #build()}-method is called.
         * <p>
         * If an injectable class has already been passed as injectable with the same key,
         * the previous one will be replaced by this new one. The old one will
         * never be instantiated.
         * 
         * @param bundleClass     containing the injectable classes that shall be appended
         * @return                a reference to this builder
         * 
         * @throws NoDefaultConstructorException  if the specified type does not 
         *                                        have a default constructor.
         */
        Builder putInBundle(Class<? extends InjectBundle> bundleClass) throws NoDefaultConstructorException;        
        
        /**
         * Overrides a particular configuration parameter in the config file
         * with the specified value.
         * 
         * @param key    the key to override
         * @param value  the new value
         * @return       a reference to this builder
         */
        Builder putParam(String key, String value);
        
        /**
         * Sets the location of the configuration file.
         * 
         * @param configFile  the new config file location
         * @return            a reference to this builder
         */
        Builder withConfigFileLocation(Path configFile);
        
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