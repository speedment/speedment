package com.speedment.runtime.field.trait;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.config.mapper.TypeMapper;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.method.CharGetter;
import com.speedment.runtime.field.method.CharSetter;
import com.speedment.runtime.field.method.SetToChar;
import com.speedment.runtime.internal.field.setter.SetToCharImpl;
import javax.annotation.Generated;

/**
 * A representation of an Entity field that is a primitive {@code char} type.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@Api(version = "3.0")
@Generated(value = "Speedment")
public interface HasCharValue<ENTITY, D> extends Field<ENTITY> {
    
    @Override
    CharSetter<ENTITY> setter();
    
    @Override
    CharGetter<ENTITY> getter();
    
    @Override
    TypeMapper<D, Character> typeMapper();
    
    /**
     * Gets the value from the Entity field.
     * 
     * @param entity the entity
     * @return       the value of the field
     */
    default char getAsChar(ENTITY entity) {
        return getter().applyAsChar(entity);
    }
    
    /**
     * Sets the value in the given Entity.
     * 
     * @param entity the entity
     * @param value  to set
     * @return       the entity itself
     */
    default ENTITY set(ENTITY entity, char value) {
        return setter().setAsChar(entity, value);
    }
    
    /**
     * Creates and returns a setter handler with a given value.
     * 
     * @param value to set
     * @return      a set-operation with a given value
     */
    default SetToChar<ENTITY, D> setTo(char value) {
        return new SetToCharImpl<>(this, value);
    }
}