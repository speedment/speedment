package com.speedment.common.function;

/**
 * @author Emil Forslund
 * @since  1.0.5
 */
@FunctionalInterface
public interface BiLongToIntFunction {

    int applyAsInt(long first, long second);

}
