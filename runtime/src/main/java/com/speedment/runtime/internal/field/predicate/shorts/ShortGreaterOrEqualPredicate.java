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
public final class ShortGreaterOrEqualPredicate<ENTITY, D>  extends AbstractFieldPredicate<ENTITY, HasShortValue<ENTITY, D>> implements Tuple1<Short> {
    
    private final short value;
    
    public ShortGreaterOrEqualPredicate(HasShortValue<ENTITY, D> field, short value) {
        super(PredicateType.GREATER_OR_EQUAL, field, entity -> field.getAsShort(entity) == value);
        this.value = value;
    }
    
    @Override
    public Short get0() {
        return value;
    }
}