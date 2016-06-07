package com.speedment.common.injector.exception;

/**
 * A runtime exception that is thrown if a class specified does
 * not have a default constructor.
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class NoDefaultConstructorException extends RuntimeException {
    
    public NoDefaultConstructorException() {}
    
    public NoDefaultConstructorException(String msg) {
        super(msg);
    }
    
    public NoDefaultConstructorException(Throwable thrw) {
        super(thrw);
    }
    
    public NoDefaultConstructorException(String msg, Throwable thrw) {
        super(msg, thrw);
    }
}