package com.speedment.runtime.field;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.field.trait.HasFloatValue;

/**
 * A field that represents a primitive {@code float} value.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 * 
 * @see ReferenceField
 */
@Api(version = "3.0")
public interface FloatField<ENTITY, D> extends Field<ENTITY>, HasFloatValue<ENTITY, D>, HasComparableOperators<ENTITY, Float> {
    
    
}