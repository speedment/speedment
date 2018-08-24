package com.speedment.runtime.config.resolver;

/**
 * Exception thrown while resolving the syntax of a document.
 *
 * @author Emil Forslund
 * @since  3.1.6
 */
public final class ResolverException extends RuntimeException {

    private static final long serialVersionUID = -93148167608451282L;

    /**
     * Creates a new instance of the exception.
     *
     * @param message  the message
     */
    public ResolverException(String message) {
        super(message);
    }

    /**
     * Creates a new instance of the exception.
     *
     * @param message  the message
     * @param cause    the cause
     */
    public ResolverException(String message, Throwable cause) {
        super(message, cause);
    }
}
