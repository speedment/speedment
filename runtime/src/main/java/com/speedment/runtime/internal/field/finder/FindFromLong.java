package com.speedment.runtime.internal.field.finder;

import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.field.LongField;
import com.speedment.runtime.field.LongForeignKeyField;
import com.speedment.runtime.manager.Manager;
import javax.annotation.Generated;

/**
 * @param <ENTITY>    entity type
 * @param <FK_ENTITY> foreign entity type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@Generated(value = "Speedment")
public final class FindFromLong<ENTITY, FK_ENTITY> extends AbstractFindFrom<ENTITY, FK_ENTITY, Long, LongForeignKeyField<ENTITY, ?, FK_ENTITY>, LongField<FK_ENTITY, ?>> {
    
    public FindFromLong(LongForeignKeyField<ENTITY, ?, FK_ENTITY> source, LongField<FK_ENTITY, ?> target, Manager<FK_ENTITY> manager) {
        super(source, target, manager);
    }
    
    @Override
    public FK_ENTITY apply(ENTITY entity) {
        final long value = getSourceField().getter().applyAsLong(entity);
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