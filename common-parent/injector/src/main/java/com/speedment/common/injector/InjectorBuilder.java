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
package com.speedment.common.injector;

import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.exception.NoDefaultConstructorException;
import com.speedment.common.injector.execution.ExecutionBuilder;
import com.speedment.common.injector.internal.InjectorBuilderImpl;
import com.speedment.common.logger.Logger;

import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.speedment.common.injector.execution.ExecutionBuilder.*;

/**
 * Builder pattern for the {@link Injector} interface.
 * 
 * @author Emil Forslund
 * @since  1.2.0
 */
public interface InjectorBuilder {

    /**
     * Appends a class that can be automatically dependency injected into
     * other classes to the builder. Classes can be appended in any order.
     * The final injection order will be determined once the
     * {@link #build()}-method is called.
     * <p>
     * If a class has already been passed as injectable with the same
     * InjectorKey, the previous one will be replaced by this new one. The
     * old one will never be instantiated.
     * <p>
     * This method will not replace any previous injectables.
     *
     * @param injectableClass the class that should be injectable
     * @return a reference to this builder
     *
     * @throws NoDefaultConstructorException if the specified type does not
     * have a default constructor.
     */
    InjectorBuilder withComponent(Class<?> injectableClass);

    /**
     * Appends a class that can be automatically dependency injected into
     * other classes to the builder. Classes can be appended in any order.
     * The final injection order will be determined once the
     * {@link #build()}-method is called.
     * <p>
     * If a class has already been passed as injectable with the same
     * InjectorKey, the previous one will be replaced by this new one. The
     * old one will never be instantiated.
     * <p>
     * This method will not replace any previous injectables.
     *
     * @param <T> the type of the injectable class
     * @param injectableClass the class that should be injectable
     * @param instanceSupplier a supplier of the component instance
     * @return a reference to this builder
     */
    <T> InjectorBuilder withComponent(Class<T> injectableClass, Supplier<T> instanceSupplier);

    /**
     * Puts one or multiple classes contained in an InjectBundle that can be
     * automatically dependency injected into other classes to the builder.
     * Classes can be appended in any order. The final injection order will
     * be determined once the {@link #build()}-method is called.
     * <p>
     * If an injectable class has already been passed as injectable with the
     * same key, the previous one will be replaced by this new one. The old
     * one will never be instantiated.
     *
     * @param bundleClass containing the injectable classes that shall be
     * appended
     * @return a reference to this builder
     *
     * @throws NoDefaultConstructorException if the specified type does not
     * have a default constructor.
     */
    InjectorBuilder withBundle(Class<? extends InjectBundle> bundleClass);

    /**
     * Overrides a particular configuration parameter in the config file
     * with the specified value.
     * 
     * @param name   the key to override
     * @param value  the new value
     * @return       a reference to this builder
     */
    InjectorBuilder withParam(String name, String value);

    /**
     * Sets the location of the configuration file.
     * 
     * @param configFile  the new config file location
     * @return            a reference to this builder
     */
    InjectorBuilder withConfigFileLocation(Path configFile);

    /**
     * Adds an InjectorProxy to the InjectorBuilder.
     * <p>
     * An InjectorProxy can be used to create/manipulate
     * instances on behalf of the actual injector.
     *
     * @param injectorProxy to add
     * @return a reference to this builder
     */
    InjectorBuilder withInjectorProxy(InjectorProxy injectorProxy);

    /**
     * Appends an action that must be executed sometime during the configuration 
     * phase. The action will have access to the instance as it is configured. 
     * This is equivalent to using the {@link ExecuteBefore}-annotation on a 
     * method in the class.
     * 
     * @param <T>               the injectable type
     * @param executionBuilder  builder for the execution to create
     * @return                  a reference to this builder
     */
    <T> InjectorBuilder before(ExecutionBuilder<T> executionBuilder);

    /**
     * Appends an action that must be executed sometime before specified 
     * type reaches the {@link State#INITIALIZED} state. The action will
     * have access to the instance as it is configured. This is equivalent 
     * to using the {@link ExecuteBefore}-annotation on a method in the 
     * class.
     * 
     * @param <T>             the injectable type
     * @param injectableType  the injectable type
     * @param action          action to be applied before state is reached
     * @return                a reference to this builder
     */
    default <T> InjectorBuilder beforeInitialized(
            Class<T> injectableType, Consumer<T> action) {
        
        return before(initialized(injectableType).withExecute(action));
    }

    /**
     * Appends an action that must be executed sometime before specified 
     * type reaches the {@link State#RESOLVED} state. The action will
     * have access to the instance as it is configured. This is equivalent 
     * to using the {@link ExecuteBefore}-annotation on a method in the 
     * class.
     * 
     * @param <T>             the injectable type
     * @param injectableType  the injectable type
     * @param action          action to be applied before state is reached
     * @return                a reference to this builder
     */
    default <T> InjectorBuilder beforeResolved(
            Class<T> injectableType, Consumer<T> action) {
        
        return before(resolved(injectableType).withExecute(action));
    }

    /**
     * Appends an action that must be executed sometime before specified 
     * type reaches the {@link State#STARTED} state. The action will
     * have access to the instance as it is configured. This is equivalent 
     * to using the {@link ExecuteBefore}-annotation on a method in the 
     * class.
     * 
     * @param <T>             the injectable type
     * @param injectableType  the injectable type
     * @param action          action to be applied before state is reached
     * @return                a reference to this builder
     */
    default <T> InjectorBuilder beforeStarted(
            Class<T> injectableType, Consumer<T> action) {
        
        return before(started(injectableType).withExecute(action));
    }

    /**
     * Appends an action that must be executed sometime before specified 
     * type reaches the {@link State#STOPPED} state. The action will
     * have access to the instance as it is configured. This is equivalent 
     * to using the {@link ExecuteBefore}-annotation on a method in the 
     * class.
     * 
     * @param <T>             the injectable type
     * @param injectableType  the injectable type
     * @param action          action to be applied before state is reached
     * @return                a reference to this builder
     */
    default <T> InjectorBuilder beforeStopped(
            Class<T> injectableType, Consumer<T> action) {
        
        return before(stopped(injectableType).withExecute(action));
    }

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

    /**
     * Returns the InjectorProxy for the provided class.
     *
     * @param clazz to obtain InjectorProxy for
     * @return the InjectorProxy for the provided class
     */
    InjectorProxy proxyFor(Class<?> clazz);
    
    /**
     * Returns the {@link Logger} object used by the default implementation of
     * the {@code InjectorBuilder}.
     * 
     * @return  the default logger
     */
    static Logger logger() {
        return InjectorBuilderImpl.INTERNAL_LOGGER;
    }

}