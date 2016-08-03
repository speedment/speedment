package com.speedment.runtime.internal.field.finder;

import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.finder.FindFrom;
import com.speedment.runtime.manager.Manager;
import static java.util.Objects.requireNonNull;

/**
 *
 * @param <ENTITY>     the source entity
 * @param <FK_ENTITY>  the target entity
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
abstract class AbstractFindFrom<
        ENTITY, 
        FK_ENTITY, 
        SOURCE extends Field<ENTITY>, 
        TARGET extends Field<FK_ENTITY>
    > implements FindFrom<ENTITY, FK_ENTITY> {
    
    private final SOURCE source;
    private final TARGET target;
    private final Manager<FK_ENTITY> manager;

    protected AbstractFindFrom(
            SOURCE source, 
            TARGET target, 
            Manager<FK_ENTITY> manager) {
        
        this.source  = requireNonNull(source);
        this.target  = requireNonNull(target);
        this.manager = requireNonNull(manager);
    }

    @Override
    public final SOURCE getSourceField() {
        return source;
    }

    @Override
    public final TARGET getTargetField() {
        return target;
    }

    @Override
    public final Manager<FK_ENTITY> getTargetManager() {
        return manager;
    }
}