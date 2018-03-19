package com.speedment.runtime.typemapper.string;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;

import java.lang.reflect.Type;
import java.math.BigInteger;

/**
 * Maps between a large integer stored as a {@code String} into a java
 * {@link java.math.BigInteger}.
 *
 * @author Emil Forslund
 * @since  3.0.23
 */
public final class StringToBigIntegerMapper
implements TypeMapper<String, BigInteger> {

    @Override
    public String getLabel() {
        return "String to BigInteger Mapper";
    }

    @Override
    public Type getJavaType(Column column) {
        return BigInteger.class;
    }

    @Override
    public Category getJavaTypeCategory(Column column) {
        return Category.COMPARABLE;
    }

    @Override
    public BigInteger toJavaType(Column column, Class<?> entityType, String value) {
        return value == null ? null : new BigInteger(value);
    }

    @Override
    public String toDatabaseType(BigInteger value) {
        return value == null ? null : value.toString();
    }

    @Override
    public Ordering getOrdering() {
        // Alphabetic order and numeric order might differ
        return Ordering.UNSPECIFIED;
    }
}