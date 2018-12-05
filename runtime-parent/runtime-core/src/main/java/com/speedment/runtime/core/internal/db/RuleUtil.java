package com.speedment.runtime.core.internal.db;

import com.speedment.runtime.core.db.JavaTypeMap;

import java.util.Optional;

final class RuleUtil {
    /**
     * All default rules that need more than just the jdbc type name to trigger
     */
    static final JavaTypeMap.Rule DEFAULT_OSS_RULE = (sqlTypeMapping, md) -> {
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
        }

        return Optional.empty();
    };
}
