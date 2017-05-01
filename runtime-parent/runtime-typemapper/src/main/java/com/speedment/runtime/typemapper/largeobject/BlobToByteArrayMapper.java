package com.speedment.runtime.typemapper.largeobject;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;
import com.speedment.runtime.typemapper.exception.SpeedmentTypeMapperException;

import javax.sql.rowset.serial.SerialBlob;
import java.lang.reflect.Type;
import java.sql.Blob;
import java.sql.SQLException;


public final class BlobToByteArrayMapper implements TypeMapper<Blob, byte[]> {

    @Override
    public String getLabel() {
        return "Blob to byte Array";
    }

    @Override
    public Type getJavaType(Column column) {
        return byte[].class;
    }

    @Override
    public byte[] toJavaType(Column column, Class<?> entityType, Blob value) {
        if (value == null) {
            return null;
        } else try {
            if (value.length() < Integer.MAX_VALUE) {
                return value.getBytes(1, (int) value.length());
            } else {
                throw new SpeedmentTypeMapperException(
                    "The provided Clob contains too many characters >" + Integer.MAX_VALUE
                );
            }
        } catch (final SQLException sqle) {
            throw new SpeedmentTypeMapperException("Unable to convert Blob to byte[].", sqle);
        }
    }

    @Override
    public Blob toDatabaseType(byte[] value) {
        if (value == null) {
            return null;
        }
        try {
            return new SerialBlob(value);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
