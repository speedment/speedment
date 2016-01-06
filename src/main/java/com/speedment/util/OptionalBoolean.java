package com.speedment.util;

import java.util.function.Supplier;

/**
 *
 * @author Emil Forslund
 */
public enum OptionalBoolean {
    
    FALSE, TRUE, EMPTY;
    
    public static OptionalBoolean empty() {
        return EMPTY;
    }
    
    public static OptionalBoolean of(boolean bool) {
        return bool ? TRUE : FALSE;
    }
    
    public static OptionalBoolean ofNullable(Boolean bool) {
        if (bool == null) {
            return empty();
        } else {
            return of(bool);
        }
    }
    
    public boolean get() {
        switch (this) {
            case FALSE : return false;
            case TRUE  : return true;
            default :
                throw new NullPointerException(
                    "Attempted to get value from empty OptionalBoolean."
                );
        }
    }
    
    public boolean orElse(boolean ifEmpty) {
        switch (this) {
            case FALSE : return false;
            case TRUE  : return true;
            default    : return ifEmpty;
        }
    }
    
    public <T extends Throwable> boolean orElseThrow(Supplier<T> thrower) throws T {
        switch (this) {
            case FALSE : return false;
            case TRUE  : return true;
            default    : throw thrower.get();
        }
    }
    
    public boolean isPresent() {
        return this != EMPTY;
    }
    
    public void ifPresent(BooleanConsumer consumer) {
        switch (this) {
            case FALSE : consumer.accept(false);
            case TRUE  : consumer.accept(true);
        }
    }
}