package com.speedment.runtime.typemapper.longs;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;
import java.lang.reflect.Type;

/**
 * Maps between {@code Long} and {@code int} values by simply casting to
 * lower precision.
 * 
 * @author Emil Forslund
 * @since  3.0.2
 */
public final class PrimitiveLongToIntegerMapper implements TypeMapper<Long, Integer> {

    @Override
    public String getLabel() {
        return "Long to int (primitive)";
    }

    @Override
    public Type getJavaType(Column column) {
        return int.class;
    }

    @Override
    public Integer toJavaType(Column column, Class<?> entityType, Long value) {
        return value == null ? null : ((int) (long) value);
    }

    @Override
    public Long toDatabaseType(Integer value) {
        return value == null ? null : ((long) (int) value);
    }

}