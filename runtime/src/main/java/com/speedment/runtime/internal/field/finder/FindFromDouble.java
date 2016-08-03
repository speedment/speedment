package com.speedment.runtime.internal.field.finder;

import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.field.DoubleField;
import com.speedment.runtime.manager.Manager;

/**
 * @param <ENTITY>    entity type
 * @param <FK_ENTITY> foreign entity type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class FindFromDouble<ENTITY, FK_ENTITY>  extends AbstractFindFrom<ENTITY, FK_ENTITY, DoubleField<ENTITY, ?>, DoubleField<FK_ENTITY, ?>> {
    
    public FindFromDouble(DoubleField<ENTITY, ?> source, DoubleField<FK_ENTITY, ?> target, Manager<FK_ENTITY> manager) {
        super(source, target, manager);
    }
    
    @Override
    public FK_ENTITY apply(ENTITY entity) {
        final double value = getSourceField().getter().getAsDouble(entity);
        return getTargetManager().findAny(getTargetField(), value)
            .orElseThrow(() -> new SpeedmentException(
                "Error! Could not find any " + 
                getTargetManager().getEntityClass().getSimpleName() + 
                " with '" + getTargetField().identifier().columnName() + 
                "' = '" + value + "'."
            ));
    }
}