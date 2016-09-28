package com.speedment.runtime.field.trait;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.config.mapper.TypeMapper;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.method.IntGetter;
import com.speedment.runtime.field.method.IntSetter;
import com.speedment.runtime.field.method.SetToInt;
import com.speedment.runtime.internal.field.setter.SetToIntImpl;
import javax.annotation.Generated;

/**
 * A representation of an Entity field that is a primitive {@code int} type.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@Api(version = "3.0")
@Generated(value = "Speedment")
public interface HasIntValue<ENTITY, D> extends Field<ENTITY> {
    
    @Override
    IntSetter<ENTITY> setter();
    
    @Override
    IntGetter<ENTITY> getter();
    
    @Override
    TypeMapper<D, Integer> typeMapper();
    
    /**
     * Gets the value from the Entity field.
     * 
     * @param entity the entity
     * @return       the value of the field
     */
    default int getAsInt(ENTITY entity) {
        return getter().applyAsInt(entity);
    }
    
    /**
     * Sets the value in the given Entity.
     * 
     * @param entity the entity
     * @param value  to set
     * @return       the entity itself
     */
    default ENTITY set(ENTITY entity, int value) {
        return setter().setAsInt(entity, value);
    }
    
    /**
     * Creates and returns a setter handler with a given value.
     * 
     * @param value to set
     * @return      a set-operation with a given value
     */
    default SetToInt<ENTITY, D> setTo(int value) {
        return new SetToIntImpl<>(this, value);
    }
}