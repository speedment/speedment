package com.speedment.orm.exception;

/**
 *
 * @author pemi
 */
public class SpeedmentException extends RuntimeException {

    static final long serialVersionUID = -623523923713561356L;

    public SpeedmentException() {
        super();
    }

    public SpeedmentException(String message) {
        super(message);
    }

    public SpeedmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpeedmentException(Throwable cause) {
        super(cause);
    }

    protected SpeedmentException(String message, Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
