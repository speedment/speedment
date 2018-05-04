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

    /**
     * Creates and returns an expression that returns the difference of
     * the result from the current expression and the other term. For an example, if the result of
     * the current expression was {@code 9} and the other term was set to {@code 3},
     * then the result of the returned expression would be {@code 6}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param other  the other term used for the subtraction
     * @return       the new expression
     */
    MINUS_BYTE minus(byte other);

    /**
     * Creates and returns an expression that returns the difference of
     * the result from the current expression and the other term. For an example, if the result of
     * the current expression was {@code 9} and the other term was set to {@code 3},
     * then the result of the returned expression would be {@code 6}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param other  the other term used for the subtraction
     * @return       the new expression
     */
    MINUS_BYTE minus(ToByte<T> other);

    /**
     * Creates and returns an expression that returns the difference of
     * the result from the current expression and the other term. For an example, if the result of
     * the current expression was {@code 9} and the other term was set to {@code 3},
     * then the result of the returned expression would be {@code 6}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param other  the other term used for the subtraction
     * @return       the new expression
     */
    MINUS_INT minus(int other);

    /**
     * Creates and returns an expression that returns the difference of
     * the result from the current expression and the other term. For an example, if the result of
     * the current expression was {@code 9} and the other term was set to {@code 3},
     * then the result of the returned expression would be {@code 6}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param other  the other term used for the subtraction
     * @return       the new expression
     */
    MINUS_INT minus(ToInt<T> other);

    /**
     * Creates and returns an expression that returns the difference of
     * the result from the current expression and the other term. For an example, if the result of
     * the current expression was {@code 9} and the other term was set to {@code 3},
     * then the result of the returned expression would be {@code 6}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param other  the other term used for the subtraction
     * @return       the new expression
     */
    MINUS_LONG minus(long other);

    /**
     * Creates and returns an expression that returns the difference of
     * the result from the current expression and the other term. For an example, if the result of
     * the current expression was {@code 9} and the other term was set to {@code 3},
     * then the result of the returned expression would be {@code 6}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param other  the other term used for the subtraction
     * @return       the new expression
     */
    MINUS_LONG minus(ToLong<T> other);

    /**
     * Creates and returns an expression that returns the difference of
     * the result from the current expression and the other term. For an example, if the result of
     * the current expression was {@code 9} and the other term was set to {@code 3},
     * then the result of the returned expression would be {@code 6}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param other  the other term used for the subtraction
     * @return       the new expression
     */
    ToDouble<T> minus(double other);

    /**
     * Creates and returns an expression that returns the difference of
     * the result from the current expression and the other term. For an example, if the result of
     * the current expression was {@code 9} and the other term was set to {@code 3},
     * then the result of the returned expression would be {@code 6}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param other  the other term used for the subtraction
     * @return       the new expression
     */
    ToDouble<T> minus(ToDouble<T> other);

}