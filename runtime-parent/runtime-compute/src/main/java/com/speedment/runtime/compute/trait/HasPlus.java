package com.speedment.runtime.compute.trait;

import com.speedment.runtime.compute.ToByte;
import com.speedment.runtime.compute.ToDouble;
import com.speedment.runtime.compute.ToInt;
import com.speedment.runtime.compute.ToLong;

/**
 * Trait that describes an expression that has several
 * {@link #plus(int)}-methods for generating new expressions for the sum of this
 * value and something else.
 *
 * @param <T>         the input type
 * @param <PLUS_BYTE> the expression type one would get if adding a {@code byte}
 *                    value to the result of the current expression
 * @param <PLUS_INT>  the expression type one would get if adding an {@code int}
 *                    value to the result of the current expression
 * @param <PLUS_LONG> the expression type one would get if adding a {@code long}
 *                    value to the result of the current expression
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface HasPlus<T, PLUS_BYTE, PLUS_INT, PLUS_LONG> {

    PLUS_BYTE plus(byte other);

    PLUS_BYTE plus(ToByte<T> other);

    PLUS_INT plus(int other);

    PLUS_INT plus(ToInt<T> other);

    PLUS_LONG plus(long other);

    PLUS_LONG plus(ToLong<T> other);

    ToDouble<T> plus(double other);

    ToDouble<T> plus(ToDouble<T> other);

}