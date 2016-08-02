package com.speedment.generator.internal.util;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.trait.HasNullable;
import static com.speedment.runtime.util.StaticClassUtil.instanceNotAllowed;

/**
 *
 * @author Emil Forslund
 */
public final class ColumnUtil {
    
    public static boolean usesOptional(Column col) {
        return col.isNullable() 
            && HasNullable.ImplementAs.OPTIONAL == col.getNullableImplementation();
    }
    
    /**
     * Utility classes should not be instantiated.
     */
    private ColumnUtil() {
        instanceNotAllowed(ColumnUtil.class);
    }
}
