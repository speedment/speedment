package com.speedment.runtime.field.trait;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.config.identifier.FieldIdentifier;
import com.speedment.runtime.config.mapper.TypeMapper;
import com.speedment.runtime.field.method.LongSetter;
import com.speedment.runtime.field.method.LongGetter;
import com.speedment.runtime.field.method.SetToLong;
import com.speedment.runtime.internal.field.SetToLongImpl;

/**
 * A representation of an Entity field that is a primitive {@code long} type.
 * 
 * @param <ENTITY>  the entity type
 * @param <D>       the database type
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
@Api(version = "3.0")
public interface LongFieldTrait<ENTITY, D> extends FieldTrait<ENTITY> {
    
    @Override
    FieldIdentifier<ENTITY> getIdentifier();

    @Override
    LongSetter<ENTITY> setter();

    @Override
    LongGetter<ENTITY> getter();

    /**
     * Returns the type mapper of this field.
     *
     * @return type mapper
     */
    TypeMapper<D, Long> typeMapper();

    /**
     * Gets the value form the Entity field.
     *
     * @param entity  the entity
     * @return        the value of the field
     */
    default long getAsLong(ENTITY entity) {
        return getter().getAsLong(entity);
    }

    /**
     * Sets the value in the given Entity.
     *
     * @param entity  the entity
     * @param value   to set
     * @return        the entity itself
     */
    default ENTITY set(ENTITY entity, long value) {
        return setter().setAsLong(entity, value);
    }

    /**
     * Creates and returns a setter handler with a given value.
     *
     * @param value  to set
     * @return       a set-operation with a given value
     */
    default SetToLong<ENTITY> setTo(long value) {
        return new SetToLongImpl<>(this, value);
    }
}
