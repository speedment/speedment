package com.speedment.runtime.field;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.field.trait.HasCharValue;
import com.speedment.runtime.field.trait.HasComparableOperators;

/**
 * A field that represents a primitive {@code char} value.
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
public interface CharField<ENTITY, D>  extends Field<ENTITY>, HasCharValue<ENTITY, D>, HasComparableOperators<ENTITY, Character> {
    
    
}