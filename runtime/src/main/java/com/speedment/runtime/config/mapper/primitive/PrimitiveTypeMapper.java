package com.speedment.runtime.config.mapper.primitive;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.mapper.TypeMapper;
import com.speedment.runtime.config.typetoken.TypeToken;
import com.speedment.runtime.util.TypeTokenFactory;

/**
 * 
 * @param <T>  the database type
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public final class PrimitiveTypeMapper<T> implements TypeMapper<T, T> {

    @Override
    public String getLabel() {
        return "(To Primitive)";
    }

    @Override
    public <ENTITY> TypeToken getJavaType(Column column) {
        final String type = column.getDatabaseType();
        switch (type) {
            case "java.lang.Byte"      : return TypeTokenFactory.create(byte.class);
            case "java.lang.Short"     : return TypeTokenFactory.create(short.class);
            case "java.lang.Integer"   : return TypeTokenFactory.create(int.class);
            case "java.lang.Long"      : return TypeTokenFactory.create(long.class);
            case "java.lang.Float"     : return TypeTokenFactory.create(float.class);
            case "java.lang.Double"    : return TypeTokenFactory.create(double.class);
            case "java.lang.Boolean"   : return TypeTokenFactory.create(boolean.class);
            case "java.lang.Character" : return TypeTokenFactory.create(char.class);
            default : throw new UnsupportedOperationException(
                "Type " + type + " is not a wrapper for a primitive type."
            );
        }
    }

    @Override
    public <ENTITY> T toJavaType(Column column, Class<ENTITY> entityType, T value) {
        return value;
    }

    @Override
    public T toDatabaseType(T value) {
        return value;
    }
}
