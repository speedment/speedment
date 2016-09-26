package com.speedment.runtime.field.trait;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.config.mapper.TypeMapper;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.method.DoubleGetter;
import com.speedment.runtime.field.method.DoubleSetter;
import com.speedment.runtime.field.method.SetToDouble;
import com.speedment.runtime.internal.field.setter.SetToDoubleImpl;

/**
 * A representation of an Entity field that is a primitive {@code double} type.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@Api(version = "3.0")
public interface HasDoubleValue<ENTITY, D> extends Field<ENTITY, Double> {
    
    @Override
    DoubleSetter<ENTITY> setter();
    
    @Override
    DoubleGetter<ENTITY> getter();
    
    @Override
    TypeMapper<D, Double> typeMapper();
    
    /**
     * Gets the value from the Entity field.
     * 
     * @param entity the entity
     * @return       the value of the field
     */
    default double getAsDouble(ENTITY entity) {
        return getter().applyAsDouble(entity);
    }
    
    /**
     * Sets the value in the given Entity.
     * 
     * @param entity the entity
     * @param value  to set
     * @return       the entity itself
     */
    default ENTITY set(ENTITY entity, double value) {
        return setter().setAsDouble(entity, value);
    }
    
    /**
     * Creates and returns a setter handler with a given value.
     * 
     * @param value to set
     * @return      a set-operation with a given value
     */
    default SetToDouble<ENTITY, D> setTo(double value) {
        return new SetToDoubleImpl<>(this, value);
    }
}