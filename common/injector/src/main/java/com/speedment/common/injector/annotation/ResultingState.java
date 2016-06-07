package com.speedment.common.injector.annotation;

import com.speedment.common.injector.platform.State;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotes that by calling the annoted method, the state of that 
 * instance will be the specified state. If a class does not have
 * any methods witht his annotation, it will be considered 
 * automatically in the highest state.
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 * 
 * @see StateBefore
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResultingState {
    
    /**
     * The state that this injectable will be in once this annoted
     * method has finished executing.
     * 
     * @return  the state once finished
     */
    State value();
    
}