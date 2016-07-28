package com.speedment.runtime.field.trait;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.config.mapper.TypeMapper;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.method.ByteGetter;
import com.speedment.runtime.field.method.ByteSetter;
import com.speedment.runtime.field.method.SetToByte;
import com.speedment.runtime.internal.field.setter.SetToByteImpl;

/**
 * A representation of an Entity field that is a primitive {@code byte} type.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@Api(version = "3.0")
public interface HasByteValue<ENTITY, D>  extends Field<ENTITY> {
    
    @Override
    ByteSetter<ENTITY> setter();
    
    @Override
    ByteGetter<ENTITY> getter();
    
    @Override
    TypeMapper<D, Byte> typeMapper();
    
    /**
     * Gets the value from the Entity field.
     * 
     * @param entity the entity
     * @return       the value of the field
     */
    default byte getAsByte(ENTITY entity) {
        return getter().getAsByte(entity);
    }
    
    /**
     * Sets the value in the given Entity.
     * 
     * @param entity the entity
     * @param value  to set
     * @return       the entity itself
     */
    default ENTITY set(ENTITY entity, byte value) {
        return setter().setAsByte(entity, value);
    }
    
    /**
     * Creates and returns a setter handler with a given value.
     * 
     * @param value to set
     * @return      a set-operation with a given value
     */
    default SetToByte<ENTITY, D> setTo(byte value) {
        return new SetToByteImpl<>(this, value);
    }
}