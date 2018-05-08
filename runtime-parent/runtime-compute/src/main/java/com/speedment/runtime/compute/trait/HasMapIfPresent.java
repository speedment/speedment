package com.speedment.runtime.compute.trait;

/**
 * Trait for expressions that can be mapped to a new expression of the same type
 * by supplying a mapping function. If the value is present in the original
 * expression, then it will be present in the new one as well. If the result of
 * the original expression is {@code null}, then the new expression will
 * evaluate to {@code null} also.
 *
 * @param <T>       the input entity type
 * @param <MAPPER>  the functional interface used for mapping values
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface HasMapIfPresent<T, MAPPER, RESULT extends HasMapIfPresent<T, MAPPER, RESULT>> {

    /**
     * Returns an expression for the value of this expression mapped using the
     * specified mapping function. The input type remains the same.
     *
     * @param mapper  the mapping function
     * @return        the mapped expression
     */
    RESULT mapIfPresent(MAPPER mapper);

}
