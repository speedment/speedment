package com.speedment.runtime.field;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.field.trait.HasIntValue;
import javax.annotation.Generated;

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
@Generated(value = "Speedment")
public interface IntField<ENTITY, D> extends Field<ENTITY>, HasIntValue<ENTITY, D>, HasComparableOperators<ENTITY, Integer> {
    
    
}