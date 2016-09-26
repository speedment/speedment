package com.speedment.runtime.field.trait;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.config.mapper.TypeMapper;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.method.BooleanGetter;
import com.speedment.runtime.field.method.BooleanSetter;
import com.speedment.runtime.field.method.SetToBoolean;
import com.speedment.runtime.internal.field.setter.SetToBooleanImpl;

/**
 * A representation of an Entity field that is a primitive {@code boolean} type.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@Api(version = "3.0")
public interface HasBooleanValue<ENTITY, D> extends Field<ENTITY, Boolean> {
    
    @Override
    BooleanSetter<ENTITY> setter();
    
    @Override
    BooleanGetter<ENTITY> getter();
    
    @Override
    TypeMapper<D, Boolean> typeMapper();
    
    /**
     * Gets the value from the Entity field.
     * 
     * @param entity the entity
     * @return       the value of the field
     */
    default boolean getAsBoolean(ENTITY entity) {
        return getter().applyAsBoolean(entity);
    }
    
    /**
     * Sets the value in the given Entity.
     * 
     * @param entity the entity
     * @param value  to set
     * @return       the entity itself
     */
    default ENTITY set(ENTITY entity, boolean value) {
        return setter().setAsBoolean(entity, value);
    }
    
    /**
     * Creates and returns a setter handler with a given value.
     * 
     * @param value to set
     * @return      a set-operation with a given value
     */
    default SetToBoolean<ENTITY, D> setTo(boolean value) {
        return new SetToBooleanImpl<>(this, value);
    }
}