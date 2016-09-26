package com.speedment.runtime.internal.field.finder;

import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.field.FloatField;
import com.speedment.runtime.manager.Manager;

/**
 * @param <ENTITY>    entity type
 * @param <FK_ENTITY> foreign entity type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class FindFromFloat<ENTITY, FK_ENTITY> extends AbstractFindFrom<ENTITY, FK_ENTITY, Float, FloatField<ENTITY, ?>, FloatField<FK_ENTITY, ?>> {
    
    public FindFromFloat(FloatField<ENTITY, ?> source, FloatField<FK_ENTITY, ?> target, Manager<FK_ENTITY> manager) {
        super(source, target, manager);
    }
    
    @Override
    public FK_ENTITY apply(ENTITY entity) {
        final float value = getSourceField().getter().applyAsFloat(entity);
        return getTargetManager().stream()
            .filter(getTargetField().equal(value))
            .findAny()
            .orElseThrow(() -> new SpeedmentException(
                "Error! Could not find any " + 
                getTargetManager().getEntityClass().getSimpleName() + 
                " with '" + getTargetField().identifier().columnName() + 
                "' = '" + value + "'."
            ));
    }
}