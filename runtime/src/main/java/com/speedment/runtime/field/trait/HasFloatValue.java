package com.speedment.runtime.field.trait;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.config.mapper.TypeMapper;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.method.FloatGetter;
import com.speedment.runtime.field.method.FloatSetter;
import com.speedment.runtime.field.method.SetToFloat;
import com.speedment.runtime.internal.field.setter.SetToFloatImpl;

/**
 * A representation of an Entity field that is a primitive {@code float} type.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@Api(version = "3.0")
public interface HasFloatValue<ENTITY, D> extends Field<ENTITY, Float> {
    
    @Override
    FloatSetter<ENTITY> setter();
    
    @Override
    FloatGetter<ENTITY> getter();
    
    @Override
    TypeMapper<D, Float> typeMapper();
    
    /**
     * Gets the value from the Entity field.
     * 
     * @param entity the entity
     * @return       the value of the field
     */
    default float getAsFloat(ENTITY entity) {
        return getter().applyAsFloat(entity);
    }
    
    /**
     * Sets the value in the given Entity.
     * 
     * @param entity the entity
     * @param value  to set
     * @return       the entity itself
     */
    default ENTITY set(ENTITY entity, float value) {
        return setter().setAsFloat(entity, value);
    }
    
    /**
     * Creates and returns a setter handler with a given value.
     * 
     * @param value to set
     * @return      a set-operation with a given value
     */
    default SetToFloat<ENTITY, D> setTo(float value) {
        return new SetToFloatImpl<>(this, value);
    }
}