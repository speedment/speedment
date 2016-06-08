package com.speedment.common.injector;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.annotation.Injectable;
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
 * @see Injectable
 * @see StateBefore
 * @see StateAfter
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
         * Appends an class with the {@link Injectable} annotation
         * to the builder. Classes can be appended in any order. The
         * final injection order will be determined once the 
         * {@link #build()}-method is called.
         * 
         * @param injectableType  the type that should be injectable
         * @return                a reference to this builder
         * 
         * @throws NoDefaultConstructorException  if the specified type does not 
         *                                        have a default constructor.
         */
        Builder canInject(Class<?> injectableType) throws NoDefaultConstructorException;
        
        /**
         * Builds the {@link Injector} instance, organizing the 
         * injectable instances based on their internal dependencies.
         * 
         * @return  the built instance
         * 
         * @throws NoDefaultConstructorException  if the specified type does not 
         *                                        have a default constructor.
         * @throws InstantiationException         if one of the injectables 
         *                                        can not be instantiated.
         */
        Injector build() throws InstantiationException, NoDefaultConstructorException;
    }
}