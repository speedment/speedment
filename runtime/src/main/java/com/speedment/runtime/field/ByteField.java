package com.speedment.runtime.field;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.field.trait.HasByteValue;
import com.speedment.runtime.field.trait.HasComparableOperators;

/**
 * A field that represents a primitive {@code byte} value.
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
public interface ByteField<ENTITY, D>  extends Field<ENTITY>, HasByteValue<ENTITY, D>, HasComparableOperators<ENTITY, Byte> {
    
    
}