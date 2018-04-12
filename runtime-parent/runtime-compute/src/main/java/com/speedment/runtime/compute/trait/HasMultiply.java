package com.speedment.runtime.compute.trait;

import com.speedment.runtime.compute.ToByte;
import com.speedment.runtime.compute.ToDouble;
import com.speedment.runtime.compute.ToInt;
import com.speedment.runtime.compute.ToLong;

/**
 * Trait that describes an expression that has several
 * {@link #multiply(int)}-methods for generating new expressions for the product
 * of this value and something else.
 *
 * @param <T>          the input type
 * @param <MULT_BYTE>  the expression type one would get if multiplying a
 *                     {@code byte} value with the result of the current
 *                     expression
 * @param <MULT_INT>   the expression type one would get if multiplying an
 *                     {@code int} value with the result of the current
 *                     expression
 * @param <MULT_LONG>  the expression type one would get if multiplying a
 *                     {@code long} value with the result of the current
 *                     expression
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface HasMultiply<T, MULT_BYTE, MULT_INT, MULT_LONG> {

    MULT_BYTE multiply(byte other);

    MULT_BYTE multiply(ToByte<T> other);

    MULT_INT multiply(int other);

    MULT_INT multiply(ToInt<T> other);

    MULT_LONG multiply(long other);

    MULT_LONG multiply(ToLong<T> other);

    ToDouble<T> multiply(double other);

    ToDouble<T> multiply(ToDouble<T> other);

}