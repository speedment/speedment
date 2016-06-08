package com.speedment.common.injector.annotation;

import com.speedment.common.injector.State;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotes a field that should be set automatically using dependency injection.
 * Each injected field has a specified state that it is required to be in. It
 * is up to the injector to make sure that a type has been properly setup.
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {
    
    /**
     * The {@link State} that the injected value must be in before 
     * it can be injected.
     * <p>
     * The default state is {@link State#STARTED}.
     * 
     * @return  the expected phase of the injected component
     */
    State value() default State.STARTED;
    
}