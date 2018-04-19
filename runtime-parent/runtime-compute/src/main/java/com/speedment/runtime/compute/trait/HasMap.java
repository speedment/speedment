package com.speedment.runtime.compute.trait;

/**
 * Trait for expressions that can be mapped to a new expression of the same type
 * by supplying a mapping function.
 *
 * @param <T>       the input entity type
 * @param <MAPPER>  the functional interface used for mapping values
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface HasMap<T, MAPPER, RESULT extends HasMap<T, MAPPER, RESULT>> {

    /**
     * Returns an expression for the value of this expression mapped using the
     * specified mapping function. The input type remains the same.
     *
     * @param mapper  the mapping function
     * @return        the mapped expression
     */
    RESULT map(MAPPER mapper);

}
