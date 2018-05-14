package com.speedment.runtime.nanohttpd.throwable;

/**
 * Exception thrown if there is a problem with the REST setup.
 *
 * @author Emil Forslund
 * @since  3.1.1
 */
public final class SpeedmentRestException extends RuntimeException {

    private static final long serialVersionUID = 3261336843215189470L;

    public SpeedmentRestException(String message) {
        super(message);
    }

    public SpeedmentRestException(String message, Throwable cause) {
        super(message, cause);
    }
}
