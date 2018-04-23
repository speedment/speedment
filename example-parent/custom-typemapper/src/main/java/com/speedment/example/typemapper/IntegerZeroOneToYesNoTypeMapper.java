package com.speedment.example.typemapper;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;
import java.lang.reflect.Type;

/**
 *
 * @author Per Minborg
 */
public final class IntegerZeroOneToYesNoTypeMapper
    implements TypeMapper<Integer, String> {

    @Override
    public String getLabel() {
        return "Integer (0|1) to String Yes/No";
    }

    @Override
    public Type getJavaType(Column column) {
        return String.class;
    }

    @Override
    public String toJavaType(Column column, Class<?> entityType, Integer value) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case 0:
                return "No";
            case 1:
                return "Yes";
        }
        throw new IllegalArgumentException("Value must be either 0 or 1. Was " + value);
    }

    @Override
    public Integer toDatabaseType(String value) {
        switch (value) {
            case "No":
                return 0;
            case "Yes":
                return 1;
        }
        throw new IllegalArgumentException("Value must be either Yes or No. Was " + value);
    }

    @Override
    public Ordering getOrdering() {
        return Ordering.RETAIN;
    }

}
