package com.speedment.runtime.internal.field.finder;

import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.field.ReferenceField;
import com.speedment.runtime.manager.Manager;

/**
 *
 * @param <ENTITY>     the source entity
 * @param <FK_ENTITY>  the target entity
 * @param <T>          the column type
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public class FindFromReference<ENTITY, FK_ENTITY, T extends Comparable<? super T>>
    extends AbstractFindFrom<ENTITY, FK_ENTITY, ReferenceField<ENTITY, ?, T>, ReferenceField<FK_ENTITY, ?, T>> {

    public FindFromReference(ReferenceField<ENTITY, ?, T> source, ReferenceField<FK_ENTITY, ?, T> target, Manager<FK_ENTITY> manager) {
        super(source, target, manager);
    }

    @Override
    public FK_ENTITY apply(ENTITY entity) {
        final T value = getSourceField().getter().apply(entity);
        return getTargetManager().findAny(getTargetField(), value)
            .orElseThrow(() -> new SpeedmentException(
                "Error! Could not find any " + 
                getTargetManager().getEntityClass().getSimpleName() + 
                " with '" + getTargetField().identifier().columnName() + 
                "' = '" + value + "'."
            ));
    }
}
