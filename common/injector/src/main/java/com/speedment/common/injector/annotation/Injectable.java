package com.speedment.common.injector.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotes that a class can be injected into instances using dependency injection.
 * Each {@link Injectable} class is stored under a specific class signature. If
 * multiple injectibles are stored under the same key, the most recent one is the
 * only one that will be used.
 * <p>
 * If the {@link UseIdentity} class signature is specified it means that every
 * annoted class is its own signature.
 * <p>
 * The initialization of an {@link Injectable} class is done in phases. To
 * insert logic in one of the phases, use the {@link StateAfter} annotation.
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 * 
 * @see StateBefore
 * @see StateAfter
 * @see Inject
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Injectable {
    
    /**
     * The class signature that instances with this annotations should
     * be stored under. If two implementations share the same signature,
     * the last one loaded will replace the other one.
     * <p>
     * The specified class must be one of the following:
     * <ul>
     *     <li>The {@link UseIdentity} class
     *     <li>The same class as the one with the annotation
     *     <li>A superclass of the one with the annotation
     * </ul>
     * 
     * @return  the class signature for this {@link Injectable}.
     */
    Class<?> value() default UseIdentity.class;
    
    final class UseIdentity {
        private UseIdentity() {}
    }
}