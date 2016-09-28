package com.speedment.runtime.internal.field.finder;

import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.field.IntField;
import com.speedment.runtime.field.IntForeignKeyField;
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
public final class FindFromInt<ENTITY, FK_ENTITY> extends AbstractFindFrom<ENTITY, FK_ENTITY, Integer, IntForeignKeyField<ENTITY, ?, FK_ENTITY>, IntField<FK_ENTITY, ?>> {
    
    public FindFromInt(IntForeignKeyField<ENTITY, ?, FK_ENTITY> source, IntField<FK_ENTITY, ?> target, Manager<FK_ENTITY> manager) {
        super(source, target, manager);
    }
    
    @Override
    public FK_ENTITY apply(ENTITY entity) {
        final int value = getSourceField().getter().applyAsInt(entity);
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