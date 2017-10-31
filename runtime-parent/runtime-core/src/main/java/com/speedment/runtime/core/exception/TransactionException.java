package com.speedment.runtime.core.exception;

/** 
 * A specialization of {@code RuntimeException} that is thrown when something 
 * is wrong with a transaction. 
 *
 * @author Per Minborg
 * @since 3.0.17
 */
public class TransactionException extends RuntimeException {

    private static final long serialVersionUID = 8352114238652823324L;

    public TransactionException() {
    }

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionException(Throwable cause) {
        super(cause);
    }

    public TransactionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
