package com.speedment.runtime.internal.field.predicate.longs;

import com.speedment.common.tuple.Tuple1;
import com.speedment.runtime.field.predicate.PredicateType;
import com.speedment.runtime.field.trait.HasLongValue;
import com.speedment.runtime.internal.field.predicate.AbstractFieldPredicate;

/**
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
public final class LongGreaterOrEqualPredicate<ENTITY, D> extends AbstractFieldPredicate<ENTITY, Long, HasLongValue<ENTITY, D>> implements Tuple1<Long> {
    
    private final long value;
    
    public LongGreaterOrEqualPredicate(HasLongValue<ENTITY, D> field, long value) {
        super(PredicateType.GREATER_OR_EQUAL, field, entity -> field.getAsLong(entity) == value);
        this.value = value;
    }
    
    @Override
    public Long get0() {
        return value;
    }
}