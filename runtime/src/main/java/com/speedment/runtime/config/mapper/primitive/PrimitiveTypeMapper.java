package com.speedment.runtime.config.mapper.primitive;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.mapper.TypeMapper;
import java.lang.reflect.Type;

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
    public Type getJavaType(Column column) {
        final String type = column.getDatabaseType();
        switch (type) {
            case "java.lang.Byte"      : return byte.class;
            case "java.lang.Short"     : return short.class;
            case "java.lang.Integer"   : return int.class;
            case "java.lang.Long"      : return long.class;
            case "java.lang.Float"     : return float.class;
            case "java.lang.Double"    : return double.class;
            case "java.lang.Boolean"   : return boolean.class;
            case "java.lang.Character" : return char.class;
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
