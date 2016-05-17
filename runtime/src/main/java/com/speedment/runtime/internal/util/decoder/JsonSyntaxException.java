package com.speedment.runtime.internal.util.decoder;

import java.util.concurrent.atomic.AtomicLong;

/**
 * A special form of exception that is thrown when parsing of a json file fails.
 * 
 * @author Emil Forslund
 * @since  2.4.0
 */
public final class JsonSyntaxException extends RuntimeException {

    private static final long serialVersionUID = -4342465797407497924L;
    
    private final long row, col;
    
    public JsonSyntaxException() {
        this.row = -1;
        this.col = -1;
    }
    
    public JsonSyntaxException(String message) {
        super(message);
        this.row = -1;
        this.col = -1;
    }
    
    public JsonSyntaxException(AtomicLong row, AtomicLong col) {
        this.row = row.get();
        this.col = col.get();
    }
    
    public JsonSyntaxException(AtomicLong row, AtomicLong col, Throwable cause) {
        super(cause);
        this.row = row.get();
        this.col = col.get();
    }
    
    public JsonSyntaxException(AtomicLong row, AtomicLong col, String message) {
        super(message);
        this.row = row.get();
        this.col = col.get();
    }
    
    public JsonSyntaxException(AtomicLong row, AtomicLong col, String message, Throwable cause) {
        super(message, cause);
        this.row = row.get();
        this.col = col.get();
    }
    
    @Override
    public String toString() {
        return getMessage() + " on row " + row + " col " + col + ".";
    }
}