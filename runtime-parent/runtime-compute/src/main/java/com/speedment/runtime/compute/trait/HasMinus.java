package com.speedment.runtime.compute.trait;

import com.speedment.runtime.compute.ToByte;
import com.speedment.runtime.compute.ToDouble;
import com.speedment.runtime.compute.ToInt;
import com.speedment.runtime.compute.ToLong;

/**
 * Trait that describes an expression that has several
 * {@link #minus(int)}-methods for generating new expressions for the difference
 * between this value and something else.
 *
 * @param <T>          the input type
 * @param <MINUS_BYTE> the expression type one would get if subtracting a
 *                     {@code byte} value from the result of the current
 *                     expression
 * @param <MINUS_INT>  the expression type one would get if subtracting an
 *                     {@code int} value from the result of the current
 *                     expression
 * @param <MINUS_LONG> the expression type one would get if subtracting a
 *                     {@code long} value from the result of the current
 *                     expression
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface HasMinus<T, MINUS_BYTE, MINUS_INT, MINUS_LONG> {

    MINUS_BYTE minus(byte other);

    MINUS_BYTE minus(ToByte<T> other);

    MINUS_INT minus(int other);

    MINUS_INT minus(ToInt<T> other);

    MINUS_LONG minus(long other);

    MINUS_LONG minus(ToLong<T> other);

    ToDouble<T> minus(double other);

    ToDouble<T> minus(ToDouble<T> other);

}