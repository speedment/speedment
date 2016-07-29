package com.speedment.runtime.field.trait;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.config.mapper.TypeMapper;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.method.LongGetter;
import com.speedment.runtime.field.method.LongSetter;
import com.speedment.runtime.field.method.SetToLong;
import com.speedment.runtime.internal.field.setter.SetToLongImpl;

/**
 * A representation of an Entity field that is a primitive {@code long} type.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@Api(version = "3.0")
public interface HasLongValue<ENTITY, D>  extends Field<ENTITY> {
    
    @Override
    LongSetter<ENTITY> setter();
    
    @Override
    LongGetter<ENTITY> getter();
    
    @Override
    TypeMapper<D, Long> typeMapper();
    
    /**
     * Gets the value from the Entity field.
     * 
     * @param entity the entity
     * @return       the value of the field
     */
    default long getAsLong(ENTITY entity) {
        return getter().getAsLong(entity);
    }
    
    /**
     * Sets the value in the given Entity.
     * 
     * @param entity the entity
     * @param value  to set
     * @return       the entity itself
     */
    default ENTITY set(ENTITY entity, long value) {
        return setter().setAsLong(entity, value);
    }
    
    /**
     * Creates and returns a setter handler with a given value.
     * 
     * @param value to set
     * @return      a set-operation with a given value
     */
    default SetToLong<ENTITY, D> setTo(long value) {
        return new SetToLongImpl<>(this, value);
    }
}