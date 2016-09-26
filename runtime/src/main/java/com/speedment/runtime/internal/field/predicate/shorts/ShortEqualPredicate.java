package com.speedment.runtime.internal.field.predicate.shorts;

import com.speedment.common.tuple.Tuple1;
import com.speedment.runtime.field.predicate.PredicateType;
import com.speedment.runtime.field.trait.HasShortValue;
import com.speedment.runtime.internal.field.predicate.AbstractFieldPredicate;

/**
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class ShortEqualPredicate<ENTITY, D> extends AbstractFieldPredicate<ENTITY, Short, HasShortValue<ENTITY, D>> implements Tuple1<Short> {
    
    private final short value;
    
    public ShortEqualPredicate(HasShortValue<ENTITY, D> field, short value) {
        super(PredicateType.EQUAL, field, entity -> field.getAsShort(entity) == value);
        this.value = value;
    }
    
    @Override
    public Short get0() {
        return value;
    }
}