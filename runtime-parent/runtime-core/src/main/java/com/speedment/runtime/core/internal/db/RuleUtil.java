package com.speedment.runtime.core.internal.db;

import com.speedment.runtime.core.db.JavaTypeMap;

import java.math.BigInteger;
import java.util.Optional;

final class RuleUtil {
    /**
     * All default rules that need more than just the jdbc type name to trigger.
     * Overrides the inner defaults of JavaTypeMapImpl
     */
    static final JavaTypeMap.Rule DEFAULT_RULE = (sqlTypeMapping, md) -> {
        final String typeName   = md.getTypeName().toUpperCase();
        final int columnSize    = md.getColumnSize();

        switch (typeName) {
            case "BIT": {
                if (columnSize > 31) {  // jdbc will return unsigned 32 bit for BIT(32) which does not fit in Integer
                    return Optional.of(Long.class);
                }
                if (columnSize == 0 || columnSize == 1) {
                    return Optional.of(Boolean.class);
                }
                break;
            }
            case "NCHAR":
            case "NVARCHAR2": {
                return Optional.of(String.class);
            }
            case "BINARY_FLOAT": {
                return Optional.of(Float.class);
            }
            case "BINARY_DOUBLE": {
                return Optional.of(Double.class);
            }
            case "NUMBER":
            case "DECIMAL": {
                if (md.getDecimalDigits() == 0) {
                    if (columnSize <= 2) {
                        return Optional.of(Byte.class);
                    } else if (columnSize <= 4) {
                        return Optional.of(Short.class);
                    } else if (columnSize <= 9) {
                        return Optional.of(Integer.class);
                    } else if (columnSize <= 18) {
                        return Optional.of(Long.class);
                    } else {
                        return Optional.of(BigInteger.class);
                    }
                }
            }
        }

        return Optional.empty();
    };
}
