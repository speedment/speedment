package com.speedment.runtime.typemapper.time;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;

import java.lang.reflect.Type;
import java.time.LocalDate;

/**
 * Implementation of {@link TypeMapper} that takes a date specified as a number
 * of days since the unix epoch start.
 *
 * @author Emil Forslund
 * @since  3.0.18
 */
public final class ShortEpochDaysToLocalDateMapper
implements TypeMapper<Short, LocalDate> {

    @Override
    public String getLabel() {
        return "Short (Epoch Day) to LocalDate";
    }

    @Override
    public Type getJavaType(Column column) {
        return LocalDate.class;
    }

    @Override
    public LocalDate toJavaType(Column column, Class<?> entityType, Short value) {
        return value == null ? null : LocalDate.ofEpochDay(value);
    }

    @Override
    public Short toDatabaseType(LocalDate value) {
        return value == null ? null : (short) value.toEpochDay();
    }

    @Override
    public Ordering getOrdering() {
        return Ordering.RETAIN;
    }
}
