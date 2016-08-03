package com.speedment.runtime.field.finder;

import com.speedment.runtime.field.Field;
import com.speedment.runtime.manager.Manager;
import java.util.function.Function;

/**
 *
 * @param <ENTITY>     the source entity
 * @param <FK_ENTITY>  the target entity
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
public interface FindFrom<ENTITY, FK_ENTITY> extends Function<ENTITY, FK_ENTITY> {
    
    Field<ENTITY> getSourceField();
    
    Field<FK_ENTITY> getTargetField();
    
    Manager<FK_ENTITY> getTargetManager();

}
