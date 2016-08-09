package com.speedment.runtime.field.trait;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.config.mapper.TypeMapper;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.method.SetToShort;
import com.speedment.runtime.field.method.ShortGetter;
import com.speedment.runtime.field.method.ShortSetter;
import com.speedment.runtime.internal.field.setter.SetToShortImpl;

/**
 * A representation of an Entity field that is a primitive {@code short} type.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@Api(version = "3.0")
public interface HasShortValue<ENTITY, D> extends Field<ENTITY> {
    
    @Override
    ShortSetter<ENTITY> setter();
    
    @Override
    ShortGetter<ENTITY> getter();
    
    @Override
    TypeMapper<D, Short> typeMapper();
    
    /**
     * Gets the value from the Entity field.
     * 
     * @param entity the entity
     * @return       the value of the field
     */
    default short getAsShort(ENTITY entity) {
        return getter().getAsShort(entity);
    }
    
    /**
     * Sets the value in the given Entity.
     * 
     * @param entity the entity
     * @param value  to set
     * @return       the entity itself
     */
    default ENTITY set(ENTITY entity, short value) {
        return setter().setAsShort(entity, value);
    }
    
    /**
     * Creates and returns a setter handler with a given value.
     * 
     * @param value to set
     * @return      a set-operation with a given value
     */
    default SetToShort<ENTITY, D> setTo(short value) {
        return new SetToShortImpl<>(this, value);
    }
}