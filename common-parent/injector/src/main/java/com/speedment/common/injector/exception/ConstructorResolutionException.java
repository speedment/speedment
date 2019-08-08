package com.speedment.common.injector.exception;

/**
 * Exception thrown if the constructor resolution failed due to parameters not
 * being available in the injector graph or cyclic dependencies.
 *
 * @author Emil Forslund
 * @since  3.1.17
 */
public final class ConstructorResolutionException extends RuntimeException {

    private static final long serialVersionUID = -1584481400123877123L;

    public ConstructorResolutionException(String msg) {
        super(msg);
    }
}
