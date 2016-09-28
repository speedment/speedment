package com.speedment.runtime.field;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.field.trait.HasLongValue;

/**
 * A field that represents a primitive {@code long} value.
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
public interface LongField<ENTITY, D> extends Field<ENTITY>, HasLongValue<ENTITY, D>, HasComparableOperators<ENTITY, Long> {
    
    
}