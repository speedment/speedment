package com.speedment.runtime.typemapper.string;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;

import java.lang.reflect.Type;
import java.math.BigDecimal;

/**
 * Maps between a large real number stored as a {@code String} into a java
 * {@link BigDecimal}.
 *
 * @author Emil Forslund
 * @since  3.0.23
 */
public final class StringToBigDecimalMapper
implements TypeMapper<String, BigDecimal> {

    @Override
    public String getLabel() {
        return "String to BigDecimal Mapper";
    }

    @Override
    public Type getJavaType(Column column) {
        return BigDecimal.class;
    }

    @Override
    public Category getJavaTypeCategory(Column column) {
        return Category.COMPARABLE;
    }

    @Override
    public BigDecimal toJavaType(Column column, Class<?> entityType, String value) {
        return value == null ? null : new BigDecimal(value);
    }

    @Override
    public String toDatabaseType(BigDecimal value) {
        return value == null ? null : value.toString();
    }

    @Override
    public Ordering getOrdering() {
        // Alphabetic order and numeric order might differ
        return Ordering.UNSPECIFIED;
    }
}