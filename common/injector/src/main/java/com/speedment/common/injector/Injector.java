package com.speedment.common.injector;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.annotation.Injectable;
import com.speedment.common.injector.exception.NoDefaultConstructorException;
import com.speedment.common.injector.internal.InjectorImpl;
import com.speedment.common.injector.platform.State;

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
     * Creates a new instance of the specified class, injecting all its
     * {@link Inject} fields automatically. The class is determined using
     * the default class loader.
     * 
     * @param className  the fully qualified name of the class to instantiate
     * @return           the created instance
     * 
     * @throws ClassNotFoundException         if the class could not be found on the class path
     * @throws NoDefaultConstructorException  if it does not have a default constructor
     * @throws IllegalStateException          if a cyclic-dependency makes it impossible
     */
    Object newInstance(String className) throws IllegalStateException, ClassNotFoundException;
    
    /**
     * Creates a new instance of the specified class, injecting all its
     * {@link Inject} fields automatically.
     * 
     * @param <T>   the type to instantiate
     * @param type  the type to instantiate
     * @return      the created instance
     * 
     * @throws NoDefaultConstructorException  if it does not have a default constructor
     * @throws IllegalStateException          if a cyclic-dependency makes it impossible
     */
    <T> T newInstance(Class<T> type) throws NoDefaultConstructorException, IllegalStateException;

    /**
     * Returns the instance associated with the specified class signature.
     * If it has not been resolved yet, it is resolved by injecting its
     * dependencies recursively.
     * <p>
     * If the specified type could not be found, a 
     * {@code NullPointerException} is thrown.
     * 
     * @param <T>    the class signature of the injectable type
     * @param type   the expected type
     * @param state  the (minimal) state that the result must be in
     * @return       the automatically resolved instance
     * 
     * @throws NullPointerException           if it could not be found
     * @throws IllegalArgumentException       if it is missing the {@link Injectable} annotation
     * @throws NoDefaultConstructorException  if it does not have a default constructor
     * @throws IllegalStateException          if a cyclic-dependency makes it impossible to resolve
     */
    <T> T getOrThrow(Class<T> type, State state) throws 
            NullPointerException, 
            IllegalArgumentException,
            NoDefaultConstructorException, 
            IllegalStateException;
    
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
         */
        Builder canInject(Class<?> injectableType);
        
        /**
         * Builds the {@link Injector} instance, organizing the 
         * injectable instances based on their internal dependencies.
         * 
         * @return  the built instance
         */
        Injector build();
    }
}