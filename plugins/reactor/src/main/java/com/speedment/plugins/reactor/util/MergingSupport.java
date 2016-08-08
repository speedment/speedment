package com.speedment.plugins.reactor.util;

import com.speedment.common.injector.annotation.InjectorKey;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Table;
import java.lang.reflect.Type;

/**
 * Utility methods that are used by several translators in this package but that
 * doesn't nescessarily need to be shared with others.
 * 
 * @author Emil Forslund
 */
@InjectorKey(MergingSupport.class)
public interface MergingSupport {
    
    Column mergingColumn(Table table);
    
    String mergingColumnField(Table table);
    
    Type mergingColumnType(Table table);
}
