package com.speedment.runtime.typemapper.other;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;
import com.speedment.runtime.typemapper.exception.SpeedmentTypeMapperException;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;

import static java.lang.String.format;

/**
 * Maps between a large integer stored in binary form into a java
 * {@link BigDecimal}.
 *
 * @author Emil Forslund
 * @since  3.0.23
 */
public final class BinaryToBigIntegerMapper
implements TypeMapper<Object, BigInteger> {

    @Override
    public String getLabel() {
        return "BINARY to BigInteger Mapper";
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
    public BigInteger toJavaType(Column column, Class<?> entityType, Object value) {
        if (value == null) return null;

        try {
            final byte[] bytes = (byte[]) value;
            return new BigInteger(bytes);
        } catch (final ClassCastException ex) {
            throw new SpeedmentTypeMapperException(format(
                "Expected input type to be a byte[], but was %s.",
                value.getClass().getName()), ex);
        }
    }

    @Override
    public Object toDatabaseType(BigInteger value) {
        if (value == null) return null;
        else return value.toByteArray();
    }

    @Override
    public Ordering getOrdering() {
        return Ordering.UNSPECIFIED;
    }
}
