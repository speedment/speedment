package com.speedment.common.injector.annotation;

import com.speedment.common.injector.State;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotes that the method should be executed as part of the
 * platform initialization. The annoted method can only take 
 * parameters with the @{@link Inject}-annotation.
 * <p>
 * This method must be executed before this component can be
 * considered to be in the specified state.
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 * 
 * @see  Execute
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExecuteBefore {
    
    /**
     * The state before which this annoted method must be
     * executed.
     * 
     * @return  the state
     */
    State value();
}
