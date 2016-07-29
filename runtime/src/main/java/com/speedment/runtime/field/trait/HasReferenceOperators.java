package com.speedment.runtime.field.trait;

import com.speedment.runtime.annotation.Api;
import java.util.function.Predicate;

/**
 *
 * @param <ENTITY>  the entity type
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
@Api(version = "3.0")
public interface HasReferenceOperators<ENTITY, V> {
    
    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is {@code null}.
     *
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is {@code null}
     */
    Predicate<ENTITY> isNull();

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>not</em> {@code null}.
     *
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>not</em> {@code null}
     */
    default Predicate<ENTITY> isNotNull() {
        return isNull().negate();
    }
}
