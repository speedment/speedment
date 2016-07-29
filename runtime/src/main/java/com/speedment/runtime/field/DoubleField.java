package com.speedment.runtime.field;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.field.trait.HasDoubleValue;

/**
 * A field that represents a primitive {@code double} value.
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
public interface DoubleField<ENTITY, D>  extends Field<ENTITY>, HasDoubleValue<ENTITY, D>, HasComparableOperators<ENTITY, Double> {
    
    
}