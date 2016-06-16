package com.speedment.common.injector.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Config {
    
    /**
     * The name of this config parameter in the configuration file.
     * 
     * @return  the config parameter name
     */
    String name();
    
    /**
     * The default name that should be used if no name is configured.
     * 
     * @return  the default name
     */
    String value();
    
}