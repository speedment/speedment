package com.speedment.runtime.typemapper.largeobject;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;
import com.speedment.runtime.typemapper.exception.SpeedmentTypeMapperException;

import javax.sql.rowset.serial.SerialBlob;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * Maps between a large integer stored in binary form into a java
 * {@link BigDecimal}.
 *
 * @author Emil Forslund
 * @since  3.0.23
 */
public final class BlobToBigIntegerMapper
implements TypeMapper<Blob, BigInteger> {

    @Override
    public String getLabel() {
        return "Blob to BigInteger Mapper";
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
    public BigInteger toJavaType(Column column, Class<?> entityType, Blob value) {
        if (value == null) return null;
        try {
            final byte[] bytes = value.getBytes(1, Math.toIntExact(value.length()));
            return new BigInteger(bytes);
        } catch (final SQLException ex) {
            throw new SpeedmentTypeMapperException(
                "Error mapping Blob to BigInteger.", ex);
        }
    }

    @Override
    public Blob toDatabaseType(BigInteger value) {
        if (value == null) return null;
        try {
            return new SerialBlob(value.toByteArray());
        } catch (final SQLException ex) {
            throw new SpeedmentTypeMapperException(
                "Error mapping BigInteger to Blob.", ex);
        }
    }

    @Override
    public Ordering getOrdering() {
        return Ordering.UNSPECIFIED;
    }
}
