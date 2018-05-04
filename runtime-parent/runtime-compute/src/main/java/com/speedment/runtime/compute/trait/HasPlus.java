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

    /**
     * Creates and returns an expression that returns the sum of
     * the result from the current expression and the other term. For an example, if the result of
     * the current expression was {@code 9} and the other term was set to {@code 3},
     * then the result of the returned expression would be {@code 12}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param other  the other term used for the addition
     * @return       the new expression
     */
    PLUS_BYTE plus(byte other);

    /**
     * Creates and returns an expression that returns the sum of
     * the result from the current expression and the other term. For an example, if the result of
     * the current expression was {@code 9} and the other term was set to {@code 3},
     * then the result of the returned expression would be {@code 12}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param other  the other term used for the addition
     * @return       the new expression
     */
    PLUS_BYTE plus(ToByte<T> other);

    /**
     * Creates and returns an expression that returns the sum of
     * the result from the current expression and the other term. For an example, if the result of
     * the current expression was {@code 9} and the other term was set to {@code 3},
     * then the result of the returned expression would be {@code 12}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param other  the other term used for the addition
     * @return       the new expression
     */
    PLUS_INT plus(int other);

    /**
     * Creates and returns an expression that returns the sum of
     * the result from the current expression and the other term. For an example, if the result of
     * the current expression was {@code 9} and the other term was set to {@code 3},
     * then the result of the returned expression would be {@code 12}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param other  the other term used for the addition
     * @return       the new expression
     */
    PLUS_INT plus(ToInt<T> other);

    /**
     * Creates and returns an expression that returns the sum of
     * the result from the current expression and the other term. For an example, if the result of
     * the current expression was {@code 9} and the other term was set to {@code 3},
     * then the result of the returned expression would be {@code 12}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param other  the other term used for the addition
     * @return       the new expression
     */
    PLUS_LONG plus(long other);

    /**
     * Creates and returns an expression that returns the sum of
     * the result from the current expression and the other term. For an example, if the result of
     * the current expression was {@code 9} and the other term was set to {@code 3},
     * then the result of the returned expression would be {@code 12}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param other  the other term used for the addition
     * @return       the new expression
     */
    PLUS_LONG plus(ToLong<T> other);

    /**
     * Creates and returns an expression that returns the sum of
     * the result from the current expression and the other term. For an example, if the result of
     * the current expression was {@code 9} and the other term was set to {@code 3},
     * then the result of the returned expression would be {@code 12}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param other  the other term used for the addition
     * @return       the new expression
     */
    ToDouble<T> plus(double other);

    /**
     * Creates and returns an expression that returns the sum of
     * the result from the current expression and the other term. For an example, if the result of
     * the current expression was {@code 9} and the other term was set to {@code 3},
     * then the result of the returned expression would be {@code 12}.
     * <p>
     * If this expression is nullable and the result was {@code null}, then the
     * result of the returned expression will also be {@code null}.
     *
     * @param other  the other term used for the addition
     * @return       the new expression
     */
    ToDouble<T> plus(ToDouble<T> other);

}