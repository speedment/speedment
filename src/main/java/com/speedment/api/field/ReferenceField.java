package com.speedment.api.field;

import com.speedment.api.annotation.Api;
import com.speedment.api.field.methods.Getter;
import com.speedment.api.field.methods.Setter;
import com.speedment.api.field.builders.UnaryPredicateBuilder;
import com.speedment.api.field.builders.SetterBuilder;

/**
 *
 * @author pemi
 * @param <ENTITY>
 * @param <V>
 */
@Api(version = "2.1")
public interface ReferenceField<ENTITY, V> extends Field<ENTITY> {
    
    /**
     * Returns a reference to the setter for this field.
     * 
     * @return  the setter
     */
    Setter<ENTITY, V> setter();
    
    /**
     * Returns a reference to the getter of this field.
     * 
     * @return  the getter
     */
    Getter<ENTITY, V> getter();
    
    /**
     * Returns a {@link java.util.function.Function} that will set this field
     * to a specific value for a entity and return that entity.
     * 
     * @param newValue    the value to set
     * @return            the function builder
     */
    SetterBuilder<ENTITY, V> set(V newValue);

    /**
     * Returns the value of this field for the specified entity.
     * 
     * @param entity  the entity
     * @return        the value of the field
     */
    V get(ENTITY entity);
    
    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is {@code null}.
     *
     * @return           a Predicate that will evaluate to {@code true}, if and 
     *                   only if this Field is {@code null}
     */
    UnaryPredicateBuilder<ENTITY> isNull();
    
    /**
     * Returns if this Field is {@code null} in the given entity.
     *
     * @param entity  to use
     * @return        if this Field is {@code null}
     */
    default boolean isNullIn(ENTITY entity) {
        return isNull().test(entity);
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>not</em> {@code null}.
     *
     * @return           a Predicate that will evaluate to {@code true}, if and 
     *                   only if this Field is <em>not</em> {@code null}
     */
    UnaryPredicateBuilder<ENTITY> isNotNull();
    
    /**
     * Returns if this Field is <b>not</b> {@code null} in the given entity.
     *
     * @param entity  to use
     * @return        if this Field is not {@code null}
     */
    default boolean isNotNullIn(ENTITY entity) {
        return isNotNull().test(entity);
    }
}