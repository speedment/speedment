package com.speedment.runtime.compute.trait;

import com.speedment.runtime.compute.ToDouble;
import com.speedment.runtime.compute.ToInt;
import com.speedment.runtime.compute.ToLong;

/**
 * Trait that describes an expression that has several
 * {@link #divide(int)}-methods for generating new expressions for the division
 * of this value with a certain divisor.
 *
 * @param <T>  the input type
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface HasDivide<T> {

    ToDouble<T> divide(int divisor);

    ToDouble<T> divide(ToInt<T> divisor);

    ToDouble<T> divide(long divisor);

    ToDouble<T> divide(ToLong<T> divisor);

    ToDouble<T> divide(double divisor);

    ToDouble<T> divide(ToDouble<T> divisor);

}