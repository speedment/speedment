package com.speedment.runtime.field.expression;

import com.speedment.common.function.ToByteFunction;
import com.speedment.runtime.compute.ToByte;

/**
 * Specific {@link FieldMapper} implementation that also implements
 * {@link ToByte}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface FieldToByte<ENTITY, T>
extends FieldMapper<ENTITY, T, ToByteFunction<T>>, ToByte<ENTITY> {

    @Override
    default MapperType getMapperType() {
        return MapperType.TO_BYTE;
    }

}