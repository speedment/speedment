package com.speedment.runtime.util;

import com.speedment.runtime.annotation.Api;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

/**
 * A utility class for converting optional types to their boxed equivalents.
 * 
 * @author Emil Forslund
 * @author Simon Jonasson
 */
@Api(version = "3.0")
public final class OptionalUtil {
    
    /**
     * Convert an optional value to a boxed one. If the inner value is
     * empty, {@code null} will be returned.
     * 
     * @param optional  the optional value
     * @return          the inner value or {@code null}
     */
    public static Integer toBoxed(OptionalInt optional) {
        return optional.isPresent() ? optional.getAsInt() : null;
    }
    
    /**
     * Convert an optional value to a boxed one. If the inner value is
     * empty, {@code null} will be returned.
     * 
     * @param optional  the optional value
     * @return          the inner value or {@code null}
     */
    public static Boolean toBoxed(OptionalBoolean optional) {
        return optional.isPresent() ? optional.getAsBoolean() : null;
    }
    
    /**
     * Convert an optional value to a boxed one. If the inner value is
     * empty, {@code null} will be returned.
     * 
     * @param optional  the optional value
     * @return          the inner value or {@code null}
     */
    public static Long toBoxed(OptionalLong optional) {
        return optional.isPresent() ? optional.getAsLong() : null;
    }
    
    /**
     * Convert an optional value to a boxed one. If the inner value is
     * empty, {@code null} will be returned.
     * 
     * @param optional  the optional value
     * @return          the inner value or {@code null}
     */
    public static Double toBoxed(OptionalDouble optional) {
        return optional.isPresent() ? optional.getAsDouble() : null;
    }
    
    private OptionalUtil() {
        throw new UnsupportedOperationException();
    }
}