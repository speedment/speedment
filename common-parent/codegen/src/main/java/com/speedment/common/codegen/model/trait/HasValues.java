package com.speedment.common.codegen.model.trait;

import com.speedment.common.codegen.model.Value;

import java.util.List;

/**
 * Trait representing a type that has multiple {@link Value values}, like a
 * method or constructor invocation.
 *
 * @author Emil Forslund
 * @since  2.4.6
 */
public interface HasValues<T extends HasValues<T>> {

    /**
     * Adds the specified {@link Value} to this model.
     *
     * @param generic  the new child
     * @return         a reference to this
     */
    @SuppressWarnings("unchecked")
    default <E> T add(final Value<E> generic) {
        getValues().add(generic);
        return (T) this;
    }

    /**
     * Returns a list of all the values in this model.
     * <p>
     * The list returned must be mutable for changes!
     *
     * @return  the values
     */
    List<Value<?>> getValues();

}
