package com.speedment.common.injector.annotation;

import com.speedment.common.injector.platform.State;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotes that the annoted method must be in the specified state
 * or a higher state before it is executed.
 * 
 * @author Emil Forslund
 * @since  1.0.0
 * 
 * @see StateAfter
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StateBefore {
    
    /**
     * The lowest acceptable {@link State} that this {@link Injectable}
     * must be in for this method to execute. This is only ensured if
     * the method is invoced internally by the injector.
     * 
     * @return  the minimum accetable state for this method to execute
     */
    State value();
}