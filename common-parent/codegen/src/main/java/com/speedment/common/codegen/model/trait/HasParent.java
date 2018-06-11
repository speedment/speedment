package com.speedment.common.codegen.model.trait;

import java.util.Optional;

/**
 * Trait for all models that has a parent. Models that are designed to be reused
 * typically don't have a parent.
 * <p>
 * If the model implementing this trait also implements {@link HasCopy}, then
 * the parent property <em>will not</em> be copied when {@link HasCopy#copy()}
 * is invoked.
 *
 * @param <P>  the model type of the parent
 * @param <T>  the extending type
 *
 * @author Emil Forslund
 * @since  2.5
 */
public interface HasParent<P, T extends HasParent<P, T>> {

    /**
     * Sets the parent of this model. This method should only be invoked by
     * other models when they add a new child, it should not be invoked
     * directly.
     *
     * @param parent  the parent
     * @return        this instance
     */
    T setParent(P parent);

    /**
     * Returns the parent model if one exists, otherwise an empty
     * {@code Optional}.
     *
     * @return  the parent node if one exists
     */
    Optional<P> getParent();

}
