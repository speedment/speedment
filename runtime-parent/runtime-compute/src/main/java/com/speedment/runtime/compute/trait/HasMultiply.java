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

    /**
     * Creates and returns an expression that returns the product of
     * the result from the current expression and the other factor. For an example, if the result of
     * the current expression was {@code 9} and the factor was set to {@code 3},
     * then the result of the returned expression would be {@code 27}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param other  the other factor used for the multiplication
     * @return       the new expression
     */
    MULT_BYTE multiply(byte other);

    /**
     * Creates and returns an expression that returns the product of
     * the result from the current expression and the other factor. For an example, if the result of
     * the current expression was {@code 9} and the factor was set to {@code 3},
     * then the result of the returned expression would be {@code 27}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param other  the other factor used for the multiplication
     * @return       the new expression
     */
    MULT_BYTE multiply(ToByte<T> other);

    /**
     * Creates and returns an expression that returns the product of
     * the result from the current expression and the other factor. For an example, if the result of
     * the current expression was {@code 9} and the factor was set to {@code 3},
     * then the result of the returned expression would be {@code 27}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param other  the other factor used for the multiplication
     * @return       the new expression
     */
    MULT_INT multiply(int other);

    /**
     * Creates and returns an expression that returns the product of
     * the result from the current expression and the other factor. For an example, if the result of
     * the current expression was {@code 9} and the factor was set to {@code 3},
     * then the result of the returned expression would be {@code 27}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param other  the other factor used for the multiplication
     * @return       the new expression
     */
    MULT_INT multiply(ToInt<T> other);

    /**
     * Creates and returns an expression that returns the product of
     * the result from the current expression and the other factor. For an example, if the result of
     * the current expression was {@code 9} and the factor was set to {@code 3},
     * then the result of the returned expression would be {@code 27}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param other  the other factor used for the multiplication
     * @return       the new expression
     */
    MULT_LONG multiply(long other);

    /**
     * Creates and returns an expression that returns the product of
     * the result from the current expression and the other factor. For an example, if the result of
     * the current expression was {@code 9} and the factor was set to {@code 3},
     * then the result of the returned expression would be {@code 27}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param other  the other factor used for the multiplication
     * @return       the new expression
     */
    MULT_LONG multiply(ToLong<T> other);

    /**
     * Creates and returns an expression that returns the product of
     * the result from the current expression and the other factor. For an example, if the result of
     * the current expression was {@code 9} and the factor was set to {@code 3},
     * then the result of the returned expression would be {@code 27}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param other  the other factor used for the multiplication
     * @return       the new expression
     */
    ToDouble<T> multiply(double other);

    /**
     * Creates and returns an expression that returns the product of
     * the result from the current expression and the other factor. For an example, if the result of
     * the current expression was {@code 9} and the factor was set to {@code 3},
     * then the result of the returned expression would be {@code 27}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param other  the other factor used for the multiplication
     * @return       the new expression
     */
    ToDouble<T> multiply(ToDouble<T> other);

}