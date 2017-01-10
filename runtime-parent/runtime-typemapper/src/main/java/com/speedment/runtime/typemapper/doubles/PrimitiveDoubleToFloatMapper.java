package com.speedment.runtime.typemapper.doubles;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;
import java.lang.reflect.Type;

/**
 * Maps between {@code Double} and {@code float} values by simply casting to
 * lower precision.
 * 
 * @author Emil Forslund
 * @since  3.0.2
 */
public final class PrimitiveDoubleToFloatMapper implements TypeMapper<Double, Float> {

    @Override
    public String getLabel() {
        return "Double to float (primitive)";
    }

    @Override
    public Type getJavaType(Column column) {
        return float.class;
    }

    @Override
    public Float toJavaType(Column column, Class<?> entityType, Double value) {
        return value == null ? null : (float) (double) value;
    }

    @Override
    public Double toDatabaseType(Float value) {
        return value == null ? null : (double) (float) value;
    }

}