package com.speedment.runtime.field;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.field.trait.HasIntValue;

/**
 * A field that represents a primitive {@code int} value.
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
public interface IntField<ENTITY, D> extends Field<ENTITY, Integer>, HasIntValue<ENTITY, D>, HasComparableOperators<ENTITY, Integer> {
    
    
}